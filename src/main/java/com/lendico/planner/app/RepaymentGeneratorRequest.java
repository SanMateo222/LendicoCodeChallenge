package com.lendico.planner.app;

import java.math.BigDecimal;

public class RepaymentGeneratorRequest {

    private BigDecimal loanAmount;
    private Double nominalRate;
    private Integer duration;
    private String startDate;

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public Double getNominalRate() {
        return nominalRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setNominalRate(Double nominalRate) {
        this.nominalRate = nominalRate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
