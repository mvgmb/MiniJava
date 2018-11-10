grammar g;

program
: mainClass classDecl* EOF
;

mainClass
: 'class' identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' identifier ')' '{' statement '}' '}'
;

classDecl 
: 'class' identifier ( 'extends' identifier )? '{' varDecl* methodDecl* '}'
;

methodDecl 
: 'public' type identifier '(' ( type identifier (',' type identifier)* )? ')' '{' varDecl* statement* 'return' exp ';' '}'
;

varDecl
: type identifier ';'
;

exp
: exp ('&&' | '<' | '-' | '+' | '*') exp           // And, LessThan, Minus, Plus, Times
| exp '.length'                                    // ArrayLength
| exp '[' exp ']'                                  // ArrayLookup
| exp '.' identifier '(' ( exp ( ',' exp )* )? ')' // Call
| 'true'                                           // True
| 'false'                                          // False
| identifier                                       // IdentifierExp
| integerLiteral                                   // IntegerLiteral
| 'new' 'int' '[' exp ']'                          // NewArray
| 'new' identifier '(' ')'                         // NewObject
| '!' exp                                          // Not
| 'this'                                           // This 
| '(' exp ')'                                      // (exp) is permited
;

statement
: identifier '[' exp ']' '=' exp ';'          // ArrayAssign
| identifier '=' exp ';'                      // Assign
| '{' statement* '}'                          // Block
| 'if' '(' exp ')' statement 'else' statement // If
| 'System.out.println' '(' exp ')' ';'        // Print
| 'while' '(' exp ')' statement               // While
;

type
: 'boolean'     // BooleanType
| identifier    // IdentifierType
| 'int' '[' ']' // IntAndArrayType
| 'int'         // IntegerType
;

identifier: IDENTIFIER;
integerLiteral: INTEGER_LITERAL;

IDENTIFIER: [_a-zA-Z][_a-zA-Z0-9]*;
INTEGER_LITERAL: '0' | [1-9][0-9]*;

COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
WHITESPACE: [ \t\f\r\n] -> skip;
