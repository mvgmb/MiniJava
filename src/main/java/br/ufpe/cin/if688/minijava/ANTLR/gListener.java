// Generated from /home/mario/Documents/minijava/src/main/java/br/ufpe/cin/if688/minijava/g.g4 by ANTLR 4.7
package br.ufpe.cin.if688.minijava.ANTLR;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link gParser}.
 */
public interface gListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link gParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(gParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(gParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(gParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(gParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(gParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(gParser.ClassDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void enterMethodDecl(gParser.MethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void exitMethodDecl(gParser.MethodDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(gParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(gParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(gParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(gParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(gParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(gParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(gParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(gParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(gParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(gParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link gParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(gParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(gParser.IntegerLiteralContext ctx);
}