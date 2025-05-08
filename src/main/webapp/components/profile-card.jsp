<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-md-4">

    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">

        <!-- Image -->
        <img src="${pageContext.request.contextPath}/images/profile-image-default.png"
             alt="${person.firstName} ${person.lastName}"
             style="max-width: 200px">

        <div class="container-fluid pt-3">

            <!-- Name, Role -->
            <h2 class="pt-3">${person.firstName} ${person.lastName}, ${person.role}</h2>

            <!-- Phone Number -->
            <p>Phone: ${person.phone}</p>

            <!-- Email Address -->
            <p>Email: <a class="text-primary"
                         href="mailto:${person.email}">${person.email}</a></p>

        </div>

        <!-- Button: Update Profile -->
        <div class="container-fluid pt-3">
            <a href="${pageContext.request.contextPath}/editPerson?id=${person.personId}"
               class="btn btn-primary btn-lg me-2">Update Profile</a>
        </div>

    </div>

</div>