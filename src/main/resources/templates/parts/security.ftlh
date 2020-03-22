<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    isInspector = user.isInspector()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    isInspector = false
    >
</#if>