<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white" action="#" method="POST">
        <h1 class="py-2">Sign Up</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="user_name"
                        id="user_name"
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="Username"
                />
                <label for="user_name">Username</label>
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
                <label for="password">Password</label>
                <div class="pt-2">
                    <input type="checkbox" onclick=""/> Show Password
                </div>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <button
                    type="submit"
                    class="btn btn-primary btn-lg"
                    name="signup_submission"
                    value="Sign Up">
                Sign Up
            </button>
        </div>
    </form>
    <!-- END Form -->
</div>
