package com.swarmbit.docker.api.common.client.jaxrs.unixsocket

/*
 * Copyright (c) 2014 Spotify AB.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import org.newsclub.net.unix.AFUNIXSocket
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketException
import java.nio.channels.SocketChannel
import java.util.ArrayDeque
import java.util.Queue

/**
 * Provides a socket that wraps an org.newsclub.net.unix.AFUNIXSocket and delays setting options until the socket is connected. This is
 * necessary because the Apache HTTP client attempts to set options prior to connecting the socket, which doesn't work for Unix sockets
 * since options are being set on the underlying file descriptor. Until the socket is connected, the file descriptor doesn't exist.
 *
 * This class also noop's any calls to setReuseAddress, which is called by the Apache client but isn't supported by AFUnixSocket.
 */
class ApacheUnixSocket : Socket() {
    private val inner = AFUNIXSocket.newInstance()
    private val optionsToSet: Queue<SocketOptionSetter> = ArrayDeque()

    @Throws(IOException::class)
    override fun connect(endpoint: SocketAddress) {
        inner.connect(endpoint)
        setAllSocketOptions()
    }

    @Throws(IOException::class)
    override fun connect(endpoint: SocketAddress, timeout: Int) {
        inner.connect(endpoint, timeout)
        setAllSocketOptions()
    }

    @Throws(IOException::class)
    override fun bind(bindpoint: SocketAddress) {
        inner.bind(bindpoint)
        setAllSocketOptions()
    }

    override fun getInetAddress(): InetAddress {
        return inner.inetAddress
    }

    override fun getLocalAddress(): InetAddress {
        return inner.localAddress
    }

    override fun getPort(): Int {
        return inner.port
    }

    override fun getLocalPort(): Int {
        return inner.localPort
    }

    override fun getRemoteSocketAddress(): SocketAddress {
        return inner.remoteSocketAddress
    }

    override fun getLocalSocketAddress(): SocketAddress? {
        return inner.localSocketAddress
    }

    override fun getChannel(): SocketChannel {
        return inner.channel
    }

    @Throws(IOException::class)
    override fun getInputStream(): InputStream {
        return inner.inputStream
    }

    @Throws(IOException::class)
    override fun getOutputStream(): OutputStream {
        return inner.outputStream
    }

    @Throws(SocketException::class)
    private fun setSocketOption(s: SocketOptionSetter) {
        if (inner.isConnected) {
            s.run()
        } else {
            if (!optionsToSet.offer(s)) {
                throw SocketException("Failed to queue option")
            }
        }
    }

    @Throws(SocketException::class)
    private fun setAllSocketOptions() {
        for (s in optionsToSet) {
            s.run()
        }
    }

    @Throws(SocketException::class)
    override fun setTcpNoDelay(on: Boolean) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.tcpNoDelay = on
                }
            }
        )
    }

    @Throws(SocketException::class)
    override fun getTcpNoDelay(): Boolean {
        return inner.tcpNoDelay
    }

    @Throws(SocketException::class)
    override fun setSoLinger(on: Boolean, linger: Int) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.setSoLinger(on, linger)
                }
            }
        )
    }

    @Throws(SocketException::class)
    override fun getSoLinger(): Int {
        return inner.soLinger
    }

    @Throws(IOException::class)
    override fun sendUrgentData(data: Int) {
        inner.sendUrgentData(data)
    }

    @Throws(SocketException::class)
    override fun setOOBInline(on: Boolean) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.oobInline = on
                }
            }
        )
    }

    @Throws(SocketException::class)
    override fun getOOBInline(): Boolean {
        return inner.oobInline
    }

    @Synchronized
    @Throws(SocketException::class)
    override fun setSoTimeout(timeout: Int) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.soTimeout = timeout
                }
            }
        )
    }

    @Synchronized
    @Throws(SocketException::class)
    override fun getSoTimeout(): Int {
        return inner.soTimeout
    }

    @Synchronized
    @Throws(SocketException::class)
    override fun setSendBufferSize(size: Int) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.sendBufferSize = size
                }
            }
        )
    }

    @Synchronized
    @Throws(SocketException::class)
    override fun getSendBufferSize(): Int {
        return inner.sendBufferSize
    }

    @Synchronized
    @Throws(SocketException::class)
    override fun setReceiveBufferSize(size: Int) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.receiveBufferSize = size
                }
            }
        )
    }

    @Synchronized
    @Throws(SocketException::class)
    override fun getReceiveBufferSize(): Int {
        return inner.receiveBufferSize
    }

    @Throws(SocketException::class)
    override fun setKeepAlive(on: Boolean) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.keepAlive = on
                }
            }
        )
    }

    @Throws(SocketException::class)
    override fun getKeepAlive(): Boolean {
        return inner.keepAlive
    }

    @Throws(SocketException::class)
    override fun setTrafficClass(tc: Int) {
        setSocketOption(
            object : SocketOptionSetter {
                @Throws(SocketException::class)
                override fun run() {
                    inner.trafficClass = tc
                }
            }
        )
    }

    @Throws(SocketException::class)
    override fun getTrafficClass(): Int {
        return inner.trafficClass
    }

    @Throws(SocketException::class)
    override fun setReuseAddress(on: Boolean) {
        // not supported: Apache client tries to set it, but we want to just ignore it
    }

    @Throws(SocketException::class)
    override fun getReuseAddress(): Boolean {
        return inner.reuseAddress
    }

    @Synchronized
    @Throws(IOException::class)
    override fun close() {
        inner.close()
    }

    @Throws(IOException::class)
    override fun shutdownInput() {
        inner.shutdownInput()
    }

    @Throws(IOException::class)
    override fun shutdownOutput() {
        inner.shutdownOutput()
    }

    override fun toString(): String {
        return inner.toString()
    }

    override fun isConnected(): Boolean {
        return inner.isConnected
    }

    override fun isBound(): Boolean {
        return inner.isBound
    }

    override fun isClosed(): Boolean {
        return inner.isClosed
    }

    override fun isInputShutdown(): Boolean {
        return inner.isInputShutdown
    }

    override fun isOutputShutdown(): Boolean {
        return inner.isOutputShutdown
    }

    override fun setPerformancePreferences(connectionTime: Int, latency: Int, bandwidth: Int) {
        inner.setPerformancePreferences(connectionTime, latency, bandwidth)
    }

    internal interface SocketOptionSetter {
        @Throws(SocketException::class)
        fun run()
    }
}
