package me.alexand.gameserver.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.core.IScope;
import me.alexand.gameserver.service.core.IScopeCapable;

@RequiredArgsConstructor
public class SetCurrentScopeCommand implements ICommand {

    private final IScopeCapable scopeCapableObject;
    private final IScope scope;

    @Override
    public void execute() {
        scopeCapableObject.setCurrentScope(scope);
    }

}
