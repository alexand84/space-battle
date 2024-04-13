package me.alexand.spacebattle.service.adapters;

import me.alexand.spacebattle.model.UObject;
import me.alexand.spacebattle.model.Vector;
import me.alexand.spacebattle.service.ICommand;
import me.alexand.spacebattle.service.IMovable;
import me.alexand.spacebattle.service.core.IDependencyCreateStrategy;
import me.alexand.spacebattle.service.core.IScope;
import me.alexand.spacebattle.service.core.IoC;
import me.alexand.spacebattle.service.core.impls.InitCommand;
import me.alexand.spacebattle.service.core.impls.ScopeCapableDependencyResolveStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static me.alexand.spacebattle.service.core.IDependencyResolveStrategy.IOC_REGISTER;
import static me.alexand.spacebattle.service.core.IScopeCapable.IOC_SCOPE_CREATE;
import static me.alexand.spacebattle.service.core.IScopeCapable.IOC_SCOPE_CURRENT_SET;
import static org.assertj.core.api.Assertions.assertThat;

public class IMovableGeneratedAdapterTests {
    private static final String ADAPTER = "Adapter";

    @BeforeAll
    static void beforeAll() {
        new InitCommand(new ScopeCapableDependencyResolveStrategy()).execute();

        IScope newScope = IoC.resolve(IOC_SCOPE_CREATE);
        IoC.<ICommand>resolve(IOC_SCOPE_CURRENT_SET, newScope).execute();
        IoC.<ICommand>resolve(IOC_REGISTER, ADAPTER, new AdapterGeneratorCreateStrategy()).execute();
    }

    @Test
    void shouldSuccessSetPositionWithGeneratedAdapter() {
        Vector expectedPosition = Vector.builder().x(2).y(3).build();
        String methodHandler = "me.alexand.spacebattle.service.IMovable.setPosition";

        IoC.<ICommand>resolve(
                IOC_REGISTER,
                methodHandler,
                (IDependencyCreateStrategy) args -> {
                    Vector actualPosition = (Vector) args[1];

                    assertThat(actualPosition)
                            .isEqualTo(expectedPosition);

                    return null;
                }
        ).execute();

        IMovable movable = IoC.resolve(ADAPTER, IMovable.class, new UObject());
        movable.setPosition(expectedPosition);
    }

    @Test
    void shouldSuccessGetVelocityFromGeneratedAdapter() {
        Vector expectedVelocity = Vector.builder().x(5).y(10).build();

        String methodHandler = "me.alexand.spacebattle.service.IMovable.getVelocity";

        IoC.<ICommand>resolve(IOC_REGISTER, methodHandler, (IDependencyCreateStrategy) args -> expectedVelocity)
                .execute();

        IMovable movable = IoC.resolve(ADAPTER, IMovable.class, new UObject());

        assertThat(movable.getVelocity()).isEqualTo(expectedVelocity);
    }

    @Test
    void shouldSuccessGetPositionFromGeneratedAdapter() {
        Vector expectedPosition = Vector.builder().x(1).y(2).build();

        String methodHandler = "me.alexand.spacebattle.service.IMovable.getPosition";

        IoC.<ICommand>resolve(IOC_REGISTER, methodHandler, (IDependencyCreateStrategy) args -> expectedPosition)
                .execute();

        IMovable movable = IoC.resolve(ADAPTER, IMovable.class, new UObject());

        assertThat(movable.getPosition()).isEqualTo(expectedPosition);
    }

}
