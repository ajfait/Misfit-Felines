<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <div class="row">
        <div class="col-md-1"></div>

        <div class="col-md-10">
            <!-- Error Message -->
            <h1 class="text-primary pt-3">Error</h1>
            <p class="text-primary pt-1">Cats don't make mistakes, but we do.</p>
            <button onclick="window.location.href = document.referrer || '/index.jsp';"
                    class="btn btn-primary mt-3 mb-5">Back to Previous Page
            </button>
            <!-- END Error Message -->
        </div>

        <div class="col-md-1"></div>
    </div>
</div>