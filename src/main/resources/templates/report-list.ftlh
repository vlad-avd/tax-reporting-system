<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col"><@spring.message "messages.report.period"/></th>
        <th scope="col"><@spring.message "messages.report.person.type"/></th>
        <th scope="col"><@spring.message "messages.report.status"/></th>
    </tr>
    </thead>
    <tbody>
    <#assign reportNumber = 1>
    <#list reports as report>
        <tr>
            <th scope="row">${reportNumber}</th>
            <#assign reportNumber++>
            <td>${report.personType}</td>
            <td>${report.reportStatus}</td>
            <td><a href="/report/${report.id}"><@spring.message "messages.report.open"/></a></td>
            <#if report.reportStatus.toString() == "NEED_TO_EDIT"><td><a href="/report/${report.id}"><@spring.message "messages.report.edit"/></a></td></#if>
        </tr>
    </#list>
    </tbody>
</table>
<div class="form-group mt-3">
    <form action="/report/individual-person-report">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.create.individual.person.report"/></button>
    </form>
</div>
<div class="form-group">
    <form action="/report/legal-entity-report">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.create.legal.entity.report"/></button>
    </form>
</div>
</@c.page>