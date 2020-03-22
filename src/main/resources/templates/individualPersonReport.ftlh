<#import "parts/common.ftlh" as c>
<@c.page>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Patronymic</th>
        <th>Workplace</th>
        <th>Salary</th>
        <th>Period</th>
        <th>Person type</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>${report.name}</td>
            <td>${report.surname}</td>
            <td>${report.patronymic}</td>
            <td>${report.workplace}</td>
            <td>${report.salary}</td>
            <td>${report.period}</td>
            <td>${report.personType.toString()}</td>
        </tr>
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