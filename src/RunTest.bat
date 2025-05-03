"C:\Program Files\Java\jdk-13.0.2\bin\java.exe" -jar jflex-1.6.1.jar Lexer.flex
yacc.exe -Jthrows="Exception" -Jextends=ParserImpl -Jclass=Parser -Jnorun -J Parser.y

"C:\Program Files\Java\jdk-13.0.2\bin\javac.exe" *.java

"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program

goto comment
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe" TestEnv

"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_01.minc    > ..\samples\output_succ_01.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_02.minc    > ..\samples\output_succ_02.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_03.minc    > ..\samples\output_succ_03.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_04.minc    > ..\samples\output_succ_04.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_05.minc    > ..\samples\output_succ_05.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_06.minc    > ..\samples\output_succ_06.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_07.minc    > ..\samples\output_succ_07.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_08.minc    > ..\samples\output_succ_08.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_09.minc    > ..\samples\output_succ_09.txt 
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\succ_10.minc    > ..\samples\output_succ_10.txt 

del ..\samples\output_*.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_01a.minc   > ..\samples\output_fail_01a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_01b.minc   > ..\samples\output_fail_01b.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_01c.minc   > ..\samples\output_fail_01c.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_01d.minc   > ..\samples\output_fail_01d.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_02a.minc   > ..\samples\output_fail_02a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_02b.minc   > ..\samples\output_fail_02b.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_03a.minc   > ..\samples\output_fail_03a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_04a.minc   > ..\samples\output_fail_04a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_04b.minc   > ..\samples\output_fail_04b.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_04c.minc   > ..\samples\output_fail_04c.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_04d.minc   > ..\samples\output_fail_04d.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_04e.minc   > ..\samples\output_fail_04e.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_05a.minc   > ..\samples\output_fail_05a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_07a.minc   > ..\samples\output_fail_07a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08a.minc   > ..\samples\output_fail_08a.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08b.minc   > ..\samples\output_fail_08b.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08c.minc   > ..\samples\output_fail_08c.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08d.minc   > ..\samples\output_fail_08d.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08e.minc   > ..\samples\output_fail_08e.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08f.minc   > ..\samples\output_fail_08f.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08g.minc   > ..\samples\output_fail_08g.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08h.minc   > ..\samples\output_fail_08h.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08i.minc   > ..\samples\output_fail_08i.txt
"C:\Program Files\Java\jdk-13.0.2\bin\java.exe"  Program   ..\samples\fail_08j.minc   > ..\samples\output_fail_08j.txt
:comment

del *.class
