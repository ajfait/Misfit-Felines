<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <c:import url="/WEB-INF/components/head.jsp"/>
</head>

<body>

<!-- Header -->
<c:import url="/WEB-INF/components/header.jsp"/>
<!-- END HEADER -->

<!-- Main Content -->
<div class="bg-primary-subtle py-5">
    <div class="row">
        <div class="col-md-1">
        </div>
        <c:choose>
            <c:when test="${sessionScope.isAdmin}">
                <c:import url="/WEB-INF/components/nav-menu-admin.jsp"/>
            </c:when>
            <c:otherwise>
                <c:import url="/WEB-INF/components/nav-menu.jsp"/>
            </c:otherwise>
        </c:choose>
        <div class="col-md-1">
        </div>
    </div>
    <div class="row">
        <div class="col-md-1">
        </div>
        <c:if test="${not empty person}">
            <c:import url="/WEB-INF/components/profile-card.jsp"/>
        </c:if>
        <c:import url="/WEB-INF/components/cat-card.jsp"/>
        <div class="col-md-1">
        </div>
    </div>
</div>
<!-- END Main Content -->

<!-- Footer -->
<c:import url="/WEB-INF/components/footer.jsp"/>
<!-- END Footer -->

</body>

</html>