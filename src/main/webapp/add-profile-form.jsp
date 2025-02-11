<form action="#" method="POST">
    <div class="container-fluid">
        <div>
            <label for="first_name" class="form-label">First Name</label>
        </div>
        <div>
            <input
                    class="form-control"
                    type="text"
                    name="first_name"
                    id="first_name"
                    required
                    minlength="2"
                    maxlength="50"
                    placeholder="First Name"
            />
        </div>
    </div>
        <div>
            <label for="last_name" class="form-label">Last Name</label>
        </div>
        <div>
            <input
                    class="form-control"
                    type="text"
                    name="last_name"
                    id="last_name"
                    required
                    minlength="2"
                    maxlength="50"
                    placeholder="Last Name"
            />
        </div>
        <div>
            <label for="phone" class="form-label">Phone Number</label>
        </div>
        <div>
            <input
                    class="form-control"
                    type="text"
                    name="phone"
                    id="phone"
                    required
                    pattern="^\d{3}-\d{4}$"
                    placeholder="555-1212"
            />
        </div>
        <input
                type="submit"
                class="btn btn-primary"
                name="add_submission"
                value="Add Profile"
        />
</form>
