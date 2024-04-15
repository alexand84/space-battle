package me.alexand.gameserver.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.core.IScopeCapable;

@RequiredArgsConstructor
public class ClearCurrentScopeCommand implements ICommand {

    private final IScopeCapable scopeCapableObject;

    @Override
    public void execute() {
        scopeCapableObject.removeCurrentScope();
    }

}
