package me.alexand.gameserver.service.core;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.exceptions.DependencyResolveException;

import java.util.function.Function;

public class IoC {
    public static final String IOC_UPDATE_DEPENDENCY_RESOLVE_STRATEGY = "IoC.Update.Dependency.Resolve.Strategy";

    @SuppressWarnings("unchecked")
    private static final IDependencyCreateStrategy updateCommand = args -> new UpdateDependencyResolveStrategy(
            (Function<IDependencyResolveStrategy, IDependencyResolveStrategy>) args[0]
    );

    private static IDependencyResolveStrategy resolveStrategy = dependencyName -> {
        throw new DependencyResolveException(dependencyName);
    };

    @SuppressWarnings("unchecked")
    public static <T> T resolve(String dependencyName, Object... args) {
        IDependencyCreateStrategy createStrategy = IOC_UPDATE_DEPENDENCY_RESOLVE_STRATEGY.equals(dependencyName) ?
                updateCommand : resolveStrategy.apply(dependencyName);

        return (T) createStrategy.apply(args);
    }

    @RequiredArgsConstructor
    private static class UpdateDependencyResolveStrategy implements ICommand {
        private final Function<IDependencyResolveStrategy, IDependencyResolveStrategy> updater;

        @Override
        public void execute() {
            resolveStrategy = updater.apply(resolveStrategy);
        }
    }

}
