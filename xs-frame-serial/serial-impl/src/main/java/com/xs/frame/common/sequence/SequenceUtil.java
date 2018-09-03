package com.xs.frame.common.sequence;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/22.
 */

public class SequenceUtil {
    private Map<String, Sequence> sequenceMap;
    private Sequence defaultSequence;

    public void setDefaultSequence(Sequence defaultSequence) {
        this.defaultSequence = defaultSequence;
    }

    public void setSequenceMap(Map<String, Sequence> sequenceMap) {
        this.sequenceMap = sequenceMap;
    }

    public void addSequence(String name, Sequence sequence) {
        if(sequenceMap==null){
            sequenceMap = new HashMap<String, Sequence>();
        }
        if (!sequenceMap.containsKey(name)) {
            synchronized (this) {
                if (!sequenceMap.containsKey(name)) {
                    sequenceMap.put(name, sequence);
                }
            }
        }
    }

    public long get(String name) {
        Sequence sequence = null;
        if (this.sequenceMap != null) {
            sequence = (Sequence) this.sequenceMap.get(name);
        }
        if (sequence == null) {
            if (this.defaultSequence != null) {
                return this.defaultSequence.get(name);
            }
            throw new RuntimeException("sequence " + name + " undefined!");
        }

        return sequence.get(name);
    }

}
