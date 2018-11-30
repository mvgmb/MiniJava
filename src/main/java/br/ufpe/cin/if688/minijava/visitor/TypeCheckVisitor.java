package br.ufpe.cin.if688.minijava.visitor;

import br.ufpe.cin.if688.minijava.exceptions.Exception;
import br.ufpe.cin.if688.minijava.ast.*;
import br.ufpe.cin.if688.minijava.symboltable.Class;
import br.ufpe.cin.if688.minijava.symboltable.Method;
import br.ufpe.cin.if688.minijava.symboltable.SymbolTable;
import br.ufpe.cin.if688.minijava.symboltable.Variable;

public class TypeCheckVisitor implements IVisitor<Type> {

    private static Type BOOLEAN = new BooleanType();
    private static Type INT_ARRAY = new IntArrayType();
    private static Type INTEGER = new IntegerType();
    private SymbolTable symbolTable;
    private Class currClass;
    private Method currMethod;
    public TypeCheckVisitor(SymbolTable st) {
        symbolTable = st;
    }

    // MainClass m;
    // ClassDeclList cl;
    public Type visit(Program n) {
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.elementAt(i).accept(this);
        }
        return null;
    }

    // Identifier i1,i2;
    // Statement s;
    public Type visit(MainClass n) {
        // Updating current class and method
        currClass = symbolTable.getClass(n.i1.toString());
        currMethod = currClass.getMethod("main");

        n.i1.accept(this);
        n.i2.accept(this);
        n.s.accept(this);

        // Resetting current class and method
        currClass = null;
        currMethod = null;

        return null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n) {
        // Updating current class
        currClass = symbolTable.getClass(n.i.toString());

        n.i.accept(this);
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept(this);
        }
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.elementAt(i).accept(this);
        }

        // Resetting current class
        currClass = null;

        return null;
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n) {
        // Updating current class
        currClass = symbolTable.getClass(n.i.toString());

        n.i.accept(this);
        n.j.accept(this);
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept(this);
        }
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.elementAt(i).accept(this);
        }

        // Resetting current class
        currClass = null;

        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(VarDecl n) {
        n.t.accept(this);
        n.i.accept(this);
        return null;
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n) {
        // Updating current method
        currMethod = currClass.getMethod(n.i.toString());

        Type returnShouldBe = n.t.accept(this);

        n.i.accept(this);
        for (int i = 0; i < n.fl.size(); i++) {
            n.fl.elementAt(i).accept(this);
        }
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept(this);
        }
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).accept(this);
        }

        Type returnType = n.e.accept(this);

        // Check if method's return is the same as the method declaration
        if (!symbolTable.compareTypes(returnShouldBe, returnType)) {
            Exception.error(returnShouldBe, returnType);
        }


        // Resetting current method
        currMethod = null;

        return null;
    }

    // Type t;
    // Identifier i;
    public Type visit(Formal n) {
        n.i.accept(this);
        return n.t.accept(this);
    }

    public Type visit(IntArrayType n) {
        return INT_ARRAY;
    }

    public Type visit(BooleanType n) {
        return BOOLEAN;
    }

    public Type visit(IntegerType n) {
        return INTEGER;
    }

    // String s;
    public Type visit(IdentifierType n) {
        return n;
    }

    // StatementList sl;
    public Type visit(Block n) {
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).accept(this);
        }
        return null;
    }

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n) {
        Type e = n.e.accept(this);
        if (!(e instanceof BooleanType)) {
            Exception.error(BOOLEAN, e);
        }

        n.s1.accept(this);
        n.s2.accept(this);
        return null;
    }

    // Exp e;
    // Statement s;
    public Type visit(While n) {
        Type e = n.e.accept(this);
        if (!(e instanceof BooleanType)) {
            Exception.error(BOOLEAN, e);
        }

        n.s.accept(this);
        return null;
    }

    // Exp e;
    public Type visit(Print n) {
        n.e.accept(this);
        return null;
    }

    // Identifier i;
    // Exp e;
    public Type visit(Assign n) {
        Type i = n.i.accept(this);
        Type e = n.e.accept(this);
        if (!(symbolTable.compareTypes(i, e))) {
            Exception.error(i, e);
        }
        return null;
    }

    // Identifier i;
    // Exp e1,e2;
    public Type visit(ArrayAssign n) {
        // identifier '[' exp ']' '=' exp '
        Type i = n.i.accept(this);
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(i instanceof IntArrayType)) {
            Exception.error(INT_ARRAY, i);
        }
        if (!(e1 instanceof IntegerType)) {
            Exception.error(INTEGER, e1);
        }
        if (!(e2 instanceof IntegerType)) {
            Exception.error(INTEGER, e1);
        }

        return null;
    }

    // Exp e1,e2;
    public Type visit(And n) {
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(e1 instanceof BooleanType))
            Exception.error(BOOLEAN, e1);
        if (!(e2 instanceof BooleanType))
            Exception.error(BOOLEAN, e2);

        return BOOLEAN;
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(e1 instanceof IntegerType))
            Exception.error(INTEGER, e1);
        if (!(e2 instanceof IntegerType))
            Exception.error(INTEGER, e2);

        return BOOLEAN;
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(e1 instanceof IntegerType))
            Exception.error(INTEGER, e1);
        if (!(e2 instanceof IntegerType))
            Exception.error(INTEGER, e2);

        return INTEGER;
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(e1 instanceof IntegerType))
            Exception.error(INTEGER, e1);
        if (!(e2 instanceof IntegerType))
            Exception.error(INTEGER, e2);

        return INTEGER;
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(e1 instanceof IntegerType))
            Exception.error(INTEGER, e1);
        if (!(e2 instanceof IntegerType))
            Exception.error(INTEGER, e2);

        return INTEGER;
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        Type e1 = n.e1.accept(this);
        Type e2 = n.e2.accept(this);

        if (!(e1 instanceof IntArrayType)) {
            Exception.error(INT_ARRAY, e1);
        }
        if (!(e2 instanceof IntegerType)) {
            Exception.error(INTEGER, e2);
        }

        return INTEGER;
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        Type e = n.e.accept(this);

        if (!(e instanceof IntArrayType)) {
            Exception.error(INT_ARRAY, e);
        }

        return INTEGER;
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {
//      (new Fac()).ComputeFac(10);
//      this.Function(12);

//	    exp '.' identifier '(' ( exp ( ',' exp )* )? ')'

        // Storing current class
        Class originalClass = currClass;

        // This will be the returned type
        Type returnType = null;

        Type e = n.e.accept(this);

        if (n.e instanceof This)
            returnType = currClass.getMethod(n.i.toString()).type();
        else {
            currClass = symbolTable.getClass(((IdentifierType) e).s);
        }

        Type id = n.i.accept(this);

        if (!(n.e instanceof This)) returnType = id;

        Class tmp = currClass;

        int i;
        for (i = 0; i < n.el.size(); i++) {
            boolean isThis = (n.el.elementAt(i) instanceof This);
            if (isThis) currClass = originalClass;

            Type el = n.el.elementAt(i).accept(this);

            if (isThis) currClass = tmp;
            Variable var = currClass.getMethod(n.i.toString()).getParamAt(i);

            if (var == null) {
                throw new RuntimeException("Method " + currClass.getMethod(n.i.toString()).getId() + " call has too many arguments");
            }

            Type param = currClass.getMethod(n.i.toString()).getParamAt(i).type();

            if (!(symbolTable.compareTypes(param, el) || symbolTable.compareTypes(el, param))) {
                Exception.error(param, el);
            }
        }

        if(currClass.getMethod(n.i.toString()).getParamAt(i) != null){
            throw new RuntimeException("Method " + currClass.getMethod(n.i.toString()).getId() + " has too few arguments");
        }

        currClass = originalClass;
        return returnType;
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        return INTEGER;
    }

    public Type visit(True n) { return BOOLEAN; }

    public Type visit(False n) {
        return BOOLEAN;
    }

    // String s;
    public Type visit(IdentifierExp n) {
        return symbolTable.getVarType(currMethod, currClass, n.s);
    }

    public Type visit(This n) {
        return currClass.type();
    }

    // Exp e;
    public Type visit(NewArray n) {
        Type e = n.e.accept(this);
        if (!(e instanceof IntegerType)) {
            Exception.error(INTEGER, e);
        }
        return INT_ARRAY;
    }

    // Identifier i;
    public Type visit(NewObject n) {
        return n.i.accept(this);
    }

    // Exp e;
    public Type visit(Not n) {
        Type e = n.e.accept(this);
        if (!(e instanceof BooleanType)) {
            Exception.error(BOOLEAN, e);
        }

        return BOOLEAN;
    }

    // String s;
    public Type visit(Identifier n) {
        String id = n.toString();
        // global > method name > parameter > local variable > class name

        Class tmp = currClass;
        while (tmp != null) { // check if is available in one of the parents (extended classes)
            if (tmp.containsVar(id)) return symbolTable.getVarType(currMethod, currClass, id);
            if (tmp.parent() == null) tmp = null;
            else tmp = symbolTable.getClass(tmp.parent());
        }
        if (currClass.containsMethod(id))
            return symbolTable.getMethodType(id, currClass.getId());
        if (currMethod != null) {
            if (currMethod.containsParam(id))
                return currMethod.getParam(id).type();
            if (currMethod.containsVar(id))
                return currMethod.getVar(id).type();
        }
        Class c = symbolTable.getClass(id);
        if (c == null)
            throw new RuntimeException(id + " could not be found");

        return c.type();
    }
}
