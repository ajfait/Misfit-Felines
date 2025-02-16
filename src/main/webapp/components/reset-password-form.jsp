<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white" action="#" method="POST">
        <h1 class="py-2">Reset Password</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="password_old"
                        id="password_old"
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="Username"
                />
                <label for="password_old">Current Password</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="password"
                        name="password"
                        id="password"
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="Password"
                />
                <label for="password">New Password</label>
                <div class="pt-2">
                    <input type="checkbox" onclick=""/> Show Password
                </div>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <button
                    type="submit"
                    class="btn btn-primary btn-lg"
                    name="reset_password_submission"
                    value="Reset Password">
                Reset Password
            </button>
        </div>
    </form>
    <!-- END Form -->
</div>
