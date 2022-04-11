package com.nhnacademy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Currency implements Exchangable {

    WON(1000L) {

        @Override
        public BigDecimal round(BigDecimal amount) {
            return amount.divide(BigDecimal.valueOf(10),0, RoundingMode.HALF_EVEN).multiply(
                BigDecimal.valueOf(10));
        }

        @Override
        public BigDecimal toWon(BigDecimal thisAmount) {
            return thisAmount;
        }

        @Override
        public BigDecimal fromWon(BigDecimal wonAmount) {
            return wonAmount.divide(BigDecimal.valueOf(getRate()), 0, RoundingMode.HALF_EVEN);
        }
    },

    DOLLAR(1000L) {
        @Override
        public BigDecimal round(BigDecimal amount) {
            return amount.setScale(2, RoundingMode.HALF_DOWN);
        }

        @Override
        public BigDecimal toWon(BigDecimal thisAmount) {
            return thisAmount.multiply(BigDecimal.valueOf(getRate()));
        }

        @Override
        public BigDecimal fromWon(BigDecimal wonAmount) {
            return wonAmount.divide(BigDecimal.valueOf(getRate()), 2, RoundingMode.HALF_DOWN);
        }
    },

    EURO(1300L) {
        @Override
        public BigDecimal round(BigDecimal amount) {
            return amount.setScale(2, RoundingMode.HALF_DOWN);
        }

        @Override
        public BigDecimal toWon(BigDecimal thisAmount) {
            return thisAmount.multiply(BigDecimal.valueOf(getRate()));
        }

        @Override
        public BigDecimal fromWon(BigDecimal wonAmount) {
            return wonAmount.divide(BigDecimal.valueOf(getRate()), 2, RoundingMode.HALF_DOWN);
        }
    };

    private final long rate;

    public long getRate() {
        return rate;
    }

    Currency(long rate) {
        this.rate = rate;
    }
}
