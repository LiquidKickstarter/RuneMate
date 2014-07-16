package methods.quick;

import com.runemate.game.api.hybrid.input.Mouse;

public class Click {
    public static void click(Mouse.Button button) {
        new Thread(() -> Mouse.click(button)).start();
    }
}
