package com.customized.libs.libs.antlr4.calc.core;// Generated from /Users/yan/Workspace/workspace-card/analyzeV2/src/test/antlr/Expr.g4 by ANTLR 4.7.2

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprLexer extends Lexer {
    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            NUMBER = 1, LPARENT = 2, RPARENT = 3, STEING = 4, ID = 5, WS = 6, MUL = 7, DIV = 8, ADD = 9,
            SUB = 10, POW = 11, MOD = 12;
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    private static String[] makeRuleNames() {
        return new String[]{
                "DIGIT", "S_QUOTE", "QUOTE", "ALPHABET", "NUMBER", "LPARENT", "RPARENT",
                "STEING", "ID", "WS", "MUL", "DIV", "ADD", "SUB", "POW", "MOD"
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


    public ExprLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16w\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3" +
                    "\3\3\3\3\4\3\4\3\5\3\5\3\6\6\6-\n\6\r\6\16\6.\3\6\3\6\6\6\63\n\6\r\6\16" +
                    "\6\64\5\6\67\n\6\3\7\3\7\3\b\3\b\3\t\3\t\7\t?\n\t\f\t\16\tB\13\t\3\t\3" +
                    "\t\3\t\3\t\3\t\3\t\7\tJ\n\t\f\t\16\tM\13\t\3\t\5\tP\n\t\5\tR\n\t\3\n\6" +
                    "\nU\n\n\r\n\16\nV\3\n\3\n\7\n[\n\n\f\n\16\n^\13\n\7\n`\n\n\f\n\16\nc\13" +
                    "\n\3\13\6\13f\n\13\r\13\16\13g\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17" +
                    "\3\17\3\20\3\20\3\21\3\21\2\2\22\3\2\5\2\7\2\t\2\13\3\r\4\17\5\21\6\23" +
                    "\7\25\b\27\t\31\n\33\13\35\f\37\r!\16\3\2\7\3\2\62;\5\2C\\aac|\3\2))\3" +
                    "\2$$\5\2\13\f\17\17\"\"\2}\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21" +
                    "\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2" +
                    "\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3#\3\2\2\2\5%\3\2\2\2\7\'\3" +
                    "\2\2\2\t)\3\2\2\2\13,\3\2\2\2\r8\3\2\2\2\17:\3\2\2\2\21Q\3\2\2\2\23T\3" +
                    "\2\2\2\25e\3\2\2\2\27k\3\2\2\2\31m\3\2\2\2\33o\3\2\2\2\35q\3\2\2\2\37" +
                    "s\3\2\2\2!u\3\2\2\2#$\t\2\2\2$\4\3\2\2\2%&\7)\2\2&\6\3\2\2\2\'(\7$\2\2" +
                    "(\b\3\2\2\2)*\t\3\2\2*\n\3\2\2\2+-\5\3\2\2,+\3\2\2\2-.\3\2\2\2.,\3\2\2" +
                    "\2./\3\2\2\2/\66\3\2\2\2\60\62\7\60\2\2\61\63\5\3\2\2\62\61\3\2\2\2\63" +
                    "\64\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\60\3\2\2\2\66" +
                    "\67\3\2\2\2\67\f\3\2\2\289\7*\2\29\16\3\2\2\2:;\7+\2\2;\20\3\2\2\2<@\5" +
                    "\5\3\2=?\n\4\2\2>=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2AC\3\2\2\2B@\3" +
                    "\2\2\2CD\5\5\3\2DR\3\2\2\2EO\5\7\4\2FG\7^\2\2GP\7$\2\2HJ\n\5\2\2IH\3\2" +
                    "\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2LN\3\2\2\2MK\3\2\2\2NP\5\7\4\2OF\3\2" +
                    "\2\2OK\3\2\2\2PR\3\2\2\2Q<\3\2\2\2QE\3\2\2\2R\22\3\2\2\2SU\5\t\5\2TS\3" +
                    "\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2Wa\3\2\2\2X\\\5\3\2\2Y[\5\t\5\2ZY" +
                    "\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]`\3\2\2\2^\\\3\2\2\2_X\3\2\2" +
                    "\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\24\3\2\2\2ca\3\2\2\2df\t\6\2\2ed\3\2" +
                    "\2\2fg\3\2\2\2ge\3\2\2\2gh\3\2\2\2hi\3\2\2\2ij\b\13\2\2j\26\3\2\2\2kl" +
                    "\7,\2\2l\30\3\2\2\2mn\7\61\2\2n\32\3\2\2\2op\7-\2\2p\34\3\2\2\2qr\7/\2" +
                    "\2r\36\3\2\2\2st\7`\2\2t \3\2\2\2uv\7\'\2\2v\"\3\2\2\2\16\2.\64\66@KO" +
                    "QV\\ag\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}