<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    <div class="container">
        <div class="row">
            <table class="table table-striped col-md-5" style="margin-right: 100px">
                <tbody>
                <tr>
                    <th><@spring.message "messages.user.username"/>:</th>
                    <td>${user.username}</td>
                </tr>
                <#if statistics.reportsNumber != 0>
                    <tr>
                        <th><@spring.message "messages.on.verifying.reports.number"/>:</th>
                        <td>${statistics.onVerifyingReportsNumber}</td>
                    </tr>
                    <tr>
                        <th><@spring.message "messages.first.report.date"/>:</th>
                        <td>${statistics.firstReportDate}</td>
                    </tr>
                    <tr>
                        <th><@spring.message "messages.last.report.date"/>:</th>
                        <td>${statistics.lastReportDate}</td>
                    </tr>
                </#if>
                </tbody>
            </table>
            <table class="table table-striped col-md-5">
                <tbody>
                <tr>
                    <th><@spring.message "messages.reports.number"/>:</th>
                    <td>${statistics.reportsNumber}</td>
                </tr>
                <#if statistics.reportsNumber != 0>
                    <tr>
                        <th><@spring.message "messages.approved.reports.number"/>:</th>
                        <td>${statistics.approvedReportsNumber}</td>
                    </tr>
                    <tr>
                        <th><@spring.message "messages.rejected.reports.number"/>:</th>
                        <td>${statistics.rejectedReportsNumber}</td>
                    </tr>
                    <tr>
                        <th><@spring.message "messages.need.to.edit.reports.number"/>:</th>
                        <td>${statistics.needToEditReportsNumber}</td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>