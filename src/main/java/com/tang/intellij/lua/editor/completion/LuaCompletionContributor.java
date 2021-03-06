/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tang.intellij.lua.editor.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.HashSet;
import com.tang.intellij.lua.lang.LuaIcons;
import com.tang.intellij.lua.psi.*;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 *
 * Created by tangzx on 2016/11/27.
 */
public class LuaCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> SHOW_CLASS_FIELD = psiElement(LuaTypes.ID)
            .withParent(LuaIndexExpr.class);
    private static final PsiElementPattern.Capture<PsiElement> IN_PARAM_NAME = psiElement(LuaTypes.ID)
            .withParent(LuaParamNameDef.class);


    private static final PsiElementPattern.Capture<PsiElement> IN_FUNC_NAME = psiElement(LuaTypes.ID)
            .withParent(LuaIndexExpr.class)
            .inside(LuaClassMethodName.class);
    private static final PsiElementPattern.Capture<PsiElement> AFTER_FUNCTION = psiElement()
            .afterLeaf(psiElement(LuaTypes.FUNCTION));
    private static final PsiElementPattern.Capture<PsiElement> IN_CLASS_METHOD_NAME = psiElement().andOr(IN_FUNC_NAME, AFTER_FUNCTION);


    private static final PsiElementPattern.Capture<PsiElement> IN_COMMENT = psiElement()
            .inside(PsiComment.class);
    private static final PsiElementPattern.Capture<PsiElement> SHOW_OVERRIDE = psiElement()
            .withParent(LuaClassMethodName.class);
    private static final PsiElementPattern.Capture<PsiElement> IN_CLASS_METHOD = psiElement(LuaTypes.ID)
            .withParent(LuaNameExpr.class)
            .inside(LuaClassMethodDef.class);
    private static final PsiElementPattern.Capture<PsiElement> SHOW_PATH = psiElement(LuaTypes.STRING)
            .inside(psiElement(LuaTypes.ARGS).afterLeaf("require"));

    public LuaCompletionContributor() {
        //可以override
        extend(CompletionType.BASIC, SHOW_OVERRIDE, new OverrideCompletionProvider());

        extend(CompletionType.BASIC, IN_CLASS_METHOD, new SuggestSelfMemberProvider());

        //提示属性, 提示方法
        extend(CompletionType.BASIC, SHOW_CLASS_FIELD, new ClassMemberCompletionProvider());

        extend(CompletionType.BASIC, SHOW_PATH, new RequirePathCompletionProvider());

        //提示全局函数,local变量,local函数
        extend(CompletionType.BASIC, psiElement().inside(LuaFile.class)
                .andNot(SHOW_CLASS_FIELD)
                .andNot(IN_COMMENT)
                .andNot(IN_CLASS_METHOD_NAME)
                .andNot(IN_PARAM_NAME)
                .andNot(SHOW_OVERRIDE), new LocalAndGlobalCompletionProvider(LocalAndGlobalCompletionProvider.ALL));

        extend(CompletionType.BASIC, IN_CLASS_METHOD_NAME, new LocalAndGlobalCompletionProvider(LocalAndGlobalCompletionProvider.VARS));
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        parameters.getEditor().putUserData(CompletionSession.KEY, new CompletionSession(parameters, result));
        super.fillCompletionVariants(parameters, result);
        suggestWordsInFile(parameters, result);
    }

    private static void suggestWordsInFile(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        CompletionSession session = parameters.getEditor().getUserData(CompletionSession.KEY);
        assert session != null;

        HashSet<String> wordsInFileSet = new HashSet<>();
        PsiFile file = session.getParameters().getOriginalFile();
        file.acceptChildren(new LuaVisitor() {
            @Override
            public void visitPsiElement(@NotNull LuaPsiElement o) {
                o.acceptChildren(this);
            }

            @Override
            public void visitElement(PsiElement element) {
                if (element.getNode().getElementType() == LuaTypes.ID && element.getTextLength() > 2) {
                    String text = element.getText();
                    if (session.getResultSet().getPrefixMatcher().prefixMatches(text) && session.addWord(text))
                        wordsInFileSet.add(text);
                }
                super.visitElement(element);
            }
        });

        for (String s : wordsInFileSet) {
            session.getResultSet().addElement(PrioritizedLookupElement.withPriority(LookupElementBuilder
                            .create(s)
                            .withIcon(LuaIcons.WORD)
                    //.withTypeText("Word In File")
                    , -1));
        }
    }
}