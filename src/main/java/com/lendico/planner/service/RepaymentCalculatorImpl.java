package com.lendico.planner.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RepaymentCalculatorImpl implements RepaymentCalculator {
    private static final Integer NUMBER_OF_MONTHS = 12;
    private static final Integer NUMBER_OF_DAYS_IN_MONTH = 30;
    private static final Integer NUMBER_OF_DAYS_IN_YEAR = 360;

    @Override
    public BigDecimal getRemainingOutstandingPrincipalValue(BigDecimal initialOutstandingPrincipal, BigDecimal principal) {

        BigDecimal remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal);

        if (remainingOutstandingPrincipal.compareTo(BigDecimal.ZERO) < 0) {
            remainingOutstandingPrincipal = roundValue(BigDecimal.ZERO);
        }

        return remainingOutstandingPrincipal;
    }

    @Override
    public BigDecimal getAnnuityValue(int duration, double nominalInterestRate, BigDecimal loanAmount) {

        double monthlyNominalRate = calculateMonthlyInterestRate(nominalInterestRate);
        BigDecimal annuity = new BigDecimal(calculateAnnuityBasedOnFormula(duration, loanAmount, monthlyNominalRate));

        return roundValue(annuity);    }

    @Override
    public BigDecimal getPrincipalValue(BigDecimal interest, BigDecimal annuity) {

        BigDecimal principal = annuity.subtract(interest);

        return roundValue(principal);    }

    @Override
    public BigDecimal getInterestValue(double nominalRate, BigDecimal initialOutstandingPrincipal) {

        BigDecimal interest = new BigDecimal(nominalRate * NUMBER_OF_DAYS_IN_MONTH * initialOutstandingPrincipal.doubleValue() / NUMBER_OF_DAYS_IN_YEAR);

        return roundValue(interest.divide(BigDecimal.valueOf(100)));    }


    private BigDecimal roundValue(BigDecimal value){
        return value.setScale(2, RoundingMode.HALF_EVEN);

    }

    private double calculateAnnuityBasedOnFormula(int duration, BigDecimal loanAmount, double monthlyNominalRate) {

        //Calculations based on http://financeformulas.net/Annuity_Payment_Formula.html

        double presentValue = monthlyNominalRate * loanAmount.doubleValue();
        return presentValue / (1 - Math.pow(1 + monthlyNominalRate, -duration));
    }

    private double calculateMonthlyInterestRate(double nominalInterestRate) {
        return convertToPercent(nominalInterestRate) / NUMBER_OF_MONTHS;
    }

    private double convertToPercent(double nominalInterestRate) {
        return nominalInterestRate / 100.0;
    }
}

