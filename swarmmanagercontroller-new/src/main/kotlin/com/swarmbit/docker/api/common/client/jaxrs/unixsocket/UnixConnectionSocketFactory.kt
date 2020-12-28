package com.swarmbit.docker.api.common.client.jaxrs.unixsocket

import org.apache.http.HttpHost
import org.apache.http.annotation.Contract
import org.apache.http.annotation.ThreadingBehavior
import org.apache.http.conn.ConnectTimeoutException
import org.apache.http.conn.socket.ConnectionSocketFactory
import org.apache.http.protocol.HttpContext
import org.newsclub.net.unix.AFUNIXSocketAddress
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException

/**
 * Provides a ConnectionSocketFactory for connecting Apache HTTP clients to Unix sockets.
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
class UnixConnectionSocketFactory(socketPath: String) : ConnectionSocketFactory {
    private val socketFile = File(socketPath)

    @Throws(IOException::class)
    override fun createSocket(context: HttpContext): Socket {
        return ApacheUnixSocket()
    }

    @Throws(IOException::class)
    override fun connectSocket(
        connectTimeout: Int,
        socket: Socket,
        host: HttpHost,
        remoteAddress: InetSocketAddress,
        localAddress: InetSocketAddress?,
        context: HttpContext
    ): Socket {
        try {
            socket.connect(AFUNIXSocketAddress(socketFile), connectTimeout)
        } catch (e: SocketTimeoutException) {
            throw ConnectTimeoutException(e, null, remoteAddress.address)
        }
        return socket
    }
}
