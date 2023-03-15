package Assembler;

import java.text.DecimalFormat;

public class HackAssembler {


    public static void main(String[] args) throws Exception {
//        quitIfNoArgs(args);

        SymbolTable table = new SymbolTable();
        Parser parser = new Parser("test.asm");
        Coder coder = new Coder();
        StringBuilder stringBuilder = new StringBuilder();

        // First iteration:
        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();
            if (instruction.type == InstructionType.L_INSTRUCTION) {
                String label = parser.getSymbol();
                table.add(label);
            }
        }


        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();

            if (instruction.type == InstructionType.A_INSTRUCTION) {
                String variable = parser.getVariableOrConstant();
                addAInstructionCode(table, stringBuilder, variable);
            } else if (instruction.type == InstructionType.C_INSTRUCTION) {
                addCInstructionCode(table, stringBuilder, instruction.instruction);
            }
            stringBuilder.append('\n');
        }
        System.out.println(stringBuilder.toString());
    }

    private static void addCInstructionCode(SymbolTable table, StringBuilder stringBuilder, String instruction) {
    }

    private static void addAInstructionCode(SymbolTable table, StringBuilder stringBuilder, String variable) {
        if (isSymbol(variable)) {
            if (table.contains(variable)) {
                stringBuilder.append(getPaddedBinaries(table.getAddress(variable)));
            } else {
                System.out.println("Label not caught in first iteration. This should not be happening");
            }
        } else {

            stringBuilder.append(getPaddedBinaries(Integer.parseInt(variable)));
        }
    }

    private static String getPaddedBinaries(int i) {
        String binaryString = getBinaries(i);
        String paddedBinaries = pad(binaryString);
        return paddedBinaries;
    }

    private static String pad(String s) {
        DecimalFormat format = new DecimalFormat("0000000000000000");
        String str = format.format(Integer.parseInt(s));
        return str;
    }

    private static String getBinaries(int i) {
        String s = Integer.toBinaryString(i);
        return s;

    }

    private static boolean isSymbol(String inst) {
        return inst.matches("[A-Za-z].*");
    }

    private static void quitIfNoArgs(String[] args) throws Exception {
        if (args[0] == null) {
            throw new Exception("No command line arguments");
        }
    }

}
