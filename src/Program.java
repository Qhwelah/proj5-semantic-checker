public class Program {
    public static void main(String[] args) throws Exception
    {
        java.io.Reader r = new java.io.StringReader
        ("num main()\n"
        +"{\n"
        +"    num a;\n"
        +"    a <- 1;\n"
        +"    return 0;\n"
        +"}\n"
        );

        //  if(args.length == 0)
        //  args = new String[]
        //  {
        //      "C:\\cmpsc470-minc-SemanticChecker-sample\\minc\\"
        //      +"succ_01.minc",
        //  };

        // if(args.length <= 0)
        //     return;
        // String minicpath = args[0];
        // java.io.Reader r = new java.io.FileReader(minicpath);

        Compiler compiler = new Compiler(r);

        compiler.Parse();
    }
}
