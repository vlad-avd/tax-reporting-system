package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualPersonReportDTO extends ReportDTO {
    //IndividualPersonReport
    private String fullName;
    private String workplace;
    private BigDecimal salary;
}
