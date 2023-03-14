package Assembler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class HackAssembler {
    public static void main(String[] args) throws Exception {
        if (args[0] == null) {
            throw new Exception("No command line arguments");
        }
        SymbolTable table = new SymbolTable();
        Parser parser = new Parser(args[0]);
        Coder coder = new Coder();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(args[0] + ".hack"));
            while (parser.hasMoreCommands()) {
                Instruction instruction = parser.getCurrentInstruction();
                if (instruction.type == InstructionType.A_INSTRUCTION) {
                    //todo
                }
                bufferedWriter.write(args[0] + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
