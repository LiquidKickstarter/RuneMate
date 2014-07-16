package methods.misc;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;

public class LocalPlayer {

    private static Player player() {
        return Players.getLocal();
    }

    public static boolean isIdle() {
        return !isMoving() && isIdleAnim();
    }

    public static boolean isMoving() {
        return player().isMoving();
    }

    public static boolean isIdleAnim() {
        return player().getAnimationId() == -1;
    }

    public static boolean inCombat() {
        return player().getInteractingEntity() != null;
    }

    public static Coordinate getTile() {
        return player().getPosition();
    }

    public static int getAnimation() {
        return player().getAnimationId();
    }

    @Deprecated
    public static int getHealthPercent() {
        return player().getHealthGauge().getPercent();
    }

    private static final InterfaceComponent HEALTH_TEXT_MAX = Interfaces.getAt(320, 9, 4);
    private static final InterfaceComponent HEALTH_TEXT_ACTUAL = Interfaces.getAt(320, 9, 3);

    //private static String healthTextAb() {
    //    return HEALTH_TEXT.getText();
    //}

    private static double getMaximumHealthAb() {
        //String h = healthTextAb();
        //return Double.parseDouble(h.substring(h.indexOf("/") + 1, h.length()));
        return Double.parseDouble(HEALTH_TEXT_MAX.getText());
    }

    private static double getHealthAb() {
        //String h = healthTextAb();
        //return Double.parseDouble(h.substring(0, h.indexOf("/")));
        return Double.parseDouble(HEALTH_TEXT_ACTUAL.getText());
    }

    public static int getHealthPercentAb() {
        return (int) Math.round(getHealthAb() / getMaximumHealthAb() * 100);
    }
}
