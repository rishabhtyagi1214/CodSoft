import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/Editstudent")
public class Editstudent extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException, IOException
            {
		   String r=request.getParameter("rollno");
		   String d=request.getParameter("dob");
		   response.setContentType("text/html");
		   PrintWriter out=response.getWriter();
		   
		   try
		   {
			   Class.forName("com.mysql.cj.jdbc.Driver"); 
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root1214");
				PreparedStatement ps=con.prepareStatement("SELECT DOB FROM data WHERE rollno=? ");
				ps.setString(1, r);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					String db=rs.getString("DOB");
					if(db.equals(d))
					{
						PreparedStatement pstm=con.prepareStatement("SELECT * FROM data WHERE rollno=?");
						pstm.setString(1, r);
						ResultSet rst=pstm.executeQuery();
						if(rst.next())
						{

							out.println("<!DOCTYPE html>");
							out.println("<html>");
							out.println("<head>");
							out.println("<meta charset='UTF-8'>");
							out.println("<title>Edit Page</title>");
							out.println("<link rel='stylesheet' href='edit.css'>");
							out.println("</head>");
							out.println("<body>");
							 out.println("<div class='container'>");
							 out.println("<h2>Update Student Details</h2>");
				                out.println("<form action='Editstudent' method='post'>");
				                out.println("<h3>Name:</h3>");
				                out.println("<input type='text' id='name' name='name' value='" + rst.getString("name") + "'>");
				                out.println("<h3>Roll No:</h3>");
				                out.println("<input type='text' id='rollno' name='rollno' value='" + rst.getString("rollno") + "' readonly>");
				                out.println("<h3>DOB:</h3>");
				                out.println("<input type='text' id='dob' name='dob' value='" + rst.getString("DOB") + "'>");
				                out.println("<h3>Email:</h3>");
				                out.println("<input type='text' id='email' name='email' value='" + rst.getString("email") + "'>");
				                out.println("<h3>Year:</h3>");
				                out.println("<input type='text' id='year' name='year' value='" + rst.getInt("year") + "'>");
				                out.println("<h3>Grade:</h3>");
				                out.println("<input type='text' id='grade' name='grade' value='" + rst.getString("grade") + "'>");
				                out.println("<h3>Course:</h3>");
				                out.println("<input type='text' id='course' name='course' value='" + rst.getString("course") + "'>");
				                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:5px; cursor:pointer;'>Update</button>");
				                out.println("</form>");
						        out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
						        out.println("</div>");
							out.println("</body>");
							out.println("</html>");
							
						}
						else
						{
							 out.println("<html><head><title>Updating Details</title>");
			            	    out.println("<link rel='stylesheet' href='edit.css'>");
			            	    out.println("</head><body><div class='container'>");
			            	    out.println("<h2>No student found for rollno " + r + "</h2>");
			            	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
			            	    out.println("</div></body></html>");
						}
				
					}
					else
					{
						 out.println("<html><head><title>Updating Details</title>");
		            	    out.println("<link rel='stylesheet' href='edit.css'>");
		            	    out.println("</head><body><div class='container'>");
		            	    out.println("<h2>DOB does not match for rollno " + r + "</h2>");
		            	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
		            	    out.println("</div></body></html>");
					}
				}
				else
				{
					 out.println("<html><head><title>Updating Details</title>");
	            	    out.println("<link rel='stylesheet' href='edit.css'>");
	            	    out.println("</head><body><div class='container'>");
	            	    out.println("<h2>Please enter correct rollno " + r + "</h2>");
	            	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Done</button>");
	            	    out.println("</div></body></html>");
					
				}
			  
			   
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
			   out.println("<html><body><h3>Database error: " + e.getMessage() + "</h3></body></html>");
			   
		   }
      }
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException, IOException
            {
				String n=request.getParameter("name");
				String r=request.getParameter("rollno");
				String d=request.getParameter("dob");
				String e=request.getParameter("email");
				String y=request.getParameter("year");
				String g=request.getParameter("grade");
				String c=request.getParameter("course");
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				try
				{
					Class.forName("com.mysql.cj.jdbc.Driver"); 
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root1214");
					 PreparedStatement ps = con.prepareStatement( "UPDATE data SET name = ?, dob = ?, email = ?, year = ?, grade = ?, course = ? WHERE rollno = ?");
					 ps.setString(1, n);
					 ps.setString(2, d);
					 ps.setString(3, e);
					 ps.setString(4, y);
					 ps.setString(5, g);
					 ps.setString(6, c);
					 ps.setString(7, r);
					 int rowsUpdated =ps.executeUpdate();
					 if (rowsUpdated > 0)
					 {
						 out.println("<html><head><title>Updating Details</title>");
		            	    out.println("<link rel='stylesheet' href='edit.css'>");
		            	    out.println("</head><body><div class='container'>");
		            	    out.println("<h2>Details updated successfully!</h2>");
		            	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
		            	    out.println("</div></body></html>");
					 }
					 else
					 {
						 out.println("<html><head><title>Updating Details</title>");
		            	    out.println("<link rel='stylesheet' href='edit.css'>");
		            	    out.println("</head><body><div class='container'>");
		            	    out.println("<h2>failed to update Details!</h2>");
		            	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
		            	    out.println("</div></body></html>");
						 
					 }
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					 out.println("<html><body><h3>Database error: " + ex.getMessage() + "</h3></body></html>");
					
				}
            }
}
