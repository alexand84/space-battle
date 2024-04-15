package me.alexand.gameserver.service.commands;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.ICommandsExecutor;
import me.alexand.gameserver.service.IExecutable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubmitCommandTests {

    @Test
    void shouldSuccessSubmit(@Mock IExecutable executable,
                             @Mock ICommandsExecutor executor,
                             @Mock ICommand commandToSubmit) {
        when(executable.getCommandExecutor()).thenReturn(executor);
        when(executable.getCommand()).thenReturn(commandToSubmit);
        doNothing().when(executor).submit(commandToSubmit);

        new SubmitCommand(executable).execute();

        verify(executable, times(1)).getCommandExecutor();
        verify(executable, times(1)).getCommand();
        verify(executor, times(1)).submit(eq(commandToSubmit));
        verifyNoMoreInteractions(executable, executor);
    }

}
