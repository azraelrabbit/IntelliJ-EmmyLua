package com.tang.intellij.lua.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.tang.intellij.lua.doc.psi.LuaDocTokenType;
import com.tang.intellij.lua.doc.psi.LuaDocTypes;
import com.tang.intellij.lua.psi.LuaTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * Created by tangzx
 * Date : 2015/11/15.
 */
public class LuaSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TokenSet KEYWORD_TOKENS = TokenSet.create(
            LuaTypes.AND,
            LuaTypes.BREAK,
            LuaTypes.DO,
            LuaTypes.ELSE,
            LuaTypes.ELSEIF,
            LuaTypes.END,
            LuaTypes.FALSE,
            LuaTypes.FOR,
            LuaTypes.FUNCTION,
            LuaTypes.IF,
            LuaTypes.IN,
            LuaTypes.LOCAL,
            LuaTypes.NIL,
            LuaTypes.NOT,
            LuaTypes.OR,
            LuaTypes.REPEAT,
            LuaTypes.RETURN,
            LuaTypes.THEN,
            LuaTypes.TRUE,
            LuaTypes.UNTIL,
            LuaTypes.WHILE
    );

    private static final TextAttributesKey KEYWORD = createTextAttributesKey("LUA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    private static final TextAttributesKey NUMBER = createTextAttributesKey("LUA_NUMBER", DefaultLanguageHighlighterColors.NUMBER);

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new LuaFileLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType type) {
        if (KEYWORD_TOKENS.contains(type))
            return pack(KEYWORD);
        else if (type == LuaTypes.NUMBER)
            return pack(NUMBER);
        else if (type == LuaTypes.STRING)
            return pack(DefaultLanguageHighlighterColors.STRING);
        else if (type == LuaTypes.SELF)
            return pack(LuaHighlightingData.KEYWORD);

        // for comment
        else if (type == LuaTypes.SHORT_COMMENT)
            return pack(DefaultLanguageHighlighterColors.LINE_COMMENT);
        else if (type == LuaDocTypes.CLASS)
            return pack(LuaHighlightingData.STRING);
        else if (type == LuaDocTypes.TAG_NAME || type == LuaDocTypes.TAG_PARAM || type == LuaDocTypes.TAG_RETURN)
            return pack(LuaHighlightingData.LUADOC_TAG);
        else if (type instanceof LuaDocTokenType)
            return pack(DefaultLanguageHighlighterColors.DOC_COMMENT);

        return new TextAttributesKey[0];
    }
}
