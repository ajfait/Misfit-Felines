<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white" action="addEvent" method="POST">
        <h1 class="py-2">Add New Event</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="name"
                        id="name"
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="Event Name"
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
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="Street Address"
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
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="City"
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
                        required
                        minlength="2"
                        maxlength="2"
                        placeholder="State"
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
                        required
                        minlength="5"
                        maxlength="5"
                        placeholder="Zip Code"
                />
                <label for="zip">Zip Code</label>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input class="form-control" type="datetime-local" name="start" id="start" required>
                <label for="start">Start Date & Time</label>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input class="form-control" type="datetime-local" name="end" id="end" required>
                <label for="end">End Date & Time</label>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <button type="submit" class="btn btn-primary btn-lg" name="add_event_submission">Add Event</button>
        </div>
    </form>
    <!-- END Form -->
</div>