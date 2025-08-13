import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/Addstudent")
public class Addstudent extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException, IOException {
		String nm=request.getParameter("name");
		String roll=request.getParameter("rollno");
		String db=request.getParameter("dob");
		String em=request.getParameter("email");
		String yr=request.getParameter("year");
		String gd=request.getParameter("grade");
		String cs=request.getParameter("course");
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try
		{
			int year = Integer.parseInt(yr);
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root1214");
			PreparedStatement checkst= con.prepareStatement("SELECT rollno FROM data WHERE rollno=?");
			checkst.setString(1, roll);
			ResultSet rs = checkst.executeQuery();
			if(rs.next())
			{
				 out.println("<!DOCTYPE html>");
	                out.println("<html><head><title>Error</title></head><body>");
	                out.println("<h2>⚠️ User ID (Roll No) already exists!</h2>");
	                out.println("<a href='addstudent.html'>Go Back</a>");
	                out.println("</body></html>");
	                return; 
			}
			PreparedStatement ps= con.prepareStatement(
					"INSERT INTO data (name,rollno,dob,email,year,grade,course) VALUES (?,?,?,?,?,?,?)");
			 ps.setString(1, nm);
			 ps.setString(2,roll);
			 ps.setString(3, db);
			 ps.setString(4, em);
			 ps.setInt(5, year);
			 ps.setString(6, gd);
			 ps.setString(7, cs);
			 
			 int rowsInserted = ps.executeUpdate();
		        if (rowsInserted > 0) {
		        	out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<meta charset='UTF-8'>");
					out.println("<title>Error</title>");
					out.println("<link rel='stylesheet' href='style.css'>");
					out.println("</head>");
					out.println("<body>");
					out.println("<div class='container'>");
					out.println("<h2>Adding Data Seccessfull</h2>");
					out.println("<button onclick=\"window.location.href='index.html'\">Go Back</button>");
					out.println("</div>");
					out.println("</body>");
					out.println("</html>");
		        }
		        else
		        {
		        	out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<meta charset='UTF-8'>");
					out.println("<title>Error</title>");
					out.println("<link rel='stylesheet' href='style.css'>");
					out.println("</head>");
					out.println("<body>");
					out.println("<div class='container'>");
					out.println("<h2>invalid </h2>");
					out.println("<button onclick=\"window.location.href='index.html'\">Go Back</button>");
					out.println("</div>");
					out.println("</body>");
					out.println("</html>");
		        }
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			out.println("<pre>" + e.getMessage() + "</pre></body></html>");
		}
	}

}
