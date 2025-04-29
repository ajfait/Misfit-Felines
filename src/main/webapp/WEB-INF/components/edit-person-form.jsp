<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-6">
  <!-- Form -->
  <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white"
        action="addPerson" method="POST">
    <h1 class="py-2">Add New Person</h1>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
                class="form-control"
                type="text"
                name="first_name"
                id="first_name"
                value="${person.firstName}"
                required
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
                value="${person.lastName}"
                required
                minlength="2"
                maxlength="50"
                placeholder="Last Name"
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
                required
                pattern="^\d{3}-\d{4}$"
                placeholder="555-1212"
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
                required
                placeholder="foster@gmail.com"
        />
        <label for="email">Email Address</label>
      </div>
      <div class="form-floating mb-3 pt-2">
        <select class="form-select form-select-lg" id="role" name="role">
          <option value=""></option>
          <option value="Foster" ${person.role == 'foster' ? 'selected' : ''}>Foster</option>
          <option value="Volunteer" ${person.role == 'volunteer' ? 'selected' : ''}>Volunteer</option>
        </select>
        <label for="role">Role</label>
      </div>
      <div class="pt-2">
        <fieldset>
          <legend>Preferences</legend>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" value="pregnant" id="prefer_pregnant"
                   <c:if test="${fn:contains(person.preferences, 'pregnant')}">checked</c:if> />>
            <label class="form-check-label" for="prefer_pregnant">
              Pregnant / nursing moms
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" value="bottle" id="prefer_bottle"
                   <c:if test="${fn:contains(person.preferences, 'bottle')}">checked</c:if> />>
            <label class="form-check-label" for="prefer_bottle">
              Bottle fed kittens
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" value="weaned" id="prefer_weaned"
                   <c:if test="${fn:contains(person.preferences, 'weaned')}">checked</c:if> />>
            <label class="form-check-label" for="prefer_weaned">
              Weaned kittens
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="checkbox" value="medical" id="prefer_medical"
                   <c:if test="${fn:contains(person.preferences, 'medical')}">checked</c:if> />>>
            <label class="form-check-label" for="prefer_medical">
              Medical needs
            </label>
          </div>
        </fieldset>
      </div>
      <div class="pt-2">
        <fieldset>
          <legend>Admin</legend>
          <div class="form-check">
            <input class="form-check-input" type="radio" name="admin" id="admin_no"
                   <c:if test="${!person.admin}">checked</c:if> /> checked>
            <label class="form-check-label" for="admin_no">
              No
            </label>
          </div>
          <div class="form-check">
            <input class="form-check-input" type="radio" name="admin" id="admin_yes"
                   <c:if test="${person.admin}">checked</c:if> />>
            <label class="form-check-label" for="admin_yes">
              Yes
            </label>
          </div>
        </fieldset>
      </div>
    </div>
    <div class="container-fluid pt-3">
      <button type="submit"
              class="btn btn-primary btn-lg"
              name="update_person_submission">
        Update Person
      </button>
    </div>
  </form>
  <!-- END Form -->
</div>
