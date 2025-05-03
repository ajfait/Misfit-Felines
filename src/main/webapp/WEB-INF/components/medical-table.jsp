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
                    <td><a href="${pageContext.request.contextPath}/editMedical?id=${medical.medicalId}">
                        <i class="bi bi-pencil"></i>
                    </a></td>
                    <td><a href="#"
                           data-bs-toggle="modal"
                           data-bs-target="#deleteMedicalModal_${medical.medicalId}">
                        <i class="bi bi-trash3"></i>
                    </a></td>
                </tr>
                <!-- Modal -->
                <div class="modal fade" id="deleteMedicalModal_${medical.medicalId}" tabindex="-1"
                     aria-labelledby="deleteMedicalModal_${medical.medicalId}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5"
                                    id="deleteMedicalModalLabel_${medical.medicalId}">
                                    Delete Medical Record
                                </h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to
                                delete ${medical.medicationName} from ${cat.name}'s record?
                            </div>
                            <div class="modal-footer">
                                <form action="${pageContext.request.contextPath}/deleteMedical"
                                      method="POST">
                                    <input type="hidden" name="medicalId"
                                           value="${medical.medicalId}"/>
                                    <button type="submit" class="btn btn-danger">
                                        <i class="bi bi-trash3"></i> Delete
                                    </button>
                                    <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Cancel
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/addMedical?id=${cat.catId}"><i class="bi bi-clipboard2-pulse"></i>
            Add
            Medical Record</a>
    </div>
</div>