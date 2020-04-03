<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    <form action="/report/individual-person-report" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.legal.entity.report.company.name"/>: </label>
            <div class="col-sm-6">
                <input type="text" name="company-name" class="form-control" placeholder=<@spring.message "messages.legal.entity.report.company.name"/>/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.legal.entity.report.financial.turnover"/>: </label>
            <div class="col-sm-6">
                <input type="text" name="financial-turnover" class="form-control" placeholder=<@spring.message "messages.legal.entity.report.financial.turnover"/>/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.legal.entity.report.employees.number"/>: </label>
            <div class="col-sm-6">
                <input type="text" name="employees-number" class="form-control" placeholder=<@spring.message "messages.legal.entity.report.employees.number"/>/>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><@spring.message "messages.report.submit"/></button>
    </form>
</@c.page>