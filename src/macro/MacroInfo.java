package src.macro;

import java.util.LinkedList;

public class MacroInfo {
    private LinkedList<SequenceItem> sequence = new LinkedList<>();
    private String name;
    private int bind;

    private Mode mode;
    private int iteration;

    public MacroInfo(String name) {
        this.name = name;
        this.mode = Mode.SINGLE;
    }

    public MacroInfo(MacroInfo macro) {
        sequence = new LinkedList<>(macro.getSequence());
        name = macro.name;
        bind = macro.bind;

        mode = macro.mode;
        iteration = macro.iteration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public int getBind() {
        return bind;
    }

    public LinkedList<SequenceItem> getSequence() {
        return sequence;
    }

    public void appendSeqItem(SequenceItem seqItem) {
        sequence.addLast(seqItem);
    }

    public String toString() {
        return name;
    }

    public void setSequence(LinkedList<SequenceItem> sequence) {
        this.sequence = sequence;
    }

    public void setRun(Mode mode, int amount) {
        this.mode = mode;
        iteration = amount;
    }

    public int getRunIter() {
        return iteration;
    }

    public Mode getRunType() {
        return mode;
    }

    public void setSelf(MacroInfo macro) {
        sequence = new LinkedList<>(macro.getSequence());
        name = macro.name;
        bind = macro.bind;

        mode = macro.mode;
        iteration = macro.iteration;
    }
}
