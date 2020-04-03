<#import "parts/template.ftlh" as c>
<#import "parts/login.ftlh" as l>
<#import "/spring.ftl" as spring/>
<@c.page>
    <div class="mb-1"><@spring.message "messages.account.add"/></div>
    <#if message??>
        ${message}
    </#if>
    <@l.login "/registration" true/>
</@c.page>