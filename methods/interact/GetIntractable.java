package methods.interact;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.util.Filter;

public class GetIntractable {

    public static GameObject getObject(Integer id) {
        return GameObjects.getNearest(id);
    }

    public static GameObject getObject(String name) {
        return GameObjects.getNearest(new Filter<GameObject>() {
            @Override
            public boolean accepts(GameObject gameObject) {
                return gameObject.getDefinition().getName().toLowerCase().contains(name);
            }
        });
    }

    public static Npc getNpc(String name) {
        return Npcs.getNearest(new Filter<Npc>() {
            @Override
            public boolean accepts(Npc npc) {
                return npc.getName().toLowerCase().contains(name.toLowerCase())
                        && !npc.getPosition().equals(new Coordinate(3246, 3157, 0))
                        && npc.getId() != 3081;
            }
        });
    }

    public static Npc getNpc(Integer id) {
        return Npcs.getNearest(id);
    }

    public static SpriteItem getItem(String name) {
        return Inventory.getItems(new Filter<SpriteItem>() {
            @Override
            public boolean accepts(SpriteItem spriteItem) {
                return spriteItem.getDefinition().getName().toLowerCase().contains(name);
            }
        }).get(0);
    }

    public static GroundItem getGroundItem(String name) {
        return GroundItems.getNearest(new Filter<GroundItem>() {
            @Override
            public boolean accepts(GroundItem groundItem) {
                return groundItem.getDefinition().getName().toLowerCase().contains(name.toLowerCase());
            }
        });
    }

    public static GroundItem getGroundItem(Integer id) {
        return GroundItems.getNearest(id);
    }
}
