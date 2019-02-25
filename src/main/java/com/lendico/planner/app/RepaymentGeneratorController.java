package com.lendico.planner.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lendico.planner.model.MonthlyRepayment;
import com.lendico.planner.service.RepaymentGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/generate-repayment-plan")
public class RepaymentGeneratorController {

    @Autowired
    private RepaymentGenerator repaymentGenerator;

    @PostMapping
    public @ResponseBody String generateFullRepaymentPlan(@RequestBody RepaymentGeneratorRequest request) {

        List<MonthlyRepayment> fullRepaymentPlan = repaymentGenerator.generateFullRepaymentPlan(request.getLoanAmount(),
                request.getNominalRate(), request.getDuration(), request.getStartDate());

        return jsonConverter(fullRepaymentPlan);
    }

    private String jsonConverter(List<MonthlyRepayment> monthlyRepayments) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(monthlyRepayments);
    }
}