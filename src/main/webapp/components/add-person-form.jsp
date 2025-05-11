<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white"
          action="addPerson" method="POST">
        <!-- Display error messages for email already exists -->
        <c:if test="${not empty dbError}">
            <p class="alert alert-danger mt-3">${dbError}</p>
        </c:if>
        <c:if test="${not empty cognitoError}">
            <p class="alert alert-danger mt-3">${cognitoError}</p>
        </c:if>

        <!-- Display error messages for form data validation -->
        <c:if test="${not empty violations}">
            <c:forEach var="violation" items="${violations}">
                <p class="alert alert-danger mt-3">${violation.message}</p>
            </c:forEach>
        </c:if>
        <h1 class="py-2">Add New Person</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="first_name"
                        id="first_name"
                        minlength="2"
                        maxlength="50"
                        placeholder="First Name"
                />
                <label for="first_name">First Name</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="last_name"
                        id="last_name"
                        minlength="2"
                        maxlength="50"
                        placeholder="Last Name"
                />
                <label for="last_name">Last Name</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="tel"
                        name="phone"
                        id="phone"
                        pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
                        placeholder="608-555-1212"
                />
                <label for="phone">Phone Number</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="email"
                        name="email"
                        id="email"
                        placeholder="foster@gmail.com"
                />
                <label for="email">Email Address</label>
            </div>
            <c:choose>
                <c:when test="${sessionScope.isAdmin == true}">
                    <div class="form-floating mb-3 pt-2">
                        <select class="form-select form-select-lg" id="role" name="role">
                            <option value=""></option>
                            <option value="Foster">Foster</option>
                            <option value="Volunteer">Volunteer</option>
                        </select>
                        <label for="role">Role</label>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Hide role from non-admin users -->
                </c:otherwise>
            </c:choose>
            <div class="pt-2">
                <fieldset>
                    <legend>Preferences</legend>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Pregnant" id="prefer_pregnant"
                               name="preferences"/>
                        <label class="form-check-label" for="prefer_pregnant">
                            Pregnant / nursing moms
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Bottle" id="prefer_bottle"
                               name="preferences"/>
                        <label class="form-check-label" for="prefer_bottle">
                            Bottle fed kittens
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Weaned" id="prefer_weaned"
                               name="preferences"/>
                        <label class="form-check-label" for="prefer_weaned">
                            Weaned kittens
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Medical" id="prefer_medical"
                               name="preferences"/>
                        <label class="form-check-label" for="prefer_medical">
                            Medical needs
                        </label>
                    </div>
                </fieldset>
            </div>
            <c:choose>
                <c:when test="${sessionScope.isAdmin == true}">
                    <div class="pt-2">
                        <fieldset>
                            <legend>Admin</legend>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" value="false" name="admin" id="admin_no"
                                       checked/>
                                <label class="form-check-label" for="admin_no">
                                    No
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" value="true" name="admin" id="admin_yes"/>
                                <label class="form-check-label" for="admin_yes">
                                    Yes
                                </label>
                            </div>
                        </fieldset>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Hide admin toggle for non-admin users -->
                </c:otherwise>
            </c:choose>
            <div class="container-fluid pt-3">
                <button type="submit"
                        class="btn btn-primary btn-lg"
                        name="add_person_submission">
                    Add Person
                </button>
            </div>
        </div>
    </form>
    <!-- END Form -->
</div>