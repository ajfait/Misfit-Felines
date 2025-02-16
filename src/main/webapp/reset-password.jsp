<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <div class="col-md-3">
    </div>

    <div class="col-md-6 d-flex justify-content-center">
      <c:import url="/components/reset-password-form.jsp"/>
    </div>

    <div class="col-md-3">
    </div>
  </div>
</div>
<!-- END Main Content -->

<!-- Footer -->
<c:import url="/components/footer.jsp"/>
<!-- END Footer -->

</body>

</html>