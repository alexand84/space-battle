package me.alexand.spacebattle.service.core;

import java.util.Optional;

public interface IScope {

    void put(String dependencyName, IDependencyCreateStrategy dependencyCreateStrategy);

    Optional<IDependencyCreateStrategy> get(String dependencyName);

}
