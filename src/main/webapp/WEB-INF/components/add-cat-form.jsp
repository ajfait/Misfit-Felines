<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white"
          action="${cat.catId != null ? 'editCat' : 'addCat'}" method="POST">
        <input type="hidden" name="catId" value="${cat.catId}"/>
        <c:if test="${not empty violations}">
            <c:forEach var="violation" items="${violations}">
                <p class="alert alert-danger mt-3">${violation.message}</p>
            </c:forEach>
        </c:if>
        <h1 class="py-2">${cat.catId == 0 || cat.catId == null ? "Add New Cat" : "Edit Cat"}</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="cat_name"
                        id="cat_name"
                        value="${cat.name}"
                        minlength="2"
                        maxlength="50"
                        placeholder="Cat Name"
                />
                <label for="cat_name">Cat Name</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <select class="form-select form-select-lg" name="breed" id="breed">
                    <c:forEach var="breed" items="${breeds}">
                        <option value="${breed}"
                                <c:if test="${breed == cat.breed}">selected</c:if>>
                                ${breed}
                        </option>
                    </c:forEach>
                </select>
                <label for="breed">Breed:</label>
            </div>
            <div class="mb-3 pt-2">
                <fieldset>
                    <legend>Sex</legend>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sex" id="sex_male" value="Male"
                               <c:if test="${cat.sex == 'Male'}">checked</c:if>>
                        <label class="form-check-label" for="sex_male">
                            Male
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sex" id="sex_female" value="Female"
                               <c:if test="${cat.sex == 'Female'}">checked</c:if>>
                        <label class="form-check-label" for="sex_female">
                            Female
                        </label>
                    </div>
                </fieldset>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input class="form-control" type="date" name="birthdate" id="birthdate" value="${cat.dob}">
                <label for="birthdate">Birthdate</label>
            </div>
        </div>
        <div class="container-fluid">
            <div class="mb-3 pt-2">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="adoptable" name="adoptable"
                           <c:if test="${cat.adoptable}">checked</c:if>>
                    <label class="form-check-label" for="adoptable">Adoptable</label>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <textarea class="form-control" name="bio" id="bio" style="height: 350px">${cat.bio}</textarea>
                <label for="bio">Bio</label>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <button type="submit" class="btn btn-primary btn-lg" name="add_cat_submission">
                ${cat.catId == 0 || cat.catId == null ? "Add Cat" : "Update Cat"}</button>
        </div>
    </form>
    <!-- END Form -->
</div>