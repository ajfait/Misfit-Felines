<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Heading and Add Cat Button -->
<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <div class="row">
            <div class="col-md-8">
                <h1 class="py-2">Current Cats</h1>
            </div>
            <div class="col-md-4 text-end">
                <c:choose>
                    <c:when test="${person.personId == null}">
                        <!-- Hide add cat button -->
                    </c:when>
                    <c:when test="${sessionScope.isAdmin == true}">
                        <!-- Display add cat button for admin users without person id -->
                        <a href="${pageContext.request.contextPath}/addCat" class="btn btn-primary btn-lg me-2">
                            <i class="bi bi-plus-circle"></i> Add Cat</a>
                    </c:when>
                    <c:otherwise>
                        <!-- Display add cat button for logged in users -->
                        <a href="${pageContext.request.contextPath}/addCat?id=${person.personId}"
                           class="btn btn-primary btn-lg me-2"><i
                                class="bi bi-plus-circle"> </i>Add
                            Cat</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="row">
            <div class="input-group mb-2">
                <span class="input-group-text"><i class="bi bi-search"></i></span>
                <input type="text" class="form-control search-input" data-target=".container" placeholder="Search...">
            </div>
        </div>
        <!-- Cards Loop -->
        <div class="container">
            <c:forEach var="cat" items="${catList}">
                <div class="card my-2 shadow-sm">
                    <div class="p-3">
                        <div class="row d-flex">
                            <div class="col-md-6">
                                <!-- Image -->
                                <div class="container-fluid pt-3">
                                    <img src="${pageContext.request.contextPath}/images/profile-image-default.png"
                                         alt="${cat.name}"
                                         style="max-width: 200px">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <!-- Details -->
                                <div class="container-fluid pt-3">
                                    <p data-searchable><strong>Name:</strong> ${cat.name}</p>
                                    <p data-searchable><strong>Breed:</strong> ${cat.breed}</p>
                                    <p data-searchable><strong>Birthdate:</strong> ${cat.dob}</p>
                                    <p data-searchable><strong>Age:</strong> ${cat.formattedAge}</p>
                                    <p data-searchable><strong>Sex:</strong> ${cat.sex}</p>
                                    <c:choose>
                                        <c:when test="${not empty cat.person}">
                                            <p data-searchable><strong>Foster:</strong>
                                                    ${cat.person.firstName} ${cat.person.lastName}
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p data-searchable><strong>Foster:</strong> Not assigned</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <!-- Bio -->
                        <div class="row py-3">
                            <div class="col-12">
                                <div class="container-fluid pt-3">
                                    <p data-searchable>${cat.bio}</p>
                                </div>
                            </div>
                        </div>
                        <!-- Buttons -->
                        <div class="row py-2">
                            <div class="col-12">
                                <c:choose>
                                    <c:when test="${sessionScope.personId == cat.person.personId || sessionScope.isAdmin == true}">
                                        <!-- Display buttons and link if the logged-in user is fostering the cat or is an admin -->
                                        <div class="d-flex align-items-end">
                                            <!-- Edit Button -->
                                            <a href="${pageContext.request.contextPath}/editCat?id=${cat.catId}"
                                               class="btn btn-primary btn-lg me-2">
                                                <i class="bi bi-pencil"></i> Edit
                                            </a>
                                            <!-- Delete Button -->
                                            <button type="button" class="btn btn-danger btn-lg me-2"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#deleteCatModal_${cat.catId}">
                                                <i class="bi bi-trash3"></i> Delete
                                            </button>
                                            <!-- Update Medical Record Link -->
                                            <a href="${pageContext.request.contextPath}/viewMedical?id=${cat.catId}"
                                               class="align-self-end">
                                                <i class="bi bi-clipboard2-pulse"></i> View or Update Medical Record
                                            </a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Hide buttons and link -->
                                    </c:otherwise>
                                </c:choose>
                                <!-- Modal -->
                                <div class="modal fade" id="deleteCatModal_${cat.catId}" tabindex="-1"
                                     aria-labelledby="deleteCatModal_${cat.catId}" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="deleteCatModal_${cat.catId}">
                                                    Delete Cat
                                                </h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Are you sure you want to delete ${cat.name}?
                                            </div>
                                            <div class="modal-footer">
                                                <form action="${pageContext.request.contextPath}/deleteCat"
                                                      method="POST">
                                                    <input type="hidden" name="catId" value="${cat.catId}"/>
                                                    <button type="submit" class="btn btn-danger">
                                                        <i class="bi bi-trash3"></i> Delete
                                                    </button>
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">
                                                        Cancel
                                                    </button>
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
</div>

<script src="${pageContext.request.contextPath}/js/search-filter.js"></script>
