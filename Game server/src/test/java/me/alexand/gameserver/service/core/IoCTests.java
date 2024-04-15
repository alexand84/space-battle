package me.alexand.gameserver.service.core;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.exceptions.DependencyResolveException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IoCTests {

    @Test
    void shouldFailDueToDependencyNotFound() {
        Assertions.assertThatThrownBy(() -> IoC.resolve("unknown"))
                .isInstanceOf(DependencyResolveException.class)
                .asString()
                .isEqualTo("dependency 'unknown' is not found");
    }

    @Test
    void shouldFailDueToClassCastException() {
        Assertions.assertThatThrownBy(
                        () -> {
                            String dependency = IoC.resolve(
                                    IoC.IOC_UPDATE_DEPENDENCY_RESOLVE_STRATEGY,
                                    (Function<IDependencyResolveStrategy, IDependencyResolveStrategy>) old -> old
                            );
                        }
                )
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    void shouldSuccessUpdateDependencyResolveStrategy(@Mock Function<IDependencyResolveStrategy, IDependencyResolveStrategy> function) {
        when(function.apply(any())).thenReturn(dependencyName -> {
            throw new DependencyResolveException(dependencyName);
        });

        IoC.<ICommand>resolve(
                IoC.IOC_UPDATE_DEPENDENCY_RESOLVE_STRATEGY,
                function
        ).execute();

        verify(function, times(1)).apply(any());
        verifyNoMoreInteractions(function);
    }


}
