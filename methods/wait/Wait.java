package methods.wait;

import com.runemate.game.api.hybrid.util.calculations.Random;

import java.util.concurrent.Callable;

public class Wait {

    private static double variances() {
        return Random.nextDouble(0.75, 1.50);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep((long) (time * variances()));
        } catch (InterruptedException ignored) {
        }
    }

    public static Boolean dynamic(Callable<Boolean> c, int f, int t) {
        try {
            for (int i = 0; i < t; i++) {
                if (!c.call()) {
                    sleep((int) (f * variances()));
                    continue;
                }
                break;
            }
            return c.call();
        } catch (Exception ignored) {
        }
        return false;
    }

    public static Boolean dynamic(Callable<Boolean> c) {
        return dynamic(c, (int) (200 * variances()), 20);
    }
}
