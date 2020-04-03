<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<form action="/report/individual-person-report" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.first.name"/> : </label>
        <div class="col-sm-6">
            <input type="text" name="name" class="form-control" placeholder=<@spring.message "messages.individual.person.report.first.name"/>>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.last.name"/> : </label>
        <div class="col-sm-6">
            <input type="text" name="surname" class="form-control" placeholder=<@spring.message "messages.individual.person.report.last.name"/>>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.patronymic"/> : </label>
        <div class="col-sm-6">
            <input type="text" name="patronymic" class="form-control" placeholder=<@spring.message "messages.individual.person.report.patronymic"/>>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.workplace"/> : </label>
        <div class="col-sm-6">
            <input type="text" name="workplace" class="form-control" placeholder=<@spring.message "messages.individual.person.report.workplace"/>>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> <@spring.message "messages.individual.person.report.salary"/>: </label>
        <div class="col-sm-6">
            <input type="text" name="salary" class="form-control" placeholder=<@spring.message "messages.individual.person.report.salary"/>>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit"><@spring.message "messages.report.submit"/></button>
</form>
</@c.page>