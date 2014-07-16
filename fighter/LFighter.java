package fighter;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.script.annotations.Manifest;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.Filter;
import com.runemate.game.api.script.data.Category;
import com.runemate.game.api.script.data.GameType;
import com.runemate.game.api.script.framework.LoopingScript;
import methods.Interact;
import methods.LocalPlayer;
import methods.Wait;

import java.util.logging.Logger;

@Manifest(
        name = "LFighter",
        description = "Power Kills!",
        version = "0.002",
        categories = { Category.COMBAT },
        compatibility = GameType.RS3
)
public class LFighter extends LoopingScript {

    private static final String MONSTER_NAME = "troll shaman";
    private static final String[] FOOD_NAMES = {"bird meat", "crayfish", "rabbit"};
    private static final String[] BONES = {"bones", "big bones"};
    private static final int SAFE_PERCENT = 30;
    private static final Logger log = Logger.getLogger("[LFighter]");

    @Override
    public void onStart() {
        this.setLoopDelay(300, 400);
        Mouse.setSpeedMultiplier(500);
    }

    @Override
    public void onLoop() {
        switch (state()) {
            case IDLE: {
                if (Interact.npc(MONSTER_NAME, "Attack"))
                    Wait.dynamic(LocalPlayer::inCombat);
                else
                    Wait.dynamic(() -> Interact.getNpc(MONSTER_NAME) != null);
                break;
            }
            case IN_COMBAT: {
                Wait.dynamic(() -> !LocalPlayer.inCombat());
                break;
            }
            case LOW_HEALTH: {
                SpriteItem food = Inventory.getFirstItem(new Filter<SpriteItem>() {
                    @Override
                    public boolean accepts(SpriteItem spriteItem) {
                        for (String foodName : FOOD_NAMES) {
                            if (spriteItem.getDefinition().getName().toLowerCase().contains(foodName))
                                return true;
                        }
                        return false;
                    }
                });
                if (food.isValid() && food.interact("Eat", food.getDefinition().getName()))
                    Wait.dynamic(() -> !food.isValid());
                else
                    Wait.dynamic(() -> LocalPlayer.getHealthPercentAb() > SAFE_PERCENT, 1000, 30);
                break;
            }
        }
    }

    private state state() {
        if (LocalPlayer.getHealthPercentAb() < SAFE_PERCENT)
            return state.LOW_HEALTH;
        if (LocalPlayer.inCombat())
            return state.IN_COMBAT;
        return state.IDLE;
    }

    private enum state {
        IN_COMBAT, IDLE, LOW_HEALTH
    }
}
