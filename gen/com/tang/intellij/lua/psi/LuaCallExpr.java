// This is a generated file. Not intended for manual editing.
package com.tang.intellij.lua.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.tang.intellij.lua.lang.type.LuaTypeSet;
import com.tang.intellij.lua.search.SearchContext;

public interface LuaCallExpr extends LuaExpr {

  @NotNull
  LuaArgs getArgs();

  @NotNull
  LuaExpr getExpr();

  LuaTypeSet guessPrefixType(SearchContext context);

  LuaFuncBodyOwner resolveFuncBodyOwner(SearchContext context);

  PsiElement getFirstStringArg();

  boolean isStaticMethodCall();

  boolean isMethodCall();

  boolean isFunctionCall();

}
