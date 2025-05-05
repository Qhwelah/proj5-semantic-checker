import java.rmi.server.ExportException;
import java.util.*;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class ParserImpl
{
    public static Boolean _debug = true;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }

    // This is for chained symbol table.
    // This includes the global scope only at this moment.
    Env env = new Env(null);
    // this stores the root of parse tree, which will be used to print parse tree and run the parse tree
    ParseTree.Program parsetree_program = null;

    Stack<String> mostRecentFunction = new Stack<>();
    boolean mainReturnsNum = false;
    boolean violatesMain = false;

    Object program____decllist(Object s1) throws Exception
    {
        // 1. check if decllist has main function having no parameters and returns int type
        // 2. assign the root, whose type is ParseTree.Program, to parsetree_program
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;
        parsetree_program = new ParseTree.Program(decllist);

        Object mainFunc = env.Get("main");

        if(mainFunc == null){
            throw new Exception("The program must have one main function that returns num value and has no parameters.");
        }

        if(mainFunc instanceof ParseTreeInfo.FuncDeclInfo){
            // ok
        }
        else {
            throw new Exception("The program must have one main function that returns num value and has no parameters.");
        }

        ParseTreeInfo.FuncDeclInfo mainFuncInfo = (ParseTreeInfo.FuncDeclInfo)mainFunc;

        if(mainReturnsNum && (violatesMain != true)){
            // ok
        }
        else{
            throw new Exception("The program must have one main function that returns num value and has no parameters.");
        }

        return parsetree_program;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // == decl_list == //

    Object decllist____decllist_decl(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;
        ParseTree.FuncDecl                decl = (ParseTree.FuncDecl           )s2;
        decllist.add(decl);
        return decllist;
    }
    Object decllist____eps() throws Exception
    {
        return new ArrayList<ParseTree.FuncDecl>();
    }

    // == decl == //

    Object decl____funcdecl(Object s1) throws Exception
    {
        ParseTree.FuncDecl funcdeclaration = (ParseTree.FuncDecl)s1;
        return funcdeclaration;
    }

    // == func_decl == // //////////////////////////////////////////////////////////////////////////////////////////////

    Object funcdecl____typespec_ID_LPAREN_params_RPAREN_BEGIN_localdecls_8X_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7) throws Exception
    {
        // 1. add function_type_info object (name, return type, params) into the global scope of env
        // 2. create a new symbol table on top of env
        // 3. add parameters into top-local scope of env
        // 4. etc.

        ParseTree.TypeSpec rettype = (ParseTree.TypeSpec)s1;
        Token idToken = (Token)s2;
        ArrayList<ParseTree.Param> params = (ArrayList<ParseTree.Param>)s4;

        String id = idToken.lexeme;
        ParseTreeInfo.FuncDeclInfo info = new ParseTreeInfo.FuncDeclInfo(); // create an info entry for this function
        info.ident = id;
        // info.params = params;
        info.type = rettype.typename;

        ArrayList<ParseTreeInfo.ParamInfo> paramList = new ArrayList<ParseTreeInfo.ParamInfo>();
        int i = 0;
        while(i < params.size()){ // add params to a funcdeclinfo info block
            ParseTreeInfo.ParamInfo tempParam = new ParseTreeInfo.ParamInfo();
            tempParam.type = params.get(i).info.type;
            tempParam.ident = params.get(i).info.ident;
            paramList.add(tempParam);
            i = i+1;
        }
        info.params = paramList;
        env.Put(id, info); // push this function to the env

        env = new Env(env); // make a new env

        i = 0; // add params to env
        while(i < params.size()){
            String name = params.get(i).info.ident;
            if(env.isDuplicate(name)) {
                throw new Exception("Identifier " + name + " is already defined.");
            }
            env.Put(name, params.get(i).info);
            i = i+1;
        }

        ArrayList<ParseTree.LocalDecl> localDecls = (ArrayList<ParseTree.LocalDecl>) s7;
        i = 0; // add local declarations to env
        while(i < localDecls.size()){
            String name = localDecls.get(i).info.ident;
            if(env.isDuplicate(name)) {
                throw new Exception("Identifier " + name + " is already defined.");
            }
            env.Put(name, localDecls.get(i).info);
            i = i+1;
        }

        mostRecentFunction.push(id);
        return null;
    }
    Object funcdecl____typespec_ID_LPAREN_params_RPAREN_BEGIN_localdecls_X8_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7, Object s8, Object s9, Object s10) throws Exception
    {
        // 1. check if this function has at least one return type
        // 2. etc.
        // 3. create and return funcdecl node
        ParseTree.TypeSpec               rettype    = (ParseTree.TypeSpec              )s1;
        Token                            id         = (Token                           )s2;
        ArrayList<ParseTree.Param>       params     = (ArrayList<ParseTree.Param>      )s4;
        ArrayList<ParseTree.LocalDecl>   localdecls = (ArrayList<ParseTree.LocalDecl>  )s7;
        ArrayList<ParseTree.Stmt>        stmtlist   = (ArrayList<ParseTree.Stmt>       )s9;
        Token                            end        = (Token                           )s10;
        ParseTree.FuncDecl funcdecl = new ParseTree.FuncDecl(id.lexeme, rettype, params, localdecls, stmtlist);
        // also exit out of the current env here
        env = env.prev;
        mostRecentFunction.pop();
        return funcdecl;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // == params == //
    Object params____paramlist(Object s1) throws Exception
    {
        List<ParseTree.Param> parseList = (List<ParseTree.Param>)s1;
        return parseList;
    }
    Object params____eps() throws Exception 
    {
        return new ArrayList<ParseTree.Param>();
    }

    // == param_list == //
    Object paramlist____paramlist_COMMA_param(Object s1, Object s2, Object s3) throws Exception
    {
        List<ParseTree.Param> parseList = (List<ParseTree.Param>)s1;
        ParseTree.Param paramVal = (ParseTree.Param)s3;
        parseList.add(paramVal);
        return parseList;
    }
    Object paramlist____param(Object s1) throws Exception
    {
        List<ParseTree.Param> theList = new ArrayList<ParseTree.Param>();
        ParseTree.Param paramVal = (ParseTree.Param)s1;
        theList.add(paramVal);
        return theList;
    }

    // == param == //
    Object param____typespec_IDENT(Object s1, Object s2) throws Exception
    {
        ParseTree.TypeSpec theType = (ParseTree.TypeSpec)s1;
        Token id = (Token)s2;
        String theIdentifier = (id.lexeme);
        ParseTree.Param theTree = new ParseTree.Param(theIdentifier, theType);

        theTree.info.ident = theIdentifier;
        theTree.info.type = theType.typename;

        return theTree;
    }
    
    // == type_spec == //
    Object typespec____primtype(Object s1)
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        ParseTreeInfo.TypeSpecInfo theInfo = (ParseTreeInfo.TypeSpecInfo)primtype.info;
        primtype.info.type = theInfo.type;
        return primtype;
        // return s1;
    }
    Object typespec____primtype_LBRACKET_RBRACKET(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        Token lbr = (Token)s2;
        Token rbr = (Token)s3;
        primtype.typename = primtype.typename + lbr.lexeme + rbr.lexeme; 
        ParseTreeInfo.TypeSpecInfo theInfo = (ParseTreeInfo.TypeSpecInfo)primtype.info;
        theInfo.type = primtype.typename;
        // check array type declaration - DONE
        return primtype;
        // return s1;
    }

    // == prim_type == //
    Object primtype____NUM(Object s1) throws Exception
    {
        Token numKey = (Token)s1;
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec(numKey.lexeme);
        typespec.info.type = numKey.lexeme;
        return typespec;
    }
    Object primtype____BOOL(Object s1) throws Exception
    {
        Token boolKey = (Token)s1;
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec(boolKey.lexeme);
        typespec.info.type = boolKey.lexeme;
        return typespec;
    }

    // == local_decls == // ////////////////////////////////////////////////////////////////////////////////////////////

    Object localdecls____localdecls_localdecl(Object s1, Object s2)
    {
        ArrayList<ParseTree.LocalDecl> localdecls = (ArrayList<ParseTree.LocalDecl>)s1;
        ParseTree.LocalDecl            localdecl  = (ParseTree.LocalDecl           )s2;
        localdecls.add(localdecl);
        return localdecls;
    }
    Object localdecls____eps() throws Exception
    {
        return new ArrayList<ParseTree.LocalDecl>();
    }

    // == local_decl == //
    Object localdecl____typespec_IDENT_SEMI(Object s1, Object s2, Object s3)
    {
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s1;
        Token              id       = (Token             )s2;
        ParseTree.LocalDecl localdecl = new ParseTree.LocalDecl(id.lexeme, typespec);
        
        localdecl.info.ident = id.lexeme;
        localdecl.info.type = typespec.typename; 
        // localdecl.reladdr = 1;
        return localdecl;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // == stmt_list == //
    Object stmtlist____stmtlist_stmt(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>)s1;
        ParseTree.Stmt            stmt     = (ParseTree.Stmt           )s2;
        stmtlist.add(stmt);
        return stmtlist;
    }
    Object stmtlist____eps() throws Exception
    {
        return new ArrayList<ParseTree.Stmt>();
    }

    // == stmt == //
    Object stmt____assignstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.AssignStmt);
        return s1;
    }
    Object stmt____printstmt (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.PrintStmt);
        return s1;
    }
    Object stmt____returnstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.ReturnStmt);
        return s1;
    }
    Object stmt____ifstmt (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.IfStmt);
        return s1;
    }
    Object stmt____whilestmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.WhileStmt);
        return s1;
    }
    Object stmt____compoundstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.CompoundStmt);
        return s1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // == assign_stmt == //
    Object assignstmt____IDENT_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        // 1. check if ident.value_type matches with expr.value_type
        // 2. etc.
        // e. create and return node
        Token          id     = (Token         )s1;
        Token          assign = (Token         )s2;
        ParseTree.Expr expr   = (ParseTree.Expr)s3;
        Object infoOfIdent = env.Get(id.lexeme);
        ParseTreeInfo.ExprInfo exprInfo = (ParseTreeInfo.ExprInfo)expr.info;

        // will need several options for this: (instanceOf)
        //ParseTreeInfo.LocalDeclInfo varInfo = (ParseTreeInfo.LocalDeclInfo)var;

        String theType = null;

        if (infoOfIdent instanceof ParseTreeInfo.ExprInfo) {
            theType = ((ParseTreeInfo.ExprInfo) infoOfIdent).type;
        } else if (infoOfIdent instanceof ParseTreeInfo.ParamInfo) {
            theType = ((ParseTreeInfo.ParamInfo) infoOfIdent).type;
        } else if (infoOfIdent instanceof ParseTreeInfo.LocalDeclInfo) {
            theType = ((ParseTreeInfo.LocalDeclInfo) infoOfIdent).type;
        } else { // if null or other type
            throw new Exception("Identifier " + id.lexeme + " is not defined.");
            //System.err.println("Unknown or null info object: " + infoOfIdent);
        }
        // theType is the type of id

        if(theType.equals(exprInfo.type)){
            // ok
        }
        else {
            throw new Exception("Variable " + id.lexeme + " should have " + theType + 
            " value, instead of " + exprInfo.type + " value.");
        }


        // // Semantic analyzer not yet implemented
        // Object id_type = env.Get(id.lexeme);
        // {
        //     // check if expr.type matches with id_type
        //     if(id_type.equals("num")
        //         && (expr instanceof ParseTree.ExprNumLit)
        //         )
        //     { // ok
        //     }
        //     else if(id_type.equals("num")
        //         && (expr instanceof ParseTree.ExprFuncCall)
        //         && (env.Get(((ParseTree.ExprFuncCall)expr).ident).equals("num()"))
        //         )
        //     { // ok
        //     }
        //     else
        //     {
        //         throw new Exception("semantic error");
        //     }
        // }
        ParseTree.AssignStmt stmt = new ParseTree.AssignStmt(id.lexeme, expr);
        //stmt.ident_reladdr = 1;
        return stmt;
    }
    Object assignstmt____IDENT_LBRACKET_expr_RBRACKET_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7) throws Exception
    {
        Token id = (Token)s1;
        // Token lbr = (Token)s2;
        ParseTree.Expr expr1 = (ParseTree.Expr)s3;
        // Token rbr = (Token)s4;
        Token assign = (Token)s5;
        ParseTree.Expr expr2 = (ParseTree.Expr)s6;
        Token semi = (Token)s7;
        String idName = id.lexeme;
        ParseTree.AssignStmtForArray stmtOut = new ParseTree.AssignStmtForArray(idName, expr1, expr2);
        // check that expr is a num (not bool)
        if(expr1.info.type.equals("num")){
            // ok
        }
        else{
            throw new Exception("Array index must be num value.");
        }
        // check that expr2 matches id content type
        Object theVar = env.Get(idName);
        if(theVar == null){
            throw new Exception("Identifier " + idName + " is not defined.");
        }
        String theType = null;
        if(theVar instanceof ParseTreeInfo.LocalDeclInfo){
            ParseTreeInfo.LocalDeclInfo varInfo = (ParseTreeInfo.LocalDeclInfo)theVar;
            theType = varInfo.type;
        }
        else if(theVar instanceof ParseTreeInfo.ArgInfo){
            ParseTreeInfo.ArgInfo varInfo = (ParseTreeInfo.ArgInfo)theVar;
            theType = varInfo.type;
        }

        if(theType == null){ throw new Exception("Array type not found.");}
        if(theType.equals("num[]")){theType = "num";}
        else if(theType.equals("bool[]")){theType = "bool";}
        
        if(theType.equals(expr2.info.type)){
            // ok
        }
        else{
            throw new Exception("Element of array " + id.lexeme + " should have a " 
            + theType + " value, instead of a " + expr2.info.type + " value.");
        }
        return stmtOut;
    }

    // == print_stmt == //
    Object printstmt____PRINT_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        Token print = (Token) s1;
        ParseTree.Expr theExpr = (ParseTree.Expr) s2;
        Token semi = (Token) s3;
        ParseTree.PrintStmt stmt = new ParseTree.PrintStmt(theExpr);
        return stmt;
    }

    // == return_stmt == //
    Object returnstmt____RETURN_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr.value_type matches with the current function return type
        // 2. etc.
        // 3. create and return node
        ParseTree.Expr expr = (ParseTree.Expr)s2;

        // get return types of most recent function
        String theFunc = mostRecentFunction.peek();
        Object infoIn = env.Get(theFunc);
        if(infoIn instanceof ParseTreeInfo.FuncDeclInfo){
            // ok
        }
        else {
            throw new Exception("Identifier " + theFunc + " should be a function.");
        }
        ParseTreeInfo.FuncDeclInfo funcInfo = (ParseTreeInfo.FuncDeclInfo)infoIn;
        // check if return types match
        if(funcInfo.type.equals(expr.info.type)){
            // ok
        }
        else {
            throw new Exception("Function " + theFunc + "() should return a " + funcInfo.type 
                + " value, instead of a " + expr.info.type + " value.");
        }

        if(theFunc.equals("main")){
            if(expr.info.type.equals("num")){
                mainReturnsNum = true;
            }
            else {
                violatesMain = true;
            }
        }

        return new ParseTree.ReturnStmt(expr);
    }

    // == if_stmt == //
    Object ifstmt____IF_LPAREN_expr_RPAREN_stmt_ELSE_stmt(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7) throws Exception
    {
        Token theIf = (Token)s1;
        Token lparen = (Token)s2;
        ParseTree.Expr expr = (ParseTree.Expr)s3;
        Token rparen = (Token)s4;
        ParseTree.Stmt stmt1 = (ParseTree.Stmt)s5;
        Token theElse = (Token)s6;
        ParseTree.Stmt stmt2 = (ParseTree.Stmt)s7;
        ParseTree.IfStmt stmtOut = new ParseTree.IfStmt(expr, stmt1, stmt2);
        
        // Check if expr is a bool
        if(expr.info.type.equals("bool")){
            // ok
        }
        else {
            throw new Exception("Condition of if or while statement should be a bool value.");
        }

        return stmtOut;
    }

    // == while_stmt == //
    Object whilestmt____WHILE_LPAREN_expr_RPAREN_stmt(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception
    {
        Token theWhile = (Token)s1;
        Token lparen = (Token)s2;
        ParseTree.Expr expr = (ParseTree.Expr)s3;
        Token rparen = (Token)s4;
        ParseTree.Stmt stmt1 = (ParseTree.Stmt)s5;
        ParseTree.WhileStmt stmtOut = new ParseTree.WhileStmt(expr, stmt1);
        // Check if expr is a bool
        if(expr.info.type.equals("bool")){
            // ok
        }
        else {
            throw new Exception("Condition of if or while statement should be a bool value.");
        }
        return stmtOut;
    }

    // == compound_stmt == //
    Object compoundstmt____BEGIN_localdecls_3X_stmtlist_END(Object s1, Object s2) throws Exception
    {
        env = new Env(env);
        ArrayList<ParseTree.LocalDecl> localDecls = (ArrayList<ParseTree.LocalDecl>)s2;
        int i = 0;
        while(i < localDecls.size()){
            env.Put(localDecls.get(i).info.ident, localDecls.get(i).info);
            i = i+1;
        }
        return null;
    }
    Object compoundstmt____BEGIN_localdecls_X3_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception
    {
        Token begin = (Token)s1;
        ArrayList<ParseTree.LocalDecl> localDeclsList = (ArrayList<ParseTree.LocalDecl>)s2;
        ArrayList<ParseTree.Stmt> stmtList = (ArrayList<ParseTree.Stmt>)s4;
        Token end = (Token)s5;
        ParseTree.CompoundStmt stmtOut = new ParseTree.CompoundStmt(localDeclsList, stmtList);
        env = env.prev;
        return stmtOut;
    }
    
    // == args == //
    Object args____arglist(Object s1) throws Exception
    {
        List<ParseTree.Arg> theList = (List<ParseTree.Arg>)s1;
        return theList;
    }
    Object args____eps() throws Exception
    {
        ArrayList<ParseTree.Arg> arglist = new ArrayList<ParseTree.Arg>();
        return arglist;
    }

    // == arg_list == //
    Object arglist____arglist_COMMA_expr(Object s1, Object s2, Object s3) throws Exception
    {
        List<ParseTree.Arg> theList = (List<ParseTree.Arg>)s1;
        Token comma = (Token)s2;
        ParseTree.Expr expr = (ParseTree.Expr)s3;
        ParseTree.Arg theArg = new ParseTree.Arg(expr);
        theArg.info.type = ((ParseTreeInfo.ExprInfo)expr.info).type;
        theList.add(theArg);
        return theList;
    }
    Object arglist____expr(Object s1) throws Exception
    {
        List<ParseTree.Arg> theList = new ArrayList<ParseTree.Arg>();
        ParseTree.Expr expr = (ParseTree.Expr)s1;
        ParseTree.Arg theArg = new ParseTree.Arg(expr);
        theArg.info.type = ((ParseTreeInfo.ExprInfo)expr.info).type;
        theList.add(theArg);
        return theList;
    }

    // == expr == // ///////////////////////////////////////////////////////////////////////////////////////////////////
    Object expr____expr_ADD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprAdd expr = new ParseTree.ExprAdd(expr1,expr2);
        expr.info.type = typeOut; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_SUB_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprSub expr = new ParseTree.ExprSub(expr1,expr2);
        expr.info.type = typeOut; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_MUL_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprMul expr = new ParseTree.ExprMul(expr1,expr2);
        expr.info.type = typeOut; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_DIV_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprDiv expr = new ParseTree.ExprDiv(expr1,expr2);
        expr.info.type = typeOut; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_MOD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprMod expr = new ParseTree.ExprMod(expr1,expr2);
        expr.info.type = typeOut; // assign the output type to whatever input was
        return expr;
    }
    //
    Object expr____expr_EQ_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type)){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprEq expr = new ParseTree.ExprEq(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_NE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type)){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprNe expr = new ParseTree.ExprNe(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_LE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprLe expr = new ParseTree.ExprLe(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_LT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprLt expr = new ParseTree.ExprLt(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_GE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprGe expr = new ParseTree.ExprGe(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_GT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("num")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprGt expr = new ParseTree.ExprGt(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    //
    Object expr____expr_AND_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("bool")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprAnd expr = new ParseTree.ExprAnd(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____expr_OR_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type
        String typeOut = null;
        if(expr1.info.type.equals(expr2.info.type) && expr1.info.type.equals("bool")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Binary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " and " + expr2.info.type + " values.");
        }
        ParseTree.ExprOr expr = new ParseTree.ExprOr(expr1,expr2);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    //
    Object expr____NOT_expr(Object s1, Object s2) throws Exception
    {
        Token          oper  = (Token         )s1;
        ParseTree.Expr expr1  = (ParseTree.Expr)s2;
        String typeOut = null;
        if(expr1.info.type.equals("bool")){
            typeOut = expr1.info.type;
        } else {
            throw new Exception("Unary operation " + oper.lexeme + " cannot be used with "
             + expr1.info.type + " value.");
        }
        ParseTree.ExprNot expr = new ParseTree.ExprNot(expr1);
        expr.info.type = "bool"; // assign the output type to whatever input was
        return expr;
    }
    Object expr____LPAREN_expr_RPAREN(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. create and return node whose value_type is the same to the expr.value_type
        Token          lparen = (Token         )s1;
        ParseTree.Expr expr   = (ParseTree.Expr)s2;
        Token          rparen = (Token         )s3;
        ParseTree.ExprParen exprOut = new ParseTree.ExprParen(expr);
        exprOut.info.type = expr.info.type;
        return exprOut;
    }
    Object expr____IDENT(Object s1) throws Exception
    {
        // 1. check if id.lexeme can be found in chained symbol tables
        // 2. check if it is variable type
        // 3. etc.
        // 4. create and return node that has the value_type of the id.lexeme
        Token id = (Token)s1;
        ParseTree.ExprIdent expr = new ParseTree.ExprIdent(id.lexeme);
        Object infoOfIdent = env.Get(id.lexeme);

        String theType = null;

        if(infoOfIdent == null){
            throw new Exception("Identifier " + id.lexeme + " is not defined.");
        }

        if (infoOfIdent instanceof ParseTreeInfo.ExprInfo) {
            theType = ((ParseTreeInfo.ExprInfo) infoOfIdent).type;
        } else if (infoOfIdent instanceof ParseTreeInfo.ParamInfo) {
            theType = ((ParseTreeInfo.ParamInfo) infoOfIdent).type;
        } else if (infoOfIdent instanceof ParseTreeInfo.LocalDeclInfo) {
            theType = ((ParseTreeInfo.LocalDeclInfo) infoOfIdent).type;
        } else { // if null or other type
            throw new Exception("Identifier " + id.lexeme + " should be a variable.");
            //System.err.println("Unknown or null info object: " + infoOfIdent);
        }
        expr.info.type = theType;
        // expr.reladdr = 1;
        return expr;
    }
    Object expr____NUMLIT(Object s1) throws Exception
    {
        // 1. create and return node that has int type
        Token token = (Token)s1;
        double value = Double.parseDouble(token.lexeme);
        ParseTree.ExprNumLit expr = new ParseTree.ExprNumLit(value);
        
        // specify the number type for hardcoded numbers
        // ParseTree.TypeSpec type = new ParseTree.TypeSpec("num"); 
        expr.info.type = new String("num");
        return expr;
    }
    // Object expr____NUMLIT(Object s1) throws Exception
    // {
    //     Double numlit = (Double)s1;
    //     return new ParseTree.ExprNumLit(numlit);
    // }
    Object expr____BOOLLIT(Object s1) throws Exception
    {
        Token theToken = (Token)s1;
        boolean value = Boolean.parseBoolean(theToken.lexeme);

        ParseTree.ExprBoolLit expr = new ParseTree.ExprBoolLit(value);
        // expr.info.type = new ParseTree.TypeSpec("bool");
        expr.info.type = new String("bool");
        return expr;
    }
    Object expr____IDENT_LPAREN_args_RPAREN(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        // 1. check if id.lexeme can be found in chained symbol tables
        // 2. check if it is function type
        // 3. check if the number and types of env(id.lexeme).params match with those of args
        // 4. etc.
        // 5. create and return node that has the value_type of env(id.lexeme).return_type
        Token                    id   = (Token                   )s1;
        ArrayList<ParseTree.Arg> args = (ArrayList<ParseTree.Arg>)s3;

        String[] argNums = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th"};
        
        Object func_attr = env.Get(id.lexeme);
        ParseTreeInfo.FuncDeclInfo funcDescr = null;
        if(func_attr == null){
            throw new Exception("Identifier " + id.lexeme + " is not defined.");
        }
        else if(func_attr instanceof ParseTreeInfo.FuncDeclInfo){
            // pass
        }
        else{
            throw new Exception("Identifier " + id.lexeme + " should be a function.");
        }
        funcDescr = ((ParseTreeInfo.FuncDeclInfo)func_attr);
        if(funcDescr.params.size() != args.size()){
            throw new Exception("Function " + id.lexeme +"() should be called with the correct number of arguments.");
        }
        int i = 0;
        while(i < funcDescr.params.size()){
            ParseTree.Arg arg = args.get(i);
            ParseTreeInfo.ArgInfo argInfo = (ParseTreeInfo.ArgInfo)arg.info;
            ParseTreeInfo.ParamInfo shouldBe = funcDescr.params.get(i);
            if(argInfo.type.equals(shouldBe.type)){
                //types match, so pass
            }
            else {
                throw new Exception("The " + argNums[i] + " argument of function " + funcDescr.ident 
                + "() should be " + shouldBe.type + " value, instead of " + argInfo.type + " value.");
            }
            i = i + 1;
        }
        // Object func_attr = env.Get(id.lexeme);
        // {
        //     // check if argument types match with function param types
        //     if(env.Get(id.lexeme).equals("num()")
        //         && (args.size() == 0)
        //         )
        //     {} // ok
        //     else
        //     {
        //         throw new Exception("semantic error");
        //     }
        // }
        ParseTree.ExprFuncCall expr = new ParseTree.ExprFuncCall(id.lexeme, args);
        expr.info.type = funcDescr.type;
        return expr;
    }
    Object expr____NEW_primtype_LBRACKET_expr_RBRACKET(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception
    {
        Token theNew = (Token)s1;
        ParseTree.TypeSpec thePrimType = (ParseTree.TypeSpec)s2;
        // Token lbr = (Token)s3;
        ParseTree.Expr expr = (ParseTree.Expr)s4;
        // Token rbr = (Token)s5;
        ParseTree.ExprArrayNew theArray = new ParseTree.ExprArrayNew(thePrimType, expr);

        // check if expr is a number
        if(expr.info.type.equals("num")){
            // good
        }
        else{
            throw new Exception("Array index must be num value.");
        }
        // change info out to an array
        ParseTreeInfo.TypeSpecInfo info = (ParseTreeInfo.TypeSpecInfo)thePrimType.info;
        theArray.info.type = (info.type + "[]"); // array type is primtype[]
        return theArray;
    }
    Object expr____IDENT_LBRACKET_expr_RBRACKET(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        Token id = (Token)s1;
        // Token lbr = (Token)s2;
        ParseTree.Expr expr = (ParseTree.Expr)s3;
        // Token rbr = (Token)s4;
        String idName = id.lexeme;
        ParseTree.ExprArrayElem theElem = new ParseTree.ExprArrayElem(idName, expr);
        Object info = env.Get(idName);
        if(info == null){
            throw new Exception("Identifier " + idName + " is not defined.");
        }
        // check if expr is a number
        if(expr.info.type.equals("num")){
            // ok
        }
        else {
            throw new Exception("Array index must be num value.");
        }
        String theType = null;
        if(info instanceof ParseTreeInfo.ExprInfo){
            theType = ((ParseTreeInfo.ExprInfo)info).type;
        }
        else if (info instanceof ParseTreeInfo.LocalDeclInfo){
            theType = ((ParseTreeInfo.LocalDeclInfo)info).type;
            // if ident is not an array type, throw an error
            if(theType.equals("bool") || theType.equals("num")){
                throw new Exception("Identifier " + idName + " should be an array variable.");
            }
            
            // already know expr is a num from prev step
            if(theType.equals("bool[]")) {theType = "bool";}
            else if(theType.equals("num[]")) {theType = "num";}
        }
        else {
            throw new Exception("Array type returned invalid.");
        }
        //ParseTreeInfo.ExprInfo exprInfo = (ParseTreeInfo.ExprInfo)info;
        theElem.info.type = theType;
        return theElem;
    }
    Object expr____IDENT_DOT_SIZE(Object s1, Object s2, Object s3) throws Exception
    {
        Token id = (Token)s1;
        Token dot = (Token)s2;
        Token size = (Token)s3;
        String idName = (id.lexeme);
        ParseTree.ExprArraySize exprArr = new ParseTree.ExprArraySize(idName);
        Object info = env.Get(idName);
        if(info == null){
            throw new Exception("Identifier " + idName + " is not defined.");
        }
        ParseTreeInfo.ExprInfo exprInfo = (ParseTreeInfo.ExprInfo)info;
        exprArr.info.type = exprInfo.type;
        return exprArr;
    }

    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
