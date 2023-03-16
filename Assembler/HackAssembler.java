package Assembler;

public class HackAssembler {
    private SymbolTable table;
    private Parser parser;
    private Coder coder;
    private StringBuilder stringBuilder;

    public void assemble(String filePath) throws Exception {
        table = new SymbolTable();
        parser = new Parser(filePath);
        coder = new Coder();
        stringBuilder = new StringBuilder();

        firstPass();
        parser.reset();
        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();
            if (instruction.type == InstructionType.A_INSTRUCTION) {
                addAInstructionCode(parser.getVariableOrConstant());
            } else if (instruction.type == InstructionType.C_INSTRUCTION) {
                addCInstructionCode(instruction.instruction);
            }
        }
        String binaries = stringBuilder.substring(0, stringBuilder.length() - 1);
        System.out.println(binaries);
    }

    private void firstPass() throws Exception {
        while (parser.hasMoreCommands()) {
            parser.advance();
            Instruction instruction = parser.getCurrentInstruction();
            if (instruction.type == InstructionType.L_INSTRUCTION) {
                String label = parser.getSymbol();
                table.addLabel(label, parser.getLineNumber());
            }
        }
    }

    private void addCInstructionCode(String instruction) {

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
            stringBuilder.append(getPaddedBinaries(Integer.parseInt(variableOrConstant)) + '\n');
        }
    }

    private String getPaddedBinaries(int i) {
        return String.format("%016d", Integer.parseInt(Integer.toString(i, 2)));
    }

    private boolean isSymbol(String inst) {
        return inst.matches("[A-Za-z].*");
    }
}
