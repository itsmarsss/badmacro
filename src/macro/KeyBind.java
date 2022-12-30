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
        if (KeyEvent.getKeyText(MacroForm.macros.get(0).getBind()).equals(NativeKeyEvent.getKeyText(e.getKeyCode()))) {
            if (MacroForm.sequenceRunner != null) {
                MacroForm.sequenceRunner.stop();
                MacroForm.sequenceRunner = null;
                Macro.setStatus("Status: Idle");
            }
            MacroForm.isRunning = true;
            MacroForm.sequenceRunner = new RunSequence();
            MacroForm.sequenceRunner.setMacro(MacroForm.macros.get(0));
            MacroForm.sequenceRunner.start();
            Macro.setStatus("Status: Terminating...");
        }
        if (MacroForm.isRunning = false) {
            return;
        }
        LinkedList<MacroInfo> macros = MacroForm.macros;
        int i = 0;
        for (MacroInfo macro : macros) {
            if (KeyEvent.getKeyText(macro.getBind()).equals(NativeKeyEvent.getKeyText(e.getKeyCode()))) {
                if (MacroForm.sequenceRunner != null) {
                    MacroForm.sequenceRunner.stop();
                    MacroForm.sequenceRunner = null;
                    Macro.setStatus("Status: Idle");
                }
                MacroForm.isRunning = true;
                MacroForm.sequenceRunner = new RunSequence();
                MacroForm.sequenceRunner.setMacro(macro);
                MacroForm.sequenceRunner.start();
                if (i == 0) {
                    Macro.setStatus("Status: Terminating...");
                } else {
                    Macro.setStatus("Status: Running \"" + macro + "\"");
                }
            }
            i++;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}