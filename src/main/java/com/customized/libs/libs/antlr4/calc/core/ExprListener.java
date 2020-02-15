package com.customized.libs.libs.antlr4.calc.core;// Generated from /Users/yan/Workspace/workspace-card/analyzeV2/src/test/antlr/Expr.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by the {@code Mul_Div_Mod}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterMul_Div_Mod(ExprParser.Mul_Div_ModContext ctx);

    /**
     * Exit a parse tree produced by the {@code Mul_Div_Mod}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitMul_Div_Mod(ExprParser.Mul_Div_ModContext ctx);

    /**
     * Enter a parse tree produced by the {@code NegativeNumber}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterNegativeNumber(ExprParser.NegativeNumberContext ctx);

    /**
     * Exit a parse tree produced by the {@code NegativeNumber}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitNegativeNumber(ExprParser.NegativeNumberContext ctx);

    /**
     * Enter a parse tree produced by the {@code Identifier}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterIdentifier(ExprParser.IdentifierContext ctx);

    /**
     * Exit a parse tree produced by the {@code Identifier}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitIdentifier(ExprParser.IdentifierContext ctx);

    /**
     * Enter a parse tree produced by the {@code Number}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterNumber(ExprParser.NumberContext ctx);

    /**
     * Exit a parse tree produced by the {@code Number}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitNumber(ExprParser.NumberContext ctx);

    /**
     * Enter a parse tree produced by the {@code Add_Sub}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterAdd_Sub(ExprParser.Add_SubContext ctx);

    /**
     * Exit a parse tree produced by the {@code Add_Sub}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitAdd_Sub(ExprParser.Add_SubContext ctx);

    /**
     * Enter a parse tree produced by the {@code Pow}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterPow(ExprParser.PowContext ctx);

    /**
     * Exit a parse tree produced by the {@code Pow}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitPow(ExprParser.PowContext ctx);

    /**
     * Enter a parse tree produced by the {@code Parentheses}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterParentheses(ExprParser.ParenthesesContext ctx);

    /**
     * Exit a parse tree produced by the {@code Parentheses}
     * labeled alternative in {@link ExprParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitParentheses(ExprParser.ParenthesesContext ctx);
}