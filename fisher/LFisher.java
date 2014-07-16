package fisher;

import com.runemate.game.api.script.annotations.Manifest;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.data.Category;
import com.runemate.game.api.script.data.GameType;
import com.runemate.game.api.script.framework.LoopingScript;
import methods.interact.GetIntractable;
import methods.interact.Interact;
import methods.interact.Interface;
import methods.misc.LocalPlayer;
import methods.misc.Script;
import methods.quick.Drop;
import methods.wait.Wait;

@Manifest(
        name = "LFisher",
        description = "Power fishes!",
        version = "0.001",
        categories = { Category.FISHING },
        compatibility = GameType.OSRS
)
public class LFisher extends LoopingScript {

    private static final double START_MOUSE_MULTI = Mouse.getSpeedMultiplier();

    private static String fishingTools[];

    @Override
    public void onStart() {
        this.setLoopDelay(500, 600);
        this.pause();



        Mouse.setSpeedMultiplier(300);

        fishingTools = new String[]{"Small fishing net"};
    }

    @Override
    public void onStop() {
        Mouse.setSpeedMultiplier(START_MOUSE_MULTI);
    }

    @Override
    public void onPause() {
        Script.allowInput(true);
    }

    @Override
    public void onResume() {
        Script.allowInput(false);
    }

    @Override
    public void onLoop() {
        switch (state()) {
            case FISH_SHORE: {
                if (Interact.npc(GetIntractable.getNpc("Fishing spot"), "Net"))
                    Wait.dynamic(() -> !LocalPlayer.isIdle());
                break;
            }
            case DROP_FISH: {
                Drop.allExcept("Small fishing net");
                break;
            }
            case NO_NET: {
                if (Interact.groundItem("Small fishing net", "Take"))
                    Wait.dynamic(() -> Inventory.containsAnyOf(fishingTools));
                break;
            }
        }
    }

    private state state() {
        if (!Inventory.containsAnyOf(fishingTools))
            return state.NO_NET;
        if (Inventory.isFull())
            return state.DROP_FISH;
        if (LocalPlayer.isIdle() || Interface.levelMessageValid())
            return state.FISH_SHORE;
        return state.BUSY;
    }

    private enum state {
        FISH_SHORE, DROP_FISH, NO_NET, BUSY
    }
}
