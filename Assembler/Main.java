package Assembler;

public class Main {
    public static void main(String[] args) throws Exception {
//        quitIfNoArgs(args);
        HackAssembler assembler = new HackAssembler();
        assembler.assemble("test.asm");
    }

    private static void quitIfNoArgs(String[] args) throws Exception {
        if (args[0] == null) {
            throw new Exception("No command line arguments");
        }
    }
}
