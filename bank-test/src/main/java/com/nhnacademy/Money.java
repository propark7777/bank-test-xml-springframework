package com.nhnacademy;

import com.nhnacademy.exception.InvalidCurrencyException;
import com.nhnacademy.exception.NegativeMoneyException;
import java.math.BigDecimal;

public class Money {

    private final Currency currency;
    private final BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new NegativeMoneyException();
        }
        this.currency = currency;
        this.amount = currency.round(amount);
    }

    public static Money WON(BigDecimal amount) {
        return new Money(Currency.WON, amount);
    }

    public static Money DOLLAR(BigDecimal amount) {
        return new Money(Currency.DOLLAR, amount);
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Money add(Money money) {
        if (!money.getCurrency()
                  .equals(this.getCurrency())) {
            throw new IllegalArgumentException();
        }

        BigDecimal resultAmount = money.getAmount().add(this.getAmount());

        if (money.getCurrency() == Currency.WON) {
            return new Money(Currency.WON, resultAmount);
        }

        return new Money(Currency.DOLLAR, resultAmount);
    }

    public Money subtract(Money money) {
        if (!money.getCurrency()
                  .equals(this.getCurrency())) {
            throw new InvalidCurrencyException();
        }

        if (this.getAmount().compareTo(money.getAmount()) < 0) {
            throw new NegativeMoneyException();
        }

        BigDecimal newAmount = this.getAmount().subtract(money.getAmount());

        if (money.getCurrency() == Currency.WON) {
            return Money.WON(newAmount);
        }

        return new Money(money.getCurrency(), newAmount);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return (currency.equals(money.getCurrency())
            && (this.getAmount().equals(money.getAmount())));
    }

}
