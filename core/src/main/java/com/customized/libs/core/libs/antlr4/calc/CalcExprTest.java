package com.customized.libs.core.libs.antlr4.calc;

import com.customized.libs.core.libs.antlr4.calc.core.ExprLexer;
import com.customized.libs.core.libs.antlr4.calc.core.ExprParser;
import com.customized.libs.core.libs.antlr4.calc.impl.CalcExpr;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


/**
 * Created by sam on 8/30/17.
 */
public class CalcExprTest {

    public static void main(String[] args) {
        CharStream input = CharStreams.fromString("3^2");
        ExprLexer lexer = new ExprLexer(input);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokenStream);
        ParseTree parseTree = parser.expr();
        CalcExpr visitor = new CalcExpr();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(visitor, parseTree);

        System.out.println(visitor.getNumbers().get(parseTree));
    }

}