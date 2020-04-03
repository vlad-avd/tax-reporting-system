<#include "security.ftlh">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/"><@spring.message "messages.app.name"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/"><@spring.message "messages.navbar.home"/></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/report"> <@spring.message "messages.navbar.user.reports"/></a>
            </li>
            <#if isInspector>
                <li class="nav-item active">
                    <a class="nav-link" href="/verification-reports"><@spring.message "messages.navbar.inspector.reports"/></a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item active">
                    <a class="nav-link" href="/user"><@spring.message "messages.navbar.users"/></a>
                </li>
            </#if>
        </ul>
    </div>
    <#if user??>
        <div class="navbar-text mr-3">${name}</div>
        <form action="/logout" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit"><@spring.message "messages.account.logout"/></button>
        </form>
    </#if>

    <select class="selectpicker" data-width="fit">
        <option data-content='<span class="flag-icon flag-icon-us"></span> English'>English</option>
        <option data-content='<span class="flag-icon flag-icon-ru"></span> Español'>Español</option>
    </select>
    <span><@spring.message "messages.lang.change"/></span>

    <a href="?lang=en">
        <@spring.message "messages.lang.en"/>
    </a>
    <a href="?lang=ru">
        <@spring.message "messages.lang.ru"/>
    </a>
</nav>