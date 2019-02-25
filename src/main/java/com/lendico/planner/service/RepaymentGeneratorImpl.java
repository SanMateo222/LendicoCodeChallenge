package com.lendico.planner.service;

import com.lendico.planner.model.MonthlyRepayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentGeneratorImpl implements RepaymentGenerator {

    public RepaymentGeneratorImpl(RepaymentCalculator repaymentCalculator){
        this.repaymentCalculator = repaymentCalculator;
    }

    @Autowired
    private RepaymentCalculator repaymentCalculator;

    @Override
    public List<MonthlyRepayment> generateFullRepaymentPlan(BigDecimal loanAmount, double nominalRate,
                                                            int duration, String startDate) {

        List<MonthlyRepayment> monthlyRepaymentsList = new ArrayList<>();
        BigDecimal initialOutstandingPrincipal = loanAmount;

        for (int currentRepaymentMonth = 0; currentRepaymentMonth < duration; currentRepaymentMonth++) {
            MonthlyRepayment monthlyRepayment = generateMonthlyRepayment(loanAmount, nominalRate, duration, startDate, currentRepaymentMonth, initialOutstandingPrincipal);
            initialOutstandingPrincipal = setNewInitialOutstandingPrincipal(monthlyRepayment);

            monthlyRepaymentsList.add(monthlyRepayment);
        }

        return monthlyRepaymentsList;
    }

    private MonthlyRepayment generateMonthlyRepayment(BigDecimal loanAmount, double nominalRate, int duration,
                                                      String startDate, int currentRepaymentMonth, BigDecimal initialOutstandingPrincipal) {

        MonthlyRepayment monthlyRepayment = new MonthlyRepayment();
        BigDecimal interest = repaymentCalculator.getInterestValue(nominalRate, initialOutstandingPrincipal);
        BigDecimal annuity = repaymentCalculator.getAnnuityValue(duration, nominalRate, loanAmount);
        BigDecimal principal = repaymentCalculator.getPrincipalValue(interest, annuity);
        BigDecimal remainingOutstandingPrincipal = repaymentCalculator.getRemainingOutstandingPrincipalValue(initialOutstandingPrincipal, principal);

        monthlyRepayment.setDate(generateCurrentDate(startDate, currentRepaymentMonth));
        monthlyRepayment.setInterest(interest);
        monthlyRepayment.setBorrowerPaymentAmount(annuity);
        monthlyRepayment.setPrincipal(principal);
        monthlyRepayment.setInitialOutstandingPrincipal(initialOutstandingPrincipal);
        monthlyRepayment.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal);

        return monthlyRepayment;
    }

    private String generateCurrentDate(String startDate, int currentRepaymentMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);

        return dateTime.plusMonths(currentRepaymentMonth).format(formatter);
    }

    private BigDecimal setNewInitialOutstandingPrincipal(MonthlyRepayment monthlyRepayment) {
        return monthlyRepayment.getRemainingOutstandingPrincipal();
    }
}