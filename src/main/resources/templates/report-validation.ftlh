<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    Report check
    <div class="container mt-5">
        <div>
            <span><@spring.message "messages.individual.person.report.first.name"/></span>
            <span>${report.name}</span>
        </div>
        <div>
            <span><@spring.message "messages.individual.person.report.last.name"/></span>
            <span>${report.surname}</span>
        </div>
        <div>
            <span><@spring.message "messages.individual.person.report.patronymic"/></span>
            <span>${report.patronymic}</span>
        </div>
        <div>
            <span><@spring.message "messages.individual.person.report.workplace"/></span>
            <span>${report.workplace}</span>
        </div>
        <div>
            <span><@spring.message "messages.individual.person.report.salary"/></span>
            <span>${report.salary}</span>
        </div>
        <div>
            <span><@spring.message "messages.report.period"/></span>
            <span>${report.period}</span>
        </div>
        <div>
            <span><@spring.message "messages.report.person.type"/></span>
            <span>${report.personType}</span>
        </div>
        <div>
            <span><@spring.message "messages.report.taxpayer"/></span>
            <span>${report.taxpayer}</span>
        </div>
        <div>
            <span><@spring.message "messages.report.id"/></span>
            <span>${report.id}</span>
        </div>

        <form action="/check-report/${report.id}" method="post">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" name="reportStatus" value="approve"><@spring.message "messages.report.approve"/></button>
            <select name="message">
                <option disabled selected value></option>
                <#list rejectionReasons as reason>
                    <option name="message" value="${reason}">${reason}</option>
                </#list>
            </select>
            <button type="submit" name="reportStatus" value="reject"><@spring.message "messages.report.reject"/></button>
            <select name="message">
                <option disabled selected value></option>
                <#list edits as edit>
                    <option name="message" value="${edit}">${edit}</option>
                </#list>
            </select>
            <div>
                <button type="submit" name="reportStatus" value="sendToEdit"><@spring.message "messages.report.send.to.edit"/></button>
            </div>
        </form>
    </div>
</@c.page>