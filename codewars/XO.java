import java.lang.Character;

public class XO {
    public static boolean getXO (String str) {
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < str.length(); ++i) {
            if ('x' == Character.toLowerCase(str.charAt(i))) {
                ++xCount;
            } if ('o' == Character.toLowerCase(str.charAt(i))) {
                ++oCount;
            }
        }
        return xCount == oCount;
    }

    public static void main(String args[]) {
        assert getXO("Xx-oO");
    }
}
