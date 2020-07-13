<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div class="container">
    <form action="/user/edit/${user.id}" method="post">
        <div class="form-group row">
            <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.account.new.username"/>:</strong></label>
            <div class="col-sm-4">
                <input type="text" name="username" class="form-control" value="${user.username}">
            </div>
        </div>
        <#if isUsernameValid??>
            <#if isUsernameValid == false>
                <div class="alert alert-danger"><p><@spring.message "messages.invalid.username"/></p></div>
            </#if>
        </#if>
        <div class="form-group row">
            <label class="col-sm-3 col-form-label"><strong> <@spring.message "messages.account.new.password"/>:</strong></label>
            <div class="col-sm-4">
                <input type="text" name="password" class="form-control" value="${user.password}">
            </div>
        </div>
        <#if isPasswordValid??>
            <#if isPasswordValid == false>
                <div class="alert alert-danger"><p><@spring.message "messages.invalid.password"/></p></div>
            </#if>
        </#if>
        <#list roles as role>
            <div class="col-sm-8 form-check form-check-inline">
                <label class="form-check-label" style="margin-right: 30px">
                    <input class="form-check-input radio-inline" type="checkbox" type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <div>
            <button class="btn btn-primary" type="submit"><@spring.message "messages.user.save"/></button>
        </div>
    </form>
    <form action="/user">
        <button class="btn btn-primary" type="submit"><@spring.message "messages.user.cancel"/></button>
    </form>
</div>
</@c.page>