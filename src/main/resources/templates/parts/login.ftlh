<#macro login path isRegisterForm>
    <#import "/spring.ftl" as spring>
    <#if RequestParameters.error??>
        <div class="alert alert-danger"><p><@spring.message "messages.incorrect.username.or.password"/></p></div>
    </#if>
    <#if isUserExist??>
    <#if isUserExist == true>
        <div class="alert alert-danger"><p><@spring.message "messages.user.already.exists"/></p></div>
    </#if>
    </#if>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><@spring.message "messages.user.username"/>: </label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder=<@spring.message "messages.user.username"/>/>
            </div>
        </div>
        <#if isUsernameValid??>
        <#if isUsernameValid == false>
            <div class="alert alert-danger"><p><@spring.message "messages.invalid.username"/></p></div>
        </#if>
        </#if>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> <@spring.message "messages.user.password"/>: </label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder=<@spring.message "messages.user.password"/>/>
            </div>
        </div>
        <#if isPasswordValid??>
        <#if isPasswordValid == false>
            <div class="alert alert-danger"><p><@spring.message "messages.invalid.password"/></p></div>
        </#if>
        </#if>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm><@spring.message "messages.account.create"/> <#else><@spring.message "messages.account.sign.in"/></#if></button>
    </form>
</#macro>