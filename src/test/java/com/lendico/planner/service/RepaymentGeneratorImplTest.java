package com.lendico.planner.service;

import com.lendico.planner.model.MonthlyRepayment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepaymentGeneratorImplTest {

    private RepaymentGenerator generator;
    private RepaymentCalculator calculator;

    private static final double basicNominalRate = 5.0;
    private static final int basicDuration = 24;
    private static final String basicStartDate = "2018-01-01T00:00:00Z";
    private static final BigDecimal basicLoanAmount = new BigDecimal(5000);
    private static final BigDecimal basicPrincipal = new BigDecimal(198.53);
    private static final BigDecimal basicInterest = new BigDecimal(20.83);
    private static final BigDecimal basicAnnuity = new BigDecimal(219.36);
    private static final BigDecimal basicRemainingOutstandingPrincipal = new BigDecimal(4801.47);

    @Before
    public void setUp() throws Exception {
        calculator = new RepaymentCalculatorImpl();
        generator = new RepaymentGeneratorImpl(calculator);
    }

    @Test
    public void givenBasicValues_whenDurationOfLoanRepaymentIs24_ThenListOfParticularPaymentsShouldBeEqual() {
        List<MonthlyRepayment> list = generator.generateFullRepaymentPlan(basicLoanAmount, basicNominalRate, basicDuration, basicStartDate);

        Assert.assertTrue(list.size() == basicDuration);
    }

    @Test
    public void givenBasicValues_whenDurationOfLoanRepaymentIs10_ThenListOfParticularPaymentsShouldBeEqual() {
        List<MonthlyRepayment> list = generator.generateFullRepaymentPlan(basicLoanAmount, basicNominalRate, 10, basicStartDate);

        Assert.assertTrue(list.size() == 10);
    }

    @Test
    public void givenBasicValues_whenExecuted_ThenFirstRepaymentShouldBeAsInGivenExample() {
        List<MonthlyRepayment> list = generator.generateFullRepaymentPlan(basicLoanAmount, basicNominalRate, basicDuration, basicStartDate);

        Assert.assertEquals(list.get(0).getBorrowerPaymentAmount().doubleValue() , basicAnnuity.doubleValue(), 0);
        Assert.assertEquals(list.get(0).getDate(), (basicStartDate));
        Assert.assertEquals(list.get(0).getInitialOutstandingPrincipal().doubleValue(), basicLoanAmount.doubleValue(), 0);
        Assert.assertEquals(list.get(0).getInterest().doubleValue(), basicInterest.doubleValue(), 0);
        Assert.assertEquals(list.get(0).getPrincipal().doubleValue(), basicPrincipal.doubleValue(), 0);
        Assert.assertEquals(list.get(0).getRemainingOutstandingPrincipal().doubleValue(), basicRemainingOutstandingPrincipal.doubleValue(), 0);
    }

    @Test
    public void givenBasicValues_whenExecuted_ThenSecondRepaymentShouldBeAsInGivenExample() {
        List<MonthlyRepayment> list = generator.generateFullRepaymentPlan(basicLoanAmount, basicNominalRate, basicDuration, basicStartDate);

        Assert.assertEquals(list.get(1).getBorrowerPaymentAmount().doubleValue() , basicAnnuity.doubleValue(), 0);
        Assert.assertEquals(list.get(1).getDate(), ("2018-02-01T00:00:00Z"));
        Assert.assertEquals(list.get(1).getInitialOutstandingPrincipal().doubleValue(), new BigDecimal(4801.47).doubleValue(), 0);
        Assert.assertEquals(list.get(1).getInterest().doubleValue(), new BigDecimal(20.01).doubleValue(), 0);
        Assert.assertEquals(list.get(1).getPrincipal().doubleValue(), new BigDecimal(199.35).doubleValue(), 0);
        Assert.assertEquals(list.get(1).getRemainingOutstandingPrincipal().doubleValue(), new BigDecimal(4602.12).doubleValue(), 0);
    }


}