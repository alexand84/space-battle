package me.alexand.gameserver.service.core.impls;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.core.IDependencyCreateStrategy;
import me.alexand.gameserver.service.core.IScope;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ScopeImpl implements IScope {

    private final Map<String, IDependencyCreateStrategy> registry;

    @Override
    public void put(String dependencyName, IDependencyCreateStrategy dependencyCreateStrategy) {
        registry.put(dependencyName, dependencyCreateStrategy);
    }

    @Override
    public Optional<IDependencyCreateStrategy> get(String dependencyName) {
        return Optional.ofNullable(registry.get(dependencyName));
    }

}
