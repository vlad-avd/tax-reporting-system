<#macro pager url page>
    <#if page.numberOfElements != 0>
    <div class="row d-flex justify-content-center">
        <ul class="pagination col-md-3">
            <li class="page-item disabled">
                <a class="page-link active" href="#" tabindex="-1">Pages</a>
            </li>
            <#list 1..page.getTotalPages() as p>
            <#if (p - 1) == page.getNumber()>
            <li class="page-item active">
                <a class="page-link active" href="#" tabindex="-1">${p}</a>
            </li>
            <#else>
            <li class="page-item">
                <a class="page-link active" href="${url}?page=${p - 1}" tabindex="-1">${p}</a>
            </li>
            </#if>
            </#list>
        </ul>
    </div>
    </#if>
</#macro>