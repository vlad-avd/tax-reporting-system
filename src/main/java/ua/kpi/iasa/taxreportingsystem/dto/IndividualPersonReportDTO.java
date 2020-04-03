package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualPersonReportDTO extends ReportDTO {
    //IndividualPersonReport
    private String name;
    private String surname;
    private String patronymic;
    private String workplace;
    private BigDecimal salary;
}
