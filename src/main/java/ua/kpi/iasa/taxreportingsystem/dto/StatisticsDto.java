package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class StatisticsDto {
    private int reportsNumber;

    private LocalDate firstReportDate;

    private LocalDate lastReportDate;

    private int onVerifyingReportsNumber;

    private int needToEditReportsNumber;

    private int approvedReportsNumber;

    private int rejectedReportsNumber;
}