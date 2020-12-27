package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); 	}
"break"   	{ return new_symbol(sym.BREAK, yytext()); 		}
"print" 	{ return new_symbol(sym.PRINT, yytext()); 		}
"return" 	{ return new_symbol(sym.RETURN, yytext());		}
"void" 		{ return new_symbol(sym.VOID, yytext()); 		}
"class" 	{ return new_symbol(sym.CLASS, yytext()); 		}
"enum" 		{ return new_symbol(sym.ENUM, yytext()); 		}
"else"		{ return new_symbol(sym.ELSE, yytext());		}
"if" 		{ return new_symbol(sym.IF, yytext());			}
"const"   	{ return new_symbol(sym.CONST, yytext());		}
"new" 		{ return new_symbol(sym.NEW, yytext()); 		}
"read" 		{ return new_symbol(sym.READ, yytext()); 		}
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); 	}
"continue" 	{ return new_symbol(sym.CONTINUE, yytext());	}
"switch" 	{ return new_symbol(sym.SWITCH, yytext()); 		}
"case" 		{ return new_symbol(sym.CASE, yytext()); 		}
"do" 		{ return new_symbol(sym.DO, yytext()); 			}
"while" 	{ return new_symbol(sym.WHILE, yytext());		}

"!="		{ return new_symbol(sym.NEQ, yytext());			}
">="		{ return new_symbol(sym.GRE, yytext());			}
"<="		{ return new_symbol(sym.LOE, yytext()); 		}
"++"		{ return new_symbol(sym.PLUSPLUS, yytext());	}
"--"		{ return new_symbol(sym.MINUSMINUS, yytext());	}
"." 		{ return new_symbol(sym.FULL, yytext());		}
"+" 		{ return new_symbol(sym.PLUS, yytext());	 	}
"-" 		{ return new_symbol(sym.MINUS, yytext());		}
"*" 		{ return new_symbol(sym.MUL, yytext());			}
"/" 		{ return new_symbol(sym.DIV, yytext());			}
"%" 		{ return new_symbol(sym.MOD, yytext());			}
"==" 		{ return new_symbol(sym.EQ, yytext());			}
">"			{ return new_symbol(sym.GR, yytext());			}
"<"			{ return new_symbol(sym.LO, yytext());			}
"&&"		{ return new_symbol(sym.AND, yytext());			}
"||"		{ return new_symbol(sym.OR, yytext());			}
"="			{ return new_symbol(sym.EQUAL, yytext());		}
";"			{ return new_symbol(sym.SEMI, yytext());		}
"," 		{ return new_symbol(sym.COMMA, yytext()); 		}
"(" 		{ return new_symbol(sym.LPAREN, yytext());		}
")" 		{ return new_symbol(sym.RPAREN, yytext()); 		}
"["			{ return new_symbol(sym.LBOX, yytext());		}
"]"			{ return new_symbol(sym.RBOX, yytext());		}
"{" 		{ return new_symbol(sym.LBRACE, yytext()); 		}
"}"			{ return new_symbol(sym.RBRACE, yytext());		}
"?" 		{ return new_symbol(sym.QUESTION, yytext()); 		}
":"			{ return new_symbol(sym.COLON, yytext());		}


<YYINITIAL> "//" { yybegin(COMMENT);   }
<COMMENT> .      { yybegin(COMMENT);   }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+  { return new_symbol(sym.NUMBER_CONST, new Integer (yytext())); }


"true"	{ return new_symbol(sym.BOOL_CONST, new Boolean(yytext())); }
"false" { return new_symbol(sym.BOOL_CONST, new Boolean(yytext())); }


([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }


"'"."'" { return new_symbol(sym.CHAR_CONST, new Character(yytext().charAt(1)));}

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }






