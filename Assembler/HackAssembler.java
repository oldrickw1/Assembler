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
            if (isLInstruction(instruction)) {
                String label = parser.getSymbol();
                table.addLabel(label, parser.getLineNumber());
            }
        }
        parser.reset();

        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();

            if (isAInstruction(instruction)) {
                String variableOrConstant = parser.getVariableOrConstant();
                addAInstructionCode(table, stringBuilder, variableOrConstant);
                stringBuilder.append('\n');
            } else if (isCInstruction(instruction)) {
                addCInstructionCode(table, stringBuilder, instruction.instruction);
                stringBuilder.append('\n');
            }
        }
        String binaries = stringBuilder.substring(0, stringBuilder.length() - 1);
        System.out.println(binaries);
        System.out.println(table);
    }

    private static boolean isCInstruction(Instruction instruction) {
        return instruction.type == InstructionType.C_INSTRUCTION;
    }

    private static boolean isAInstruction(Instruction instruction) {
        return instruction.type == InstructionType.A_INSTRUCTION;
    }

    private static boolean isLInstruction(Instruction instruction) {
        return instruction.type == InstructionType.L_INSTRUCTION;
    }

    private static void addCInstructionCode(SymbolTable table, StringBuilder stringBuilder, String instruction) {

    }

    private static void addAInstructionCode(SymbolTable table, StringBuilder stringBuilder, String variableOrConstant) {
        if (isSymbol(variableOrConstant)) {
            if (table.contains(variableOrConstant)) {
                // Get the address of the variable, recursively pass it again so that it gets added in binary form;
                int address = table.getAddress(variableOrConstant);
                addAInstructionCode(table, stringBuilder, Integer.toString(address));
            } else {
                // Add the newly declared variable to the SymbolTable, recursively get it's value;
                table.addVariable(variableOrConstant);
                addAInstructionCode(table, stringBuilder, variableOrConstant);
            }
        // Is a constant, can be added in binary form
        } else {
            stringBuilder.append(getPaddedBinaries(Integer.parseInt(variableOrConstant)));
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
