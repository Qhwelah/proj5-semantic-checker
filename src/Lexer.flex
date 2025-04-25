/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj

%{

  public Parser   parser;
  public int      lineno;
  public int      column;
  public int      tokencolumns;
  public int      tokenlines;

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
  }

%}

RETURN              = "return"
PRINT               = "print"
IF                  = "if"
ELSE                = "else"
WHILE               = "while"
BEGIN               = "{"
END                 = "}"
LPAREN              = "("
RPAREN              = ")"
LBRACKET            = "["
RBRACKET            = "]"
NUM                 = "num"
BOOL                = "bool"
NEW                 = "new"
SIZE                = "size"
ASSIGN              = "<-"
ADD                 = "+"
SUB                 = "-"
MUL                 = "*"
DIV                 = "/"
MOD                 = "%"
AND                 = "and"
OR                  = "or"
NOT                 = "not"
LT                  = "<"
GT                  = ">"
LE                  = "<="
GE                  = ">="
EQ                  = "="
NE                  = "<>"
SEMI                = ";"
COMMA               = ","
DOT                 = "."
BOOL_LIT            = "true"|"false"
NUM_LIT             = [0-9]+("."[0-9]+)?
IDENT               = [a-zA-Z][a-zA-Z0-9_]*
NEWLINE             = \n
WHITESPACE          = [ \t\r]+
LINECOMMENT         = "%%".*
BLKCOMMENT          = "%("[^]*")%"

%%

{BEGIN}                               { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.BEGIN  ; }
{END}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.END    ; }                 
{RETURN}                              { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.RETURN ; }            
{PRINT}                               { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.PRINT  ; }             
{IF}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.IF     ; }                
{ELSE}                                { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.ELSE   ; }              
{WHILE}                               { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.WHILE  ; }             
{LPAREN}                              { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.LPAREN ; }                 
{RPAREN}                              { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.RPAREN ; }                 
{LBRACKET}                            { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.LBRACKET; }                 
{RBRACKET}                            { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.RBRACKET; }                 
{NUM}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.NUM    ; }               
{BOOL}                                { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.BOOL   ; }              
{NEW}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.NEW    ; }               
{SIZE}                                { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.SIZE   ; }              
{ASSIGN}                              { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.ASSIGN ; }                
{ADD}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.ADD    ; }
{SUB}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.SUB    ; }                
{MUL}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.MUL    ; }                
{DIV}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.DIV    ; }                
{MOD}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.MOD    ; }                
{AND}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.AND    ; }                
{OR}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.OR     ; }                
{NOT}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.NOT    ; }                
{LT}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.LT     ; }                
{GT}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.GT     ; }                
{LE}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.LE     ; }                
{GE}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.GE     ; }                
{EQ}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.EQ     ; }                
{NE}                                  { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.NE     ; }                
{SEMI}                                { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.SEMI   ; }                 
{COMMA}                               { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.COMMA  ; }                 
{DOT}                                 { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.DOT    ; }                 
{BOOL_LIT}                            { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.BOOL_LIT  ; }      
{NUM_LIT}                             { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.NUM_LIT   ; }
{IDENT}                               { parser.yylval = new ParserVal(new Token(yytext())); column += tokencolumns; tokencolumns += yytext().length(); return Parser.IDENT  ; }

{LINECOMMENT}                         { column += yytext().length() ; }
{NEWLINE}                             { column = 1 ; lineno += 1 ; tokencolumns = 0 ; }
{WHITESPACE}                          { column += yytext().length() ; }
{BLKCOMMENT}                          {  
                                      for(int i=1; i<yytext().length(); i++){
                                        if(yytext().charAt(i) == 10 ){ lineno += 1; column = 1; }
                                        else if(yytext().charAt(i) == 13){ column = 0; }
                                        else { column += 1; }
                                      }
                                      column += 0;
                                    }


\b     { System.err.println("Sorry, backspace doesn't work"); }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
