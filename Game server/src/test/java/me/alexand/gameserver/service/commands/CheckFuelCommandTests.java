package me.alexand.gameserver.service.commands;

import me.alexand.gameserver.service.IFuelPowered;
import me.alexand.gameserver.service.exceptions.InsufficientFuelException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckFuelCommandTests {

    @Test
    void shouldSuccessCheck(@Mock IFuelPowered fuelPoweredObject) {
        when(fuelPoweredObject.getFuelLevel()).thenReturn(10L);
        when(fuelPoweredObject.getFuelConsumptionRate()).thenReturn(10L);

        new CheckFuelCommand(fuelPoweredObject).execute();

        verify(fuelPoweredObject, times(1)).getFuelLevel();
        verify(fuelPoweredObject, times(1)).getFuelConsumptionRate();
        verifyNoMoreInteractions(fuelPoweredObject);
    }

    @Test
    void shouldFailCheck(@Mock IFuelPowered fuelPoweredObject) {
        when(fuelPoweredObject.getFuelLevel()).thenReturn(9L);
        when(fuelPoweredObject.getFuelConsumptionRate()).thenReturn(10L);

        assertThatThrownBy(() -> new CheckFuelCommand(fuelPoweredObject).execute())
                .isInstanceOf(InsufficientFuelException.class);

        verify(fuelPoweredObject, times(1)).getFuelLevel();
        verify(fuelPoweredObject, times(1)).getFuelConsumptionRate();
        verifyNoMoreInteractions(fuelPoweredObject);
    }

}
