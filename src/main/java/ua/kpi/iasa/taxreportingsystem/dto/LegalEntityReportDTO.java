package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kpi.iasa.taxreportingsystem.domain.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegalEntityReportDTO extends ReportDTO {
    //LegalEntityReport
    private String companyName;
    private BigDecimal financialTurnover;
    private int employeesNumber;

    private List<User> replacedInspectors = new ArrayList<>();
}
