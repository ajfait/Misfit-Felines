<div class="row">
    <div class="col-md-2">
    </div>
    <div class="col-md-8">
        <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm" action="#" method="POST">
            <h1>Add New Profile</h1>
            <div class="container-fluid">
                <div class="form-floating mb-3 pt-2">
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
                    <label for="first_name">First Name</label>
                </div>
                <div class="form-floating mb-3 pt-2">
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
                    <label for="last_name">Last Name</label>
                </div>
                <div class="form-floating mb-3 pt-2">
                    <input
                            class="form-control"
                            type="text"
                            name="phone"
                            id="phone"
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
                            required
                            placeholder="foster@gmail.com"
                    />
                    <label for="email">Email Address</label>
                </div>
            </div>
            <div class="container-fluid">
                <input
                        type="submit"
                        class="btn btn-primary"
                        name="add_submission"
                        value="Add Profile"
                />
            </div>
        </form>
    </div>
    <div class="col-md-2">
    </div>
</div>
