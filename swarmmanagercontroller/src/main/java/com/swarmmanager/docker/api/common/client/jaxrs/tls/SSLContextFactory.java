package com.swarmmanager.docker.api.common.client.jaxrs.tls;

import com.swarmmanager.docker.api.common.client.DockerWebClientProperties;
import com.swarmmanager.exception.UnsupportedConfiguration;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.glassfish.jersey.SslConfigurator;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class SSLContextFactory {

    private static final Logger LOGGER = Logger.getLogger(SSLContextFactory.class.getName());

    private static final String INTERNAL_KEYSTORE_PASS = "docker";

    private SSLContextFactory() {
    }

    public static SSLContext createSSLContext(DockerWebClientProperties properties) {
        try {
            TLSAuth tlsAuth = TLSAuth.getTLSMode(properties);
            Security.addProvider(new BouncyCastleProvider());
            switch (tlsAuth) {
                case TLS_AUTHENTICATE_CLIENT:
                    return createAuthClientSSLContext(properties.getDockerApiTlsCert().get(), properties.getDockerApiTlsKey().get());
                case TLS_AUTHENTICATE_SERVER:
                    return createAuthServerSSLContext(properties.getDockerApiTlsCacert().get());
                case TLS_AUTHENTICATE_SERVER_CLIENT:
                    return createAuthServerClientSSLContext(properties.getDockerApiTlsCert().get(),
                            properties.getDockerApiTlsKey().get(),
                            properties.getDockerApiTlsCacert().get());
            }
            return SSLContext.getDefault();
        } catch (Exception e) {
            LOGGER.error("Exception creating ssl config ", e);
            throw new UnsupportedConfiguration(e.getMessage());
        }
    }

    private static SSLContext createAuthClientSSLContext(String certPath, String keyPath) throws InvalidKeySpecException,
            CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        SslConfigurator sslConfig = createSSLConfigurator();
        sslConfig.keyStore(createKeyStore(certPath, keyPath));
        sslConfig.keyStorePassword(INTERNAL_KEYSTORE_PASS);
        return sslConfig.createSSLContext();
    }

    private static SSLContext createAuthServerSSLContext(String caCert) throws InvalidKeySpecException,
            CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        SslConfigurator sslConfig = createSSLConfigurator();
        sslConfig.trustStore(createTrustStore(Paths.get(caCert)));
        return sslConfig.createSSLContext();
    }

    private static SSLContext createAuthServerClientSSLContext(String certPath, String keyPath, String caCert) throws InvalidKeySpecException,
            CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        SslConfigurator sslConfig = createSSLConfigurator();
        sslConfig.keyStore(createKeyStore(certPath, keyPath));
        sslConfig.keyStorePassword(INTERNAL_KEYSTORE_PASS);
        sslConfig.trustStore(createTrustStore(Paths.get(caCert)));
        return sslConfig.createSSLContext();
    }

    private static SslConfigurator createSSLConfigurator() {
        SslConfigurator sslConfig = SslConfigurator.newInstance(true);
        sslConfig.securityProtocol("TLSv1.2");
        return sslConfig;
    }

    private static KeyStore createKeyStore(String certPath, String keyPath) throws NoSuchAlgorithmException,
            InvalidKeySpecException, IOException, CertificateException, KeyStoreException {
        PrivateKey privateKey = loadPrivateKey(Paths.get(keyPath));
        List<java.security.cert.Certificate> privateCertificates = loadCertificates(Paths.get(certPath));

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null);

        keyStore.setKeyEntry(INTERNAL_KEYSTORE_PASS,
                privateKey,
                INTERNAL_KEYSTORE_PASS.toCharArray(),
                privateCertificates.toArray(new java.security.cert.Certificate[privateCertificates.size()])
        );

        return keyStore;
    }

    private static PrivateKey loadPrivateKey(Path keyPath) throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        if (Files.exists(keyPath)) {

            String key = new String(Files.readAllBytes(keyPath));
            try (StringReader reader = new StringReader(key); PEMParser pemParser = new PEMParser(reader)) {
                Object readObject = pemParser.readObject();
                while (readObject != null) {
                    if (readObject instanceof PEMKeyPair) {
                        PEMKeyPair pemKeyPair = (PEMKeyPair) readObject;
                        PrivateKey privateKey = guessKey(pemKeyPair.getPrivateKeyInfo().getEncoded());
                        if (privateKey != null) {
                            return privateKey;
                        }
                    } else if (readObject instanceof PrivateKeyInfo) {
                        PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) readObject;
                        PrivateKey privateKey = guessKey(privateKeyInfo.getEncoded());
                        if (privateKey != null) {
                            return privateKey;
                        }
                    }

                    readObject = pemParser.readObject();
                }
            }
        } else {
            throw new UnsupportedConfiguration("Key " + keyPath + " doesn't exist");
        }

        return null;
    }

    private static List<java.security.cert.Certificate> loadCertificates(Path certPath) throws IOException,
            CertificateException {
        if (Files.exists(certPath)) {

            String cert = new String(Files.readAllBytes(certPath));
            try (StringReader reader = new StringReader(cert); PEMParser pemParser = new PEMParser(reader)) {
                List<java.security.cert.Certificate> certificates = new ArrayList<>();

                JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter()
                        .setProvider(BouncyCastleProvider.PROVIDER_NAME);
                Object certObj = pemParser.readObject();

                if (certObj instanceof X509CertificateHolder) {
                    X509CertificateHolder certificateHolder = (X509CertificateHolder) certObj;
                    certificates.add(certificateConverter.getCertificate(certificateHolder));
                }

                return certificates;
            }
        } else {
            throw new UnsupportedConfiguration("Certificate " + certPath + " doesn't exist");
        }
    }

    private static PrivateKey guessKey(byte[] encodedKey) throws NoSuchAlgorithmException {
        //no way to know, so iterate
        for (String guessFactory : new String[]{"RSA", "ECDSA"}) {
            try {
                KeyFactory factory = KeyFactory.getInstance(guessFactory);

                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
                return factory.generatePrivate(privateKeySpec);
            } catch (InvalidKeySpecException ignore) {
            }
        }

        return null;
    }

    private static KeyStore createTrustStore(Path caCertPath) throws IOException, CertificateException,
            KeyStoreException, NoSuchAlgorithmException {
        if (Files.exists(caCertPath)) {

            String caCert = new String(Files.readAllBytes(caCertPath));
            try (StringReader reader = new StringReader(caCert); PEMParser pemParser = new PEMParser(reader)) {

                KeyStore trustStore = KeyStore.getInstance("JKS");
                trustStore.load(null);

                int index = 1;
                Object pemCert;

                while ((pemCert = pemParser.readObject()) != null) {
                    Certificate caCertificate = new JcaX509CertificateConverter()
                            .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                            .getCertificate((X509CertificateHolder) pemCert);
                    trustStore.setCertificateEntry("ca-" + index, caCertificate);
                    index++;
                }

                return trustStore;
            }
        } else {
            throw new UnsupportedConfiguration("CA Certificate " + caCertPath + " doesn't exist");
        }
    }
}
