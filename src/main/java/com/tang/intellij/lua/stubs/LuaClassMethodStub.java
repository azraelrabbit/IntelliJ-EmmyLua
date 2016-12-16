package com.tang.intellij.lua.stubs;

import com.intellij.psi.stubs.StubElement;
import com.tang.intellij.lua.psi.LuaClassMethodDef;

/**
 * class method static/instance
 * Created by tangzx on 2016/12/4.
 */
public interface LuaClassMethodStub extends StubElement<LuaClassMethodDef> {

    String getClassName();

    String getShortName();

    boolean isStatic();
}