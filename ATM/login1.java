package atm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class login1 extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException, IOException {
		String acc=request.getParameter("account");
		String pin=request.getParameter("pin");
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root1214");
			PreparedStatement ps=con.prepareStatement("SELECT accountno,pin FROM account WHERE accountno=?" );
			ps.setString(1, acc);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				String a=rs.getString("accountno");
				String p=rs.getString("pin");
				
				if(acc.equals(a)&&pin.equals(p))
				{
					ServletContext context = getServletContext();
				    context.setAttribute("account", acc);
					response.sendRedirect("user.html");
				}
				else
				{
					out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<meta charset='UTF-8'>");
					out.println("<title>Error</title>");
					out.println("<style>");
					out.println("body {");
					out.println("  margin: 0;");
					out.println("  padding: 0;");
					out.println("  height: 100vh;");
					out.println("  display: flex;");
					out.println("  justify-content: center;");
					out.println("  align-items: center;");
					out.println("  font-family: Arial, sans-serif;");
					out.println("  background-color: rgba(200, 200, 259, 255); /* dark blue */");
					out.println("}");

					out.println(".error-box {");
					out.println("  background-color: rgba(255, 255, 255, 0.1);");
					out.println("  padding: 50px 80px;");
					out.println("  border-radius: 15px;");
					out.println("  box-shadow: 0 0 30px rgba(0, 0, 0, 0.3);");
					out.println("  text-align: center;");
					out.println("  max-width: 600px;");
					out.println("}");

					out.println(".error-box h2 {");
					out.println("  color: white;");
					out.println("  font-size: 34px;");
					out.println("  margin-bottom: 30px;");
					out.println("  animation: moveText 2s infinite alternate ease-in-out;");
					out.println("  text-shadow: 0 0 12px red, 0 0 25px rgba(255, 0, 0, 0.5);");
					out.println("}");

					out.println(".error-box button {");
					out.println("  background-color: white;");
					out.println("  color: blue;");
					out.println("  border: none;");
					out.println("  padding: 14px 30px;");
					out.println("  font-size: 18px;");
					out.println("  border-radius: 8px;");
					out.println("  cursor: pointer;");
					out.println("  transition: 0.3s ease;");
					out.println("}");

					out.println(".error-box button:hover {");
					out.println("  background-color: #d9e6ff;");
					out.println("  color: darkblue;");
					out.println("  transform: scale(1.05);");
					out.println("}");

					out.println("@keyframes moveText {");
					out.println("  0% { transform: translateX(-30px); }");
					out.println("  100% { transform: translateX(30px); }");
					out.println("}");
					out.println("</style>");
					out.println("</head>");
					out.println("<body>");
					out.println("<div class='error-box'>");
					out.println("<h2>Incorrect Account Number or PIN</h2>");
					out.println("<button onclick=\"window.location.href='login.html'\">Go Back</button>");
					out.println("</div>");
					out.println("</body>");
					out.println("</html>");




				}
			}
			else
			{out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='UTF-8'>");
			out.println("<title>Error</title>");
			out.println("<link rel='stylesheet' href='login.css'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class='container'>");
			out.println("<h2>Account Number does not exist</h2>");
			out.println("<button onclick=\"window.location.href='login.html'\">Go Back</button>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
	    	out.println("<html><body><h3>Database error: " + e.getMessage() + "</h3></body></html>");
		}
		
	}

}
