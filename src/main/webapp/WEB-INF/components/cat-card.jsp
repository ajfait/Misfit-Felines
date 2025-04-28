<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Heading and Add Cat Button -->
<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <div class="row">
            <div class="col-md-9">
                <h1 class="py-2">Current Cats</h1>
            </div>
            <div class="col-md-3">
                <button type="button" class="btn btn-primary btn-lg me-2" name="add_cat" value="Add Cat">Add Cat
                </button>
            </div>
        </div>
        <!-- Cards Loop -->
        <c:forEach var="cat" items="${catList}">
            <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
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
                            <p><strong>Name:</strong> ${cat.name}</p>
                            <p><strong>Breed:</strong> ${cat.breed}</p>
                            <p><strong>Birthdate:</strong> ${cat.dob}</p>
                            <p><strong>Sex:</strong> ${cat.sex}</p>
                        </div>
                    </div>
                </div>
                <!-- Bio -->
                <div class="row py-3">
                    <div class="col-12">
                        <div class="container-fluid pt-3">
                            <p>${cat.bio}</p>
                        </div>
                    </div>
                </div>
                <!-- Buttons -->
                <div class="row py-2">
                    <div class="col-12">
                        <div class="container-fluid d-flex justify-content-start">
                            <a href="${pageContext.request.contextPath}/editCat?id=${cat.catId}"
                               class="btn btn-primary btn-lg me-2">Edit</a>
                            <!-- Delete Button -->
                            <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal"
                                    data-bs-target="#deleteCatModal_${cat.catId}">
                                Delete
                            </button>
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
                                            <form action="${pageContext.request.contextPath}/deleteCat" method="POST">
                                                <input type="hidden" name="catId" value="${cat.catId}"/>
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
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