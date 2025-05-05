public class Program {
    public static void main(String[] args) throws Exception
    {
        // java.io.Reader r = new java.io.StringReader
        // (""
        // +"num main(num x)\n"
        // +"{\n"
        // +"    num a;\n"
        // +"    num b;\n"
        // +"    a <- not a;\n"
        // +"    return 0;\n"
        // +"}\n"
        // );

         if(args.length == 0)
         args = new String[]
         {
            "C:\\Users\\jdcoo\\Coding Stuff\\Compilers\\proj5-6-semantic-checker\\proj5-semantic-checker\\samples\\fail_04a.minc",
            //  "C:\\cmpsc470-minc-SemanticChecker-sample\\minc\\"
            //  +"succ_01.minc",
         };

        if(args.length <= 0)
            return;
        String minicpath = args[0];
        java.io.Reader r = new java.io.FileReader(minicpath);

        Compiler compiler = new Compiler(r);

        compiler.Parse();
    }
}
