package Assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Instruction> commands;
    private int index;
    private Instruction currentInstruction;


    public Parser(String filepath) throws IOException {
        commands = new ArrayList<>();
        parseContent(filepath);
        index = -1;
    }

    private void parseContent(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.strip();
            if (isCommand(line)) {
                commands.add(new Instruction(getType(line), line));
            }
        }
        reader.close();
    }

    private InstructionType getType(String line) {
        if (line.charAt(0) == '(') return InstructionType.L_INSTRUCTION;
        if (line.charAt(0) == '@') return InstructionType.A_INSTRUCTION;
        return InstructionType.C_INSTRUCTION;
    }

    private boolean isCommand(String line) {
        return !(line.equals("") || line.charAt(0) == '/');
    }

    public boolean hasMoreCommands() {
        return index < commands.size() -1;
    }

    public void advance() {
        currentInstruction = commands.get(++index);
    }

    public String getSymbol() throws Exception {
        if (currentInstruction.type != InstructionType.L_INSTRUCTION) {
            throw new Exception("Wrong instruction type");
        }
        return currentInstruction.instruction.substring(1, currentInstruction.instruction.length() - 2);
    }

    public String getVariableOrConstant() throws Exception {
        if (currentInstruction.type != InstructionType.A_INSTRUCTION) {
            throw new Exception("Wrong instruction type");
        }
        return currentInstruction.instruction.substring(1);
    }

    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }
}
