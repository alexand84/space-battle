package me.alexand.spacebattle.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.core.IScopeCapable;

@RequiredArgsConstructor
public class ClearCurrentScopeCommand implements ICommand {

    private final IScopeCapable scopeCapableObject;

    @Override
    public void execute() {
        scopeCapableObject.removeCurrentScope();
    }

}
