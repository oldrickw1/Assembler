package Assembler;

public class HackAssembler {
    public static final String ZEROES_FOR_PADDING = "0000000000000000";

    private SymbolTable table;
    private Parser parser;
    private Coder coder;
    private StringBuilder stringBuilder;

    public String assemble(String filePath) throws Exception {
        table = new SymbolTable();
        parser = new Parser(filePath);
        coder = new Coder();
        stringBuilder = new StringBuilder();

        firstPass();
        parser.reset();
        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();
            switch (instruction.type) {
                case A_INSTRUCTION -> addAInstructionCode(parser.getVariableOrConstant());
                case C_INSTRUCTION -> addCInstructionCode(parser.getDest(), parser.getComp(), parser.getJump());
            }
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private void firstPass() throws Exception {
        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();
            if (instruction.type == InstructionType.L_INSTRUCTION) {
                table.addLabel(parser.getSymbol(), parser.getLineNumber());
            }
        }
    }

    private void addCInstructionCode(String dest, String comp, String jump) {
//        System.out.printf("Destination: %20s\nComputation: %20s\nJump: %20s\n", dest, comp, jump);
        stringBuilder.append("111").append(coder.getComp(comp)).append(coder.getDest(dest)).append(coder.getJump(jump)).append('\n');
    }

    private void addAInstructionCode(String variableOrConstant) {
        if (isSymbol(variableOrConstant)) {
            if (table.contains(variableOrConstant)) {
                // Get the address of the variable, recursively pass it again so that it gets added in binary form;
                int address = table.getAddress(variableOrConstant);
                addAInstructionCode(Integer.toString(address));
            } else {
                // Add the newly declared variable to the SymbolTable, recursively get it's value;
                table.addVariable(variableOrConstant);
                addAInstructionCode(variableOrConstant);
            }
        // Is a constant, can be added in binary form
        } else {
            stringBuilder.append(getPaddedBinaries(Integer.parseInt(variableOrConstant))).append('\n');
        }
    }

    private String getPaddedBinaries(int i) {
        String binString = Integer.toString(i,2);
        String paddedBin = ZEROES_FOR_PADDING.substring(binString.length()) + binString;

        return paddedBin;

    }

    private boolean isSymbol(String inst) {
        return inst.matches("[A-Za-z].*");
    }
}
