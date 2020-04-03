<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    <form action="/report/legal-entity-report" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.legal.entity.report.company.name"/> : </label>
            <div class="col-sm-6">
                <input type="text" name="companyName" class="form-control" placeholder=<@spring.message "messages.legal.entity.report.company.name"/>>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.report.taxPeriodFrom"/> : </label>
            <div class="col-sm-6">
                <input type="text" name="taxPeriodFrom" class="form-control" placeholder=<@spring.message "messages.report.taxPeriodFrom"/>>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.report.taxPeriodTo"/> : </label>
            <div class="col-sm-6">
                <input type="text" name="taxPeriodTo" class="form-control" placeholder=<@spring.message "messages.report.taxPeriodTo"/>>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.legal.entity.report.employees.number"/> : </label>
            <div class="col-sm-6">
                <input type="text" name="employeesNumber" class="form-control" placeholder=<@spring.message "messages.legal.entity.report.employees.number"/>>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.legal.entity.report.financial.turnover"/> : </label>
            <div class="col-sm-6">
                <input type="text" name="financialTurnover" class="form-control" placeholder=<@spring.message "messages.legal.entity.report.financial.turnover"/>>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><@spring.message "messages.report.submit"/></button>
    </form>
</@c.page>