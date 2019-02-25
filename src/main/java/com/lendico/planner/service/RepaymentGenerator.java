package com.lendico.planner.service;

import com.lendico.planner.model.MonthlyRepayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RepaymentGenerator {

    List<MonthlyRepayment>  generateFullRepaymentPlan(BigDecimal loanAmount, double nominalRate, int duration, String startDate);

}