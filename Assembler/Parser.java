package Assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Parser {
    private List<Instruction> commands;
    private int index;
    private Instruction currentInstruction;


    public Parser(String filepath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                line = line.strip();
                if (isCommand(line)) {
                    InstructionType type = getType(line);
                    commands.add(new Instruction(type, line));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        index = 0 ;
    }


    private InstructionType getType(String line) {
        if (line.charAt(0) == '(') return InstructionType.L_INSTRUCTION;
        if (line.charAt(0) == '@') return InstructionType.A_INSTRUCTION;
        return InstructionType.C_INSTRUCTION;
    }


    private boolean isCommand(String line) {
        if (line == "") return false;
        if (line.charAt(0) == '/') return false;
        return true;
    }


    public boolean hasMoreCommands() {
        return index < commands.size();
    }


    public void advance() {
        currentInstruction = commands.get(index++);
    }


    public String getSymbol() throws Exception {
        if (!(currentInstruction.type == InstructionType.L_INSTRUCTION)) {
            throw new Exception("Wrong instruction type");
        }
        return currentInstruction.instruction.substring(1,currentInstruction.instruction.length()-2);
    }


    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }
}
