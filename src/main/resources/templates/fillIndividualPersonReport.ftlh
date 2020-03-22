<#import "parts/common.ftlh" as c>
<@c.page>
<form action="/report/individual-person-report" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> First Name : </label>
        <div class="col-sm-6">
            <input type="text" name="name" class="form-control" placeholder="First name"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Last Name : </label>
        <div class="col-sm-6">
            <input type="text" name="surname" class="form-control" placeholder="Last name"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Patronymic : </label>
        <div class="col-sm-6">
            <input type="text" name="patronymic" class="form-control" placeholder="Patronymic"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Place of work : </label>
        <div class="col-sm-6">
            <input type="text" name="workplace" class="form-control" placeholder="Place of work"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Salary: </label>
        <div class="col-sm-6">
            <input type="text" name="salary" class="form-control" placeholder="Salary"/>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">Submit report</button>
</form>
</@c.page>