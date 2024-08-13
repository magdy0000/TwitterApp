package com.halan.twittercomponent.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.math.BigInteger
import java.net.URLEncoder
import java.security.SecureRandom
import java.util.Base64
import java.util.Date
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class OAuth1Interceptor(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val token: String,
    private val tokenSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val nonce = generateNonce()
        val timestamp = (Date().time / 1000).toString()

        val params = mutableMapOf<String, String>(
            "oauth_consumer_key" to consumerKey,
            "oauth_token" to token,
            "oauth_signature_method" to "HMAC-SHA1",
            "oauth_timestamp" to timestamp,
            "oauth_nonce" to nonce,
            "oauth_version" to "1.0"
        )
        val signatureBaseString = generateSignatureBaseString(
            originalRequest.method,
            originalRequest.url.toString(),
            params
        )

        val signingKey = generateSigningKey(consumerSecret, tokenSecret)

        val signature = generateSignature(signatureBaseString, signingKey)

        val authorizationHeader = generateOAuthHeader(
            consumerKey,
            token,
            signature,
            timestamp,
            nonce
        )

        val requestWithAuth = originalRequest.newBuilder()
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization", authorizationHeader)
            .build()

        return chain.proceed(requestWithAuth)
    }

    private fun generateNonce(): String {
        val random = SecureRandom()
        return BigInteger(130, random).toString(32)
    }
    fun generateOAuthHeader(
        consumerKey: String,
        token: String,
        signature: String,
        timestamp: String,
        nonce: String,
        version: String = "1.0",
        signatureMethod: String = "HMAC-SHA1"
    ): String {
        return "OAuth " +
                "oauth_consumer_key=\"${encode(consumerKey)}\", " +
                "oauth_token=\"${encode(token)}\", " +
                "oauth_signature_method=\"$signatureMethod\", " +
                "oauth_timestamp=\"$timestamp\", " +
                "oauth_nonce=\"$nonce\", " +
                "oauth_version=\"$version\", " +
                "oauth_signature=\"${encode(signature)}\""
    }

    fun encode(value: String): String {
        return URLEncoder.encode(value, "UTF-8")
            .replace("+", "%20")
            .replace("*", "%2A")
            .replace("%7E", "~")
    }
    fun generateSignatureBaseString(
        method: String,
        url: String,
        params: Map<String, String>
    ): String {
        val encodedParams = params.entries
            .sortedBy { it.key }
            .joinToString("&") { "${encode(it.key)}=${encode(it.value)}" }

        val baseString = "$method&${encode(url)}&${encode(encodedParams)}"
        return baseString
    }
    fun generateSignature(baseString: String, signingKey: String): String {
        val mac = Mac.getInstance("HmacSHA1")
        val keySpec = SecretKeySpec(signingKey.toByteArray(Charsets.UTF_8), "HmacSHA1")
        mac.init(keySpec)
        val rawSignature = mac.doFinal(baseString.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(rawSignature)
    }
    fun generateSigningKey(consumerSecret: String, tokenSecret: String): String {
        return "${encode(consumerSecret)}&${encode(tokenSecret)}"
    }

}