<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Heading and Add Person Button -->
<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <div class="row">
            <div class="col-md-9">
                <h1 class="py-2">Current Fosters</h1>
            </div>
            <div class="col-md-3 text-end">
                <a href="${pageContext.request.contextPath}/addPerson" class="btn btn-primary btn-lg me-2">Add
                    Person</a>
            </div>
        </div>
        <!-- Cards Loop -->
        <c:forEach var="person" items="${peopleList}">
            <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
                <div class="row d-flex">
                    <div class="col-md-6">
                        <!-- Image -->
                        <div class="container-fluid pt-3">
                            <img src="${pageContext.request.contextPath}/images/profile-image-default.png"
                                 alt="${person.firstName} ${person.lastName}"
                                 style="max-width: 200px">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <!-- Details -->
                        <div class="container-fluid pt-3">
                            <p><strong>Name:</strong> ${person.firstName} ${person.lastName}</p>
                            <p><strong>Role:</strong> ${person.role}</p>
                            <p><strong>Phone:</strong> ${person.phone}</p>
                            <p><strong>Email:</strong> <a class="text-primary"
                                                          href="mailto:${person.email}">${person.email}</a></p>
                            <p><strong>Preferences:</strong> ${person.preferences}</p>
                        </div>
                    </div>
                </div>
                <!-- Buttons -->
                <div class="row py-2">
                    <div class="col-12">
                        <div class="container-fluid d-flex justify-content-start">
                            <a href="${pageContext.request.contextPath}/editPerson?id=${person.personId}"
                               class="btn btn-primary btn-lg me-2">Edit</a>
                            <!-- Delete Button -->
                            <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal"
                                    data-bs-target="#deletePersonModal_${person.personId}">
                                Delete
                            </button>
                            <!-- Modal -->
                            <div class="modal fade" id="deletePersonModal_${person.personId}" tabindex="-1"
                                 aria-labelledby="deletePersonModalLabel_${person.personId}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="deletePersonModalLabel_${person.personId}">
                                                Delete Person
                                            </h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            Are you sure you want to delete ${person.firstName} ${person.lastName}?
                                        </div>
                                        <div class="modal-footer">
                                            <form action="${pageContext.request.contextPath}/deletePerson" method="POST">
                                                <input type="hidden" name="personId" value="${person.personId}"/>
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
