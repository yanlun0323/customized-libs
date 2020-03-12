package com.customized.libs.core.libs.antlr4.calc.impl;

import com.customized.libs.core.libs.antlr4.calc.core.ExprBaseListener;
import com.customized.libs.core.libs.antlr4.calc.core.ExprParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcExpr extends ExprBaseListener {

    ParseTreeProperty<BigDecimal> numbers = new ParseTreeProperty<>();

    @Override
    public void exitPow(ExprParser.PowContext ctx) {
        if (ctx.POW() != null) {
            BigDecimal left = numbers.get(ctx.expr(0));
            BigDecimal right = numbers.get(ctx.expr(1));
            numbers.put(ctx, left.pow(right.intValue()));
        }
        super.exitPow(ctx);
    }

    @Override
    public void exitAdd_Sub(ExprParser.Add_SubContext ctx) {
        BigDecimal left = numbers.get(ctx.expr(0));
        BigDecimal right = numbers.get(ctx.expr(1));
        if (ctx.ADD() != null) {
            numbers.put(ctx, left.add(right));
        } else if (ctx.SUB() != null) {
            numbers.put(ctx, left.subtract(right));
        }
        super.exitAdd_Sub(ctx);
    }

    @Override
    public void exitMul_Div_Mod(ExprParser.Mul_Div_ModContext ctx) {
        BigDecimal left = numbers.get(ctx.expr(0));
        BigDecimal right = numbers.get(ctx.expr(1));
        if (ctx.MUL() != null) {
            numbers.put(ctx, left.multiply(right));
        } else if (ctx.DIV() != null) {
            //Only keep up to 21 digits after the decimal point
            numbers.put(ctx, left.divide(right, 21, RoundingMode.HALF_UP).stripTrailingZeros());
        } else if (ctx.MOD() != null) {
            numbers.put(ctx, left.remainder(right));
        }
        super.exitMul_Div_Mod(ctx);
    }

    @Override
    public void exitNumber(ExprParser.NumberContext ctx) {
        if (!StringUtils.isEmpty(ctx.getText())) {
            numbers.put(ctx, new BigDecimal(ctx.getText()));
        }
        super.exitNumber(ctx);
    }

    public ParseTreeProperty<BigDecimal> getNumbers() {
        return numbers;
    }

    public void setNumbers(ParseTreeProperty<BigDecimal> numbers) {
        this.numbers = numbers;
    }
}
