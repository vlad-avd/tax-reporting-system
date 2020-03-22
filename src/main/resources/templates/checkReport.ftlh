<#import "parts/common.ftlh" as c>
<@c.page>
    Report check
    <div class="container mt-5">
        <form action="/check-report" method="post">
            <table>
                <thead>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Patronymic</th>
                    <th>Workplace</th>
                    <th>Salary</th>
                    <th>Period</th>
                    <th>Person Type</th>
                    <th>Taxpayer</th>
                    <th>Id</th>
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
                    <td>${report.personType}</td>
                    <td>${report.taxpayer}</td>
                    <td>${report.id}</td>
                </tr>
                </tbody>
            </table>
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Approve</button>
            <button type="submit">Reject</button>
            <button type="submit">Send to edit</button>
        </form>
    </div>
</@c.page>