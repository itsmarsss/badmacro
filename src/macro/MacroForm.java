package src.macro;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import src.macro.seqitems.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.LogManager;

public class MacroForm {
    private JList macrosList;
    private JButton editButton;
    private JButton deleteButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton newButton;
    private JButton importButton;
    public JPanel mainPanel;
    private JButton exportButton;
    private JLabel statusLabel;

    public static final LinkedList<MacroInfo> macros = new LinkedList<>();

    public static RunSequence sequenceRunner;
    private JFrame frame;

    public static boolean isRunning = false;

    void setStatus(String status) {
        statusLabel.setText(status);
    }


    public void updateList() {
        macrosList.setListData(macros.toArray());
    }

    public MacroForm() {
        newButton.addActionListener(e -> {
            String path = JOptionPane.showInputDialog("Enter a name:");
            if (path == null) {
                return;
            }
            if (path.equals("")) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid macro name!", "New Macro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            macros.add(new MacroInfo(path));
            macrosList.setListData(macros.toArray());
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MacroInfo selected = (MacroInfo) macrosList.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                EditMacroForm editMac = new EditMacroForm();
                JFrame editFrame = new JFrame("Editing macro: \"" + selected + "\"");
                editFrame.setContentPane(editMac.editPanel);
                editMac.edit(selected, editFrame, frame);
                editFrame.pack();
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("src/assets/icon.png")));
                editFrame.setIconImage(icon.getImage());
                editFrame.setResizable(false);
                editFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                editFrame.setVisible(true);
                frame.setEnabled(false);
            }
        });
        deleteButton.addActionListener(e -> {
            if (macrosList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            macros.remove(macrosList.getSelectedIndex());
            macrosList.setListData(macros.toArray());
        });
        runButton.addActionListener(e -> {
            if (macrosList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (sequenceRunner != null) {
                sequenceRunner.stop();
                sequenceRunner = null;
                statusLabel.setText("Status: Idle");
            }
            sequenceRunner = new RunSequence();
            sequenceRunner.setMacro(macros.get(macrosList.getSelectedIndex()));
            sequenceRunner.start();
            statusLabel.setText("Status: Running \"" + macros.get(macrosList.getSelectedIndex()) + "\"");
        });
        stopButton.addActionListener(e -> {
            if (sequenceRunner != null) {
                sequenceRunner.stop();
                sequenceRunner = null;
                statusLabel.setText("Status: Idle");
            }
        });
        exportButton.addActionListener(e -> {
            if (macrosList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(mainPanel, "Please select a macro!", "Edit Macro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            JFileChooser fileChooser = new JFileChooser(".txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(mainPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                writeFile(selectedFile);
            }
        });
        importButton.addActionListener(e -> {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            JFileChooser fileChooser = new JFileChooser(".txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(mainPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                readFile(selectedFile);
            }
        });
        macrosList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteButton.setEnabled(macrosList.getSelectedIndex() != 0);
            }
        });
    }

    private void readFile(File selectedFile) {
        try {
            Scanner reader = new Scanner(selectedFile);
            MacroInfo info = new MacroInfo(selectedFile.getName().replaceFirst("[.][^.]+$", ""));
            while (reader.hasNextLine()) {
                String seqItem = reader.nextLine();
                SequenceItem newSeqItem = null;
                boolean notSeq = false;
                if (seqItem.startsWith("Bind: ")) {
                    info.setBind(Integer.parseInt(seqItem.replace("Bind: ", "")));
                    notSeq = true;
                } else if (seqItem.startsWith("RunType: ")) {
                    String temp = seqItem.replace("RunType: ", "");
                    String[] args = temp.split(",");
                    switch (args[0]) {
                        case "Single":
                            info.setRun(Mode.SINGLE, 0);
                            break;
                        case "RepeatUntilStopped":
                            info.setRun(Mode.REPEATUNTILSTOPPED, 0);
                            break;
                        case "Repeat":
                            info.setRun(Mode.REPEAT, Integer.parseInt(args[1]));
                            break;
                        default:
                            JOptionPane.showMessageDialog(mainPanel, "Error occurred during importing macro!", "Import Macro", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    notSeq = true;
                } else if (seqItem.startsWith("Delay: ")) {
                    newSeqItem = new DelayItem(Integer.parseInt(seqItem.replace("Delay: ", "")));
                } else if (seqItem.startsWith("KeyUp: ")) {
                    newSeqItem = new KeyItem(Integer.parseInt(seqItem.replace("KeyUp: ", "")), Mode.UP);
                } else if (seqItem.startsWith("KeyDown: ")) {
                    newSeqItem = new KeyItem(Integer.parseInt(seqItem.replace("KeyDown: ", "")), Mode.DOWN);
                } else if (seqItem.startsWith("MouseUp: ")) {
                    newSeqItem = new MouseItem(Integer.parseInt(seqItem.replace("MouseUp: ", "")), Mode.UP);
                } else if (seqItem.startsWith("MouseDown: ")) {
                    newSeqItem = new MouseItem(Integer.parseInt(seqItem.replace("MouseDown: ", "")), Mode.DOWN);
                } else if (seqItem.startsWith("MouseMove: ")) {
                    String temp = seqItem.replace("MouseMove: ", "");
                    String[] args = temp.split(":");
                    newSeqItem = new MoveItem(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                } else if (seqItem.startsWith("MouseScroll: ")) {
                    newSeqItem = new ScrollItem(Integer.parseInt(seqItem.replace("MouseScroll: ", "")));
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Error occurred during importing macro!", "Import Macro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!notSeq) {
                    info.appendSeqItem(newSeqItem);
                }
            }
            macros.add(info);
            macrosList.setListData(macros.toArray());
            reader.close();
            JOptionPane.showMessageDialog(mainPanel, "Successfully imported macro!", "Import Macro", JOptionPane.INFORMATION_MESSAGE);
        } catch (
                Exception e) {
            JOptionPane.showMessageDialog(mainPanel, "Error occurred during importing macro!", "Import Macro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    private void writeFile(File selectedFile) {
        try {
            FileWriter writer = new FileWriter(selectedFile);
            writer.write("Bind: " + macros.get(macrosList.getSelectedIndex()).getBind());
            writer.write("\n");
            Mode mode = macros.get(macrosList.getSelectedIndex()).getRunType();
            if (mode == Mode.SINGLE) {
                writer.write("RunType: Single,0");
            } else if (mode == Mode.REPEATUNTILSTOPPED) {
                writer.write("RunType: RepeatUntilStopped,0");
            } else if (mode == Mode.REPEAT) {
                int iter = macros.get(macrosList.getSelectedIndex()).getRunIter();
                writer.write("RunType: Repeat," + iter);
            }
            writer.write("\n");
            for (SequenceItem seqItem : macros.get(macrosList.getSelectedIndex()).getSequence()) {
                writer.write(seqItem.toExport());
                writer.write("\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(mainPanel, "Successfully exported macro!", "Export Macro", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainPanel, "Error occurred during exporting macro!", "Export Macro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setParent(JFrame frame) {
        this.frame = frame;
        try {
            LogManager.getLogManager().reset();
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new KeyBind());

        MacroInfo killkey = new MacroInfo("<html><font color=red><b>KILLKEY</b></font></html>");
        killkey.setBind(119);
        killkey.setRun(Mode.SINGLE, 0);
        macros.add(killkey);
        macrosList.setListData(macros.toArray());
    }
}
