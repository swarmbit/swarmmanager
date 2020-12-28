package com.swarmbit.extensions

import java.net.URLEncoder
import java.util.Base64

fun String.urlEncode(): String = URLEncoder.encode(this, "UTF-8")

fun String.base64URLEncode(): String = Base64.getUrlEncoder().withoutPadding().encodeToString(this.toByteArray())

fun String.base64URLDecode(): String = String(Base64.getUrlDecoder().decode(this.toByteArray()))

fun String.base64Encode(): String = String(Base64.getEncoder().encode(this.toByteArray()))

fun String.base64Decode(): String = String(Base64.getDecoder().decode(this.toByteArray()))
