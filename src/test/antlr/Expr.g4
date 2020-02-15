grammar Expr;

import BasicTypes, Operators;

// 加入'#'，让Annotation生成的Listener和Visitor会有相应的方法来触发这个事件
// 这个注解要么一个都不写，要么整条规则每一个模式都写
expr: SUB NUMBER               #NegativeNumber
    | expr POW expr            #Pow
    | expr (MUL|DIV|MOD) expr  #Mul_Div_Mod
    | expr (ADD|SUB) expr      #Add_Sub
    | ID                       #Identifier
    | NUMBER                   #Number
    | LPARENT expr RPARENT     #Parentheses
    ;