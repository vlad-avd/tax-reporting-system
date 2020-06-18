<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<form action="/report/individual-person-report" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.full.name"/> : </label>
        <div class="col-sm-4">
            <input type="text" name="fullName" class="form-control" placeholder=<@spring.message "messages.individual.person.report.full.name"/>>
        </div>
    </div>
    <#if isFullNameValid??>
        <#if isFullNameValid == false>
            <div class="alert alert-danger"><p><@spring.message "messages.incorrect.input"/></p></div>
        </#if>
    </#if>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.workplace"/> : </label>
        <div class="col-sm-4">
            <input type="text" name="workplace" class="form-control" placeholder=<@spring.message "messages.individual.person.report.workplace"/>>
        </div>
    </div>
    <#if isWorkplaceValid??>
        <#if isWorkplaceValid == false>
            <div class="alert alert-danger"><p><@spring.message "messages.incorrect.input"/></p></div>
        </#if>
    </#if>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.salary"/>: </label>
        <div class="col-sm-4">
            <input type="text" name="salary" class="form-control" placeholder=<@spring.message "messages.individual.person.report.salary"/>>
        </div>
    </div>
    <#if isSalaryValid??>
        <#if isSalaryValid == false>
            <div class="alert alert-danger"><p><@spring.message "messages.incorrect.input"/></p></div>
        </#if>
    </#if>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit"><@spring.message "messages.report.submit"/></button>
</form>
</@c.page>