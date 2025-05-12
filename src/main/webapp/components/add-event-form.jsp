<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-6">
  <!-- Form -->
  <form
    class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white"
    action="${event.eventId != null ? 'editEvent' : 'addEvent'}"
    method="POST"
  >
    <input type="hidden" name="eventId" value="${event.eventId}" />
    <c:if test="${not empty violations}">
      <c:forEach var="violation" items="${violations}">
        <p class="alert alert-danger mt-3">${violation.message}</p>
      </c:forEach>
    </c:if>
    <h1 class="py-2">Add New Event</h1>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="text"
          name="name"
          id="name"
          value="${event.eventName}"
          minlength="2"
          maxlength="50"
        />
        <label for="name">Event Name</label>
      </div>
    </div>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="text"
          name="street"
          id="street"
          value="${event.eventLocationStreet}"
          minlength="2"
          maxlength="50"
        />
        <label for="street">Street Address</label>
      </div>
    </div>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="text"
          name="city"
          id="city"
          value="${event.eventLocationCity}"
          minlength="2"
          maxlength="50"
        />
        <label for="city">City</label>
      </div>
    </div>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="text"
          name="state"
          id="state"
          value="${event.eventLocationState}"
          minlength="2"
          maxlength="2"
        />
        <label for="state">State</label>
      </div>
    </div>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="text"
          name="zip"
          id="zip"
          value="${event.eventLocationZip}"
          minlength="5"
          maxlength="5"
        />
        <label for="zip">Zip Code</label>
      </div>
    </div>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="datetime-local"
          name="start"
          id="start"
          value="${startFormatted}"
        />
        <label for="start">Start Date & Time</label>
      </div>
    </div>
    <div class="container-fluid">
      <div class="form-floating mb-3 pt-2">
        <input
          class="form-control"
          type="datetime-local"
          name="end"
          id="end"
          value="${endFormatted}"
        />
        <label for="end">End Date & Time</label>
      </div>
    </div>
    <div class="container-fluid pt-3">
      <button
        type="submit"
        class="btn btn-primary btn-lg"
        name="add_event_submission"
      >
        ${event.eventId == 0 || event.eventId == null ? "Add Event" : "Update
        Event"}
      </button>
    </div>
  </form>
  <!-- END Form -->
</div>
