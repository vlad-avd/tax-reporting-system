<#include "security.ftlh">
<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Taxer</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/report"> My reports</a>
            </li>
            <#if isInspector>
                <li class="nav-item active">
                    <a class="nav-link" href="/verification-reports">Verification reports</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item active">
                    <a class="nav-link" href="/user">User list</a>
                </li>
            </#if>
        </ul>
    </div>
    <div class="navbar-text mr-3">${name}</div>
    <@l.logout/>
</nav>