package ogya.lokakarya.be.util;

import java.util.Random;

public class RandGen {
    private static Random random = new Random();
    private static final String APHA_NUM_SYM =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:',.<>?/~`";

    public static String generate(int n) {
        StringBuilder generated = new StringBuilder(n);

        while (n-- > 0) {
            generated.append(APHA_NUM_SYM.charAt(random.nextInt(APHA_NUM_SYM.length() - 1)));
        }

        return generated.toString();
    }
}
