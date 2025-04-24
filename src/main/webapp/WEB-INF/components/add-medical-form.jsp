<div class="col-md-6">
    <!-- Form -->
    <form class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white" action="addMedical" method="POST">
        <h1 class="py-2">Add New Medical</h1>
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
                        placeholder="Medication Name"
                />
                <label for="name">Medication Name</label>
            </div>
        </div>
        <div class="container-fluid">
            <div class="form-floating mb-3 pt-2">
                <input class="form-control" type="date" name="date" id="date" required>
                <label for="date">Date Given</label>
            </div>
        </div>
        <div class="container-fluid pt-3">
            <button type="submit" class="btn btn-primary btn-lg" name="add_medical_submission">Add Medical</button>
        </div>
    </form>
    <!-- END Form -->
</div>