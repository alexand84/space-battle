package me.alexand.spacebattle.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.core.*;

import java.util.HashMap;
import java.util.function.Function;

@RequiredArgsConstructor
public class InitCommand implements ICommand {

    private final IScopeCapableDependencyResolveStrategy strategy;

    @Override
    public void execute() {
        strategy.getRootScope().put(
                IScopeCapable.IOC_SCOPE_CURRENT_SET,
                args -> new SetCurrentScopeCommand(strategy, (IScope) args[0])
        );

        strategy.getRootScope().put(
                IScopeCapable.IOC_SCOPE_CURRENT_CLEAR,
                args -> new ClearCurrentScopeCommand(strategy)
        );

        strategy.getRootScope().put(
                IScopeCapable.IOC_SCOPE_CURRENT,
                args -> strategy.getCurrentScope()
        );

        strategy.getRootScope().put(
                IScopeCapable.IOC_SCOPE_PARENT,
                args -> {
                    throw new RuntimeException("The root scope has no a parent scope");
                }
        );

        strategy.getRootScope().put(
                IScopeCapable.IOC_SCOPE_CREATE_EMPTY,
                args -> new ScopeImpl(new HashMap<>())
        );

        strategy.getRootScope().put(
                IScopeCapable.IOC_SCOPE_CREATE,
                args -> {
                    IScope createdScope = IoC.resolve(IScopeCapable.IOC_SCOPE_CREATE_EMPTY);
                    IScope parentScope = args.length > 0 ? (IScope) args[0] : IoC.resolve(IScopeCapable.IOC_SCOPE_CURRENT);
                    createdScope.put(IScopeCapable.IOC_SCOPE_PARENT, a -> parentScope);

                    return createdScope;
                }
        );

        strategy.getRootScope().put(
                IDependencyResolveStrategy.IOC_REGISTER,
                args -> new RegisterDependencyCommand(strategy, (String) args[0], (IDependencyCreateStrategy) args[1])
        );

        IoC.<ICommand>resolve(
                        IoC.IOC_UPDATE_DEPENDENCY_RESOLVE_STRATEGY,
                        (Function<IDependencyResolveStrategy, IDependencyResolveStrategy>) old -> strategy)
                .execute();
    }

}
