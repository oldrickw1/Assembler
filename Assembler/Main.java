package Assembler;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputFileName = args.length == 1 ? args[0] : "default.asm";
        String outputFileName = getOutputFileName(inputFileName);
        System.out.println(inputFileName);
        System.out.println(outputFileName);
        HackAssembler assembler = new HackAssembler();
        String machineCodeInBinaries = assembler.assemble(inputFileName);
//        System.out.println(machineCodeInBinaries);

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
        writer.write(machineCodeInBinaries);
        writer.close();
    }

    private static String getOutputFileName(String inputFileName) {
        if (inputFileName.contains("/")) {
            String[] strings = inputFileName.split("/");
            inputFileName = strings[strings.length-1];
        }
        return inputFileName.split("\\.")[0] + ".hack";
    }

    private static void quitIfNoArgs(String[] args) throws Exception {
        if (args[0] == null) {
            throw new Exception("No command line arguments");
        }
    }
}
