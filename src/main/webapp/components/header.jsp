<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="row bg-primary">
        <div class="col-md-1">
        </div>
        <div class="col-md-2">
            <img src="${pageContext.request.contextPath}/images/misfit-felines-logo.png" alt="Misfit Felines logo"
                 style="max-height: 200px">
        </div>
        <div class="col-md-7">
        </div>
        <div class="col-md-2">
            <c:choose>
                <c:when test="${not empty person}">
                    <a class="btn btn-success btn-lg mt-5"
                       href="${pageContext.request.contextPath}/logout">Log
                        Out</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-success btn-lg mt-5"
                       href="<%= request.getContextPath() %>/logIn">Log In</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>