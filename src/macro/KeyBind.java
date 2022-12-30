package src.macro;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyBind implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        LinkedList<MacroInfo> macros = MacroForm.macros;
        for (MacroInfo macro : macros) {
            if (KeyEvent.getKeyText(macro.getBind()).equals(e.getKeyText(e.getKeyCode()))) {
                if (MacroForm.sequenceRunner != null) {
                    MacroForm.sequenceRunner.interrupt();
                    MacroForm.sequenceRunner.stop();
                    MacroForm.sequenceRunner = null;
                    Macro.setStatus("Status: Idle");
                }
                MacroForm.sequenceRunner = new RunSequence();
                MacroForm.sequenceRunner.setMacro(macro);
                MacroForm.sequenceRunner.start();
                Macro.setStatus("Status: Running \"" + macro + "\"");
            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}