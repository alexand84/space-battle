package me.alexand.gameserver.service.adapters;


import me.alexand.gameserver.model.UObject;
import me.alexand.gameserver.service.core.IDependencyCreateStrategy;

import java.lang.reflect.Proxy;

public class AdapterGeneratorCreateStrategy implements IDependencyCreateStrategy {

    @Override
    public Object apply(Object[] args) {
        Class<?> clazz = (Class<?>) args[0];
        UObject uObject = (UObject) args[1];

        return clazz.cast(
                Proxy.newProxyInstance(
                        clazz.getClassLoader(),
                        new Class[]{clazz},
                        new AdapterInvocationHandler(uObject, clazz)
                )
        );
    }

}
