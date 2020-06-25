<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftlh" as p />

<@c.page>
    <div class="form-group mt-3 container">
    <div class="row">
        <form style="margin-right: 50px" class="col-md-4" action="/report/individual-person-report">
            <button class="btn btn-primary" type="submit"><@spring.message "messages.user.create.individual.person.report"/></button>
        </form>
        <form class="col-md-4" action="/report/legal-entity-report">
            <button class="btn btn-primary" type="submit"><@spring.message "messages.user.create.legal.entity.report"/></button>
        </form>
    </div>
    <table class="table table-striped">
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
        <#list reports.content as report>
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
    </div>
    <@p.pager url reports/>
</@c.page>