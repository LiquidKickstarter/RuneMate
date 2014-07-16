package methods.interact;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.Filter;
import methods.interact.GetIntractable;
import methods.wait.Wait;

import java.util.concurrent.Callable;

public class Interact {

    /* STRING CONSTRUCTORS */

    public static boolean item(String name, String action) {
        return item(GetIntractable.getItem(name), action);
    }

    public static boolean npc(String name, String action) {
        return npc(GetIntractable.getNpc(name), action);
    }

    public static boolean object(String name, String action) {
        return object(GetIntractable.getObject(name), action);
    }

    public static boolean groundItem(String name, String action) {
        return groundItem(GetIntractable.getGroundItem(name), action);
    }

    /* COMPLEX CONSTRUCTORS */

    @Deprecated
    private static boolean component(String action) {
        InterfaceComponent i = Interfaces.getLoaded(new Filter<InterfaceComponent>() {
            @Override
            public boolean accepts(InterfaceComponent interfaceComponent) {
                return interfaceComponent.isVisible() && interfaceComponent.getActions().contains(action);
            }
        }).get(0);
        return i.interact(action);
    }

    private static boolean item(SpriteItem spriteItem, String action) {
        return spriteItem.interact(action);
    }

    public static boolean npc(Npc npc, String action) {
        if (npc == null) return false;
        Callable<Npc> i = () -> npc;
        try {
            if (!i.call().isVisible())
                if (Camera.turnTo(i.call())
                        && !i.call().isVisible()
                        && i.call().getArea().getRandomCoordinate().minimap().click()
                        && !Wait.dynamic(i.call()::isVisible))
                    return false;
            return i.call().interact(action);
        } catch (Exception ignored) {
        }
        return false;
    }

    private static boolean object(GameObject gameObject, String action) {
        if (gameObject == null) return false;
        Callable<GameObject> i = () -> gameObject;
        try {
            if (!i.call().isVisible()) {
                if (Camera.turnTo(i.call()) && !Wait.dynamic(i.call()::isVisible)) {
                    if (i.call().getArea().getRandomCoordinate().minimap().click()
                            && !Wait.dynamic(i.call()::isVisible)) {
                        return false;
                    }
                }
            }
            return i.call().interact(action);
        } catch (Exception ignored) {
        }
        return false;
    }

    private static boolean groundItem(GroundItem groundItem, String action) {
        Callable<GroundItem> i = () -> groundItem;
        try {
            if (!i.call().isVisible()) {
                if (Camera.turnTo(i.call()) && !Wait.dynamic(i.call()::isVisible)) {
                    if (i.call().getArea().getRandomCoordinate().minimap().click()
                            && !Wait.dynamic(i.call()::isVisible)) {
                        return false;
                    }
                }
            }
            return i.call().interact(action);
        } catch (Exception ignored) {
        }
        return false;
    }

}
