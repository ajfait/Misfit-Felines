<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-10">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <ul class="nav nav-pills">
            <li class="nav-item">
                <a class="nav-link bg-primary active" aria-current="page"
                   href="${pageContext.request.contextPath}/viewCat">View Cats</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-primary" href="${pageContext.request.contextPath}/addCat">Add Cat</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-primary" href="${pageContext.request.contextPath}/viewEvent">View Events</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-primary" href="${pageContext.request.contextPath}/addEvent">Add Event</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-primary" href="${pageContext.request.contextPath}/viewPerson">View People</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-primary" href="${pageContext.request.contextPath}/addPerson">Add Person</a>
            </li>
        </ul>
    </div>
</div>

