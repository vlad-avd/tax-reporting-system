<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftlh" as p/>
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
        <#list reports.content as report>
            <tr>
                <td>${report.id}</td>
                <td>${report.reportStatus}</td>
                <td><a href="/verification-report/${report.id}"><@spring.message "messages.report.open"/></a></td>
            </tr>
        </#list>
        </tbody>
    </table>
    <@p.pager url reports/>
</@c.page>