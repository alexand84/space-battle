package me.alexand.spacebattle.service.core;

import java.util.function.Function;

public interface IDependencyResolveStrategy extends Function<String, IDependencyCreateStrategy> {
    String IOC_REGISTER = "IoC.Register";
}
