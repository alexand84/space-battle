package me.alexand.gameserver.service.commands;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IRetryable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetryCommandTests {

    @Test
    void shouldSuccessRetry(@Mock IRetryable retryable,
                            @Mock ICommand commandToRetry) {
        doNothing().when(commandToRetry).execute();
        when(retryable.getCommand()).thenReturn(commandToRetry);

        new RetryCommand(retryable).execute();

        verify(retryable, times(1)).getCommand();
        verify(commandToRetry, times(1)).execute();
        verifyNoMoreInteractions(retryable, commandToRetry);
    }

}
