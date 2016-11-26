package com.tang.intellij.lua.doc.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.tang.intellij.lua.doc.psi.LuaDocTypes;

%%

%class _LuaDocLexer
%implements FlexLexer, LuaDocTypes


%unicode
%public

%function advance
%type IElementType

%eof{ return;
%eof}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////// User code //////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

%{ // User code

  public _LuaDocLexer() {
    this((java.io.Reader)null);
  }
%}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////// LuaDoc lexems ////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+
ID=[A-Za-z0-9_]+
AT=@
//三个-以上
DOC_COMMENT_START = ----*
//两个-以上
DOC_DASHES = ---*

%state xDOC_COMMENT_START

%%

<YYINITIAL> {
 {WHITE_SPACE}              { return com.intellij.psi.TokenType.WHITE_SPACE; }
 {DOC_COMMENT_START}        { yybegin(xDOC_COMMENT_START); return LDOC_COMMENT_START; }
 [^]                        { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
<xDOC_COMMENT_START> {
 {WHITE_SPACE}              { return com.intellij.psi.TokenType.WHITE_SPACE; }
 {DOC_DASHES}               { return LDOC_DASHES; }
 "@"                        { return AT; }
 "="                        { return EQ; }
 "{"                        { return LCURLY; }
 "}"                        { return RCURLY; }
 ","                        { return COMMA; }
 "param"                    { return PARAM; }
 "private"                  { return PRIVATE; }
 "public"                   { return PUBLIC; }
 "class"                    { return CLASS; }
 "interface"                { return INTERFACE; }
 "typedef"                  { return TYPE_DEF; }
 "extends"                  { return EXTENDS; }
 "return"                   { return RETURN; }
 {ID}                       { return ID; }
 [^]                        { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}