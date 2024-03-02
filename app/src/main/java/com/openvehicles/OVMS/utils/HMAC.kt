package com.openvehicles.OVMS.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays

//Copyright (c) 1999-2004 Brian Wellington (bwelling@xbill.org)
/**
 * A pure java implementation of the HMAC-MD5 secure hash algorithm
 *
 * @author Brian Wellington
 */
class HMAC {

    var digest: MessageDigest? = null
    private lateinit var ipad: ByteArray
    private lateinit var opad: ByteArray

    /**
     * Creates a new HMAC instance
     * @param digest The message digest object.
     * @param key The secret key
     */
    constructor(digest: MessageDigest, key: ByteArray) {
        digest.reset()
        this.digest = digest
        init(key)
    }

    /**
     * Creates a new HMAC instance
     * @param digestName The name of the message digest function.
     * @param key The secret key.
     */
    constructor(digestName: String, key: ByteArray) {
        digest = try {
            MessageDigest.getInstance(digestName)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalArgumentException(
                "unknown digest algorithm "
                        + digestName
            )
        }
        init(key)
    }

    private fun init(key: ByteArray) {
        var keyByteArray = key
        if (keyByteArray.size > PADLEN) {
            keyByteArray = digest!!.digest(keyByteArray)
            digest!!.reset()
        }
        ipad = ByteArray(PADLEN.toInt())
        opad = ByteArray(PADLEN.toInt())
        var i = 0
        while (i < keyByteArray.size) {
            ipad[i] = (keyByteArray[i].toInt() xor IPAD.toInt()).toByte()
            opad[i] = (keyByteArray[i].toInt() xor OPAD.toInt()).toByte()
            i++
        }
        while (i < PADLEN) {
            ipad[i] = IPAD
            opad[i] = OPAD
            i++
        }
        digest!!.update(ipad)
    }

    /**
     * Adds data to the current hash
     * @param data The data
     * @param offset The index at which to start adding to the hash
     * @param length The number of bytes to hash
     */
    fun update(data: ByteArray, offset: Int, length: Int) {
        digest!!.update(data, offset, length)
    }

    /**
     * Adds data to the current hash
     * @param data The data
     */
    fun update(data: ByteArray) {
        digest!!.update(data)
    }

    /**
     * Signs the data (computes the secure hash)
     * @return An array with the signature
     */
    fun sign(): ByteArray {
        val output = digest!!.digest()
        digest!!.reset()
        digest!!.update(opad)
        return digest!!.digest(output)
    }

    /**
     * Verifies the data (computes the secure hash and compares it to the input)
     * @param signature The signature to compare against
     * @return true if the signature matched, false otherwise
     */
    fun verify(signature: ByteArray?): Boolean {
        return Arrays.equals(signature, sign())
    }

    /**
     * Resets the HMAC object for further use
     */
    fun clear() {
        digest!!.reset()
        digest!!.update(ipad)
    }

    /*
     * Inner types
     */

    companion object {

        private const val IPAD: Byte = 0x36
        private const val OPAD: Byte = 0x5c
        private const val PADLEN: Byte = 64

    }
}