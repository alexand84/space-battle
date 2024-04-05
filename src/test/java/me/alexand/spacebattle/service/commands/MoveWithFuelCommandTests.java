package me.alexand.spacebattle.service.commands;

import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IFuelPowered;
import me.alexand.spacebattle.service.IMovable;
import me.alexand.spacebattle.service.exceptions.CommandException;
import me.alexand.spacebattle.service.exceptions.InsufficientFuelException;
import me.alexand.spacebattle.service.factory.ICommandFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoveWithFuelCommandTests {

    @Test
    void shouldSuccessMoveWithFuel(@Mock ICommandFactory commandFactory,
                                   @Mock IFuelPowered fuelPowered,
                                   @Mock IMovable movable,
                                   @Mock ICommand checkFuelCommand,
                                   @Mock ICommand moveCommand,
                                   @Mock ICommand burnFuelCommand) {
        when(commandFactory.createCheckFuelCommand(fuelPowered)).thenReturn(checkFuelCommand);
        when(commandFactory.createMoveCommand(movable)).thenReturn(moveCommand);
        when(commandFactory.createBurnFuelCommand(fuelPowered)).thenReturn(burnFuelCommand);
        doNothing().when(checkFuelCommand).execute();
        doNothing().when(moveCommand).execute();
        doNothing().when(burnFuelCommand).execute();

        new MoveWithFuelCommand(commandFactory, fuelPowered, movable).execute();

        verify(commandFactory, times(1)).createCheckFuelCommand(eq(fuelPowered));
        verify(commandFactory, times(1)).createMoveCommand(eq(movable));
        verify(commandFactory, times(1)).createBurnFuelCommand(eq(fuelPowered));
        verify(checkFuelCommand, times(1)).execute();
        verify(moveCommand, times(1)).execute();
        verify(burnFuelCommand, times(1)).execute();
        verifyNoMoreInteractions(commandFactory, checkFuelCommand, moveCommand, burnFuelCommand);
    }

    @Test
    void shouldFailDueToInsufficientFuelLevel(@Mock ICommandFactory commandFactory,
                                              @Mock IFuelPowered fuelPowered,
                                              @Mock IMovable movable,
                                              @Mock ICommand checkFuelCommand,
                                              @Mock ICommand moveCommand,
                                              @Mock ICommand burnFuelCommand) {
        when(commandFactory.createCheckFuelCommand(fuelPowered)).thenReturn(checkFuelCommand);
        when(commandFactory.createMoveCommand(movable)).thenReturn(moveCommand);
        when(commandFactory.createBurnFuelCommand(fuelPowered)).thenReturn(burnFuelCommand);
        doThrow(InsufficientFuelException.class).when(checkFuelCommand).execute();

        assertThatThrownBy(() -> new MoveWithFuelCommand(commandFactory, fuelPowered, movable).execute())
                .isInstanceOf(CommandException.class)
                .cause()
                .isInstanceOf(InsufficientFuelException.class);

        verify(commandFactory, times(1)).createCheckFuelCommand(eq(fuelPowered));
        verify(commandFactory, times(1)).createMoveCommand(eq(movable));
        verify(commandFactory, times(1)).createBurnFuelCommand(eq(fuelPowered));
        verify(checkFuelCommand, times(1)).execute();
        verifyNoMoreInteractions(commandFactory, checkFuelCommand, moveCommand, burnFuelCommand);
    }

}
