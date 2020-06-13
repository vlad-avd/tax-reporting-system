<#import "parts/template.ftlh" as c>
<#import "parts/login.ftlh" as l>
<#import "/spring.ftl" as spring/>
<@c.page>
    <#if message??>
        <div class="alert alert-danger"><p>${message}</p></div>
    </#if>
    <@l.login "/registration" true/>
</@c.page>