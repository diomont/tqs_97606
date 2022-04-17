package tqs.hw.covidtracker;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheUnitTests {
    
    private Cache<String> cache;

    @BeforeEach
    public void setup() {
        cache = new Cache<>(1200);
    }


    @Test
    void whenGetX_thenReturnEmpty_andMissesEqual1() {
        Optional<String> result = cache.get("Non existent");
        assertThat(result).isEmpty();
        assertThat(cache.getMisses()).isOne();
        assertThat(cache.getHits()).isZero();
    }

    @Test
    void givenX_whenGetX_thenReturnX_andHitsEqual1() {
        cache.add("Good key", "Good value");
        Optional<String> result = cache.get("Good key");
        assertThat(result).contains("Good value");
        assertThat(cache.getHits()).isOne();
        assertThat(cache.getMisses()).isZero();
    }

    @Test
    void givenX_whenWaitTimeToLive_andGetX_thenReturnX_andMissesEqual1() throws InterruptedException {
        cache.add("Good key", "Good value");

        TimeUnit.SECONDS.sleep(2);

        Optional<String> result = cache.get("Good key");
        assertThat(result).isEmpty();
        assertThat(cache.getMisses()).isOne();
        assertThat(cache.getHits()).isZero();
    }

}
