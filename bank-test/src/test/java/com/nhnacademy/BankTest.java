package com.nhnacademy;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankTest {

    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @DisplayName("수수료 확인")
    @Test
    void checkExchangeFee() {
        Money money1 = Money.WON(BigDecimal.valueOf(10000).setScale(0, RoundingMode.HALF_DOWN));

        assertThat(bank.getExchangedFee(money1.getAmount())).isEqualTo(money1.getAmount().multiply(BigDecimal.valueOf(0.05)));
    }

    @DisplayName("[수수료 계산 포함] 환전 WON -> DOLLAR")
    @Test
    void exchangeToDollarFee() {

        Money moneyWon = Money.WON(BigDecimal.valueOf(10000));
        BigDecimal exchangeResult = Money.DOLLAR(BigDecimal.valueOf(10))
                                         .getAmount()
                                         .subtract(bank.getExchangedFee(Money.DOLLAR(BigDecimal.valueOf(10))
                                                                             .getAmount()));

        assertThat(bank.exchange(moneyWon, Currency.DOLLAR).getAmount()).isEqualTo(exchangeResult);
    }

    @DisplayName("[수수료 계산 포함] 환전 DOLLAR -> WON")
    @Test
    void exchangeToWonFee() {
        Money moneyDollar = Money.DOLLAR(BigDecimal.valueOf(10));
        BigDecimal resultAmount = bank.exchange(moneyDollar, Currency.WON)
                                      .getAmount();
        BigDecimal expectAmount = (Money.WON(BigDecimal.valueOf(10000))
                                        .getAmount()
                                        .subtract(bank.getExchangedFee(BigDecimal.valueOf(10000)))).setScale(0,RoundingMode.HALF_UP);
        assertThat(resultAmount).isEqualTo(expectAmount);
    }

    @DisplayName("EURO -> DOLLAR 환전")
    @Test
    void euroToDollar() {
        Money euro = new Money(Currency.EURO, BigDecimal.valueOf(100));
        // euro -> won 100 -> 130_000 - fee(6,500) = 123,500
        Money expect = Money.DOLLAR(BigDecimal.valueOf(123.5));

        Money exchangeResult = bank.exchange(euro, Currency.DOLLAR);

        assertThat(exchangeResult.getAmount()).isEqualTo(expect.getAmount());
    }
}