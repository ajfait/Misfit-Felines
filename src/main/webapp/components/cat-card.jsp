<div class="col-md-6">
    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">
        <div class="row">
            <div class="col-md-9">
                <h1>Current Cats</h1>
            </div>
            <div class="col-md-3">
                <input type="submit" class="btn btn-primary btn-lg me-2" name="add_cat" value="Add Cat"/>
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
                        <p>April</p>
                        <p>Domestic Shorthair</p>
                        <p>Female</p>
                        <p>6 years</p>
                    </div>
                </div>
            </div>
            <!-- Bio -->
            <div class="row py-3">
                <div class="col-12">
                    <div class="container-fluid pt-3">
                        <p>What a cutie! Testing what some really long copy will look like. So I am just going to
                            ramble here about what a silly kitty April is. She has an itty bitty head and a chunky
                            little body.</p>
                    </div>
                </div>
            </div>
            <!-- Buttons -->
            <div class="row py-2">
                <div class="col-12">
                    <div class="container-fluid d-flex justify-content-start">
                        <input type="submit" class="btn btn-primary btn-lg me-2" name="edit_cat" value="Edit Cat"/>
                        <input type="submit" class="btn btn-primary btn-lg" name="delete_cat" value="Delete Cat"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
