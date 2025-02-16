<%--
  Created by IntelliJ IDEA.
  User: ajfait
  Date: 2/13/25
  Time: 8:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="col-md-4">

    <div class="p-3 mt-3 border border-secondary-subtle rounded shadow-sm bg-white">

        <!-- Image -->
        <img src="${pageContext.request.contextPath}/images/Alison%20Fait%20Headshot_resized.jpg" alt="Alison Fait">

        <div class="container-fluid pt-3">

            <!-- Name, Role -->
            <h2 class="pt-3">Alison Fait, Foster</h2>

            <!-- Phone Number -->
            <p>Phone: (715) 383-8927</p>

            <!-- Email Address -->
            <p>Email: jafait1004@gmail.com</p>

        </div>

        <!-- Button: Update Profile -->

        <div class="container-fluid pt-3">
            <input
                    type="button"
                    class="btn btn-primary btn-lg"
                    name="update_profile"
                    value="Update Profile"
            />
        </div>

    </div>

</div>