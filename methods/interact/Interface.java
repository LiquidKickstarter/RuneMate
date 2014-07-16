package methods.interact;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;

public class Interface {
    private static final String CLICK_CONTINUE_TEXT = "continue", CLICK_CONTINUE_ACTION = "Continue";

    public static boolean InventoryMessageValid() {
        InterfaceComponent i = getInventoryMessage();
        return i != null && i.isVisible() && i.getText().toLowerCase().contains(CLICK_CONTINUE_TEXT.toLowerCase());
    }

    public static boolean levelMessageValid() {
        InterfaceComponent i = getLevelMessage();
        return i != null && i.isVisible() && i.getText().toLowerCase().contains(CLICK_CONTINUE_TEXT.toLowerCase());
    }

    private static InterfaceComponent getInventoryMessage() {
        InterfaceComponent c = Interfaces.getAt(210, 1);
        return c != null ? c : null;
    }

    private static InterfaceComponent getLevelMessage() {
        InterfaceComponent l = Interfaces.getAt(164, 2);
        return l != null ? l : null;
    }

    public static boolean inventoryMessageInteract() {
        return InventoryMessageValid() && clickComponent(getInventoryMessage(), CLICK_CONTINUE_ACTION);
    }

    public static boolean levelMessageInteract() {
        return levelMessageValid() && clickComponent(getLevelMessage(), CLICK_CONTINUE_ACTION);
    }

    public static boolean clickComponent(InterfaceComponent i, String action) {
        return i.isValid() && i.isVisible() && i.interact(action);
    }

}
