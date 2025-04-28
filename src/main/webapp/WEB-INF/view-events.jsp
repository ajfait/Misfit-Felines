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
        <c:import url="/WEB-INF/components/admin-menu.jsp"/>
        <div class="col-md-1">
        </div>
    </div>
    <div class="row">
        <div class="col-md-1">
        </div>
        <c:if test="${not empty person}">
            <jsp:include page="/WEB-INF/components/profile-card.jsp" />
        </c:if>
        <c:import url="/WEB-INF/components/event-card.jsp"/>
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