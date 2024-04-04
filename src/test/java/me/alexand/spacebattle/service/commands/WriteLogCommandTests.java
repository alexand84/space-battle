package me.alexand.spacebattle.service.commands;

import me.alexand.spacebattle.service.ILoggable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WriteLogCommandTests {

    @Test
    void shouldSuccessWriteLog(@Mock ILoggable loggable,
                               @Mock Logger logger) {
        String message = "";
        RuntimeException exception = new RuntimeException("test");

        doNothing().when(logger).error(message, exception);
        when(loggable.getLogger()).thenReturn(logger);
        when(loggable.getThrowable()).thenReturn(exception);

        new WriteLogCommand(loggable).execute();

        verify(loggable, times(1)).getLogger();
        verify(loggable, times(1)).getThrowable();
        verify(logger, times(1)).error(eq(""), eq(exception));
        verifyNoMoreInteractions(logger, loggable);

    }

}
