package android.util;

public class Base64 {
    public static byte[] decode(String str, int flags) {
        return java.util.Base64.getDecoder().decode(str);
    }
}