package com.customized.libs.libs.antlr4.calc.core;// Generated from /Users/yan/Workspace/workspace-card/analyzeV2/src/test/antlr/Expr.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            NUMBER = 1, LPARENT = 2, RPARENT = 3, STEING = 4, ID = 5, WS = 6, MUL = 7, DIV = 8, ADD = 9,
            SUB = 10, POW = 11, MOD = 12;
    public static final int
            RULE_expr = 0;

    private static String[] makeRuleNames() {
        return new String[]{
                "expr"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, null, "'('", "')'", null, null, null, "'*'", "'/'", "'+'", "'-'",
                "'^'", "'%'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "NUMBER", "LPARENT", "RPARENT", "STEING", "ID", "WS", "MUL", "DIV",
                "ADD", "SUB", "POW", "MOD"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "Expr.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public ExprParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ExprContext extends ParserRuleContext {
        public ExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expr;
        }

        public ExprContext() {
        }

        public void copyFrom(ExprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class Mul_Div_ModContext extends ExprContext {
        public List<ExprContext> expr() {
            return getRuleContexts(ExprContext.class);
        }

        public ExprContext expr(int i) {
            return getRuleContext(ExprContext.class, i);
        }

        public TerminalNode MUL() {
            return getToken(ExprParser.MUL, 0);
        }

        public TerminalNode DIV() {
            return getToken(ExprParser.DIV, 0);
        }

        public TerminalNode MOD() {
            return getToken(ExprParser.MOD, 0);
        }

        public Mul_Div_ModContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterMul_Div_Mod(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitMul_Div_Mod(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitMul_Div_Mod(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NegativeNumberContext extends ExprContext {
        public TerminalNode SUB() {
            return getToken(ExprParser.SUB, 0);
        }

        public TerminalNode NUMBER() {
            return getToken(ExprParser.NUMBER, 0);
        }

        public NegativeNumberContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterNegativeNumber(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitNegativeNumber(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitNegativeNumber(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IdentifierContext extends ExprContext {
        public TerminalNode ID() {
            return getToken(ExprParser.ID, 0);
        }

        public IdentifierContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterIdentifier(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitIdentifier(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitIdentifier(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NumberContext extends ExprContext {
        public TerminalNode NUMBER() {
            return getToken(ExprParser.NUMBER, 0);
        }

        public NumberContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterNumber(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitNumber(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitNumber(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class Add_SubContext extends ExprContext {
        public List<ExprContext> expr() {
            return getRuleContexts(ExprContext.class);
        }

        public ExprContext expr(int i) {
            return getRuleContext(ExprContext.class, i);
        }

        public TerminalNode ADD() {
            return getToken(ExprParser.ADD, 0);
        }

        public TerminalNode SUB() {
            return getToken(ExprParser.SUB, 0);
        }

        public Add_SubContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterAdd_Sub(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitAdd_Sub(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitAdd_Sub(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PowContext extends ExprContext {
        public List<ExprContext> expr() {
            return getRuleContexts(ExprContext.class);
        }

        public ExprContext expr(int i) {
            return getRuleContext(ExprContext.class, i);
        }

        public TerminalNode POW() {
            return getToken(ExprParser.POW, 0);
        }

        public PowContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterPow(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitPow(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitPow(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ParenthesesContext extends ExprContext {
        public TerminalNode LPARENT() {
            return getToken(ExprParser.LPARENT, 0);
        }

        public ExprContext expr() {
            return getRuleContext(ExprContext.class, 0);
        }

        public TerminalNode RPARENT() {
            return getToken(ExprParser.RPARENT, 0);
        }

        public ParenthesesContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).enterParentheses(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof ExprListener) ((ExprListener) listener).exitParentheses(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ExprVisitor) return ((ExprVisitor<? extends T>) visitor).visitParentheses(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ExprContext expr() throws RecognitionException {
        return expr(0);
    }

    private ExprContext expr(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        ExprContext _localctx = new ExprContext(_ctx, _parentState);
        ExprContext _prevctx = _localctx;
        int _startState = 0;
        enterRecursionRule(_localctx, 0, RULE_expr, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(11);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case SUB: {
                        _localctx = new NegativeNumberContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(3);
                        match(SUB);
                        setState(4);
                        match(NUMBER);
                    }
                    break;
                    case ID: {
                        _localctx = new IdentifierContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(5);
                        match(ID);
                    }
                    break;
                    case NUMBER: {
                        _localctx = new NumberContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(6);
                        match(NUMBER);
                    }
                    break;
                    case LPARENT: {
                        _localctx = new ParenthesesContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(7);
                        match(LPARENT);
                        setState(8);
                        expr(0);
                        setState(9);
                        match(RPARENT);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                _ctx.stop = _input.LT(-1);
                setState(24);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(22);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                                case 1: {
                                    _localctx = new PowContext(new ExprContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(13);
                                    if (!(precpred(_ctx, 6)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    setState(14);
                                    match(POW);
                                    setState(15);
                                    expr(7);
                                }
                                break;
                                case 2: {
                                    _localctx = new Mul_Div_ModContext(new ExprContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(16);
                                    if (!(precpred(_ctx, 5)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                                    setState(17);
                                    _la = _input.LA(1);
                                    if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0))) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(18);
                                    expr(6);
                                }
                                break;
                                case 3: {
                                    _localctx = new Add_SubContext(new ExprContext(_parentctx, _parentState));
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(19);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(20);
                                    _la = _input.LA(1);
                                    if (!(_la == ADD || _la == SUB)) {
                                        _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(21);
                                    expr(5);
                                }
                                break;
                            }
                        }
                    }
                    setState(26);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 0:
                return expr_sempred((ExprContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean expr_sempred(ExprContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 6);
            case 1:
                return precpred(_ctx, 5);
            case 2:
                return precpred(_ctx, 4);
        }
        return true;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\16\36\4\2\t\2\3\2" +
                    "\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\16\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3" +
                    "\2\3\2\3\2\7\2\31\n\2\f\2\16\2\34\13\2\3\2\2\3\2\3\2\2\4\4\2\t\n\16\16" +
                    "\3\2\13\f\2\"\2\r\3\2\2\2\4\5\b\2\1\2\5\6\7\f\2\2\6\16\7\3\2\2\7\16\7" +
                    "\7\2\2\b\16\7\3\2\2\t\n\7\4\2\2\n\13\5\2\2\2\13\f\7\5\2\2\f\16\3\2\2\2" +
                    "\r\4\3\2\2\2\r\7\3\2\2\2\r\b\3\2\2\2\r\t\3\2\2\2\16\32\3\2\2\2\17\20\f" +
                    "\b\2\2\20\21\7\r\2\2\21\31\5\2\2\t\22\23\f\7\2\2\23\24\t\2\2\2\24\31\5" +
                    "\2\2\b\25\26\f\6\2\2\26\27\t\3\2\2\27\31\5\2\2\7\30\17\3\2\2\2\30\22\3" +
                    "\2\2\2\30\25\3\2\2\2\31\34\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2\33\3\3" +
                    "\2\2\2\34\32\3\2\2\2\5\r\30\32";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}