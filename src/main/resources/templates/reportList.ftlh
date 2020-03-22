<#import "parts/common.ftlh" as c>
<@c.page>
<table>
    <thead>
    <tr>
        <th>Period</th>
        <th>Id</th>
        <th>Report status</th>
    </tr>
    </thead>
    <tbody>
    <#list reports as report>
        <tr>
            <td>${report.period}</td>
            <td>${report.id}</td>
            <td>${report.reportStatus}</td>
            <td><a href="/report/${report.id}">Open</a></td>
        </tr>
    </#list>
    </tbody>
</table>
<div class="form-group mt-3">
    <form action="/individual-person-report">
        <button class="btn btn-primary" type="submit">Create individual person report</button>
    </form>
</div>
<div class="form-group">
    <form action="/legal-entity-report">
        <button class="btn btn-primary" type="submit">Create legal entity report</button>
    </form>
</div>
</@c.page>