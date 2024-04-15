package me.alexand.gameserver.service.adapters;

import lombok.RequiredArgsConstructor;
import me.alexand.gameserver.model.UObject;
import me.alexand.gameserver.service.core.IoC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class AdapterInvocationHandler implements InvocationHandler {
    private final UObject uObject;
    private final Class<?> clazz;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return IoC.resolve(
                String.format("%s.%s", clazz.getCanonicalName(), method.getName()),
                prependTo(args, uObject)
        );
    }

    private Object[] prependTo(Object[] args, Object object) {
        int argsLength = args == null ? 0 : args.length;
        Object[] result = new Object[1 + argsLength];
        result[0] = object;
        if (args != null) {
            System.arraycopy(args, 0, result, 1, argsLength);
        }
        return result;
    }

}
