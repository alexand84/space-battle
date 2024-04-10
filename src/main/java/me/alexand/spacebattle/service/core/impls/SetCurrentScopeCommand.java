package me.alexand.spacebattle.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.core.IScope;
import me.alexand.spacebattle.service.core.IScopeCapable;

@RequiredArgsConstructor
public class SetCurrentScopeCommand implements ICommand {

    private final IScopeCapable scopeCapableObject;
    private final IScope scope;

    @Override
    public void execute() {
        scopeCapableObject.setCurrentScope(scope);
    }

}
