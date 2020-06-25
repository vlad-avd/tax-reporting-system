<#import "parts/template.ftlh" as c>
<#import "/spring.ftl" as spring/>
<@c.page>
    <div class="container">
        <table class="table col-md-4">
            <tbody>
            <tr>
                <th><@spring.message "messages.user.username"/>:</th>
                <td>${user.username}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.user.password"/>:</th>
                <td>${user.password}</td>
            </tr>
            <tr>
                <th><@spring.message "messages.user.role"/>:</th>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
            </tr>
            </tbody>
        </table>
    </div>
</@c.page>