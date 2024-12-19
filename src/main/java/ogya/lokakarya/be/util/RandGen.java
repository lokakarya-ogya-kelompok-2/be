package ogya.lokakarya.be.util;

import java.util.Random;

public class RandGen {
    private RandGen() {}

    private static Random random = new Random();
    private static final String ALPHA_NUM =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generate(int n) {
        StringBuilder generated = new StringBuilder(n);

        while (n-- > 0) {
            generated.append(ALPHA_NUM.charAt(random.nextInt(ALPHA_NUM.length() - 1)));
        }

        return generated.toString();
    }
}
