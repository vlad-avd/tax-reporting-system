<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div>
    <table class="table">
        <tbody>
        <#if report.personType == "INDIVIDUAL">
            <tr>
                <th><@spring.message "messages.individual.person.report.first.name"/>:</th>
                <td>${report.name}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.individual.person.report.last.name"/>:</th>
                <td>${report.surname}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.individual.person.report.patronymic"/>:</th>
                <td>${report.patronymic}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.individual.person.report.workplace"/>:</th>
                <td>${report.workplace}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.individual.person.report.salary"/>:</th>
                <td>${report.salary}</td>
            </tr>
        <#else>
            <tr>
                <th><@spring.message "messages.legal.entity.report.company.name"/>:</th>
                <td>${report.companyName}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.legal.entity.report.employees.number"/>:</th>
                <td>${report.employeesNumber}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.legal.entity.report.financial.turnover"/>:</th>
                <td>${report.financialTurnover}</td>
            </tr>
        </#if>
            <tr>
                <th><@spring.message "messages.report.taxPeriodFrom"/>:</th>
                <td>${report.taxPeriodFrom}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.report.taxPeriodTo"/>:</th>
                <td>${report.taxPeriodTo}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.report.person.type"/>:</th>
                <td>${report.personType}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.report.status"/>:</th>
                <td>${report.reportStatus}</td>
            </tr>
        </tbody>
    </table>
</div>
<div class="form-group mt-3">
    <form action="/user/replace-inspector/${report.id}">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.replace.inspector"/></button>
    </form>
</div>
<div class="form-group mt-3">
    <form action="/individual-person-report">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.create.individual.person.report"/></button>
    </form>
</div>
<div class="form-group">
    <form action="/legal-entity-report">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.create.legal.entity.report"/></button>
    </form>
</div>
</@c.page>