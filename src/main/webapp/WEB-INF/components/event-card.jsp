<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Heading and Add Event Button -->
<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <div class="row">
            <div class="col-md-9">
                <h1 class="py-2">Upcoming Events</h1>
            </div>
            <div class="col-md-3 text-end">
                <a href="${pageContext.request.contextPath}/addEvent" class="btn btn-primary btn-lg me-2">Add
                    Event</a>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <input type="text" class="search-input form-control mb-2" data-target=".container"
                       placeholder="Search...">
            </div>
        </div>
        <!-- Cards Loop -->
        <div class="container">
            <c:forEach var="event" items="${eventList}">
                <div class="card my-2 shadow-sm">
                    <div class="p-3">
                        <div class="row d-flex">
                            <div class="col-md-6">
                                <!-- Details -->
                                <div class="container-fluid pt-3">
                                    <p data-searchable><strong>Name:</strong> ${event.eventName}</p>
                                    <p data-searchable>
                                        <strong>Location:</strong> ${event.eventLocationStreet}, ${event.eventLocationCity}, ${event.eventLocationState} ${event.eventLocationZip}
                                    </p>
                                    <p data-searchable><strong>Date & Time:</strong> ${event.eventDateTimeStart}
                                        - ${event.eventDateTimeEnd}</p>
                                </div>
                            </div>
                        </div>
                        <!-- Buttons -->
                        <div class="row py-2">
                            <div class="col-12">
                                <div class="container-fluid d-flex justify-content-start">
                                    <a href="${pageContext.request.contextPath}/editEvent?id=${event.eventId}"
                                       class="btn btn-primary btn-lg me-2">Edit</a>
                                    <!-- Delete Button -->
                                    <button type="button" class="btn btn-danger btn-lg" data-bs-toggle="modal"
                                            data-bs-target="#deleteEventModal_${event.eventId}">
                                        Delete
                                    </button>
                                    <!-- Modal -->
                                    <div class="modal fade" id="deleteEventModal_${event.eventId}" tabindex="-1"
                                         aria-labelledby="deleteEventModal_${event.eventId}" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="deleteEventModal_${event.eventId}">
                                                        Delete Event
                                                    </h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Are you sure you want to delete ${event.eventName}?
                                                </div>
                                                <div class="modal-footer">
                                                    <form action="${pageContext.request.contextPath}/deleteEvent"
                                                          method="POST">
                                                        <input type="hidden" name="eventId" value="${event.eventId}"/>
                                                        <button type="submit" class="btn btn-danger">Delete</button>
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
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/search-filter.js"></script>