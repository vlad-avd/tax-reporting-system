<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftlh" as p/>
<@c.page>
    <div class="form-group mt-3 container">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th><@spring.message "messages.report.person.type"/></th>
            <th><@spring.message "messages.report.created.date"/></th>
            <th><@spring.message "messages.report.status"/></th>
        </tr>
        </thead>
        <tbody>
        <#assign reportNumber = reports.getNumber() * reports.getSize() + 1>
        <#list reports.content as report>
            <tr>
                <th scope="row">${reportNumber}</th>
                <#assign reportNumber++>
                <td>${report.personType}</td>
                <td>${report.reportStatus}</td>
                <td><a href="/verification-report/${report.id}"><@spring.message "messages.report.open"/></a></td>
            </tr>
        </#list>
        </tbody>
    </table>
    </div>
    <@p.pager url reports/>
</@c.page>