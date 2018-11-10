package br.ufpe.cin.if688.minijava.main;

import br.ufpe.cin.if688.minijava.ANTLR.gParser;
import br.ufpe.cin.if688.minijava.ANTLR.gVisitor;
import br.ufpe.cin.if688.minijava.ast.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufpe.cin.if688.minijava.ANTLR.gParser.*;

public class MiniJavaVisitor implements gVisitor {

    @Override
    public Object visitProgram(ProgramContext ctx) {
        MainClass main = (MainClass) ctx.mainClass().accept(this);
        ClassDeclList list = new ClassDeclList();

        for(gParser.ClassDeclContext classDecl : ctx.classDecl()) {
            list.addElement((ClassDecl) classDecl.accept(this));
        }

        return new Program(main, list);
    }

    @Override
    public Object visitMainClass(MainClassContext ctx) {
        Identifier i1 = (Identifier) ctx.identifier(0).accept(this);
        Identifier i2 = (Identifier) ctx.identifier(1).accept(this);
        Statement stm = (Statement) ctx.statement().accept(this);

        return new MainClass(i1, i2, stm);
    }

    @Override
    public Object visitClassDecl(ClassDeclContext ctx) {
        VarDeclList vl = new VarDeclList();
        for (VarDeclContext vc : ctx.varDecl()) {
            VarDecl v = (VarDecl) vc.accept(this);
            vl.addElement(v);
        }

        MethodDeclList ml = new MethodDeclList();
        for (MethodDeclContext mc : ctx.methodDecl()){
            MethodDecl md = (MethodDecl) mc.accept(this);
            ml.addElement(md);
        }

        Identifier i1 = (Identifier) ctx.identifier(0).accept(this);
        if (ctx.identifier().size() > 1) {
            Identifier i2 = (Identifier) ctx.identifier(1).accept(this);
            return new ClassDeclExtends(i1, i2, vl, ml);
        }
        return new ClassDeclSimple(i1, vl, ml);
    }

    @Override
    public Object visitMethodDecl(MethodDeclContext ctx) {
        Type t = (Type) ctx.type(0).accept(this);
        Identifier i = (Identifier) ctx.identifier(0).accept(this);

        FormalList fl = new FormalList();
        for(int j = 1; j < ctx.type().size(); j++) {
            Formal f = new Formal((Type) ctx.type(j).accept(this), (Identifier) ctx.identifier(j).accept(this));
            fl.addElement(f);
        }

        VarDeclList vl = new VarDeclList();
        for(VarDeclContext vc : ctx.varDecl()) {
            VarDecl v = (VarDecl) vc.accept(this);
            vl.addElement(v);
        }

        StatementList sl = new StatementList();
        for(StatementContext sc : ctx.statement()) {
            Statement s = (Statement) sc.accept(this);
            sl.addElement(s);
        }

        Exp e = (Exp) ctx.exp().accept(this);

        return new MethodDecl(t, i, fl, vl, sl, e);
    }

    @Override
    public Object visitVarDecl(VarDeclContext ctx) {
        Type t = (Type) ctx.type().accept(this);
        Identifier i = (Identifier) ctx.identifier().accept(this);

        return new VarDecl(t, i);
    }

    @Override
    public Object visitExp(ExpContext ctx) {
        int noExp = ctx.exp().size();

        // | 'true'                                           0 // True
        // | 'false'                                          0 // False
        // | 'this'                                           0 // This
        // | 'new' identifier '(' ')'                         0 // NewObject
        // | identifier                                       0 // IdentifierExp
        // | integerLiteral                                   0 // IntegerLiteral

        if (noExp == 0) {
            String s = ctx.getText();
            if("true".equals(s)) return new True();
            else if("false".equals(s)) return new False();
            else if("this".equals(s)) return new This();
            else if(s != null && "n".equals(s.substring(0,1))){
                Identifier i = (Identifier) ctx.identifier().accept(this);
                return new NewObject(i);
            }
            else if(s != null && s.matches("[-+]?\\d*\\.?\\d+")) { // checks if s is Numeric (thank you stack overflow)
                return ctx.integerLiteral().accept(this);
            }
            else {
                return new IdentifierExp(ctx.getText());
            }
        }

        // | '(' exp ')'                                      1 // (exp) is permited
        // | '!' exp                                          1 // Not
        // | 'new' 'int' '[' exp ']'                          1 // NewArray
        // | exp '.length'                                    1 // ArrayLength

        else if (noExp == 1) {
            String s = ctx.getText();
            Exp e = (Exp) ctx.exp(0).accept(this);

            if("(".equals(s)) return e;
            else if("!".equals(s)) return new Not(e);
            else if("new".equals(s)) return new NewArray(e);
            else return new ArrayLength(e);
        }

        // | exp '[' exp ']'                                  2 4  // ArrayLookup
        // : exp ('&&' | '<' | '-' | '+' | '*') exp           2 3  // And, LessThan, Minus, Plus, Times
        // | exp '.' identifier '(' ( exp ( ',' exp )* )? ')' 1+ 5+ // Call

        else {
            int noChildren = ctx.getChildCount();
            if (noChildren == 3) {
                Exp e1 = (Exp) ctx.exp(0).accept(this);
                Exp e2 = (Exp) ctx.exp(1).accept(this);

                String op = ctx.getChild(1).getText();

                if ("&&".equals(op)) return new And(e1,e2);
                else if ("+".equals(op)) return new Plus(e1,e2);
                else if ("-".equals(op)) return new Minus(e1,e2);
                else if ("<".equals(op)) return new LessThan(e1,e2);
                else return new Times(e1,e2);
            }
            else if (noChildren == 4) {
                Exp e1 = (Exp) ctx.exp(0).accept(this);
                Exp e2 = (Exp) ctx.exp(1).accept(this);

                return new ArrayLookup(e1, e2);
            }
            else {
                Exp e = (Exp) ctx.exp(0).accept(this);
                Identifier i = (Identifier) ctx.identifier().accept(this);
                ExpList el = new ExpList();
                for (int j = 1; j<ctx.exp().size(); j++){
                    el.addElement((Exp) ctx.exp(j).accept(this));
                }
                return new Call(e, i, el);
            }

        }
    }

    @Override
    public Object visitStatement(StatementContext ctx) {
        String s = ctx.getStart().getText();
        if ("{".equals(s)) {
            StatementList sl = new StatementList();
            for( StatementContext stmCtx : ctx.statement()) {
                Statement stm = (Statement) stmCtx.accept(this);
                sl.addElement(stm);
            }

            return new Block(sl);
        }
        else if ("if".equals(s)) {
            Exp e = (Exp) ctx.exp(0).accept(this);
            Statement s1 = (Statement) ctx.statement(0).accept(this);
            Statement s2 = (Statement) ctx.statement(1).accept(this);

            return new If(e,s1, s2);
        }
        else if ("System.out.println".equals(s)) {
            Exp e = (Exp) ctx.exp(0).accept(this);
            return new Print(e);
        }
        else if ("while".equals(s)) {
            Exp e = (Exp) ctx.exp(0).accept(this);
            Statement s1 = (Statement) ctx.statement(0).accept(this);

            return new While(e, s1);
        }
        else {
            if(ctx.exp().size() > 1) {
                Identifier i = (Identifier) ctx.identifier().accept(this);
                Exp e1 = (Exp) ctx.exp(0).accept(this);
                Exp e2 = (Exp) ctx.exp(1).accept(this);

                return new ArrayAssign(i, e1, e2);
            }
            else {
                Identifier i = (Identifier) ctx.identifier().accept(this);
                Exp e = (Exp) ctx.exp(0).accept(this);

                return new Assign(i, e);
            }
        }
    }

    @Override
    public Object visitType(TypeContext ctx) {
        String str = ctx.getText();

        if(str.equals("boolean"))    return new BooleanType();
        else if(str.equals("int[]")) return new IntArrayType();
        else if (str.equals("int"))  return new IntegerType();
        else                         return new IdentifierType(str);
    }

    @Override
    public Object visitIdentifier(IdentifierContext ctx) {
        return new Identifier(ctx.getText());
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteralContext ctx) {
        return new IntegerLiteral(Integer.parseInt(ctx.getText()));
    }

    @Override
    public Object visit(ParseTree parseTree) {
        return parseTree.accept(this);
    }

    @Override
    public Object visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Object visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Object visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
