package com.swarmbit.docker.api.client.jaxrs

import com.swarmbit.docker.api.config.DockerClientConfig
import com.swarmbit.docker.api.exception.UnsupportedConfiguration
import org.apache.log4j.Logger
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.glassfish.jersey.SslConfigurator
import java.io.IOException
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.*
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.net.ssl.SSLContext

object SSLContextFactory {
    private val LOGGER = Logger.getLogger(SSLContextFactory::class.java.name)
    private const val INTERNAL_KEYSTORE_PASS = "docker"

    fun createSSLContext(config: DockerClientConfig): SSLContext {
        return try {
            val tlsAuth: TLSAuth = TLSAuth.getTLSMode(config)
            Security.addProvider(BouncyCastleProvider())
            when (tlsAuth) {
                TLSAuth.TLS_AUTHENTICATE_CLIENT -> return createAuthClientSSLContext(config.tlsCert.orEmpty(), config.tlsKey.orEmpty())
                TLSAuth.TLS_AUTHENTICATE_SERVER -> return createAuthServerSSLContext(config.tlsCacert.orEmpty())
                TLSAuth.TLS_AUTHENTICATE_SERVER_CLIENT -> return createAuthServerClientSSLContext(config.tlsCert.orEmpty(),
                        config.tlsKey.orEmpty(),
                        config.tlsCacert.orEmpty())
                else -> SSLContext.getDefault()
            }
        } catch (e: Exception) {
            LOGGER.error("Exception creating ssl config ", e)
            throw UnsupportedConfiguration(e.message.orEmpty())
        }
    }

    @Throws(InvalidKeySpecException::class, CertificateException::class, NoSuchAlgorithmException::class, KeyStoreException::class, IOException::class)
    private fun createAuthClientSSLContext(certPath: String, keyPath: String): SSLContext {
        val sslConfig = createSSLConfigurator()
        sslConfig.keyStore(createKeyStore(certPath, keyPath))
        sslConfig.keyStorePassword(INTERNAL_KEYSTORE_PASS)
        return sslConfig.createSSLContext()
    }

    @Throws(InvalidKeySpecException::class, CertificateException::class, NoSuchAlgorithmException::class, KeyStoreException::class, IOException::class)
    private fun createAuthServerSSLContext(caCert: String): SSLContext {
        val sslConfig = createSSLConfigurator()
        sslConfig.trustStore(createTrustStore(Paths.get(caCert)))
        return sslConfig.createSSLContext()
    }

    @Throws(InvalidKeySpecException::class, CertificateException::class, NoSuchAlgorithmException::class, KeyStoreException::class, IOException::class)
    private fun createAuthServerClientSSLContext(certPath: String, keyPath: String, caCert: String): SSLContext {
        val sslConfig = createSSLConfigurator()
        sslConfig.keyStore(createKeyStore(certPath, keyPath))
        sslConfig.keyStorePassword(INTERNAL_KEYSTORE_PASS)
        sslConfig.trustStore(createTrustStore(Paths.get(caCert)))
        return sslConfig.createSSLContext()
    }

    private fun createSSLConfigurator(): SslConfigurator {
        val sslConfig = SslConfigurator.newInstance(true)
        sslConfig.securityProtocol("TLSv1.2")
        return sslConfig
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class, IOException::class, CertificateException::class, KeyStoreException::class)
    private fun createKeyStore(certPath: String, keyPath: String): KeyStore {
        val privateKey = loadPrivateKey(Paths.get(keyPath))
        val privateCertificates = loadCertificates(Paths.get(certPath))
        val keyStore = KeyStore.getInstance("JKS")
        keyStore.load(null)
        keyStore.setKeyEntry(INTERNAL_KEYSTORE_PASS,
                privateKey,
                INTERNAL_KEYSTORE_PASS.toCharArray(),
                privateCertificates.toTypedArray()
        )
        return keyStore
    }

    @Throws(IOException::class, NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun loadPrivateKey(keyPath: Path): PrivateKey? {
        if (Files.exists(keyPath)) {
            val key = String(Files.readAllBytes(keyPath))
            StringReader(key).use { reader ->
                PEMParser(reader).use { pemParser ->
                    var readObject: Any? = pemParser.readObject()
                    while (readObject != null) {
                        if (readObject is PEMKeyPair) {
                            val pemKeyPair: PEMKeyPair = readObject
                            val privateKey = guessKey(pemKeyPair.privateKeyInfo.encoded)
                            if (privateKey != null) {
                                return privateKey
                            }
                        } else if (readObject is org.bouncycastle.asn1.pkcs.PrivateKeyInfo) {
                            val privateKeyInfo: org.bouncycastle.asn1.pkcs.PrivateKeyInfo = readObject
                            val privateKey = guessKey(privateKeyInfo.encoded)
                            if (privateKey != null) {
                                return privateKey
                            }
                        }
                        readObject = pemParser.readObject()
                    }
                }
            }
        } else {
            throw UnsupportedConfiguration("Key $keyPath doesn't exist")
        }
        return null
    }

    @Throws(IOException::class, CertificateException::class)
    private fun loadCertificates(certPath: Path): List<Certificate> {
        if (Files.exists(certPath)) {
            val cert = String(Files.readAllBytes(certPath))
            StringReader(cert).use { reader ->
                PEMParser(reader).use { pemParser ->
                    val certificates: MutableList<Certificate> = ArrayList()
                    val certificateConverter: JcaX509CertificateConverter = JcaX509CertificateConverter()
                            .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    val certObj: Any = pemParser.readObject()
                    if (certObj is X509CertificateHolder) {
                        val certificateHolder: X509CertificateHolder = certObj
                        certificates.add(certificateConverter.getCertificate(certificateHolder))
                    }
                    return certificates
                }
            }
        } else {
            throw UnsupportedConfiguration("Certificate $certPath doesn't exist")
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun guessKey(encodedKey: ByteArray): PrivateKey? {
        //no way to know, so iterate
        for (guessFactory in arrayOf("RSA", "ECDSA")) {
            try {
                val factory = KeyFactory.getInstance(guessFactory)
                val privateKeySpec = PKCS8EncodedKeySpec(encodedKey)
                return factory.generatePrivate(privateKeySpec)
            } catch (ignore: InvalidKeySpecException) {
            }
        }
        return null
    }

    @Throws(IOException::class, CertificateException::class, KeyStoreException::class, NoSuchAlgorithmException::class)
    private fun createTrustStore(caCertPath: Path): KeyStore {
        if (Files.exists(caCertPath)) {
            val caCert = String(Files.readAllBytes(caCertPath))
            StringReader(caCert).use { reader ->
                PEMParser(reader).use { pemParser ->
                    val trustStore = KeyStore.getInstance("JKS")
                    trustStore.load(null)
                    var index = 1
                    var pemCert: Any?
                    while (pemParser.readObject().also { pemCert = it } != null) {
                        val caCertificate: Certificate = JcaX509CertificateConverter()
                                .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                                .getCertificate(pemCert as X509CertificateHolder?)
                        trustStore.setCertificateEntry("ca-$index", caCertificate)
                        index++
                    }
                    return trustStore
                }
            }
        } else {
            throw UnsupportedConfiguration("CA Certificate $caCertPath doesn't exist")
        }
    }
}