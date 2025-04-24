<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white" action="addCat" method="POST">
        <h1 class="py-2">Add New Cat</h1>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input
                        class="form-control"
                        type="text"
                        name="cat_name"
                        id="cat_name"
                        required
                        minlength="2"
                        maxlength="50"
                        placeholder="Cat Name"
                />
                <label for="cat_name">Cat Name</label>
            </div>
            <div class="form-floating mb-3 pt-2">
                <select class="form-select form-select-lg" name="breed" id="breed">
                    <option selected></option>
                    <option value="dsh">Domestic Shorthair</option>
                    <option value="dmh">Domestic Medium Hair</option>
                    <option value="dlh">Domestic Long Hair</option>
                    <option value="persian">Persian</option>
                </select>
                <label for="breed">Breed</label>
            </div>
            <div class="mb-3 pt-2">
                <fieldset>
                    <legend>Sex</legend>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sex" id="sex_male" value="m" checked>
                        <label class="form-check-label" for="sex_male">
                            Male
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="sex" id="sex_female" value="f">
                        <label class="form-check-label" for="sex_female">
                            Female
                        </label>
                    </div>
                </fieldset>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input class="form-control" type="date" name="birthdate" id="birthdate" required>
                <label for="birthdate">Birthdate</label>
            </div>
        </div>
        <div class="container-fluid">
            <div class="mb-3 pt-2">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="adoptable" name="adoptable">
                    <label class="form-check-label" for="adoptable">Adoptable</label>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <textarea class="form-control" name="bio" id="bio" style="height: 350px" required></textarea>
                <label for="bio">Bio</label>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <button type="submit" class="btn btn-primary btn-lg" name="add_cat_submission">Add Cat</button>
        </div>
    </form>
    <!-- END Form -->
</div>