package me.alexand.gameserver.service.core;

public interface IScopeCapable {

    String IOC_SCOPE_CREATE = "IoC.Scope.Create";
    String IOC_SCOPE_CREATE_EMPTY = "IoC.Scope.Create.Empty";
    String IOC_SCOPE_CURRENT_SET = "IoC.Scope.Current.Set";
    String IOC_SCOPE_CURRENT_CLEAR = "IoC.Scope.Current.Clear";
    String IOC_SCOPE_CURRENT = "IoC.Scope.Current";
    String IOC_SCOPE_PARENT = "IoC.Scope.Parent";

    IScope getRootScope();

    IScope getCurrentScope();

    void removeCurrentScope();

    void setCurrentScope(IScope scope);

}
