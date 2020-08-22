package io.raindev.uniquestr;

public class UniqueString {
    private static final int CHARSET_SIZE = 16;

    public static boolean unique(String s) {
        byte[] foundChars = new byte[(int)Math.pow(2, CHARSET_SIZE - 2)];
        int i = 0;
        while (i < s.length()) {
            if (!markChar(foundChars, s.charAt(i))) {
                return false;
            }
	    i++;
        }
        return true;
    }

    private static boolean markChar(byte[] foundChars, char c) {
        int bytePos = c / 8;
        int bitPos = c % 8;
        byte mask = (byte)(1 << bitPos);
        boolean exists = (foundChars[bytePos] & mask) != 0;
        if (!exists) {
            foundChars[bytePos] = (byte)(foundChars[bytePos] | mask);
            return true;
        } else {
            return false;
        }
    }

}
