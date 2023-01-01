package src.macro;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;
import src.macro.seqitems.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class KeyBind implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener, NativeMouseWheelListener {


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (MacroForm.recording) {
            MacroForm.addDelay();
            MacroForm.tempSequence.add(new KeyItem(e.getRawCode(), Mode.DOWN));
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (MacroForm.recording) {
            MacroForm.addDelay();
            MacroForm.tempSequence.add(new KeyItem(e.getRawCode(), Mode.UP));
        }
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
        if (MacroForm.isRunning) {
            return;
        }
        LinkedList<MacroInfo> macros = MacroForm.macros;
        int i = 0;
        for (MacroInfo macro : macros) {
            if (macro.getBind() == e.getRawCode()) {
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


    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        if (MacroForm.recording) {
            MacroForm.addDelay();
            if (e.getButton() == 1) {
                MacroForm.tempSequence.add(new MouseItem(MouseEvent.BUTTON1_DOWN_MASK, Mode.DOWN));
            } else if (e.getButton() == 2) {
                MacroForm.tempSequence.add(new MouseItem(MouseEvent.BUTTON2_DOWN_MASK, Mode.DOWN));
            } else if (e.getButton() == 3) {
                MacroForm.tempSequence.add(new MouseItem(MouseEvent.BUTTON3_DOWN_MASK, Mode.DOWN));
            }
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (MacroForm.recording) {
            MacroForm.addDelay();
            if (e.getButton() == 1) {
                MacroForm.tempSequence.add(new MouseItem(MouseEvent.BUTTON1_DOWN_MASK, Mode.UP));
            } else if (e.getButton() == 2) {
                MacroForm.tempSequence.add(new MouseItem(MouseEvent.BUTTON2_DOWN_MASK, Mode.UP));
            } else if (e.getButton() == 3) {
                MacroForm.tempSequence.add(new MouseItem(MouseEvent.BUTTON3_DOWN_MASK, Mode.UP));
            }
        }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (MacroForm.recording) {
            MacroForm.addDelay();
            MacroForm.tempSequence.add(new MoveItem(e.getX(), e.getY()));
        }
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        if (MacroForm.recording) {
            MacroForm.addDelay();
            MacroForm.tempSequence.add(new ScrollItem(e.getWheelRotation()));
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

}