<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    <div class="container">
        <div>
            <table class="table table-striped col-md-8">
                <tbody>
                <#if report.personType == 'INDIVIDUAL'>
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
                </#if>
                <#if report.personType == 'ENTITY'>
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
        </div>
        <form action="/check-report/${report.id}" method="post">
            <div class="row container" style="margin-bottom: 20px">
                <button class="btn btn-success" type="submit" name="reportStatus" value="approve"><@spring.message "messages.report.approve"/></button>
                <button style="margin: 0px 50px" class="btn btn-danger" type="submit" name="reportStatus" value="reject"><@spring.message "messages.report.reject"/></button>
                <select class="form-control col-md-4" name="rejectionReason">
                    <option selected value="">- - - - -</option>
                    <#list rejectionReasons as reason>
                        <option name="rejectionReason" value="${reason}">${reason}</option>
                    </#list>
                </select>
            </div>
            <div class="row container">
                <button class="btn btn-warning" style="margin-right: 40px" type="submit" name="reportStatus" value="sendToEdit"><@spring.message "messages.report.send.to.edit"/></button>
                <textarea class="form-control col-md-4 " name="comment" rows="4"></textarea>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</@c.page>