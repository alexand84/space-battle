package me.alexand.spacebattle.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.spacebattle.service.core.IDependencyCreateStrategy;
import me.alexand.spacebattle.service.core.IScope;
import me.alexand.spacebattle.service.core.IScopeCapableDependencyResolveStrategy;
import me.alexand.spacebattle.service.exceptions.DependencyResolveException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class ScopeCapableDependencyResolveStrategy implements IScopeCapableDependencyResolveStrategy {

    private final ThreadLocal<IScope> threadLocalScopes = new ThreadLocal<>();
    private final IScope rootScope = new ScopeImpl(new ConcurrentHashMap<>());

    @Override
    public IDependencyCreateStrategy apply(String dependencyName) {
        IScope currentScope = getCurrentScope();

        while (true) {
            Optional<IDependencyCreateStrategy> createStrategy = currentScope.get(dependencyName);

            if (createStrategy.isPresent()) {
                return createStrategy.get();
            } else {
                Optional<IDependencyCreateStrategy> parentScope = currentScope.get(IOC_SCOPE_PARENT);

                if (parentScope.isPresent()) {
                    try {
                        currentScope = (IScope) parentScope.get().apply(null);
                    } catch (RuntimeException e) {
                        throw new DependencyResolveException(dependencyName);
                    }
                } else {
                    throw new DependencyResolveException(dependencyName);
                }
            }
        }
    }

    @Override
    public IScope getRootScope() {
        return rootScope;
    }

    @Override
    public IScope getCurrentScope() {
        IScope threadLocalScope = threadLocalScopes.get();
        return threadLocalScope != null ? threadLocalScope : rootScope;
    }

    @Override
    public void removeCurrentScope() {
        threadLocalScopes.remove();
    }

    @Override
    public void setCurrentScope(IScope scope) {
        threadLocalScopes.set(scope);
    }

}
