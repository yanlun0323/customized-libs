lexer grammar BasicTypes;

fragment DIGIT: [0-9];
// fragment修饰的方法，不回在程序里生成DIGIT()
// 而下面的NUMBER没有fragment，则可以通过NUMBER()来访问

fragment S_QUOTE: '\'';
fragment QUOTE:   '"';
fragment ALPHABET: [A-Za-z_];

NUMBER: DIGIT+('.'DIGIT+)?; //数字类型，包括浮点型和整型
LPARENT: '(';
RPARENT: ')';

// 两种模式，单引号+双引号
STEING: S_QUOTE(~'\'')*S_QUOTE
      | QUOTE('\\"'|~'"'*QUOTE)
      ;
ID: ALPHABET+(DIGIT(ALPHABET)*)*;

WS: [ \r\t\n]+ -> skip;