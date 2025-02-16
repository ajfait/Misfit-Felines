<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <c:import url="/components/head.jsp"/>
</head>

<body>

<!-- Header -->
<c:import url="/components/header.jsp"/>
<!-- END HEADER -->

<!-- Main Content -->
<div class="bg-primary-subtle py-5">
    <div class="row">
        <div class="col-md-1">
        </div>
        <c:import url="/components/profile-card.jsp"/>
        <c:import url="/components/cat-card.jsp"/>
        <div class="col-md-1">
        </div>
    </div>
</div>
<!-- END Main Content -->

<!-- Footer -->
<c:import url="/components/footer.jsp"/>
<!-- END Footer -->

</body>

</html>




