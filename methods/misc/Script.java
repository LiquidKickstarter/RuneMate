package methods.misc;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.input.Mouse;

public class Script {
    public static void allowInput(boolean input) {
        if (input) {
            if (!Mouse.isInputAllowed())
                Mouse.toggleInput();
            if (!Keyboard.isInputAllowed())
                Keyboard.toggleInput();
            return;
        }
        if (Mouse.isInputAllowed())
            Mouse.toggleInput();
        if (Keyboard.isInputAllowed())
            Keyboard.toggleInput();
    }
}
