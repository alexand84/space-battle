package me.alexand.gameserver.service.core;

import me.alexand.gameserver.service.ICommand;
import me.alexand.gameserver.service.core.impls.InitCommand;
import me.alexand.gameserver.service.core.impls.ScopeCapableDependencyResolveStrategy;
import me.alexand.gameserver.service.exceptions.DependencyResolveException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ScopeCapableDependencyResolveStrategyTests {

    @BeforeAll
    static void beforeAll() {
        new InitCommand(new ScopeCapableDependencyResolveStrategy()).execute();

        IScope newScope = IoC.resolve(IScopeCapable.IOC_SCOPE_CREATE);
        IoC.<ICommand>resolve(IScopeCapable.IOC_SCOPE_CURRENT_SET, newScope)
                .execute();
    }

    @Test
    void shouldSuccessResolveDependencyInCurrentScope() {
        String dependencyName = "object";
        Integer expectedObject = 1;
        IoC.<ICommand>resolve(
                        IDependencyResolveStrategy.IOC_REGISTER,
                        dependencyName,
                        (IDependencyCreateStrategy) args -> expectedObject
                )
                .execute();

        assertThat(IoC.<Object>resolve(dependencyName))
                .isEqualTo(expectedObject);
    }

    @Test
    void shouldFailResolveDependencyInCurrentScope() {
        assertThatThrownBy(() -> IoC.resolve("unknown"))
                .isInstanceOf(DependencyResolveException.class);
    }

    @Test
    void shouldSuccessResolveDependencyInParentScope() {
        String dependencyName = "object";
        Integer expectedObject = 10;
        IoC.<ICommand>resolve(
                        IDependencyResolveStrategy.IOC_REGISTER,
                        dependencyName,
                        (IDependencyCreateStrategy) args -> expectedObject
                )
                .execute();

        IScope parentScope = IoC.resolve(IScopeCapable.IOC_SCOPE_CURRENT);
        IScope newScope = IoC.resolve(IScopeCapable.IOC_SCOPE_CREATE, parentScope);
        IoC.<ICommand>resolve(IScopeCapable.IOC_SCOPE_CURRENT_SET, newScope).execute();

        assertThat(IoC.<Object>resolve(dependencyName))
                .isEqualTo(expectedObject);
    }

    @Test
    void shouldFailResolveFromAnotherThread() {
        String dependencyName = "object";
        int expectedObject = 1;
        IoC.<ICommand>resolve(
                        IDependencyResolveStrategy.IOC_REGISTER,
                        dependencyName,
                        (IDependencyCreateStrategy) args -> expectedObject
                )
                .execute();

        CompletableFuture.runAsync(() -> {
                    assertThatThrownBy(() -> IoC.resolve(dependencyName))
                            .isInstanceOf(DependencyResolveException.class);
                })
                .join();
    }

    @AfterAll
    static void afterAll() {
        IoC.<ICommand>resolve(IScopeCapable.IOC_SCOPE_CURRENT_CLEAR).execute();
    }

}
