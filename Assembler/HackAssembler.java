package Assembler;

import java.io.IOException;

public class HackAssembler {
    public static void main(String[] args) throws Exception {
//        quitIfNoArgs(args);

        SymbolTable table = new SymbolTable();
        Parser parser = new Parser("\\c\\Users\\OWeen\\IdeaProjects\\Nand2Tetris\\out\\artifacts\\Nand2Tetris_jartest.asm");
        Coder coder = new Coder();
        StringBuilder stringBuilder = new StringBuilder();

        while (parser.hasMoreCommands()) {
            Instruction instruction = parser.getCurrentInstruction();
            String inst = instruction.instruction;

            if (instruction.type == InstructionType.A_INSTRUCTION) {
                if (isSymbol(inst)) {
                    if (table.contains(inst)) {
                        stringBuilder.append(Integer.toBinaryString(table.getAddress(inst)));
                    }
                } else {
                    stringBuilder.append(Integer.toBinaryString(Integer.getInteger(inst)));
                }
            } else if (instruction.type == InstructionType.C_INSTRUCTION) {

            }
            else if (instruction.type == InstructionType.L_INSTRUCTION){

            }
            parser.advance();
        }
        System.out.println(stringBuilder.toString());
    }

    private static boolean isSymbol(String inst) {
        return inst.matches("[A-Za-z]+");
    }

    private static void quitIfNoArgs(String[] args) throws Exception {
        if (args[0] == null) {
            throw new Exception("No command line arguments");
        }
    }

}
