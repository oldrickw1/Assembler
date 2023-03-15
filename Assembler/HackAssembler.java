package Assembler;

import java.io.IOException;
import java.text.DecimalFormat;

public class HackAssembler {
    public static void main(String[] args) throws Exception {
//        quitIfNoArgs(args);

        SymbolTable table = new SymbolTable();
        Parser parser = new Parser("test.asm");
        Coder coder = new Coder();
        StringBuilder stringBuilder = new StringBuilder();

        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();

            System.out.println(instruction);


            if (instruction.type == InstructionType.A_INSTRUCTION) {
                String variable = parser.getVariable();
                System.out.println(variable);
                if (isSymbol(variable)) {
                    if (table.contains(variable)) {
                        stringBuilder.append(getPaddedBinaries(table.getAddress(variable)));
                    }
                } else {
                    stringBuilder.append(getPaddedBinaries(Integer.parseInt(variable)));
                }
            } else if (instruction.type == InstructionType.C_INSTRUCTION) {
//                String binDestCode = coder.getBinVersionOfDest(parser.getDest());

            }
            else if (instruction.type == InstructionType.L_INSTRUCTION){

            }

            stringBuilder.append('\n');
        }
        System.out.println(stringBuilder.toString());
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
