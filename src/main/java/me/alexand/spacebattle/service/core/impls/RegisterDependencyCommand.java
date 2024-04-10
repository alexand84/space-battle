package me.alexand.spacebattle.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.core.IDependencyCreateStrategy;
import me.alexand.spacebattle.service.core.IScopeCapable;

@RequiredArgsConstructor
public class RegisterDependencyCommand implements ICommand {

    private final IScopeCapable scopeCapableObject;
    private final String dependencyName;
    private final IDependencyCreateStrategy createStrategy;

    @Override
    public void execute() {
        scopeCapableObject.getCurrentScope().put(dependencyName, createStrategy);
    }
}
