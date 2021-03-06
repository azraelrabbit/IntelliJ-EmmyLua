// This is a generated file. Not intended for manual editing.
package com.tang.intellij.lua.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.tang.intellij.lua.psi.LuaTypes.*;
import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.tang.intellij.lua.stubs.LuaClassMethodStub;
import com.tang.intellij.lua.psi.*;
import com.intellij.navigation.ItemPresentation;
import com.tang.intellij.lua.comment.psi.api.LuaComment;
import com.tang.intellij.lua.lang.type.LuaType;
import com.tang.intellij.lua.lang.type.LuaTypeSet;
import com.tang.intellij.lua.search.SearchContext;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class LuaClassMethodDefImpl extends StubBasedPsiElementBase<LuaClassMethodStub> implements LuaClassMethodDef {

  public LuaClassMethodDefImpl(LuaClassMethodStub stub, IStubElementType type) {
    super(stub, type);
  }

  public LuaClassMethodDefImpl(ASTNode node) {
    super(node);
  }

  public LuaClassMethodDefImpl(LuaClassMethodStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull LuaVisitor visitor) {
    visitor.visitClassMethodDef(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuaVisitor) accept((LuaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public LuaClassMethodName getClassMethodName() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, LuaClassMethodName.class));
  }

  @Override
  @Nullable
  public LuaFuncBody getFuncBody() {
    return PsiTreeUtil.getChildOfType(this, LuaFuncBody.class);
  }

  public LuaComment getComment() {
    return LuaPsiImplUtil.getComment(this);
  }

  @Nullable
  public LuaType getClassType(SearchContext context) {
    return LuaPsiImplUtil.getClassType(this, context);
  }

  public List<LuaParamNameDef> getParamNameDefList() {
    return LuaPsiImplUtil.getParamNameDefList(this);
  }

  public PsiElement getNameIdentifier() {
    return LuaPsiImplUtil.getNameIdentifier(this);
  }

  public PsiElement setName(String name) {
    return LuaPsiImplUtil.setName(this, name);
  }

  public String getName() {
    return LuaPsiImplUtil.getName(this);
  }

  public int getTextOffset() {
    return LuaPsiImplUtil.getTextOffset(this);
  }

  public String toString() {
    return LuaPsiImplUtil.toString(this);
  }

  public LuaTypeSet guessReturnTypeSet(SearchContext searchContext) {
    return LuaPsiImplUtil.guessReturnTypeSet(this, searchContext);
  }

  @NotNull
  public LuaParamInfo[] getParams() {
    return LuaPsiImplUtil.getParams(this);
  }

  public boolean isStatic() {
    return LuaPsiImplUtil.isStatic(this);
  }

  public ItemPresentation getPresentation() {
    return LuaPsiImplUtil.getPresentation(this);
  }

}
