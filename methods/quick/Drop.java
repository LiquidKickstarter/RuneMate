package methods.quick;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import methods.wait.Wait;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class Drop {

    private static final int[] ROWS = {0, 4, 8, 12, 16, 20, 24}, COLS = {0, 1, 2, 3};

    public static boolean item(SpriteItem spriteItem) {
        if (Menu.isOpen()) {
            Mouse.hop(Menu.getItem("Cancel").getInteractionPoint());
            Click.click(Mouse.Button.LEFT);
        }
        if (!Menu.isOpen() && Mouse.hop(spriteItem.getInteractionPoint())) {
            Click.click(Mouse.Button.RIGHT);
            if (Wait.dynamic(() -> Menu.isOpen() && Menu.getItem("Drop") != null, 20, 10)
                    && Mouse.hop(new Point(Mouse.getPosition().x, Menu.getItem("Drop").getInteractionPoint().y))) {
                Click.click(Mouse.Button.LEFT);
                Wait.sleep(50);
                return true;
            }
        }
        return false;
    }

    public static void all() {
        Inventory.getItems().stream().filter(Drop::item).forEach(spriteItem ->
                Wait.dynamic(() -> !Menu.isOpen(), 20, 100));
    }

    public static void all(java.util.List<SpriteItem> spriteItemList) {
        sortToColumns(spriteItemList).stream().filter(Drop::item).forEach(spriteItem ->
                Wait.dynamic(() -> !Menu.isOpen(), 20, 100));
    }

    public static void allExcept(String name) {
        java.util.List<SpriteItem> toDrop = Inventory.getItems().stream()
                .filter(t -> !t.getDefinition().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        all(toDrop);
    }


    private static java.util.List<SpriteItem> sortToColumns(java.util.List<SpriteItem> spriteItemList) {
        java.util.List<SpriteItem> sortedList = new ArrayList<>();
        for (Integer i : COLS) {
            for (Integer r : ROWS) {
                spriteItemList.stream().filter(spriteItem -> spriteItem.getIndex() == r + i).forEach(sortedList::add);
            }
        }
        return sortedList;
    }
}
