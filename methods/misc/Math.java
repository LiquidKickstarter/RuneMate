package methods.misc;

import java.text.DecimalFormat;

public class Math {

    public static double round(double d) {
        return Double.valueOf(new DecimalFormat("#.##").format(d));
    }

}
