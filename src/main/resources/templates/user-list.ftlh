<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
<div class="form-group mt-3 container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>#</th>
                <th><@spring.message "messages.user.username"/></th>
                <th><@spring.message "messages.user.role"/></th>
            </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>1</td>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/edit/${user.id}"><@spring.message "messages.user.edit"/></a></td>
                <td><a href="/user/statistics/${user.id}"><@spring.message "messages.user.statistics"/></a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</@c.page>