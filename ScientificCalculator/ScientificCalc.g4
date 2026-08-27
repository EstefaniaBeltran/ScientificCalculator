grammar ScientificCalc;

prog
    : stat+ EOF
    ;

stat
    : expr NEWLINE # printExpr
    | ID '=' expr NEWLINE # assign
    | 'plot' '(' expr ',' expr ',' expr ',' expr ',' expr ')' NEWLINE # plotExpr
    | NEWLINE # blank
    | 'plot' '(' expr ',' expr ',' expr ',' expr ')' NEWLINE # plotTwoExpr
    ;

expr
    : <assoc=right> expr '^' expr # power
    | function '(' expr ',' expr ')' # functionCall
    | function '(' expr ')' # functionCall
    | expr op=('*'|'/') expr # mulDiv
    | op=('+'|'-') expr # unary
    | expr op=('+'|'-') expr # addSub
    | 'vars' NEWLINE # showVars
    | NUMBER # number
    | 'clear' NEWLINE # clear
    | ID # id
    | constant # constantExpr
    | '(' expr ')' # parens
    ;

MUL : '*';
DIV : '/';
ADD : '+';
SUB : '-';

constant
  : 'pi'
  | 'e'
  ;

function
    : 'sin'
    | 'cos'
    | 'tan'
    | 'sqrt'
    | 'log'
    | 'ln'
    | 'abs'
    | 'exp'
    | 'asin'
    | 'acos'
    | 'atan'
    | 'floor'
    | 'ceil'
    | 'pow'
    | 'max'
    | 'min'
    ;
    
functionDef
    : ID '(' ID ')' '=' expr
    ;

functionCallUser
    : ID '(' expr ')'
    ;
    
NUMBER
    : [0-9]+ ('.' [0-9]+)?
    ;

ID
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;
