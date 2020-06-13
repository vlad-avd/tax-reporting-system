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
public class LegalEntityReportDTO extends ReportDTO {
    //LegalEntityReport
    private String companyName;
    private BigDecimal financialTurnover;
}
