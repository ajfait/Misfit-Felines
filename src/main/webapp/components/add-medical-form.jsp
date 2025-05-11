<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white"
          action="${medical.medicalId != null ? 'editMedical' : 'addMedical'}" method="POST">
        <input type="hidden" name="medicalId" value="${medical.medicalId}"/>
        <c:if test="${not empty violations}">
            <c:forEach var="violation" items="${violations}">
                <p class="alert alert-danger mt-3">${violation.message}</p>
            </c:forEach>
        </c:if>
        <h1 class="py-2">${medical.medicalId == 0 || medical.medicalId == null ? "Add New Medical" : "Edit Medical"}</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="name"
                        id="name"
                        value="${medical.medicationName}"
                        minlength="2"
                        maxlength="50"
                />
                <label for="name">Medication Name</label>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input class="form-control" type="date" name="date" id="date"
                       value="${medical.medicationDateGiven}">
                <label for="date">Date Given</label>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <input type="hidden" name="catId" value="${cat.catId != null ? cat.catId : param.id}"/>
            <button type="submit" class="btn btn-primary btn-lg" name="add_medical_submission">
                ${medical.medicalId == 0 || medical.medicalId == null ? "Add Medical" : "Update Medical"}</button>
        </div>
    </form>
    <!-- END Form -->
</div>