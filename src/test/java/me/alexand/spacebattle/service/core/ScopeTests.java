package me.alexand.spacebattle.service.core;

import me.alexand.spacebattle.service.core.impls.ScopeImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScopeTests {

    @Test
    void shouldSuccessPut(@Mock Map<String, IDependencyCreateStrategy> map) {
        String dependency = "dep";
        IDependencyCreateStrategy strategy = args -> new Object();
        when(map.put(dependency, strategy)).thenReturn(null);

        new ScopeImpl(map).put(dependency, strategy);
        verify(map, times(1)).put(eq(dependency), eq(strategy));
        verifyNoMoreInteractions(map);
    }

    @Test
    void shouldSuccessGetStrategy(@Mock Map<String, IDependencyCreateStrategy> map) {
        String dependency = "dep";
        IDependencyCreateStrategy expectedStrategy = args -> new Object();
        when(map.get(dependency)).thenReturn(expectedStrategy);

        Optional<IDependencyCreateStrategy> actualStrategy = new ScopeImpl(map).get(dependency);

        Assertions.assertThat(actualStrategy)
                .isNotEmpty()
                .get()
                .isEqualTo(expectedStrategy);

        verify(map, times(1)).get(eq(dependency));
        verifyNoMoreInteractions(map);
    }

    @Test
    void shouldNotGetStrategy(@Mock Map<String, IDependencyCreateStrategy> map) {
        String dependency = "dep";
        when(map.get(dependency)).thenReturn(null);

        Optional<IDependencyCreateStrategy> actualStrategy = new ScopeImpl(map).get(dependency);

        Assertions.assertThat(actualStrategy)
                .isEmpty();

        verify(map, times(1)).get(eq(dependency));
        verifyNoMoreInteractions(map);
    }

}
