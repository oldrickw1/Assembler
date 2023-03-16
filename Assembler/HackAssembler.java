package Assembler;

import java.text.DecimalFormat;

public class HackAssembler {
    public static void main(String[] args) throws Exception {
//        quitIfNoArgs(args);

        SymbolTable table = new SymbolTable();
        Parser parser = new Parser("test.asm");
        Coder coder = new Coder();
        StringBuilder stringBuilder = new StringBuilder();

        firstPass(table, parser);
        parser.reset();

        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();

            if (instruction.type == InstructionType.A_INSTRUCTION) {
                String variableOrConstant = parser.getVariableOrConstant();
                addAInstructionCode(table, stringBuilder, variableOrConstant);
                stringBuilder.append('\n');
            } else if (instruction.type == InstructionType.C_INSTRUCTION) {
                addCInstructionCode(table, stringBuilder, instruction.instruction);
                stringBuilder.append('\n');
            }
        }
        String binaries = stringBuilder.substring(0, stringBuilder.length() - 1);
        System.out.println(binaries);
        System.out.println(table);
    }

    private static void firstPass(SymbolTable table, Parser parser) throws Exception {
        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();
            if (instruction.type == InstructionType.L_INSTRUCTION) {
                String label = parser.getSymbol();
                table.addLabel(label, parser.getLineNumber());
            }
        }
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
        return String.format("%016d", Integer.parseInt(Integer.toString(i, 2)));
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
