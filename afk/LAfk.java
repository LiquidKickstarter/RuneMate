package afk;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.annotations.Manifest;
import com.runemate.game.api.script.data.Category;
import com.runemate.game.api.script.data.GameType;
import com.runemate.game.api.script.framework.LoopingScript;

import java.awt.*;

@Manifest(
        name = "LAfk",
        description = "Keeps your account logged in!",
        version = "0.001",
        categories = { Category.OTHER },
        compatibility = GameType.OSRS
)
public class LAfk extends LoopingScript {

    @Override
    public void onStart() {
        this.setLoopDelay(10000);
    }

    @Override
    public void onLoop() {
        if (Menu.isOpen())
            Menu.getItem("Cancel").click();

        Mouse.hop(new Point(Random.nextInt(700), Random.nextInt(400, 500)));

        if (Random.nextInt(0, 100) < 50)
            Mouse.click(Mouse.Button.RIGHT);
    }
}
