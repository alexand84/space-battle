package me.alexand.spacebattle.service.core;

import me.alexand.spacebattle.service.core.impls.SetCurrentScopeCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetCurrentScopeCommandTests {

    @Test
    void shouldSuccessSetCurrentScope(@Mock IScopeCapable scopeCapable,
                                      @Mock IScope scope) {
        doNothing().when(scopeCapable).setCurrentScope(scope);

        new SetCurrentScopeCommand(scopeCapable, scope).execute();

        verify(scopeCapable, times(1)).setCurrentScope(eq(scope));
        verifyNoMoreInteractions(scopeCapable);
    }


}
