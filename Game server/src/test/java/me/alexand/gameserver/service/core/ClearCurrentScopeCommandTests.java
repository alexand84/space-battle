package me.alexand.gameserver.service.core;

import me.alexand.gameserver.service.core.impls.ClearCurrentScopeCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClearCurrentScopeCommandTests {

    @Test
    void shouldSuccessClearCurrentScope(@Mock IScopeCapable scopeCapable) {
        doNothing().when(scopeCapable).removeCurrentScope();

        new ClearCurrentScopeCommand(scopeCapable).execute();

        verify(scopeCapable, times(1)).removeCurrentScope();
        verifyNoMoreInteractions(scopeCapable);
    }

}
