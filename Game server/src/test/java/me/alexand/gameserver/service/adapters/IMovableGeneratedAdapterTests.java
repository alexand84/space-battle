package me.alexand.gameserver.service.adapters;

import me.alexand.gameserver.model.UObject;
import me.alexand.gameserver.model.Vector;
import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.IMovable;
import me.alexand.gameserver.service.core.IDependencyCreateStrategy;
import me.alexand.gameserver.service.core.IScope;
import me.alexand.gameserver.service.core.IoC;
import me.alexand.gameserver.service.core.impls.InitCommand;
import me.alexand.gameserver.service.core.impls.ScopeCapableDependencyResolveStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static me.alexand.gameserver.service.core.IDependencyResolveStrategy.IOC_REGISTER;
import static me.alexand.gameserver.service.core.IScopeCapable.IOC_SCOPE_CREATE;
import static me.alexand.gameserver.service.core.IScopeCapable.IOC_SCOPE_CURRENT_SET;
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
        Class<IMovable> clazz = IMovable.class;
        String methodHandler = clazz.getCanonicalName() + ".setPosition";

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

        IMovable movable = IoC.resolve(ADAPTER, clazz, new UObject());
        movable.setPosition(expectedPosition);
    }

    @Test
    void shouldSuccessGetVelocityFromGeneratedAdapter() {
        Vector expectedVelocity = Vector.builder().x(5).y(10).build();
        Class<IMovable> clazz = IMovable.class;
        String methodHandler = clazz.getCanonicalName() + ".getVelocity";

        IoC.<ICommand>resolve(IOC_REGISTER, methodHandler, (IDependencyCreateStrategy) args -> expectedVelocity)
                .execute();

        IMovable movable = IoC.resolve(ADAPTER, clazz, new UObject());

        assertThat(movable.getVelocity()).isEqualTo(expectedVelocity);
    }

    @Test
    void shouldSuccessGetPositionFromGeneratedAdapter() {
        Vector expectedPosition = Vector.builder().x(1).y(2).build();
        Class<IMovable> clazz = IMovable.class;
        String methodHandler = clazz.getCanonicalName() + ".getPosition";

        IoC.<ICommand>resolve(IOC_REGISTER, methodHandler, (IDependencyCreateStrategy) args -> expectedPosition)
                .execute();

        IMovable movable = IoC.resolve(ADAPTER, clazz, new UObject());

        assertThat(movable.getPosition()).isEqualTo(expectedPosition);
    }

}
