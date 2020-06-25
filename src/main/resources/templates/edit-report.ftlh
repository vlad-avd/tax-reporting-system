<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftlh" as p />

<@c.page>
    <div class="container">
        <form action="/report/edit/${report.id}" method="post">
            <#if report.personType.toString() == 'INDIVIDUAL'>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.individual.person.report.full.name"/>:</strong></label>
                    <div class="col-sm-4">
                        <input type="text" name="fullName" class="form-control" value="${report.fullName}">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.individual.person.report.workplace"/>:</strong></label>
                    <div class="col-sm-4">
                        <input type="text" name="workplace" class="form-control" value="${report.workplace}">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.individual.person.report.salary"/>:</strong></label>
                    <div class="col-sm-4">
                        <input type="text" name="salary" class="form-control" value="${report.salary}">
                    </div>
                </div>
            </#if>
            <#if report.personType.toString() == 'ENTITY'>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.legal.entity.report.company.name"/>:</strong></label>
                    <div class="col-sm-4">
                        <input type="text" name="companyName" class="form-control" value="${report.companyName}">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.legal.entity.report.financial.turnover"/>:</strong></label>
                    <div class="col-sm-4">
                        <input type="text" name="financialTurnover" class="form-control" value="${report.financialTurnover}">
                    </div>
                </div>
            </#if>
            <div>
                <button class="btn btn-primary" type="submit"><@spring.message "messages.report.save.changes"/></button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
        </form>
        <form action="/report">
            <button class="btn btn-primary" type="submit"><@spring.message "messages.user.cancel"/></button>
        </form>
    </div>
</@c.page>