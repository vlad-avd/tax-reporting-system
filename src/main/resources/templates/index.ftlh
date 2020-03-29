<#import "parts/common.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div class="container mt-5">
    <div><@spring.message "messages.app.description"/></div>
    <a class="mr-2" href="/login"><@spring.message "messages.account.sign.in"/></a>
    <a href="/registration"><@spring.message "messages.account.add"/></a>
</div>
</@c.page>