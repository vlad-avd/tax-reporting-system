<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    <table>
        <thead>
        <tr>
            <th><@spring.message "messages.report.period"/></th>
            <th><@spring.message "messages.report.id"/></th>
            <th><@spring.message "messages.report.status"/></th>
        </tr>
        </thead>
        <tbody>
        <#list reports as report>
            <tr>
                <td>${report.taxPeriodFrom} <br> ${report.taxPeriodTo}</td>
                <td>${report.id}</td>
                <td>${report.reportStatus}</td>
                <td><a href="/verification-report/${report.id}"><@spring.message "messages.report.open"/></a></td>
            </tr>
        </#list>
        </tbody>
    </table>
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