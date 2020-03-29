<#import "parts/common.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div class="container mt-5">
    List of users
    <table>
        <thead>
        <tr>
            <th><@spring.message "messages.user.username"/></th>
            <th><@spring.message "messages.user.role"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}"><@spring.message "messages.user.edit"/></a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</@c.page>