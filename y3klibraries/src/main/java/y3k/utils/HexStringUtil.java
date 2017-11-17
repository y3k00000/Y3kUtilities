package y3k.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class HexStringUtil {
    public static byte[] DecodeHexStringToByteArray(String string) {
        int len = string.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
        }
        return data;
    }

    public static String encodeByteArrayToHexString(byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String encodeCompressedHexString(String originalString) {
        Log.d(HexStringUtil.class.getName(), "originalString = "+originalString);
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(originalString.getBytes());
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] deflateBuffer = new byte[1024];
        for (int deflateCount; (deflateCount = deflater.deflate(deflateBuffer)) > 0; ) {
            byteArrayOutputStream.write(deflateBuffer, 0, deflateCount);
        }
        byte[] deflatedArray = byteArrayOutputStream.toByteArray();
        Log.d(HexStringUtil.class.getName(), "deflatedArray.length = "+deflatedArray.length);
        return encodeByteArrayToHexString(deflatedArray);
    }

    public static String decodeCompressedHexString(String hexString) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(DecodeHexStringToByteArray(hexString));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] inflateBuffer = new byte[1024];
        for (int inflateCount; (inflateCount = inflater.inflate(inflateBuffer)) > 0; ) {
            byteArrayOutputStream.write(inflateBuffer, 0, inflateCount);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
}
