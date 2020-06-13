<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<#include "parts/security.ftlh">
<@c.page>
<div class="container">
    <@spring.message "messages.app.description"/>
    <#if user??>
    <#else>
    <div class="container">
        <div class="row" style="margin-top: 20px">
            <form style="margin-right: 20px" action="/login">
                <button class="btn btn-primary" type="submit"><@spring.message "messages.account.sign.in"/></button>
            </form>
            <form action="/registration">
                <button class="btn btn-primary" type="submit"><@spring.message "messages.account.add"/></button>
            </form>
        </div>
    </div>
    </#if>
</div>
</@c.page>
