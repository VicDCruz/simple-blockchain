package cr.vic;

public class StringCommons {
    public static String createPrefixString(int prefixLength) {
        return new String(new char[prefixLength]).replace('\0', '0');
    }
}
