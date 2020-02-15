package com.customized.libs.libs.antlr4.calc.core;// Generated from /Users/yan/Workspace/workspace-card/analyzeV2/src/test/antlr/Expr.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface ExprVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by the {@code Mul_Div_Mod}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMul_Div_Mod(ExprParser.Mul_Div_ModContext ctx);

    /**
     * Visit a parse tree produced by the {@code NegativeNumber}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNegativeNumber(ExprParser.NegativeNumberContext ctx);

    /**
     * Visit a parse tree produced by the {@code Identifier}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIdentifier(ExprParser.IdentifierContext ctx);

    /**
     * Visit a parse tree produced by the {@code Number}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNumber(ExprParser.NumberContext ctx);

    /**
     * Visit a parse tree produced by the {@code Add_Sub}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdd_Sub(ExprParser.Add_SubContext ctx);

    /**
     * Visit a parse tree produced by the {@code Pow}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPow(ExprParser.PowContext ctx);

    /**
     * Visit a parse tree produced by the {@code Parentheses}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParentheses(ExprParser.ParenthesesContext ctx);
}