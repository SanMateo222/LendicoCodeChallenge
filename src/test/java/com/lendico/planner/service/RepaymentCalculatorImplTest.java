package com.lendico.planner.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepaymentCalculatorImplTest {

    private RepaymentCalculator calculator;
    private static final BigDecimal basicLoanAmount = new BigDecimal(5000);
    private static final double basicNominalRate = 5.0;
    private static final int basicDuration = 24;
    private static final BigDecimal basicPrincipal = new BigDecimal(198.53);
    private static final BigDecimal basicInterest = new BigDecimal(20.83);
    private static final BigDecimal basicAnnuity = new BigDecimal(219.36);

    @Before
    public void setUp() throws Exception {
        calculator = new RepaymentCalculatorImpl();
    }

    @Test
    public void givenBasicValues_annuityShouldBeAsInGivenExample() {
        BigDecimal annuity = calculator.getAnnuityValue(basicDuration, basicNominalRate, basicLoanAmount);

        Assert.assertEquals(219.36, annuity.doubleValue(), 0);
    }

    @Test
    public void givenBasicValues_remainingOutstandingPrincipalShouldBeAsInGivenExample() {
        BigDecimal remainingOutstandingPrincipal = calculator.getRemainingOutstandingPrincipalValue(basicLoanAmount, basicPrincipal);

        Assert.assertEquals(remainingOutstandingPrincipal.doubleValue() , 4801.47, 0);
    }

    @Test
    public void givenBasicValues_principalShouldBeAsInGivenExample() {
        BigDecimal principal = calculator.getPrincipalValue(basicInterest, basicAnnuity);

        Assert.assertEquals(198.53, principal.doubleValue(), 0);
    }

    @Test
    public void givenBasicValues_interestShouldBeAsInGivenExample() {
        BigDecimal interest = calculator.getInterestValue(basicNominalRate, basicLoanAmount);

        Assert.assertEquals(basicInterest.doubleValue(), interest.doubleValue(), 0);
    }
}