package me.alexand.spacebattle.service.commands;

import me.alexand.spacebattle.service.IFuelPowered;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BurnFuelCommandTests {

    @Test
    void shouldSuccessBurnFuel(@Mock IFuelPowered fuelPoweredObject) {
        when(fuelPoweredObject.getFuelLevel()).thenReturn(10L);
        when(fuelPoweredObject.getFuelConsumptionRate()).thenReturn(10L);
        doNothing().when(fuelPoweredObject).setFuelLevel(0L);

        new BurnFuelCommand(fuelPoweredObject).execute();

        verify(fuelPoweredObject, times(1)).getFuelLevel();
        verify(fuelPoweredObject, times(1)).getFuelConsumptionRate();
        verify(fuelPoweredObject, times(1)).setFuelLevel(0);
        verifyNoMoreInteractions(fuelPoweredObject);
    }

}
