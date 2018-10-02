	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/js/materialize.min.js"></script>

	<!-- jQuery Library -->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.2.1.min.js"></script>
	<!--scrollbar-->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/resources/js/perfect-scrollbar.min.js"></script>
	<!--plugins.js - Some Specific JS codes for Plugin Settings-->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/plugins.js"></script>
	<!--custom-script.js - Add your own theme custom JS-->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/custom-script.js"></script>


	<!-- form validation -->
	<script>
        $(document).ready(function () {
            $('select').material_select();

            // for HTML5 "required" attribute
            $("select[required]").css({
                display : "inline",
                height : 0,
                padding : 0,
                width : 0
            });
        });
    </script>
</body>
</html>