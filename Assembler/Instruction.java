package Assembler;

public class Instruction {

    InstructionType type;
    String instruction;

    public Instruction(InstructionType type, String instruction) {
        this.type = type;
        this.instruction = instruction;
    }
}

