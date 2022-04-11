package com.nhnacademy;

import com.nhnacademy.exception.InvalidCurrencyException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bank {

    private final static BigDecimal EXCHANGE_FEE = BigDecimal.valueOf(0.05);

    /**
     * 서로 다른 두 통화 간 환전을 진행하는 Method
     * @param money 환전을 요청한 통화
     * @param currency 환전이 될 통화
     * @return 환전 완료 후 통화
     */
    public Money exchange(Money money, Currency currency) {

        // 1. money.getCurrency == currency -> throw Exception
        if (money.getCurrency()
                 .equals(currency)) {
            throw new InvalidCurrencyException();
        }

        BigDecimal exchangedAmount = BigDecimal.valueOf(0);
        // 2. currency == WON
        if (currency.equals(Currency.WON)) {  // 다른 통화 -> 원으로 환전
            exchangedAmount = money.getCurrency()
                                   .toWon(money.getAmount());
            exchangedAmount = exchangedAmount.subtract(getExchangedFee(exchangedAmount));

            return Money.WON(exchangedAmount);
        }

        // 3. money.getCurrency == WON
        if (money.getCurrency().equals(Currency.WON)) {  // 원 -> 다른 통화
            exchangedAmount = currency.fromWon(money.getAmount());
            exchangedAmount = exchangedAmount.subtract(getExchangedFee(exchangedAmount));

            return new Money(currency, exchangedAmount);
        }

        // 4. money.getCurrency != WON && currency != WON
        if (money.getCurrency().equals(Currency.EURO)) {
            Money otherCurrencyToWon = exchange(money, Currency.WON);
            exchangedAmount = currency.fromWon(otherCurrencyToWon.getAmount());
        }

        return new Money(currency, exchangedAmount);
    }

    public BigDecimal getExchangedFee(BigDecimal amount) {
        return amount.multiply(EXCHANGE_FEE).setScale(2, RoundingMode.HALF_DOWN);
    }
}
