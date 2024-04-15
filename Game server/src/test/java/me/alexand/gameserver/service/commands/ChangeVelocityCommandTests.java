package me.alexand.gameserver.service.commands;

import me.alexand.gameserver.model.Vector;
import me.alexand.gameserver.service.IMovableAndRotatable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeVelocityCommandTests {

    @Test
    void shouldSuccessChangeWhenTurning90Degrees(@Mock IMovableAndRotatable object) {
        Vector oldVelocity = Vector.builder().x(2).y(5).build();
        int direction = 90;

        Vector newVelocity = Vector.builder().x(-5).y(2).build();

        when(object.getVelocity()).thenReturn(oldVelocity);
        when(object.getDirection()).thenReturn(direction);

        new ChangeVelocityCommand(object).execute();

        verify(object, times(1)).getVelocity();
        verify(object, times(1)).getDirection();
        verify(object, times(1)).setVelocity(eq(newVelocity));
        verifyNoMoreInteractions(object);
    }

    @Test
    void shouldSuccessChangeWhenTurning180Degrees(@Mock IMovableAndRotatable object) {
        Vector oldVelocity = Vector.builder().x(2).y(5).build();
        int direction = 180;

        Vector newVelocity = Vector.builder().x(-2).y(-5).build();

        when(object.getVelocity()).thenReturn(oldVelocity);
        when(object.getDirection()).thenReturn(direction);

        new ChangeVelocityCommand(object).execute();

        verify(object, times(1)).getVelocity();
        verify(object, times(1)).getDirection();
        verify(object, times(1)).setVelocity(eq(newVelocity));
        verifyNoMoreInteractions(object);
    }

}
