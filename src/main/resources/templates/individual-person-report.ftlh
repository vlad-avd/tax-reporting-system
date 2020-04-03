<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6"><@spring.message "messages.individual.person.report.first.name"/>:</span>
        <span>${report.name}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.individual.person.report.last.name"/>:</span>
        <span>${report.surname}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.individual.person.report.patronymic"/>:</span>
        <span>${report.patronymic}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.individual.person.report.workplace"/>:</span>
        <span>${report.workplace}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.individual.person.report.salary"/>:</span>
        <span>${report.salary}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.report.taxPeriodFrom"/>:</span>
        <span>${report.taxPeriodFrom}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.report.taxPeriodTo"/>:</span>
        <span>${report.taxPeriodTo}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.report.person.type"/>:</span>
        <span>${report.personType}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.report.status"/>:</span>
        <span>${report.reportStatus}</span>
    </div>
    <div class="border-bottom mb-2 col-md-4">
        <span class="h6 mb-1"><@spring.message "messages.report.id"/>:</span>
        <span>${report.id}</span>
    </div>
</div>
<div class="form-group mt-3">
    <form action="/user/replace-inspector/${report.id}">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.replace.inspector"/></button>
    </form>
</div>
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