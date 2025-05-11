<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white"
          action="editPerson" method="POST">
        <input type="hidden" name="personId" value="${person.personId}"/>
        <c:if test="${not empty violations}">
            <c:forEach var="violation" items="${violations}">
                <p class="alert alert-danger mt-3">${violation.message}</p>
            </c:forEach>
        </c:if>
        <h1 class="py-2">Edit Person</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="first_name"
                        id="first_name"
                        value="${person.firstName}"
                        minlength="2"
                        maxlength="50"
                />
                <label for="first_name">First Name</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="last_name"
                        id="last_name"
                        value="${person.lastName}"
                        minlength="2"
                        maxlength="50"
                />
                <label for="last_name">Last Name</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="phone"
                        id="phone"
                        value="${person.phone}"
                        pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
                />
                <label for="phone">Phone Number</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="email"
                        name="email"
                        id="email"
                        value="${person.email}"
                        disabled
                />
                <label for="email">Email Address</label>
            </div>
            <c:choose>
                <c:when test="${sessionScope.isAdmin == true}">
                    <div class="form-floating mb-3 pt-2">
                        <select class="form-select form-select-lg" id="role" name="role">
                            <option value=""></option>
                            <option value="Foster" ${person.role == 'Foster' ? 'selected' : ''}>Foster</option>
                            <option value="Volunteer" ${person.role == 'Volunteer' ? 'selected' : ''}>Volunteer</option>
                        </select>
                        <label for="role">Role</label>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Hide role for non-admin users -->
                </c:otherwise>
            </c:choose>
            <div class="pt-2">
                <fieldset>
                    <legend>Preferences</legend>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Pregnant" id="prefer_pregnant"
                               name="preferences"
                               <c:if test="${not empty person.preferences and fn:contains(person.preferences, 'Pregnant')}">checked</c:if>>
                        <label class="form-check-label" for="prefer_pregnant">
                            Pregnant / nursing moms
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Bottle" id="prefer_bottle"
                               name="preferences"
                               <c:if test="${not empty person.preferences and fn:contains(person.preferences, 'Bottle')}">checked</c:if>>
                        <label class="form-check-label" for="prefer_bottle">
                            Bottle fed kittens
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Weaned" id="prefer_weaned"
                               name="preferences"
                               <c:if test="${not empty person.preferences and fn:contains(person.preferences, 'Weaned')}">checked</c:if>>
                        <label class="form-check-label" for="prefer_weaned">
                            Weaned kittens
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="Medical" id="prefer_medical"
                               name="preferences"
                               <c:if test="${not empty person.preferences and fn:contains(person.preferences, 'Medical')}">checked</c:if>>
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
                                <input class="form-check-input" type="radio" name="admin" id="admin_yes"
                                       value="true"
                                       <c:if test="${person.admin == true}">checked</c:if>>
                                <label class="form-check-label" for="admin_yes">Yes</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="admin" id="admin_no"
                                       value="false"
                                       <c:if test="${person.admin != true}">checked</c:if>>
                                <label class="form-check-label" for="admin_no">No</label>
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
                        name="update_person_submission">
                    Update Person
                </button>
            </div>
        </div>
    </form>
    <!-- END Form -->
</div>
