
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/Displaystudent")
public class Displaystudent extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		String pass=request.getParameter("password").toLowerCase();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root1214");
			if(pass.equals("student"))
			{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM data");

				out.println("<!DOCTYPE html>");
			    out.println("<html>");
			    out.println("<head>");
			    out.println("<meta charset='UTF-8'>");
			    out.println("<title>Student Details</title>");
			    out.println("<link rel='stylesheet' href='edit.css'>");
			    out.println("<style>");
			    out.println(".container { max-width: 700px; margin: auto; padding: 20px; background: black; border-radius: 8px; }");
			    out.println(".student-box { padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 6px; background: black; }");
			    out.println(".button-area { text-align: center; margin-top: 20px; }");
			    out.println("button { padding: 10px 20px; background-color: #007BFF; color: white; border: none; border-radius: 5px; cursor: pointer; }");
			    out.println("</style>");
			    out.println("</head>");
			    out.println("<body>");
			    out.println("<div class='container'>");

			    while (rs.next()) {
			       out.println("<div class='student-box'>");
				            out.println("<p>Name: " + rs.getString("name") + "</p>");
				           out.println("<p>Roll No: " + rs.getString("rollno") + "</p>");
				            out.println("<p> DOB: " + rs.getString("dob") + "</p>");
				            out.println("<p> Email: " + rs.getString("email") + "</p>");
				           out.println("<p>Year: " + rs.getString("year") + "</p>");
				           out.println("<p>Grade: " + rs.getString("grade") + "</p>");
				            out.println("<p>Course: " + rs.getString("course") + "</p>");
		
			        out.println("</div>");
			    }

			    // Place Close Button after all student details inside container
			    out.println("<div class='button-area'>");
			    out.println("<button onclick=\"window.location.href='index.html'\">Close</button>");
			    out.println("</div>");

			    out.println("</div>"); // Close container
			    out.println("</body>");
			    out.println("</html>");
			}
			else
			{
				out.println("<html><head><title>Updating Details</title>");
        	    out.println("<link rel='stylesheet' href='edit.css'>");
        	    out.println("</head><body><div class='container'>");
        	    out.println("<h2>Password Incorrect</h2>");
        	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
        	    out.println("</div></body></html>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
}
