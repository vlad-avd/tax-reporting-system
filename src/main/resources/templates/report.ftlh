<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div class="container">
    <table class="table table-striped">
        <tbody>
        <#if report.personType == "INDIVIDUAL">
            <tr>
                <th><@spring.message "messages.individual.person.report.full.name"/>:</th>
                <td>${report.fullName}</td>
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
                <th><@spring.message "messages.legal.entity.report.financial.turnover"/>:</th>
                <td>${report.financialTurnover}</td>
            </tr>
        </#if>
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
    <#if (report.reportStatus == 'NEED_TO_EDIT'
    || report.reportStatus == 'ON_VERIFYING')
    && replaceInspector == true
    && report.taxpayer.getId() == userId>
        <div class="form-group mt-3">
            <form action="/replace-inspector/${report.id}" method="post">
                <button class="btn btn-primary" type="submit"><@spring.message "messages.user.replace.inspector"/></button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </#if>
    <#if report.reportStatus == 'NEED_TO_EDIT' && report.taxpayer.getId() == userId>
        <div class="form-group mt-3">
            <form action="/report/edit/${report.id}">
                <button class="btn btn-primary" type="submit"><@spring.message "messages.report.edit"/></button>
            </form>
        </div>
    </#if>
</div>
</@c.page>