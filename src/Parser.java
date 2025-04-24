//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 15 "Parser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASSIGN=257;
public final static short OR=258;
public final static short AND=259;
public final static short NOT=260;
public final static short EQ=261;
public final static short NE=262;
public final static short LE=263;
public final static short LT=264;
public final static short GE=265;
public final static short GT=266;
public final static short ADD=267;
public final static short SUB=268;
public final static short MUL=269;
public final static short DIV=270;
public final static short MOD=271;
public final static short IDENT=272;
public final static short NUM_LIT=273;
public final static short BOOL_LIT=274;
public final static short BOOL=275;
public final static short NUM=276;
public final static short IF=277;
public final static short ELSE=278;
public final static short WHILE=279;
public final static short PRINT=280;
public final static short RETURN=281;
public final static short BEGIN=282;
public final static short END=283;
public final static short LPAREN=284;
public final static short RPAREN=285;
public final static short LBRACKET=286;
public final static short RBRACKET=287;
public final static short SEMI=288;
public final static short COMMA=289;
public final static short NEW=290;
public final static short DOT=291;
public final static short SIZE=292;
public final static short param_list=293;
public final static short param=294;
public final static short arg_list=295;
public final static short print_stmt=296;
public final static short if_stmt=297;
public final static short while_stmt=298;
public final static short compound_stmt=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    7,    6,   15,    3,    8,   10,
   10,   11,   11,   12,   13,    4,    4,    5,    9,   14,
   14,   14,   14,   14,   14,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    0,   10,    0,    2,
    0,    1,    1,    4,    3,    2,    0,    3,    0,    3,
    3,    3,    1,    1,    4,
};
final static short yydefred[] = {                         3,
    0,    0,    5,    2,    4,    0,    6,    0,    9,    0,
    0,   17,    0,   16,    0,   11,    0,    0,   18,    0,
    0,    8,   10,   12,   13,    0,    0,   24,    0,    0,
    0,   19,    0,    0,    0,   15,   14,    0,   22,    0,
   20,   25,
};
final static short yydgoto[] = {                          1,
    2,    4,    5,   13,   14,    6,    7,   10,   38,   18,
   23,   24,   25,   30,   16,
};
final static short yysindex[] = {                         0,
    0, -271,    0,    0,    0, -255,    0, -273,    0, -272,
 -270,    0, -271,    0, -254,    0, -268, -258,    0, -238,
 -269,    0,    0,    0,    0, -269, -263,    0, -269, -260,
 -259,    0, -251, -269, -269,    0,    0, -253,    0, -245,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,   30,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -246,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -261,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -252,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,   18,    0,    0,    0,    0,
    0,    0,    0,   12,    0,
};
final static int YYTABLESIZE=47;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         23,
   34,   34,   27,   28,    3,   23,   35,   35,   21,   34,
    9,   12,   11,   20,   29,   35,    8,   17,   26,   19,
   32,   35,   21,   23,   22,    7,   23,   36,   37,    1,
   15,   42,   21,   39,    7,   21,    7,   31,    0,    0,
   33,    0,    0,    0,    0,   40,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        261,
  261,  261,  272,  273,  276,  267,  267,  267,  261,  261,
  284,  282,  285,  272,  284,  267,  272,  272,  257,  288,
  284,  267,  281,  285,  283,  272,  288,  288,  288,    0,
   13,  285,  285,  285,  281,  288,  283,   26,   -1,   -1,
   29,   -1,   -1,   -1,   -1,   34,   35,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=299;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASSIGN","OR","AND","NOT","EQ","NE","LE","LT","GE","GT","ADD",
"SUB","MUL","DIV","MOD","IDENT","NUM_LIT","BOOL_LIT","BOOL","NUM","IF","ELSE",
"WHILE","PRINT","RETURN","BEGIN","END","LPAREN","RPAREN","LBRACKET","RBRACKET",
"SEMI","COMMA","NEW","DOT","SIZE","param_list","param","arg_list","print_stmt",
"if_stmt","while_stmt","compound_stmt",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : func_decl",
"prim_type : NUM",
"type_spec : prim_type",
"$$1 :",
"func_decl : type_spec IDENT LPAREN params RPAREN BEGIN local_decls $$1 stmt_list END",
"params :",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : assign_stmt",
"stmt : return_stmt",
"assign_stmt : IDENT ASSIGN expr SEMI",
"return_stmt : RETURN expr SEMI",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : type_spec IDENT SEMI",
"args :",
"expr : expr ADD expr",
"expr : expr EQ expr",
"expr : LPAREN expr RPAREN",
"expr : IDENT",
"expr : NUM_LIT",
"expr : IDENT LPAREN args RPAREN",
};

//#line 104 "Parser.y"
    private Lexer lexer;
    private Token last_token;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
            last_token = (Token)yylval.obj;
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }


    public void yyerror (String error) {
        //System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        int last_token_lineno = 0;
        int last_token_column = 0;
        System.out.println ("Error message by Parser.yyerror() at near " + last_token_lineno+":"+last_token_column + ": " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }
//#line 278 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 47 "Parser.y"
{ Debug("program -> decl_list"                  ); yyval.obj = program____decllist(val_peek(0).obj); }
break;
case 2:
//#line 50 "Parser.y"
{ Debug("decl_list -> decl_list decl"           ); yyval.obj = decllist____decllist_decl(val_peek(1).obj,val_peek(0).obj); }
break;
case 3:
//#line 51 "Parser.y"
{ Debug("decl_list -> eps"                      ); yyval.obj = decllist____eps          (     ); }
break;
case 4:
//#line 54 "Parser.y"
{ Debug("decl -> func_decl"                     ); yyval.obj = decl____funcdecl(val_peek(0).obj); }
break;
case 5:
//#line 57 "Parser.y"
{ Debug("prim_type -> num"                      ); yyval.obj = primtype____NUM(val_peek(0).obj); }
break;
case 6:
//#line 60 "Parser.y"
{ Debug("type_spec -> prim_type"                ); yyval.obj = typespec____primtype(val_peek(0).obj); }
break;
case 7:
//#line 64 "Parser.y"
{ Debug("func_decl -> type_spec ID(params) begin local_decls"              ); yyval.obj = fundecl____typespec_ID_LPAREN_params_RPAREN_BEGIN_localdecls_8X_stmtlist_END(val_peek(6).obj,val_peek(5).obj,val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj          ); }
break;
case 8:
//#line 65 "Parser.y"
{ Debug("                                                    stmt_list end"); yyval.obj =      fundecl____typespec_ID_LPAREN_params_RPAREN_BEGIN_localdecls_X8_stmtlist_END(val_peek(9).obj,val_peek(8).obj,val_peek(7).obj,val_peek(6).obj,val_peek(5).obj,val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 9:
//#line 68 "Parser.y"
{ Debug("params -> eps"                         ); yyval.obj = params____eps(); }
break;
case 10:
//#line 71 "Parser.y"
{ Debug("stmt_list -> stmt_list stmt"           ); yyval.obj = stmtlist____stmtlist_stmt(val_peek(1).obj,val_peek(0).obj); }
break;
case 11:
//#line 72 "Parser.y"
{ Debug("stmt_list -> eps"                      ); yyval.obj = stmtlist____eps          (     ); }
break;
case 12:
//#line 75 "Parser.y"
{ Debug("stmt -> assign_stmt"                   ); yyval.obj = stmt____assignstmt  (val_peek(0).obj); }
break;
case 13:
//#line 76 "Parser.y"
{ Debug("stmt -> return_stmt"                   ); yyval.obj = stmt____returnstmt  (val_peek(0).obj); }
break;
case 14:
//#line 79 "Parser.y"
{ Debug("assign_stmt -> IDENT := expr ;"        ); yyval.obj = assignstmt____IDENT_ASSIGN_expr_SEMI(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 15:
//#line 82 "Parser.y"
{ Debug("return_stmt -> return expr ;"          ); yyval.obj = returnstmt____RETURN_expr_SEMI(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 16:
//#line 85 "Parser.y"
{ Debug("local_decls -> local_decls local_decl" ); yyval.obj = localdecls____localdecls_localdecl(val_peek(1).obj,val_peek(0).obj); }
break;
case 17:
//#line 86 "Parser.y"
{ Debug("local_decls -> eps"                    ); yyval.obj = localdecls____eps(); }
break;
case 18:
//#line 89 "Parser.y"
{ Debug("local_decl -> type_spec IDENT ;"       ); yyval.obj = localdecl____typespec_IDENT_SEMI(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 19:
//#line 92 "Parser.y"
{ Debug("args -> eps"                           ); yyval.obj = args____eps(); }
break;
case 20:
//#line 95 "Parser.y"
{ Debug("expr -> expr ADD expr"                 ); yyval.obj = expr____expr_ADD_expr           (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj   ); }
break;
case 21:
//#line 96 "Parser.y"
{ Debug("expr -> expr EQ  expr"                 ); yyval.obj = expr____expr_EQ_expr            (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj   ); }
break;
case 22:
//#line 97 "Parser.y"
{ Debug("expr -> LPAREN expr RPAREN"            ); yyval.obj = expr____LPAREN_expr_RPAREN      (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj   ); }
break;
case 23:
//#line 98 "Parser.y"
{ Debug("expr -> IDENT"                         ); yyval.obj = expr____IDENT                   (val_peek(0).obj         ); }
break;
case 24:
//#line 99 "Parser.y"
{ Debug("expr -> NUM_LIT"                       ); yyval.obj = expr____NUMLIT                  (val_peek(0).obj         ); }
break;
case 25:
//#line 100 "Parser.y"
{ Debug("expr -> IDENT LPAREN args RPAREN"      ); yyval.obj = expr____IDENT_LPAREN_args_RPAREN(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
//#line 527 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
