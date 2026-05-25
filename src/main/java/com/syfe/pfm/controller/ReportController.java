package com.syfe.pfm.controller;

import com.syfe.pfm.dto.response.MonthlyReportResponse;
import com.syfe.pfm.dto.response.YearlyReportResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.service.AuthService;
import com.syfe.pfm.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// reports endpoints - monthly and yearly
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final AuthService authService;

    public ReportController(ReportService reportService, AuthService authService) {
        this.reportService = reportService;
        this.authService = authService;
    }

    @GetMapping("/monthly/{year}/{month}")
    public MonthlyReportResponse monthly(@PathVariable int year, @PathVariable int month) {
        User user = authService.getCurrentUser();
        return reportService.getMonthlyReport(user, year, month);
    }

    @GetMapping("/yearly/{year}")
    public YearlyReportResponse yearly(@PathVariable int year) {
        User user = authService.getCurrentUser();
        return reportService.getYearlyReport(user, year);
    }
}
