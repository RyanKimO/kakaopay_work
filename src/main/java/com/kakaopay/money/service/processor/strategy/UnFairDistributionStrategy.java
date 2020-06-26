package com.kakaopay.money.service.processor.strategy;

import java.util.HashSet;
import java.util.Set;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class UnFairDistributionStrategy implements DistributionStrategy {

    @Override
    public Set<Dividend> distribute(Distribution distribution) {
        int divCnt = distribution.getDividedCnt();
        long initialAmount = distribution.getAmount();

        Set<Dividend> dividends = new HashSet<>();
        long sumOfDividends = 0;
        for(int i = 0; i < divCnt; i++) {
            int pivot = (int) (initialAmount / divCnt);
            long dividendAmount = (long) (Math.random() * pivot);
            Dividend dividend = new Dividend();
            dividend.setAmount(dividendAmount);
            dividends.add(dividend);
            sumOfDividends += dividendAmount;
        }
        if(sumOfDividends != initialAmount) {
            adjustDiff(initialAmount - sumOfDividends, dividends);
        }

        return dividends;
    }


    private void adjustDiff(long diff, Set<Dividend> dividends) {
        if(dividends.iterator().hasNext()) {
            Dividend leverage = dividends.iterator().next();
            leverage.setAmount(leverage.getAmount() + diff);
        }
    }

}
