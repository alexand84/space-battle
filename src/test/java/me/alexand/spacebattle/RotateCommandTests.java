package me.alexand.spacebattle;

import me.alexand.spacebattle.service.IRotatable;
import me.alexand.spacebattle.service.commands.Rotate;
import me.alexand.spacebattle.service.exceptions.AngularVelocityReadException;
import me.alexand.spacebattle.service.exceptions.DirectionReadException;
import me.alexand.spacebattle.service.exceptions.DirectionWriteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RotateCommandTests {

    @Test
    void shouldSuccessRotate(@Mock IRotatable rotatable) {
        int oldDirection = 180;
        int angularVelocity = 10;
        int directionsNumber = 360;
        int newDirection = 190;

        when(rotatable.getDirection()).thenReturn(oldDirection);
        when(rotatable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotatable.getDirectionsNumber()).thenReturn(directionsNumber);

        new Rotate(rotatable).execute();

        verify(rotatable, times(1)).setDirection(newDirection);
    }

    @Test
    void shouldFailDueToImpossibleGetDirection(@Mock IRotatable rotatable) {
        when(rotatable.getDirection()).thenThrow(DirectionReadException.class);

        assertThatThrownBy(() -> new Rotate(rotatable).execute())
                .isInstanceOf(DirectionReadException.class);

        verify(rotatable, times(1)).getDirection();
    }

    @Test
    void shouldFailDueToImpossibleGetAngularVelocity(@Mock IRotatable rotatable) {
        when(rotatable.getAngularVelocity()).thenThrow(AngularVelocityReadException.class);

        assertThatThrownBy(() -> new Rotate(rotatable).execute())
                .isInstanceOf(AngularVelocityReadException.class);

        verify(rotatable, times(1)).getAngularVelocity();
    }

    @Test
    void shouldFailDueToImpossibleChangeDirection(@Mock IRotatable rotatable) {
        int oldDirection = 180;
        int angularVelocity = 10;
        int directionsNumber = 360;

        when(rotatable.getDirection()).thenReturn(oldDirection);
        when(rotatable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotatable.getDirectionsNumber()).thenReturn(directionsNumber);
        doThrow(DirectionWriteException.class).when(rotatable)
                .setDirection(anyInt());

        assertThatThrownBy(() -> new Rotate(rotatable).execute())
                .isInstanceOf(DirectionWriteException.class);

        verify(rotatable, times(1)).setDirection(anyInt());
    }

}
