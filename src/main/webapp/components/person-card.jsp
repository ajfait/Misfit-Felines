<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <div class="row">
            <div class="col-md-9">
                <h1>Current Fosters</h1>
            </div>
            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-lg me-2" name="add_person" value="Add Foster"/>
            </div>
        </div>
        <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
            <div class="row d-flex">
                <div class="col-md-6">
                    <!-- Image -->
                    <div class="container-fluid pt-3">
                        <img src="${pageContext.request.contextPath}/images/profile-image-default.png" alt="name of person"
                             style="max-width: 200px">
                    </div>
                </div>
                <div class="col-md-6">
                    <!-- Details -->
                    <div class="container-fluid pt-3">
                        <p>Name</p>
                        <p>Role</p>
                        <p>Phone Number</p>
                        <p><a href="mailto:jafait1004@gmail.com">Email Address</a></p>
                        <p>Preferences</p>
                    </div>
                </div>
            </div>
            <!-- Buttons -->
            <div class="row py-2">
                <div class="col-12">
                    <div class="container-fluid d-flex justify-content-start">
                        <input type="submit" class="btn btn-primary btn-lg me-2" name="edit_person" value="Edit Foster"/>
                        <input type="submit" class="btn btn-primary btn-lg" name="delete_person" value="Delete Foster"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
