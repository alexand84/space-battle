package me.alexand.spacebattle.service.adapters;

import me.alexand.spacebattle.model.UObject;
import me.alexand.spacebattle.service.IMovable;
import me.alexand.spacebattle.service.core.IoC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdapterInvocationHandlerTests {

    @Test
    void shouldSuccessInvokeWithoutMethodArgs(@Mock Method method) {
        try (MockedStatic<IoC> ioc = Mockito.mockStatic(IoC.class)) {
            UObject uObject = new UObject();
            Class<IMovable> clazz = IMovable.class;
            String interfaceName = clazz.getCanonicalName();
            String methodName = "mName";
            String dependencyName = String.format("%s.%s", interfaceName, methodName);
            Object[] args = {uObject};
            Object expectedValue = new Object();

            when(method.getName()).thenReturn(methodName);
            ioc.when(() -> IoC.resolve(dependencyName, args)).thenReturn(expectedValue);

            assertThat(
                    new AdapterInvocationHandler(uObject, clazz)
                            .invoke(null, method, null)
            ).isEqualTo(expectedValue);

            verify(method, times(1)).getName();
            verifyNoMoreInteractions(method);
            ioc.verify(
                    () -> IoC.resolve(eq(dependencyName), eq(args)),
                    times(1)
            );
            ioc.verifyNoMoreInteractions();
        }
    }

    @Test
    void shouldSuccessInvokeWithMethodArgs(@Mock Method method) {
        try (MockedStatic<IoC> ioc = Mockito.mockStatic(IoC.class)) {
            UObject uObject = new UObject();
            Class<IMovable> clazz = IMovable.class;
            String interfaceName = clazz.getCanonicalName();
            String methodName = "mName";
            Object[] methodArgs = {1, 2};
            String dependencyName = String.format("%s.%s", interfaceName, methodName);
            Object[] args = {uObject, 1, 2};
            Object expectedValue = new Object();

            when(method.getName()).thenReturn(methodName);
            ioc.when(() -> IoC.resolve(dependencyName, args)).thenReturn(expectedValue);

            assertThat(
                    new AdapterInvocationHandler(uObject, clazz)
                            .invoke(null, method, methodArgs)
            ).isEqualTo(expectedValue);

            verify(method, times(1)).getName();
            verifyNoMoreInteractions(method);
            ioc.verify(
                    () -> IoC.resolve(eq(dependencyName), eq(args)),
                    times(1)
            );
            ioc.verifyNoMoreInteractions();
        }
    }
}
