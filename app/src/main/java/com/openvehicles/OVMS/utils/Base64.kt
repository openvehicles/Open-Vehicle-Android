package com.openvehicles.OVMS.utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FilterInputStream
import java.io.FilterOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.ObjectStreamClass
import java.io.Serializable
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 *
 * Encodes and decodes to and from Base64 notation.
 *
 * Homepage: [http://iharder.net/base64](http://iharder.net/base64).
 *
 *
 * Example:
 *
 * `String encoded = Base64.encode( myByteArray );`
 * <br></br>
 * `byte[] myByteArray = Base64.decode( encoded );`
 *
 *
 * The <tt>options</tt> parameter, which appears in a few places, is used to pass
 * several pieces of information to the encoder. In the "higher level" methods such as
 * encodeBytes( bytes, options ) the options parameter can be used to indicate such
 * things as first gzipping the bytes before encoding them, not inserting linefeeds,
 * and encoding using the URL-safe and Ordered dialects.
 *
 *
 * Note, according to [RFC3548](http://www.faqs.org/rfcs/rfc3548.html),
 * Section 2.1, implementations should not add line feeds unless explicitly told
 * to do so. I've got Base64 set to this behavior now, although earlier versions
 * broke lines by default.
 *
 *
 * The constants defined in Base64 can be OR-ed together to combine options, so you
 * might make a call like this:
 *
 * `String encoded = Base64.encodeBytes( mybytes, Base64.GZIP | Base64.DO_BREAK_LINES );`
 *
 * to compress the data before encoding it and then making the output have newline characters.
 *
 * Also...
 * `String encoded = Base64.encodeBytes( crazyString.getBytes() );`
 *
 *
 *
 *
 *
 * Change Log:
 *
 *
 *  * v2.3.7 - Fixed subtle bug when base 64 input stream contained the
 * value 01111111, which is an invalid base 64 character but should not
 * throw an ArrayIndexOutOfBoundsException either. Led to discovery of
 * mishandling (or potential for better handling) of other bad input
 * characters. You should now get an IOException if you try decoding
 * something that has bad characters in it.
 *  * v2.3.6 - Fixed bug when breaking lines and the final byte of the encoded
 * string ended in the last column; the buffer was not properly shrunk and
 * contained an extra (null) byte that made it into the string.
 *  * v2.3.5 - Fixed bug in [.encodeFromFile] where estimated buffer size
 * was wrong for files of size 31, 34, and 37 bytes.
 *  * v2.3.4 - Fixed bug when working with gzipped streams whereby flushing
 * the Base64.OutputStream closed the Base64 encoding (by padding with equals
 * signs) too soon. Also added an option to suppress the automatic decoding
 * of gzipped streams. Also added experimental support for specifying a
 * class loader when using the
 * [.decodeToObject]
 * method.
 *  * v2.3.3 - Changed default char encoding to US-ASCII which reduces the internal Java
 * footprint with its CharEncoders and so forth. Fixed some javadocs that were
 * inconsistent. Removed imports and specified things like java.io.IOException
 * explicitly inline.
 *  * v2.3.2 - Reduced memory footprint! Finally refined the "guessing" of how big the
 * final encoded data will be so that the code doesn't have to create two output
 * arrays: an oversized initial one and then a final, exact-sized one. Big win
 * when using the [.encodeBytesToBytes] family of methods (and not
 * using the gzip options which uses a different mechanism with streams and stuff).
 *  * v2.3.1 - Added [.encodeBytesToBytes] and some
 * similar helper methods to be more efficient with memory by not returning a
 * String but just a byte array.
 *  * v2.3 - **This is not a drop-in replacement!** This is two years of comments
 * and bug fixes queued up and finally executed. Thanks to everyone who sent
 * me stuff, and I'm sorry I wasn't able to distribute your fixes to everyone else.
 * Much bad coding was cleaned up including throwing exceptions where necessary
 * instead of returning null values or something similar. Here are some changes
 * that may affect you:
 *
 *  * *Does not break lines, by default.* This is to keep in compliance with
 * [RFC3548](http://www.faqs.org/rfcs/rfc3548.html).
 *  * *Throws exceptions instead of returning null values.* Because some operations
 * (especially those that may permit the GZIP option) use IO streams, there
 * is a possiblity of an java.io.IOException being thrown. After some discussion and
 * thought, I've changed the behavior of the methods to throw java.io.IOExceptions
 * rather than return null if ever there's an error. I think this is more
 * appropriate, though it will require some changes to your code. Sorry,
 * it should have been done this way to begin with.
 *  * *Removed all references to System.out, System.err, and the like.*
 * Shame on me. All I can say is sorry they were ever there.
 *  * *Throws NullPointerExceptions and IllegalArgumentExceptions* as needed
 * such as when passed arrays are null or offsets are invalid.
 *  * Cleaned up as much javadoc as I could to avoid any javadoc warnings.
 * This was especially annoying before for people who were thorough in their
 * own projects and then had gobs of javadoc warnings on this file.
 *
 *  * v2.2.1 - Fixed bug using URL_SAFE and ORDERED encodings. Fixed bug
 * when using very small files (~&lt; 40 bytes).
 *  * v2.2 - Added some helper methods for encoding/decoding directly from
 * one file to the next. Also added a main() method to support command line
 * encoding/decoding from one file to the next. Also added these Base64 dialects:
 *
 *  1. The default is RFC3548 format.
 *  1. Calling Base64.setFormat(Base64.BASE64_FORMAT.URLSAFE_FORMAT) generates
 * URL and file name friendly format as described in Section 4 of RFC3548.
 * http://www.faqs.org/rfcs/rfc3548.html
 *  1. Calling Base64.setFormat(Base64.BASE64_FORMAT.ORDERED_FORMAT) generates
 * URL and file name friendly format that preserves lexical ordering as described
 * in http://www.faqs.org/qa/rfcc-1940.html
 *
 * Special thanks to Jim Kellerman at [http://www.powerset.com/](http://www.powerset.com/)
 * for contributing the new Base64 dialects.
 *
 *
 *  * v2.1 - Cleaned up javadoc comments and unused variables and methods. Added
 * some convenience methods for reading and writing to and from files.
 *  * v2.0.2 - Now specifies UTF-8 encoding in places where the code fails on systems
 * with other encodings (like EBCDIC).
 *  * v2.0.1 - Fixed an error when decoding a single byte, that is, when the
 * encoded data was a single byte.
 *  * v2.0 - I got rid of methods that used booleans to set options.
 * Now everything is more consolidated and cleaner. The code now detects
 * when data that's being decoded is gzip-compressed and will decompress it
 * automatically. Generally things are cleaner. You'll probably have to
 * change some method calls that you were making to support the new
 * options format (<tt>int</tt>s that you "OR" together).
 *  * v1.5.1 - Fixed bug when decompressing and decoding to a
 * byte[] using <tt>decode( String s, boolean gzipCompressed )</tt>.
 * Added the ability to "suspend" encoding in the Output Stream so
 * you can turn on and off the encoding if you need to embed base64
 * data in an otherwise "normal" stream (like an XML file).
 *  * v1.5 - Output stream pases on flush() command but doesn't do anything itself.
 * This helps when using GZIP streams.
 * Added the ability to GZip-compress objects before encoding them.
 *  * v1.4 - Added helper methods to read/write files.
 *  * v1.3.6 - Fixed OutputStream.flush() so that 'position' is reset.
 *  * v1.3.5 - Added flag to turn on and off line breaks. Fixed bug in input stream
 * where last buffer being read, if not completely full, was not returned.
 *  * v1.3.4 - Fixed when "improperly padded stream" error was thrown at the wrong time.
 *  * v1.3.3 - Fixed I/O streams which were totally messed up.
 *
 *
 *
 *
 * I am placing this code in the Public Domain. Do with it as you will.
 * This software comes with no guarantees or warranties but with
 * plenty of well-wishing instead!
 * Please visit [http://iharder.net/base64](http://iharder.net/base64)
 * periodically to check for updates or to contribute improvements.
 *
 *
 * @author Robert Harder
 * @author rob@iharder.net
 * @version 2.3.7
 */
object Base64 {

    /* ********  P U B L I C   F I E L D S  ******** */

    /** No options specified. Value is zero.  */
    const val NO_OPTIONS = 0

    /** Specify encoding in first bit. Value is one.  */
    const val ENCODE = 1

    /** Specify decoding in first bit. Value is zero.  */
    const val DECODE = 0

    /** Specify that data should be gzip-compressed in second bit. Value is two.  */
    const val GZIP = 2

    /** Specify that gzipped data should *not* be automatically gunzipped.  */
    const val DONT_GUNZIP = 4

    /** Do break lines when encoding. Value is 8.  */
    const val DO_BREAK_LINES = 8

    /**
     * Encode using Base64-like encoding that is URL- and Filename-safe as described
     * in Section 4 of RFC3548:
     * [http://www.faqs.org/rfcs/rfc3548.html](http://www.faqs.org/rfcs/rfc3548.html).
     * It is important to note that data encoded this way is *not* officially valid Base64,
     * or at the very least should not be called Base64 without also specifying that is
     * was encoded using the URL- and Filename-safe dialect.
     */
    const val URL_SAFE = 16

    /**
     * Encode using the special "ordered" dialect of Base64 described here:
     * [http://www.faqs.org/qa/rfcc-1940.html](http://www.faqs.org/qa/rfcc-1940.html).
     */
    const val ORDERED = 32

    /* ********  P R I V A T E   F I E L D S  ******** */

    /** Maximum line length (76) of Base64 output.  */
    private const val MAX_LINE_LENGTH = 76

    /** The equals sign (=) as a byte.  */
    private const val EQUALS_SIGN = '='.code.toByte()

    /** The new line character (\n) as a byte.  */
    private const val NEW_LINE = '\n'.code.toByte()

    /** Preferred encoding.  */
    private const val PREFERRED_ENCODING = "US-ASCII"
    private const val WHITE_SPACE_ENC: Byte = -5 // Indicates white space in encoding
    private const val EQUALS_SIGN_ENC: Byte = -1 // Indicates equals sign in encoding

    /* ********  S T A N D A R D   B A S E 6 4   A L P H A B E T  ******** */

    /** The 64 valid Base64 values.  */ /* Host platform me be something funny like EBCDIC, so we hardcode these values. */
    private val _STANDARD_ALPHABET = byteArrayOf(
        'A'.code.toByte(),
        'B'.code.toByte(),
        'C'.code.toByte(),
        'D'.code.toByte(),
        'E'.code.toByte(),
        'F'.code.toByte(),
        'G'.code.toByte(),
        'H'.code.toByte(),
        'I'.code.toByte(),
        'J'.code.toByte(),
        'K'.code.toByte(),
        'L'.code.toByte(),
        'M'.code.toByte(),
        'N'.code.toByte(),
        'O'.code.toByte(),
        'P'.code.toByte(),
        'Q'.code.toByte(),
        'R'.code.toByte(),
        'S'.code.toByte(),
        'T'.code.toByte(),
        'U'.code.toByte(),
        'V'.code.toByte(),
        'W'.code.toByte(),
        'X'.code.toByte(),
        'Y'.code.toByte(),
        'Z'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte(),
        'g'.code.toByte(),
        'h'.code.toByte(),
        'i'.code.toByte(),
        'j'.code.toByte(),
        'k'.code.toByte(),
        'l'.code.toByte(),
        'm'.code.toByte(),
        'n'.code.toByte(),
        'o'.code.toByte(),
        'p'.code.toByte(),
        'q'.code.toByte(),
        'r'.code.toByte(),
        's'.code.toByte(),
        't'.code.toByte(),
        'u'.code.toByte(),
        'v'.code.toByte(),
        'w'.code.toByte(),
        'x'.code.toByte(),
        'y'.code.toByte(),
        'z'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        '+'.code.toByte(),
        '/'.code.toByte()
    )

    /**
     * Translates a Base64 value to either its 6-bit reconstruction value
     * or a negative number indicating some other meaning.
     */
    private val _STANDARD_DECODABET = byteArrayOf(
        -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal  0 -  8
        -5, -5,  // Whitespace: Tab and Linefeed
        -9, -9,  // Decimal 11 - 12
        -5,  // Whitespace: Carriage Return
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 14 - 26
        -9, -9, -9, -9, -9,  // Decimal 27 - 31
        -5,  // Whitespace: Space
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 33 - 42
        62,  // Plus sign at decimal 43
        -9, -9, -9,  // Decimal 44 - 46
        63,  // Slash at decimal 47
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61,  // Numbers zero through nine
        -9, -9, -9,  // Decimal 58 - 60
        -1,  // Equals sign at decimal 61
        -9, -9, -9,  // Decimal 62 - 64
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,  // Letters 'A' through 'N'
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,  // Letters 'O' through 'Z'
        -9, -9, -9, -9, -9, -9,  // Decimal 91 - 96
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,  // Letters 'a' through 'm'
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,  // Letters 'n' through 'z'
        -9, -9, -9, -9, -9 // Decimal 123 - 127
        , -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 128 - 139
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 140 - 152
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 153 - 165
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 166 - 178
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 179 - 191
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 192 - 204
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 205 - 217
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 218 - 230
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 231 - 243
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 // Decimal 244 - 255 
    )

    /* ********  U R L   S A F E   B A S E 6 4   A L P H A B E T  ******** */

    /**
     * Used in the URL- and Filename-safe dialect described in Section 4 of RFC3548:
     * [http://www.faqs.org/rfcs/rfc3548.html](http://www.faqs.org/rfcs/rfc3548.html).
     * Notice that the last two bytes become "hyphen" and "underscore" instead of "plus" and "slash."
     */
    private val _URL_SAFE_ALPHABET = byteArrayOf(
        'A'.code.toByte(),
        'B'.code.toByte(),
        'C'.code.toByte(),
        'D'.code.toByte(),
        'E'.code.toByte(),
        'F'.code.toByte(),
        'G'.code.toByte(),
        'H'.code.toByte(),
        'I'.code.toByte(),
        'J'.code.toByte(),
        'K'.code.toByte(),
        'L'.code.toByte(),
        'M'.code.toByte(),
        'N'.code.toByte(),
        'O'.code.toByte(),
        'P'.code.toByte(),
        'Q'.code.toByte(),
        'R'.code.toByte(),
        'S'.code.toByte(),
        'T'.code.toByte(),
        'U'.code.toByte(),
        'V'.code.toByte(),
        'W'.code.toByte(),
        'X'.code.toByte(),
        'Y'.code.toByte(),
        'Z'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte(),
        'g'.code.toByte(),
        'h'.code.toByte(),
        'i'.code.toByte(),
        'j'.code.toByte(),
        'k'.code.toByte(),
        'l'.code.toByte(),
        'm'.code.toByte(),
        'n'.code.toByte(),
        'o'.code.toByte(),
        'p'.code.toByte(),
        'q'.code.toByte(),
        'r'.code.toByte(),
        's'.code.toByte(),
        't'.code.toByte(),
        'u'.code.toByte(),
        'v'.code.toByte(),
        'w'.code.toByte(),
        'x'.code.toByte(),
        'y'.code.toByte(),
        'z'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        '-'.code.toByte(),
        '_'.code.toByte()
    )

    /**
     * Used in decoding URL- and Filename-safe dialects of Base64.
     */
    private val _URL_SAFE_DECODABET = byteArrayOf(
        -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal  0 -  8
        -5, -5,  // Whitespace: Tab and Linefeed
        -9, -9,  // Decimal 11 - 12
        -5,  // Whitespace: Carriage Return
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 14 - 26
        -9, -9, -9, -9, -9,  // Decimal 27 - 31
        -5,  // Whitespace: Space
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 33 - 42
        -9,  // Plus sign at decimal 43
        -9,  // Decimal 44
        62,  // Minus sign at decimal 45
        -9,  // Decimal 46
        -9,  // Slash at decimal 47
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61,  // Numbers zero through nine
        -9, -9, -9,  // Decimal 58 - 60
        -1,  // Equals sign at decimal 61
        -9, -9, -9,  // Decimal 62 - 64
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,  // Letters 'A' through 'N'
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,  // Letters 'O' through 'Z'
        -9, -9, -9, -9,  // Decimal 91 - 94
        63,  // Underscore at decimal 95
        -9,  // Decimal 96
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,  // Letters 'a' through 'm'
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,  // Letters 'n' through 'z'
        -9, -9, -9, -9, -9 // Decimal 123 - 127
        , -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 128 - 139
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 140 - 152
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 153 - 165
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 166 - 178
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 179 - 191
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 192 - 204
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 205 - 217
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 218 - 230
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 231 - 243
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 // Decimal 244 - 255 
    )

    /* ********  O R D E R E D   B A S E 6 4   A L P H A B E T  ******** */

    /**
     * I don't get the point of this technique, but someone requested it,
     * and it is described here:
     * [http://www.faqs.org/qa/rfcc-1940.html](http://www.faqs.org/qa/rfcc-1940.html).
     */
    private val _ORDERED_ALPHABET = byteArrayOf(
        '-'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        'A'.code.toByte(),
        'B'.code.toByte(),
        'C'.code.toByte(),
        'D'.code.toByte(),
        'E'.code.toByte(),
        'F'.code.toByte(),
        'G'.code.toByte(),
        'H'.code.toByte(),
        'I'.code.toByte(),
        'J'.code.toByte(),
        'K'.code.toByte(),
        'L'.code.toByte(),
        'M'.code.toByte(),
        'N'.code.toByte(),
        'O'.code.toByte(),
        'P'.code.toByte(),
        'Q'.code.toByte(),
        'R'.code.toByte(),
        'S'.code.toByte(),
        'T'.code.toByte(),
        'U'.code.toByte(),
        'V'.code.toByte(),
        'W'.code.toByte(),
        'X'.code.toByte(),
        'Y'.code.toByte(),
        'Z'.code.toByte(),
        '_'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte(),
        'g'.code.toByte(),
        'h'.code.toByte(),
        'i'.code.toByte(),
        'j'.code.toByte(),
        'k'.code.toByte(),
        'l'.code.toByte(),
        'm'.code.toByte(),
        'n'.code.toByte(),
        'o'.code.toByte(),
        'p'.code.toByte(),
        'q'.code.toByte(),
        'r'.code.toByte(),
        's'.code.toByte(),
        't'.code.toByte(),
        'u'.code.toByte(),
        'v'.code.toByte(),
        'w'.code.toByte(),
        'x'.code.toByte(),
        'y'.code.toByte(),
        'z'.code.toByte()
    )

    /**
     * Used in decoding the "ordered" dialect of Base64.
     */
    private val _ORDERED_DECODABET = byteArrayOf(
        -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal  0 -  8
        -5, -5,  // Whitespace: Tab and Linefeed
        -9, -9,  // Decimal 11 - 12
        -5,  // Whitespace: Carriage Return
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 14 - 26
        -9, -9, -9, -9, -9,  // Decimal 27 - 31
        -5,  // Whitespace: Space
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 33 - 42
        -9,  // Plus sign at decimal 43
        -9,  // Decimal 44
        0,  // Minus sign at decimal 45
        -9,  // Decimal 46
        -9,  // Slash at decimal 47
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10,  // Numbers zero through nine
        -9, -9, -9,  // Decimal 58 - 60
        -1,  // Equals sign at decimal 61
        -9, -9, -9,  // Decimal 62 - 64
        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,  // Letters 'A' through 'M'
        24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,  // Letters 'N' through 'Z'
        -9, -9, -9, -9,  // Decimal 91 - 94
        37,  // Underscore at decimal 95
        -9,  // Decimal 96
        38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,  // Letters 'a' through 'm'
        51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,  // Letters 'n' through 'z'
        -9, -9, -9, -9, -9 // Decimal 123 - 127
        , -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 128 - 139
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 140 - 152
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 153 - 165
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 166 - 178
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 179 - 191
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 192 - 204
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 205 - 217
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 218 - 230
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 231 - 243
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9 // Decimal 244 - 255 
    )

    /* ********  D E T E R M I N E   W H I C H   A L H A B E T  ******** */

    /**
     * Returns one of the _SOMETHING_ALPHABET byte arrays depending on
     * the options specified.
     * It's possible, though silly, to specify ORDERED **and** URLSAFE
     * in which case one of them will be picked, though there is
     * no guarantee as to which one will be picked.
     */
    private fun getAlphabet(options: Int): ByteArray {
        return if (options and URL_SAFE == URL_SAFE) {
            _URL_SAFE_ALPHABET
        } else if (options and ORDERED == ORDERED) {
            _ORDERED_ALPHABET
        } else {
            _STANDARD_ALPHABET
        }
    }

    /**
     * Returns one of the _SOMETHING_DECODABET byte arrays depending on
     * the options specified.
     * It's possible, though silly, to specify ORDERED and URL_SAFE
     * in which case one of them will be picked, though there is
     * no guarantee as to which one will be picked.
     */
    private fun getDecodabet(options: Int): ByteArray {
        return if (options and URL_SAFE == URL_SAFE) {
            _URL_SAFE_DECODABET
        } else if (options and ORDERED == ORDERED) {
            _ORDERED_DECODABET
        } else {
            _STANDARD_DECODABET
        }
    }

    /* ********  E N C O D I N G   M E T H O D S  ******** */

    /**
     * Encodes up to the first three bytes of array <var>threeBytes</var>
     * and returns a four-byte array in Base64 notation.
     * The actual number of significant bytes in your array is
     * given by <var>numSigBytes</var>.
     * The array <var>threeBytes</var> needs only be as big as
     * <var>numSigBytes</var>.
     * Code can reuse a byte array by passing a four-byte array as <var>b4</var>.
     *
     * @param b4 A reusable byte array to reduce array instantiation
     * @param threeBytes the array to convert
     * @param numSigBytes the number of significant bytes in your array
     * @return four byte array in Base64 notation.
     * @since 1.5.1
     */
    private fun encode3to4(
        b4: ByteArray,
        threeBytes: ByteArray?,
        numSigBytes: Int,
        options: Int
    ): ByteArray {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0, options)
        return b4
    }

    /**
     *
     * Encodes up to three bytes of the array <var>source</var>
     * and writes the resulting four Base64 bytes to <var>destination</var>.
     * The source and destination arrays can be manipulated
     * anywhere along their length by specifying
     * <var>srcOffset</var> and <var>destOffset</var>.
     * This method does not check to make sure your arrays
     * are large enough to accomodate <var>srcOffset</var> + 3 for
     * the <var>source</var> array or <var>destOffset</var> + 4 for
     * the <var>destination</var> array.
     * The actual number of significant bytes in your array is
     * given by <var>numSigBytes</var>.
     *
     * This is the lowest level of the encoding methods with
     * all possible parameters.
     *
     * @param source the array to convert
     * @param srcOffset the index where conversion begins
     * @param numSigBytes the number of significant bytes in your array
     * @param destination the array to hold the conversion
     * @param destOffset the index where output will be put
     * @return the <var>destination</var> array
     * @since 1.3
     */
    private fun encode3to4(
        source: ByteArray?, srcOffset: Int, numSigBytes: Int,
        destination: ByteArray, destOffset: Int, options: Int
    ): ByteArray {
        val ALPHABET = getAlphabet(options)

        //           1         2         3  
        // 01234567890123456789012345678901 Bit position
        // --------000000001111111122222222 Array position from threeBytes
        // --------|    ||    ||    ||    | Six bit groups to index ALPHABET
        //          >>18  >>12  >> 6  >> 0  Right shift necessary
        //                0x3f  0x3f  0x3f  Additional AND

        // Create buffer with zero-padding if there are only one or two
        // significant bytes passed in the array.
        // We have to shift left 24 in order to flush out the 1's that appear
        // when Java treats a value as negative that is cast from a byte to an int.
        val inBuff = ((if (numSigBytes > 0) source!![srcOffset].toInt() shl 24 ushr 8 else 0)
                or (if (numSigBytes > 1) source!![srcOffset + 1].toInt() shl 24 ushr 16 else 0)
                or if (numSigBytes > 2) source!![srcOffset + 2].toInt() shl 24 ushr 24 else 0)
        return when (numSigBytes) {
            3 -> {
                destination[destOffset] = ALPHABET[inBuff ushr 18]
                destination[destOffset + 1] = ALPHABET[inBuff ushr 12 and 0x3f]
                destination[destOffset + 2] = ALPHABET[inBuff ushr 6 and 0x3f]
                destination[destOffset + 3] = ALPHABET[inBuff and 0x3f]
                destination
            }

            2 -> {
                destination[destOffset] = ALPHABET[inBuff ushr 18]
                destination[destOffset + 1] = ALPHABET[inBuff ushr 12 and 0x3f]
                destination[destOffset + 2] = ALPHABET[inBuff ushr 6 and 0x3f]
                destination[destOffset + 3] = EQUALS_SIGN
                destination
            }

            1 -> {
                destination[destOffset] = ALPHABET[inBuff ushr 18]
                destination[destOffset + 1] = ALPHABET[inBuff ushr 12 and 0x3f]
                destination[destOffset + 2] = EQUALS_SIGN
                destination[destOffset + 3] = EQUALS_SIGN
                destination
            }

            else -> destination
        }
    }

    /**
     * Performs Base64 encoding on the `raw` ByteBuffer,
     * writing it to the `encoded` ByteBuffer.
     * This is an experimental feature. Currently it does not
     * pass along any options (such as [.DO_BREAK_LINES]
     * or [.GZIP].
     *
     * @param raw input buffer
     * @param encoded output buffer
     * @since 2.3
     */
    fun encode(raw: ByteBuffer, encoded: ByteBuffer) {
        val raw3 = ByteArray(3)
        val enc4 = ByteArray(4)
        while (raw.hasRemaining()) {
            val rem = Math.min(3, raw.remaining())
            raw[raw3, 0, rem]
            encode3to4(enc4, raw3, rem, NO_OPTIONS)
            encoded.put(enc4)
        }
    }

    /**
     * Performs Base64 encoding on the `raw` ByteBuffer,
     * writing it to the `encoded` CharBuffer.
     * This is an experimental feature. Currently it does not
     * pass along any options (such as [.DO_BREAK_LINES]
     * or [.GZIP].
     *
     * @param raw input buffer
     * @param encoded output buffer
     * @since 2.3
     */
    fun encode(raw: ByteBuffer, encoded: CharBuffer) {
        val raw3 = ByteArray(3)
        val enc4 = ByteArray(4)
        while (raw.hasRemaining()) {
            val rem = Math.min(3, raw.remaining())
            raw[raw3, 0, rem]
            encode3to4(enc4, raw3, rem, NO_OPTIONS)
            for (i in 0..3) {
                encoded.put((enc4[i].toInt() and 0xFF).toChar())
            }
        }
    }

    /**
     * Serializes an object and returns the Base64-encoded
     * version of that serialized object.
     *
     *
     * As of v 2.3, if the object
     * cannot be serialized or there is another error,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned a null value, but
     * in retrospect that's a pretty poor way to handle it.
     *
     * The object is not GZip-compressed before being encoded.
     *
     *
     * Example options:<pre>
     * GZIP: gzip-compresses object before encoding it.
     * DO_BREAK_LINES: break lines at 76 characters
    </pre> *
     *
     *
     * Example: `encodeObject( myObj, Base64.GZIP )` or
     *
     *
     * Example: `encodeObject( myObj, Base64.GZIP | Base64.DO_BREAK_LINES )`
     *
     * @param serializableObject The object to encode
     * @param options Specified options
     * @return The Base64-encoded object
     * @see Base64.GZIP
     *
     * @see Base64.DO_BREAK_LINES
     *
     * @throws java.io.IOException if there is an error
     * @since 2.0
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun encodeObject(serializableObject: Serializable?, options: Int = NO_OPTIONS): String {
        if (serializableObject == null) {
            throw NullPointerException("Cannot serialize a null object.")
        }

        // Streams
        var baos: ByteArrayOutputStream? = null
        var b64os: java.io.OutputStream? = null
        var gzos: GZIPOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            // ObjectOutputStream -> (GZIP) -> Base64 -> ByteArrayOutputStream
            baos = ByteArrayOutputStream()
            b64os = OutputStream(baos, ENCODE or options)
            if (options and GZIP != 0) {
                // Gzip
                gzos = GZIPOutputStream(b64os)
                oos = ObjectOutputStream(gzos)
            } else {
                // Not gzipped
                oos = ObjectOutputStream(b64os)
            }
            oos.writeObject(serializableObject)
        }
        catch (e: IOException) {
            // Catch it and then throw it immediately so that
            // the finally{} block is called for cleanup.
            throw e
        }
        finally {
            try {
                oos!!.close()
            } catch (e: Exception) {
            }
            try {
                gzos!!.close()
            } catch (e: Exception) {
            }
            try {
                b64os!!.close()
            } catch (e: Exception) {
            }
            try {
                baos!!.close()
            } catch (e: Exception) {
            }
        }

        // Return value according to relevant encoding.
        return try {
            String(baos!!.toByteArray(), charset(PREFERRED_ENCODING))
        }
        catch (uue: UnsupportedEncodingException) {
            // Fall back to some Java default
            String(baos!!.toByteArray())
        }
    }

    /**
     * Encodes a byte array into Base64 notation.
     * Does not GZip-compress data.
     *
     * @param source The data to convert
     * @return The data in Base64-encoded form
     * @throws NullPointerException if source array is null
     * @since 1.4
     */
    fun encodeBytes(source: ByteArray): String? {
        // Since we're not going to have the GZIP encoding turned on,
        // we're not going to have an java.io.IOException thrown, so
        // we should not force the user to have to catch it.
        var encoded: String? = null
        try {
            encoded = encodeBytes(source, 0, source.size, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { ex.message!! }
        }
        assert(encoded != null)
        return encoded
    }

    /**
     * Encodes a byte array into Base64 notation.
     *
     *
     * Example options:<pre>
     * GZIP: gzip-compresses object before encoding it.
     * DO_BREAK_LINES: break lines at 76 characters
     * *Note: Technically, this makes your encoding non-compliant.*
    </pre> *
     *
     *
     * Example: `encodeBytes( myData, Base64.GZIP )` or
     *
     *
     * Example: `encodeBytes( myData, Base64.GZIP | Base64.DO_BREAK_LINES )`
     *
     *
     *
     * As of v 2.3, if there is an error with the GZIP stream,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned a null value, but
     * in retrospect that's a pretty poor way to handle it.
     *
     *
     * @param source The data to convert
     * @param options Specified options
     * @return The Base64-encoded data as a String
     * @see Base64.GZIP
     *
     * @see Base64.DO_BREAK_LINES
     *
     * @throws java.io.IOException if there is an error
     * @throws NullPointerException if source array is null
     * @since 2.0
     */
    @Throws(IOException::class)
    fun encodeBytes(source: ByteArray, options: Int): String {
        return encodeBytes(source, 0, source.size, options)
    }

    /**
     * Encodes a byte array into Base64 notation.
     * Does not GZip-compress data.
     *
     *
     * As of v 2.3, if there is an error,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned a null value, but
     * in retrospect that's a pretty poor way to handle it.
     *
     *
     * @param source The data to convert
     * @param off Offset in array where conversion should begin
     * @param len Length of data to convert
     * @return The Base64-encoded data as a String
     * @throws NullPointerException if source array is null
     * @throws IllegalArgumentException if source array, offset, or length are invalid
     * @since 1.4
     */
    fun encodeBytes(source: ByteArray?, off: Int, len: Int): String? {
        // Since we're not going to have the GZIP encoding turned on,
        // we're not going to have an java.io.IOException thrown, so
        // we should not force the user to have to catch it.
        var encoded: String? = null
        try {
            encoded = encodeBytes(source, off, len, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { ex.message!! }
        }
        assert(encoded != null)
        return encoded
    }

    /**
     * Encodes a byte array into Base64 notation.
     *
     *
     * Example options:<pre>
     * GZIP: gzip-compresses object before encoding it.
     * DO_BREAK_LINES: break lines at 76 characters
     * *Note: Technically, this makes your encoding non-compliant.*
    </pre> *
     *
     *
     * Example: `encodeBytes( myData, Base64.GZIP )` or
     *
     *
     * Example: `encodeBytes( myData, Base64.GZIP | Base64.DO_BREAK_LINES )`
     *
     *
     *
     * As of v 2.3, if there is an error with the GZIP stream,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned a null value, but
     * in retrospect that's a pretty poor way to handle it.
     *
     *
     * @param source The data to convert
     * @param off Offset in array where conversion should begin
     * @param len Length of data to convert
     * @param options Specified options
     * @return The Base64-encoded data as a String
     * @see Base64.GZIP
     *
     * @see Base64.DO_BREAK_LINES
     *
     * @throws java.io.IOException if there is an error
     * @throws NullPointerException if source array is null
     * @throws IllegalArgumentException if source array, offset, or length are invalid
     * @since 2.0
     */
    @Throws(IOException::class)
    fun encodeBytes(source: ByteArray?, off: Int, len: Int, options: Int): String {
        val encoded = encodeBytesToBytes(source, off, len, options)

        // Return value according to relevant encoding.
        return try {
            String(encoded, charset(PREFERRED_ENCODING))
        }
        catch (uue: UnsupportedEncodingException) {
            String(encoded)
        }
    }

    /**
     * Similar to [.encodeBytes] but returns
     * a byte array instead of instantiating a String. This is more efficient
     * if you're working with I/O streams and have large data sets to encode.
     *
     *
     * @param source The data to convert
     * @return The Base64-encoded data as a byte[] (of ASCII characters)
     * @throws NullPointerException if source array is null
     * @since 2.3.1
     */
    fun encodeBytesToBytes(source: ByteArray): ByteArray? {
        var encoded: ByteArray? = null
        try {
            encoded = encodeBytesToBytes(source, 0, source.size, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { "IOExceptions only come from GZipping, which is turned off: " + ex.message }
        }
        return encoded
    }

    /**
     * Similar to [.encodeBytes] but returns
     * a byte array instead of instantiating a String. This is more efficient
     * if you're working with I/O streams and have large data sets to encode.
     *
     *
     * @param source The data to convert
     * @param off Offset in array where conversion should begin
     * @param len Length of data to convert
     * @param options Specified options
     * @return The Base64-encoded data as a String
     * @see Base64.GZIP
     *
     * @see Base64.DO_BREAK_LINES
     *
     * @throws java.io.IOException if there is an error
     * @throws NullPointerException if source array is null
     * @throws IllegalArgumentException if source array, offset, or length are invalid
     * @since 2.3.1
     */
    @Throws(IOException::class)
    fun encodeBytesToBytes(source: ByteArray?, off: Int, len: Int, options: Int): ByteArray {
        if (source == null) {
            throw NullPointerException("Cannot serialize a null array.")
        }
        require(off >= 0) { "Cannot have negative offset: $off" }
        require(len >= 0) { "Cannot have length offset: $len" }
        require(off + len <= source.size) {
            String.format(
                "Cannot have offset of %d and length of %d with array of length %d",
                off,
                len,
                source.size
            )
        }


        // Compress?
        return if (options and GZIP != 0) {
            var baos: ByteArrayOutputStream? = null
            var gzos: GZIPOutputStream? = null
            var b64os: OutputStream? = null
            try {
                // GZip -> Base64 -> ByteArray
                baos = ByteArrayOutputStream()
                b64os = OutputStream(baos, ENCODE or options)
                gzos = GZIPOutputStream(b64os)
                gzos.write(source, off, len)
                gzos.close()
            }
            catch (e: IOException) {
                // Catch it and then throw it immediately so that
                // the finally{} block is called for cleanup.
                throw e
            }
            finally {
                try {
                    gzos!!.close()
                } catch (e: Exception) {
                }
                try {
                    b64os!!.close()
                } catch (e: Exception) {
                }
                try {
                    baos!!.close()
                } catch (e: Exception) {
                }
            }
            baos!!.toByteArray()
        }
        else {
            val breakLines = options and DO_BREAK_LINES != 0

            //int    len43   = len * 4 / 3;
            //byte[] outBuff = new byte[   ( len43 )                      // Main 4:3
            //                           + ( (len % 3) > 0 ? 4 : 0 )      // Account for padding
            //                           + (breakLines ? ( len43 / MAX_LINE_LENGTH ) : 0) ]; // New lines
            // Try to determine more precisely how big the array needs to be.
            // If we get it right, we don't have to do an array copy, and
            // we save a bunch of memory.
            var encLen = len / 3 * 4 + if (len % 3 > 0) 4 else 0 // Bytes needed for actual encoding
            if (breakLines) {
                encLen += encLen / MAX_LINE_LENGTH // Plus extra newline characters
            }
            val outBuff = ByteArray(encLen)
            var d = 0
            var e = 0
            val len2 = len - 2
            var lineLength = 0
            while (d < len2) {
                encode3to4(source, d + off, 3, outBuff, e, options)
                lineLength += 4
                if (breakLines && lineLength >= MAX_LINE_LENGTH) {
                    outBuff[e + 4] = NEW_LINE
                    e++
                    lineLength = 0
                }
                d += 3
                e += 4
            }
            if (d < len) {
                encode3to4(source, d + off, len - d, outBuff, e, options)
                e += 4
            }


            // Only resize array if we didn't guess it right.
            if (e <= outBuff.size - 1) {
                // If breaking lines and the last byte falls right at
                // the line length (76 bytes per line), there will be
                // one extra byte, and the array will need to be resized.
                // Not too bad of an estimate on array size, I'd say.
                val finalOut = ByteArray(e)
                System.arraycopy(outBuff, 0, finalOut, 0, e)
                //System.err.println("Having to resize array from " + outBuff.length + " to " + e );
                finalOut
            } else {
                //System.err.println("No need to resize array.");
                outBuff
            }
        }
    }

    /* ********  D E C O D I N G   M E T H O D S  ******** */

    /**
     * Decodes four bytes from array <var>source</var>
     * and writes the resulting bytes (up to three of them)
     * to <var>destination</var>.
     * The source and destination arrays can be manipulated
     * anywhere along their length by specifying
     * <var>srcOffset</var> and <var>destOffset</var>.
     * This method does not check to make sure your arrays
     * are large enough to accomodate <var>srcOffset</var> + 4 for
     * the <var>source</var> array or <var>destOffset</var> + 3 for
     * the <var>destination</var> array.
     * This method returns the actual number of bytes that
     * were converted from the Base64 encoding.
     *
     * This is the lowest level of the decoding methods with
     * all possible parameters.
     *
     *
     * @param source the array to convert
     * @param srcOffset the index where conversion begins
     * @param destination the array to hold the conversion
     * @param destOffset the index where output will be put
     * @param options alphabet type is pulled from this (standard, url-safe, ordered)
     * @return the number of decoded bytes converted
     * @throws NullPointerException if source or destination arrays are null
     * @throws IllegalArgumentException if srcOffset or destOffset are invalid
     * or there is not enough room in the array.
     * @since 1.3
     */
    private fun decode4to3(
        source: ByteArray?,
        srcOffset: Int,
        destination: ByteArray?,
        destOffset: Int,
        options: Int
    ): Int {

        // Lots of error checking and exception throwing
        if (source == null) {
            throw NullPointerException("Source array was null.")
        }
        if (destination == null) {
            throw NullPointerException("Destination array was null.")
        }
        require(!(srcOffset < 0 || srcOffset + 3 >= source.size)) {
            String.format(
                "Source array with length %d cannot have offset of %d and still process four bytes.",
                source.size,
                srcOffset
            )
        }
        require(!(destOffset < 0 || destOffset + 2 >= destination.size)) {
            String.format(
                "Destination array with length %d cannot have offset of %d and still store three bytes.",
                destination.size,
                destOffset
            )
        }
        val decodabet = getDecodabet(options)

        // Example: Dk==
        return when (EQUALS_SIGN) {
            source[srcOffset + 2] -> {
                // Two ways to do the same thing. Don't know which way I like best.
                //int outBuff =   ( ( DECODABET[ source[ srcOffset    ] ] << 24 ) >>>  6 )
                //              | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
                val outBuff = (decodabet[source[srcOffset].toInt()].toInt() and 0xFF shl 18
                        or (decodabet[source[srcOffset + 1].toInt()].toInt() and 0xFF shl 12))
                destination[destOffset] = (outBuff ushr 16).toByte()
                1
            }
            source[srcOffset + 3] -> {
                // Two ways to do the same thing. Don't know which way I like best.
                //int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] << 24 ) >>>  6 )
                //              | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
                //              | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
                val outBuff = (decodabet[source[srcOffset].toInt()].toInt() and 0xFF shl 18
                        or (decodabet[source[srcOffset + 1].toInt()].toInt() and 0xFF shl 12)
                        or (decodabet[source[srcOffset + 2].toInt()].toInt() and 0xFF shl 6))
                destination[destOffset] = (outBuff ushr 16).toByte()
                destination[destOffset + 1] = (outBuff ushr 8).toByte()
                2
            }
            else -> {
                // Two ways to do the same thing. Don't know which way I like best.
                //int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] << 24 ) >>>  6 )
                //              | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
                //              | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
                //              | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
                val outBuff = (decodabet[source[srcOffset].toInt()].toInt() and 0xFF shl 18
                        or (decodabet[source[srcOffset + 1].toInt()].toInt() and 0xFF shl 12)
                        or (decodabet[source[srcOffset + 2].toInt()].toInt() and 0xFF shl 6)
                        or (decodabet[source[srcOffset + 3].toInt()].toInt() and 0xFF))
                destination[destOffset] = (outBuff shr 16).toByte()
                destination[destOffset + 1] = (outBuff shr 8).toByte()
                destination[destOffset + 2] = outBuff.toByte()
                3
            }
        }
    }

    /**
     * Low-level access to decoding ASCII characters in
     * the form of a byte array. **Ignores GUNZIP option, if
     * it's set.** This is not generally a recommended method,
     * although it is used internally as part of the decoding process.
     * Special case: if len = 0, an empty array is returned. Still,
     * if you need more speed and reduced memory footprint (and aren't
     * gzipping), consider this method.
     *
     * @param source The Base64 encoded data
     * @return decoded data
     * @since 2.3.1
     */
    @Throws(IOException::class)
    fun decode(source: ByteArray): ByteArray {
        return decode(source, 0, source.size, NO_OPTIONS)
    }

    /**
     * Low-level access to decoding ASCII characters in
     * the form of a byte array. **Ignores GUNZIP option, if
     * it's set.** This is not generally a recommended method,
     * although it is used internally as part of the decoding process.
     * Special case: if len = 0, an empty array is returned. Still,
     * if you need more speed and reduced memory footprint (and aren't
     * gzipping), consider this method.
     *
     * @param source The Base64 encoded data
     * @param off    The offset of where to begin decoding
     * @param len    The length of characters to decode
     * @param options Can specify options such as alphabet type to use
     * @return decoded data
     * @throws java.io.IOException If bogus characters exist in source data
     * @since 1.3
     */
    @Throws(IOException::class)
    fun decode(source: ByteArray?, off: Int, len: Int, options: Int): ByteArray {

        // Lots of error checking and exception throwing
        if (source == null) {
            throw NullPointerException("Cannot decode null source array.")
        }
        require(!(off < 0 || off + len > source.size)) {
            String.format(
                "Source array with length %d cannot have offset of %d and process %d bytes.",
                source.size,
                off,
                len
            )
        }
        if (len == 0) {
            return ByteArray(0)
        } else require(len >= 4) { "Base64-encoded string must have at least four characters, but length specified was $len" }
        val decodabet = getDecodabet(options)
        val len34 = len * 3 / 4 // Estimate on array size
        val outBuff = ByteArray(len34) // Upper limit on size of output
        var outBuffPosn = 0 // Keep track of where we're writing
        val b4 = ByteArray(4) // Four byte buffer from source, eliminating white space
        var b4Posn = 0 // Keep track of four byte input buffer
        var i = 0 // Source array counter
        var sbiDecode: Byte = 0 // Special value from DECODABET
        i = off
        while (i < off + len) {
            // Loop through source
            sbiDecode = decodabet[source[i].toInt() and 0xFF]

            // White space, Equals sign, or legit Base64 character
            // Note the values such as -5 and -9 in the
            // DECODABETs at the top of the file.
            if (sbiDecode >= WHITE_SPACE_ENC) {
                if (sbiDecode >= EQUALS_SIGN_ENC) {
                    b4[b4Posn++] = source[i] // Save non-whitespace
                    if (b4Posn > 3) {                  // Time to decode?
                        outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options)
                        b4Posn = 0

                        // If that was the equals sign, break out of 'for' loop
                        if (source[i] == EQUALS_SIGN) {
                            break
                        }
                    }
                }
            }
            else {
                // There's a bad input character in the Base64 stream.
                throw IOException(
                    String.format(
                        "Bad Base64 input character decimal %d in array position %d",
                        source[i].toInt() and 0xFF,
                        i
                    )
                )
            }
            i++
        }
        val out = ByteArray(outBuffPosn)
        System.arraycopy(outBuff, 0, out, 0, outBuffPosn)
        return out
    }

    /**
     * Decodes data from Base64 notation, automatically
     * detecting gzip-compressed data and decompressing it.
     *
     * @param s the string to decode
     * @param options encode options such as URL_SAFE
     * @return the decoded data
     * @throws java.io.IOException if there is an error
     * @throws NullPointerException if <tt>s</tt> is null
     * @since 1.4
     */
    @JvmOverloads
    @Throws(IOException::class)
    fun decode(s: String?, options: Int = NO_OPTIONS): ByteArray {
        if (s == null) {
            throw NullPointerException("Input string was null.")
        }
        var bytes: ByteArray
        bytes = try {
            s.toByteArray(charset(PREFERRED_ENCODING))
        }
        catch (uee: UnsupportedEncodingException) {
            s.toByteArray()
        }
        //</change>

        // Decode
        bytes = decode(bytes, 0, bytes.size, options)

        // Check to see if it's gzip-compressed
        // GZIP Magic Two-Byte Number: 0x8b1f (35615)
        val dontGunzip = options and DONT_GUNZIP != 0
        if (bytes.size >= 4 && !dontGunzip) {
            val head = bytes[0].toInt() and 0xff or (bytes[1].toInt() shl 8 and 0xff00)
            if (GZIPInputStream.GZIP_MAGIC == head) {
                var bais: ByteArrayInputStream? = null
                var gzis: GZIPInputStream? = null
                var baos: ByteArrayOutputStream? = null
                val buffer = ByteArray(2048)
                var length = 0
                try {
                    baos = ByteArrayOutputStream()
                    bais = ByteArrayInputStream(bytes)
                    gzis = GZIPInputStream(bais)
                    while (gzis.read(buffer).also { length = it } >= 0) {
                        baos.write(buffer, 0, length)
                    }

                    // No error? Get new bytes.
                    bytes = baos.toByteArray()
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    // Just return originally-decoded bytes
                }
                finally {
                    try {
                        baos!!.close()
                    } catch (e: Exception) {
                    }
                    try {
                        gzis!!.close()
                    } catch (e: Exception) {
                    }
                    try {
                        bais!!.close()
                    } catch (e: Exception) {
                    }
                }
            }
        }
        return bytes
    }

    /**
     * Attempts to decode Base64 data and deserialize a Java
     * Object within. Returns <tt>null</tt> if there was an error.
     * If <tt>loader</tt> is not null, it will be the class loader
     * used when deserializing.
     *
     * @param encodedObject The Base64 data to decode
     * @param options Various parameters related to decoding
     * @param loader Optional class loader to use in deserializing classes.
     * @return The decoded and deserialized object
     * @throws NullPointerException if encodedObject is null
     * @throws java.io.IOException if there is a general error
     * @throws ClassNotFoundException if the decoded object is of a
     * class that cannot be found by the JVM
     * @since 2.3.4
     */
    @JvmOverloads
    @Throws(IOException::class, ClassNotFoundException::class)
    fun decodeToObject(
        encodedObject: String?,
        options: Int = NO_OPTIONS,
        loader: ClassLoader? = null
    ): Any? {
        // Decode and gunzip if necessary
        val objBytes = decode(encodedObject, options)
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        var obj: Any? = null
        try {
            bais = ByteArrayInputStream(objBytes)

            // If no custom class loader is provided, use Java's builtin OIS.
            ois = if (loader == null) {
                ObjectInputStream(bais)
            }
            else {
                object : ObjectInputStream(bais) {
                    @Throws(IOException::class, ClassNotFoundException::class)
                    public override fun resolveClass(streamClass: ObjectStreamClass): Class<*> {
                        val c = Class.forName(streamClass.name, false, loader)
                        return c // Class loader knows of this class.
                            ?: super.resolveClass(streamClass)
                    }
                }
            }
            obj = ois.readObject()
        }
        catch (e: IOException) {
            throw e // Catch and throw in order to execute finally{}
        }
        catch (e: ClassNotFoundException) {
            throw e // Catch and throw in order to execute finally{}
        }
        finally {
            try {
                bais!!.close()
            } catch (e: Exception) {
            }
            try {
                ois!!.close()
            } catch (e: Exception) {
            }
        }
        return obj
    }

    /**
     * Convenience method for encoding data to a file.
     *
     *
     * As of v 2.3, if there is a error,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned false, but
     * in retrospect that's a pretty poor way to handle it.
     *
     * @param dataToEncode byte array of data to encode in base64 form
     * @param filename Filename for saving encoded data
     * @throws java.io.IOException if there is an error
     * @throws NullPointerException if dataToEncode is null
     * @since 2.1
     */
    @Throws(IOException::class)
    fun encodeToFile(dataToEncode: ByteArray?, filename: String?) {
        if (dataToEncode == null) {
            throw NullPointerException("Data to encode was null.")
        }
        var bos: OutputStream? = null
        try {
            bos = OutputStream(
                FileOutputStream(filename), ENCODE
            )
            bos.write(dataToEncode)
        }
        catch (e: IOException) {
            throw e // Catch and throw to execute finally{} block
        }
        finally {
            try {
                bos!!.close()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * Convenience method for decoding data to a file.
     *
     *
     * As of v 2.3, if there is a error,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned false, but
     * in retrospect that's a pretty poor way to handle it.
     *
     * @param dataToDecode Base64-encoded data as a string
     * @param filename Filename for saving decoded data
     * @throws java.io.IOException if there is an error
     * @since 2.1
     */
    @Throws(IOException::class)
    fun decodeToFile(dataToDecode: String, filename: String?) {
        var bos: OutputStream? = null
        try {
            bos = OutputStream(
                FileOutputStream(filename), DECODE
            )
            bos.write(dataToDecode.toByteArray(charset(PREFERRED_ENCODING)))
        }
        catch (e: IOException) {
            throw e // Catch and throw to execute finally{} block
        }
        finally {
            try {
                bos!!.close()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * Convenience method for reading a base64-encoded
     * file and decoding it.
     *
     *
     * As of v 2.3, if there is a error,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned false, but
     * in retrospect that's a pretty poor way to handle it.
     *
     * @param filename Filename for reading encoded data
     * @return decoded byte array
     * @throws java.io.IOException if there is an error
     * @since 2.1
     */
    @Throws(IOException::class)
    fun decodeFromFile(filename: String): ByteArray? {
        val decodedData: ByteArray?
        var bis: InputStream? = null
        try {
            // Set up some useful variables
            val file = File(filename)
            var buffer: ByteArray? = null
            var length = 0
            var numBytes = 0

            // Check for size of file
            if (file.length() > Int.MAX_VALUE) {
                throw IOException("File is too big for this convenience method (" + file.length() + " bytes).")
            }
            buffer = ByteArray(file.length().toInt())

            // Open a stream
            bis = InputStream(
                BufferedInputStream(
                    FileInputStream(file)
                ), DECODE
            )

            // Read until done
            while (bis.read(buffer, length, 4096).also { numBytes = it } >= 0) {
                length += numBytes
            }

            // Save in a variable to return
            decodedData = ByteArray(length)
            System.arraycopy(buffer, 0, decodedData, 0, length)
        }
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        }
        finally {
            try {
                bis!!.close()
            } catch (e: Exception) {
            }
        }
        return decodedData
    }

    /**
     * Convenience method for reading a binary file
     * and base64-encoding it.
     *
     *
     * As of v 2.3, if there is a error,
     * the method will throw an java.io.IOException. **This is new to v2.3!**
     * In earlier versions, it just returned false, but
     * in retrospect that's a pretty poor way to handle it.
     *
     * @param filename Filename for reading binary data
     * @return base64-encoded string
     * @throws java.io.IOException if there is an error
     * @since 2.1
     */
    @Throws(IOException::class)
    fun encodeFromFile(filename: String): String? {
        var encodedData: String?
        var bis: InputStream? = null
        try {
            // Set up some useful variables
            val file = File(filename)
            val buffer = ByteArray(
                Math.max(
                    (file.length() * 1.4 + 1).toInt(),
                    40
                )
            ) // Need max() for math on small files (v2.2.1); Need +1 for a few corner cases (v2.3.5)
            var length = 0
            var numBytes = 0

            // Open a stream
            bis = InputStream(
                BufferedInputStream(
                    FileInputStream(file)
                ), ENCODE
            )

            // Read until done
            while (bis.read(buffer, length, 4096).also { numBytes = it } >= 0) {
                length += numBytes
            }

            // Save in a variable to return
            encodedData = String(buffer, 0, length, charset(PREFERRED_ENCODING))
        }
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        }
        finally {
            try {
                bis!!.close()
            } catch (e: Exception) {
            }
        }
        return encodedData
    }

    /**
     * Reads <tt>infile</tt> and encodes it to <tt>outfile</tt>.
     *
     * @param infile Input file
     * @param outfile Output file
     * @throws java.io.IOException if there is an error
     * @since 2.2
     */
    @Throws(IOException::class)
    fun encodeFileToFile(infile: String, outfile: String?) {
        val encoded = encodeFromFile(infile)
        var out: java.io.OutputStream? = null
        try {
            out = BufferedOutputStream(
                FileOutputStream(outfile)
            )
            out.write(encoded!!.toByteArray(charset("US-ASCII"))) // Strict, 7-bit output.
        }
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        }
        finally {
            try {
                out!!.close()
            } catch (ex: Exception) {
            }
        }
    }

    /**
     * Reads <tt>infile</tt> and decodes it to <tt>outfile</tt>.
     *
     * @param infile Input file
     * @param outfile Output file
     * @throws java.io.IOException if there is an error
     * @since 2.2
     */
    @Throws(IOException::class)
    fun decodeFileToFile(infile: String, outfile: String?) {
        val decoded = decodeFromFile(infile)
        var out: java.io.OutputStream? = null
        try {
            out = BufferedOutputStream(
                FileOutputStream(outfile)
            )
            out.write(decoded)
        }
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        }
        finally {
            try {
                out!!.close()
            } catch (ex: Exception) {
            }
        }
    }

    /* ********  I N N E R   C L A S S   I N P U T S T R E A M  ******** */

    /**
     * A [Base64.InputStream] will read data from another
     * <tt>java.io.InputStream</tt>, given in the constructor,
     * and encode/decode to/from Base64 notation on the fly.
     *
     * @see Base64
     *
     * @since 1.3
     */
    class InputStream @JvmOverloads constructor(
        `in`: java.io.InputStream?, // Record options used to create the stream.
        private val options: Int = DECODE
    ) : FilterInputStream(`in`) {
        private val encode // Encoding or decoding
                : Boolean
        private var position // Current position in the buffer
                : Int
        private val buffer // Small buffer holding converted data
                : ByteArray
        private val bufferLength // Length of buffer (3 or 4)
                : Int
        private var numSigBytes = 0 // Number of meaningful bytes in the buffer
        private var lineLength = 0
        // Break lines at less than 80 characters
        private val breakLines: Boolean
        private val decodabet // Local copies to avoid extra method calls
                : ByteArray

        init {
            // Record for later
            breakLines = options and DO_BREAK_LINES > 0
            encode = options and ENCODE > 0
            bufferLength = if (encode) 4 else 3
            buffer = ByteArray(bufferLength)
            position = -1
            decodabet = getDecodabet(options)
        }

        /**
         * Reads enough of the input stream to convert
         * to/from Base64 and returns the next byte.
         *
         * @return next byte
         * @since 1.3
         */
        @Throws(IOException::class)
        override fun read(): Int {
            // Do we need to get data?
            if (position < 0) {
                if (encode) {
                    val b3 = ByteArray(3)
                    var numBinaryBytes = 0
                    for (i in 0..2) {
                        val b = `in`.read()

                        // If end of stream, b is -1.
                        if (b >= 0) {
                            b3[i] = b.toByte()
                            numBinaryBytes++
                        } else {
                            break // out of for loop
                        }
                    }
                    if (numBinaryBytes > 0) {
                        encode3to4(b3, 0, numBinaryBytes, buffer, 0, options)
                        position = 0
                        numSigBytes = 4
                    }
                    else {
                        return -1 // Must be end of stream
                    }
                }
                else {
                    val b4 = ByteArray(4)
                    var i = 0
                    i = 0
                    while (i < 4) {

                        // Read four "meaningful" bytes:
                        var b = 0
                        do {
                            b = `in`.read()
                        } while (b >= 0 && decodabet[b and 0x7f] <= WHITE_SPACE_ENC)
                        if (b < 0) {
                            break // Reads a -1 if end of stream
                        }
                        b4[i] = b.toByte()
                        i++
                    }
                    if (i == 4) {
                        numSigBytes = decode4to3(b4, 0, buffer, 0, options)
                        position = 0
                    }
                    else return if (i == 0) {
                        -1
                    }
                    else {
                        // Must have broken out from above.
                        throw IOException("Improperly padded Base64 input.")
                    }
                }
            }

            // Got data?
            return if (position >= 0) {
                // End of relevant data?
                if ( /*!encode &&*/position >= numSigBytes) {
                    return -1
                }
                if (encode && breakLines && lineLength >= MAX_LINE_LENGTH) {
                    lineLength = 0
                    '\n'.toInt()
                }
                else {
                    lineLength++ // This isn't important when decoding
                    // but throwing an extra "if" seems
                    // just as wasteful.
                    val b = buffer[position++].toInt()
                    if (position >= bufferLength) {
                        position = -1
                    }
                    b and 0xFF // This is how you "cast" a byte that's
                    // intended to be unsigned.
                }
            } else {
                throw IOException("Error in Base64 code reading stream.")
            }
        }

        /**
         * Calls [.read] repeatedly until the end of stream
         * is reached or <var>len</var> bytes are read.
         * Returns number of bytes read into array or -1 if
         * end of stream is encountered.
         *
         * @param dest array to hold values
         * @param off offset for array
         * @param len max number of bytes to read into array
         * @return bytes read into array or -1 if end of stream is encountered.
         * @since 1.3
         */
        @Throws(IOException::class)
        override fun read(dest: ByteArray, off: Int, len: Int): Int {
            var i: Int
            var b: Int
            i = 0
            while (i < len) {
                b = read()
                if (b >= 0) {
                    dest[off + i] = b.toByte()
                } else return if (i == 0) {
                    -1
                } else {
                    break // Out of 'for' loop
                } // Out of 'for' loop
                i++
            }
            return i
        }
    }

    /* ********  I N N E R   C L A S S   O U T P U T S T R E A M  ******** */

    /**
     * A [Base64.OutputStream] will write data to another
     * <tt>java.io.OutputStream</tt>, given in the constructor,
     * and encode/decode to/from Base64 notation on the fly.
     *
     * @see Base64
     *
     * @since 1.3
     */
    class OutputStream @JvmOverloads constructor(
        out: java.io.OutputStream?, // Record for later
        private val options: Int = ENCODE
    ) : FilterOutputStream(out) {

        private val encode: Boolean
        private var position = 0
        private var buffer: ByteArray?
        private val bufferLength: Int
        private var lineLength = 0
        private val breakLines: Boolean
        // Scratch used in a few places
        private val b4: ByteArray
        private var suspendEncoding = false
        // Local copies to avoid extra method calls
        private val decodabet: ByteArray

        init {
            breakLines = options and DO_BREAK_LINES != 0
            encode = options and ENCODE != 0
            bufferLength = if (encode) 3 else 4
            buffer = ByteArray(bufferLength)
            b4 = ByteArray(4)
            decodabet = getDecodabet(options)
        }

        /**
         * Writes the byte to the output stream after
         * converting to/from Base64 notation.
         * When encoding, bytes are buffered three
         * at a time before the output stream actually
         * gets a write() call.
         * When decoding, bytes are buffered four
         * at a time.
         *
         * @param theByte the byte to write
         * @since 1.3
         */
        @Throws(IOException::class)
        override fun write(theByte: Int) {
            // Encoding suspended?
            if (suspendEncoding) {
                out.write(theByte)
                return
            }

            // Encode?
            if (encode) {
                buffer!![position++] = theByte.toByte()
                if (position >= bufferLength) { // Enough to encode.
                    out.write(encode3to4(b4, buffer, bufferLength, options))
                    lineLength += 4
                    if (breakLines && lineLength >= MAX_LINE_LENGTH) {
                        out.write(NEW_LINE.toInt())
                        lineLength = 0
                    }
                    position = 0
                }
            }
            else {
                // Meaningful Base64 character?
                if (decodabet[theByte and 0x7f] > WHITE_SPACE_ENC) {
                    buffer!![position++] = theByte.toByte()
                    if (position >= bufferLength) { // Enough to output.
                        val len = decode4to3(buffer, 0, b4, 0, options)
                        out.write(b4, 0, len)
                        position = 0
                    }
                }
                else if (decodabet[theByte and 0x7f] != WHITE_SPACE_ENC) {
                    throw IOException("Invalid character in Base64 data.")
                }
            }
        }

        /**
         * Calls [.write] repeatedly until <var>len</var>
         * bytes are written.
         *
         * @param theBytes array from which to read bytes
         * @param off offset for array
         * @param len max number of bytes to read into array
         * @since 1.3
         */
        @Throws(IOException::class)
        override fun write(theBytes: ByteArray, off: Int, len: Int) {
            // Encoding suspended?
            if (suspendEncoding) {
                out.write(theBytes, off, len)
                return
            }
            for (i in 0 until len) {
                write(theBytes[off + i].toInt())
            }
        }

        /**
         * Method added by PHIL. [Thanks, PHIL. -Rob]
         * This pads the buffer without closing the stream.
         * @throws java.io.IOException  if there's an error.
         */
        @Throws(IOException::class)
        fun flushBase64() {
            if (position > 0) {
                position = if (encode) {
                    out.write(encode3to4(b4, buffer, position, options))
                    0
                }
                else {
                    throw IOException("Base64 input not properly padded.")
                }
            }
        }

        /**
         * Flushes and closes (I think, in the superclass) the stream.
         *
         * @since 1.3
         */
        @Throws(IOException::class)
        override fun close() {
            // 1. Ensure that pending characters are written
            flushBase64()

            // 2. Actually close the stream
            // Base class both flushes and closes.
            super.close()
            buffer = null
            out = null
        }

        /**
         * Suspends encoding of the stream.
         * May be helpful if you need to embed a piece of
         * base64-encoded data in a stream.6
         *
         * @throws java.io.IOException  if there's an error flushing
         * @since 1.5.1
         */
        @Throws(IOException::class)
        fun suspendEncoding() {
            flushBase64()
            suspendEncoding = true
        }

        /**
         * Resumes encoding of the stream.
         * May be helpful if you need to embed a piece of
         * base64-encoded data in a stream.
         *
         * @since 1.5.1
         */
        fun resumeEncoding() {
            suspendEncoding = false
        }
    }
}
