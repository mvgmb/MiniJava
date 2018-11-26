package br.ufpe.cin.if688.minijava.main;

import br.ufpe.cin.if688.minijava.ANTLR.gLexer;
import br.ufpe.cin.if688.minijava.ANTLR.gParser;
import br.ufpe.cin.if688.minijava.ast.Program;
import br.ufpe.cin.if688.minijava.visitor.BuildSymbolTableVisitor;
import br.ufpe.cin.if688.minijava.visitor.MiniJavaVisitor;
import br.ufpe.cin.if688.minijava.visitor.PrettyPrintVisitor;
import br.ufpe.cin.if688.minijava.visitor.TypeCheckVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String[] tests = new String[8];
        // Paste your main folder path here
        String mainFolderPath = "/home/mario/git/MiniJava/src/main/java/br/ufpe/cin/if688/minijava/main";

        tests[0] = mainFolderPath + "/tests/BinarySearch";
        tests[1] = mainFolderPath + "/tests/BinaryTree";
        tests[2] = mainFolderPath + "/tests/BubbleSort";
        tests[3] = mainFolderPath + "/tests/Factorial";
        tests[4] = mainFolderPath + "/tests/LinearSearch";
        tests[5] = mainFolderPath + "/tests/LinkedList";
        tests[6] = mainFolderPath + "/tests/QuickSort";
        tests[7] = mainFolderPath + "/tests/TreeVisitor";

        int i = 0;

        for (String test : tests) {
            // Creating AST
            Program program = (Program) new MiniJavaVisitor().visit(new gParser(new CommonTokenStream(new gLexer(CharStreams.fromFileName(test)))).program());

            // Activity 4 test - printing AST
            // new PrettyPrintVisitor().visit(program);

            // Activity 5 test - Building SymbolTable and checking types
            BuildSymbolTableVisitor buildSymbolTableVisitor = new BuildSymbolTableVisitor();
            buildSymbolTableVisitor.visit(program);

            TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(buildSymbolTableVisitor.getSymbolTable());
            typeCheckVisitor.visit(program);

        }

    }
}