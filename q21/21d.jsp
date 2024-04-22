<%@page import="java.util.*"%>
<html>
	<head><title>Hidden field demo: Session API</title></head>
	<body>
		<%
        	int current = 0;
			String last = (String)session.getAttribute("last");
			if(last != null) {
				String button = request.getParameter("button");
					if(button != null) {
						if(button.equals("next"))
							current = Integer.parseInt(last) + 1;
						else 
							current = Integer.parseInt(last) - 1;
					}
			}
			session.setAttribute("last", String.valueOf(current));
			out.println(current);
			%>
		<br>
		<form name="myForm" method="post">
			<input type="submit" name="button" value="prev">
			<input type="submit" name="button" value="next">
		</form>
	</body>
</html>

