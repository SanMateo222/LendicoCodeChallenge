package com.lendico.planner.service;

import java.math.BigDecimal;

public interface RepaymentCalculator {

    BigDecimal getAnnuityValue(int duration, double nominalInterestRate, BigDecimal loanAmount);

    BigDecimal getRemainingOutstandingPrincipalValue(BigDecimal initialOutstandingPrincipal, BigDecimal principal);

    BigDecimal getPrincipalValue(BigDecimal interest, BigDecimal annuity);

    BigDecimal getInterestValue(double nominalRate, BigDecimal initialOutstandingPrincipal);


}
