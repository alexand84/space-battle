package me.alexand.spacebattle;

import me.alexand.spacebattle.model.Vector;
import me.alexand.spacebattle.service.IMovable;
import me.alexand.spacebattle.service.commands.Move;
import me.alexand.spacebattle.service.exceptions.PositionReadException;
import me.alexand.spacebattle.service.exceptions.PositionWriteException;
import me.alexand.spacebattle.service.exceptions.VelocityReadException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoveCommandTests {

    @Test
    void shouldSuccessMove(@Mock IMovable movable) {
        Vector oldPosition = Vector.builder().x(12).y(5).build();
        Vector velocity = Vector.builder().x(-7).y(3).build();
        Vector newPosition = Vector.builder().x(5).y(8).build();

        when(movable.getPosition()).thenReturn(oldPosition);
        when(movable.getVelocity()).thenReturn(velocity);

        new Move(movable).execute();

        verify(movable, times(1)).setPosition(newPosition);
    }

    @Test
    void shouldFailDueToImpossibleGetPosition(@Mock IMovable movable) {
        when(movable.getPosition()).thenThrow(PositionReadException.class);

        assertThatThrownBy(() -> new Move(movable).execute())
                .isInstanceOf(PositionReadException.class);

        verify(movable, times(1)).getPosition();
    }

    @Test
    void shouldFailDueToImpossibleGetVelocity(@Mock IMovable movable) {
        when(movable.getVelocity()).thenThrow(VelocityReadException.class);

        assertThatThrownBy(() -> new Move(movable).execute())
                .isInstanceOf(VelocityReadException.class);

        verify(movable, times(1)).getVelocity();
    }

    @Test
    void shouldFailDueToImpossibleChangePosition(@Mock IMovable movable) {
        Vector oldPosition = Vector.builder().x(12).y(5).build();
        Vector velocity = Vector.builder().x(-7).y(3).build();

        when(movable.getPosition()).thenReturn(oldPosition);
        when(movable.getVelocity()).thenReturn(velocity);
        doThrow(PositionWriteException.class).when(movable)
                .setPosition(any());

        assertThatThrownBy(() -> new Move(movable).execute())
                .isInstanceOf(PositionWriteException.class);

        verify(movable, times(1)).setPosition(any());
    }

}
