package me.alexand.spacebattle.service.core;

import me.alexand.spacebattle.service.core.impls.RegisterDependencyCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterDependencyCommandTests {

    @Test
    void shouldSuccessRegisterDependency(@Mock IScopeCapable scopeCapable,
                                         @Mock IScope scope) {
        String dependency = "dep";
        IDependencyCreateStrategy strategy = args -> new Object();

        when(scopeCapable.getCurrentScope()).thenReturn(scope);
        doNothing().when(scope).put(dependency, strategy);

        new RegisterDependencyCommand(scopeCapable, dependency, strategy).execute();

        verify(scopeCapable, times(1)).getCurrentScope();
        verify(scope, times(1)).put(eq(dependency), eq(strategy));
        verifyNoMoreInteractions(scopeCapable, scope);
    }

}
