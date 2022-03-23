package tqs;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @Mock
    IStockmarketService market;

    @InjectMocks
    StocksPortfolio portfolio;


    @Test
    void testGetTotalValue() {

        when(market.lookUpPrice("APPLES")).thenReturn(2.5);
        when(market.lookUpPrice("ORANGES")).thenReturn(4.0);

        portfolio.addStock(new Stock("APPLES", 3));
        portfolio.addStock(new Stock("ORANGES", 2));
        double result = portfolio.getTotalValue();

        assertThat(result, is(15.5));
        //assertEquals(15.5, result);
        verify(market, times(2)).lookUpPrice( anyString() );
    }
}
