package ua.kpi.iasa.taxreportingsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportValidationResultDto {
    private boolean isFullNameValid;
    private boolean isWorkplaceValid;
    private boolean isSalaryValid;

    private boolean isCompanyNameValid;
    private boolean isFinancialTurnoverValid;
}
