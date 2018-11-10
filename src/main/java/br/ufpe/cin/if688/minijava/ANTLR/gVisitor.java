// Generated from /home/mario/Documents/minijava/src/main/java/br/ufpe/cin/if688/minijava/g.g4 by ANTLR 4.7
package br.ufpe.cin.if688.minijava.ANTLR;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link gParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface gVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link gParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(gParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(gParser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(gParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#methodDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDecl(gParser.MethodDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(gParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(gParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(gParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(gParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(gParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link gParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(gParser.IntegerLiteralContext ctx);
}