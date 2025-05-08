<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-md-10">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <ul class="nav nav-pills">
            <li class="nav-item">
                <a class="nav-link ${currentPage == 'viewCat' ? 'active bg-primary text-white' : 'text-primary'}"
                   href="${pageContext.request.contextPath}/viewCat">View Cats</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${currentPage == 'addCat' ? 'active bg-primary text-white' : 'text-primary'}"
                   href="${pageContext.request.contextPath}/addCat">Add Cat</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${currentPage == 'viewEvent' ? 'active bg-primary text-white' : 'text-primary'}"
                   href="${pageContext.request.contextPath}/viewEvent">View Events</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${currentPage == 'viewPerson' ? 'active bg-primary text-white' : 'text-primary'}"
                   href="${pageContext.request.contextPath}/viewPerson">View People</a>
            </li>
        </ul>
    </div>
</div>