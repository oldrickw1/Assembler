package Assembler;

import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, Integer> map;
    private Integer referenceIndex;

    public SymbolTable() {
        map = new HashMap<>();

        map.put("R0",0);
        map.put("R1",1);
        map.put("R2",2);
        map.put("R3",3);
        map.put("R4",4);
        map.put("R5",5);
        map.put("R6",6);
        map.put("R7",7);
        map.put("R8",8);
        map.put("R9",9);
        map.put("R10",10);
        map.put("R11",11);
        map.put("R12",12);
        map.put("R13",13);
        map.put("R14",14);
        map.put("R15",15);
        map.put("SCREEN", 16384);
        map.put("KBD", 24576);
        map.put("SP", 0);
        map.put("LCL", 1);
        map.put("ARG", 2);
        map.put("THIS", 3);
        map.put("THAT", 4);

        referenceIndex = 16;
    }
    public boolean contains(String symbol) {
        return map.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return map.get(symbol);
    }

    public void addVariable(String name) {
        map.put(name, referenceIndex++);
    }

    public void addLabel(String label, int lineNumber) {
        map.put(label, lineNumber + 1);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
