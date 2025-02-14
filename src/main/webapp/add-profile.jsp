<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <c:import url="/head.jsp" />
</head>

<body>

<!-- Header -->
<c:import url="/header.jsp" />
<!-- END HEADER -->

<!-- Main Content -->
<div class="row">
    <div class="col-md-1">
    </div>
    <c:import url="/admin-menu.jsp" />
    <div class="col-md-1">
    </div>
</div>
<div class="row">
    <div class="col-md-1">
    </div>
    <c:import url="/profile-card.jsp" />
    <c:import url="/add-profile-form.jsp" />
    <div class="col-md-1">
    </div>
</div>
<!-- END Main Content -->

<!-- Footer -->
<c:import url="/footer.jsp" />
<!-- END Footer -->

</body>

</html>




