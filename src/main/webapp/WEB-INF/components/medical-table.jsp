<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <h1>Medical Record for ${cat.name}</h1>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Medication Name</th>
                <th>Date Given</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${medicalList}" var="medical">
                <tr>
                    <td>${medical.medicationName}</td>
                    <td>${medical.medicationDateGiven}</td>
                    <td><i class="bi bi-pencil"></i></td>
                    <td><i class="bi bi-trash3"></i></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/addMedical?id=${cat.catId}"><i class="bi bi-clipboard2-pulse"></i>
            Add
            Medical Record</a>
    </div>
</div>