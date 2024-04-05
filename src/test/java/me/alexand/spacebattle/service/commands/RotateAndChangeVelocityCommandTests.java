package me.alexand.spacebattle.service.commands;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IMovableAndRotatable;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RotateAndChangeVelocityCommandTests {

    @Test
    void shouldSuccessRotateAndChangeVelocity(@Mock ICommandFactory commandFactory,
                                              @Mock IMovableAndRotatable movableAndRotatable,
                                              @Mock ICommand rotateCommand,
                                              @Mock ICommand changeVelocityCommand) {
        when(commandFactory.createRotateCommand(movableAndRotatable)).thenReturn(rotateCommand);
        when(commandFactory.createChangeVelocityCommand(movableAndRotatable)).thenReturn(changeVelocityCommand);
        doNothing().when(rotateCommand).execute();
        doNothing().when(changeVelocityCommand).execute();

        new RotateAndChangeVelocityCommand(commandFactory, movableAndRotatable).execute();

        verify(commandFactory, times(1)).createRotateCommand(eq(movableAndRotatable));
        verify(commandFactory, times(1)).createChangeVelocityCommand(eq(movableAndRotatable));
        verify(rotateCommand, times(1)).execute();
        verify(changeVelocityCommand, times(1)).execute();
        verifyNoMoreInteractions(commandFactory, rotateCommand, changeVelocityCommand);
    }

}
