
import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/Removestudent")
public class Removestudent extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		String r=req.getParameter("rollno");
		String d=req.getParameter("dob");
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root1214");
			PreparedStatement ps=con.prepareStatement("SELECT DOB FROM data WHERE rollno=? ");
			ps.setString(1, r);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				String db=rs.getString("dob");
				if(db.equals(d))
				{
					PreparedStatement psmt=con.prepareStatement("SELECT * FROM data WHERE rollno=?");
					psmt.setString(1, r);
					ResultSet rst=psmt.executeQuery();
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
						 out.println("<h2>Student Details are:</h2>");
			                out.println("<form action='Removestudent' method='get'>");
			                out.println("<h3>Name:</h3>");
			                out.println("<label for='name'>"+rst.getString("name")+"</label>");
			                out.println("<h3>Roll No:</h3>");
			                out.println("<input type='hidden' name='rollno' value='" + rst.getString("rollno") + "'>");
			                out.println("<label>" + rst.getString("rollno") + "</label>");
			                out.println("<h3>DOB:</h3>");
			                out.println("<label for='dob'>"+rst.getString("DOB")+"</label>");
			                out.println("<h3>Email:</h3>");
			                out.println("<label for='email'>"+rst.getString("email")+"</label>");
			                out.println("<h3>Year:</h3>");
			                out.println("<label for='year'>"+rst.getString("year")+"</label>");
			                out.println("<h3>Grade:</h3>");
			                out.println("<label for='grade'>"+rst.getString("grade")+"</label>");
			                out.println("<h3>Course:</h3>");
			                out.println("<label for='course'>"+rst.getString("course")+"</label>");
			                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:5px; cursor:pointer;'>Delete</button>");
			                out.println("</form>");
					        out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
					        out.println("</div>");
						out.println("</body>");
						out.println("</html>");
					}
					else {
						 out.println("<html><head><title>Updating Details</title>");
		            	    out.println("<link rel='stylesheet' href='edit.css'>");
		            	    out.println("</head><body><div class='container'>");
		            	    out.println("<h2>Details are empty</h2>");
		            	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
		            	    out.println("</div></body></html>");
					}
				}
					else
					{
						 out.println("<html><head><title>Updating Details</title>");
		            	    out.println("<link rel='stylesheet' href='edit.css'>");
		            	    out.println("</head><body><div class='container'>");
		            	    out.println("<h2>DOB are Not Match For RollNo" + r + "</h2>");
		            	    out.println("<button onclick=\"window.location.href='remove.html'\" style=\"margin-top: 10px;\">Cancel</button>");
		            	    out.println("</div></body></html>");
					}
				}
		}
		catch(Exception e){
			e.printStackTrace();
			out.println("<pre>" + e.getMessage() + "</pre></body></html>");
			
		}
		
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String roll=request.getParameter("rollno");
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student", "root", "root1214");
            PreparedStatement ps = con.prepareStatement("DELETE FROM data WHERE rollno = ?");
            ps.setString(1, roll);
            
            int result = ps.executeUpdate();
            if(result > 0)
            {
            	out.println("<html><head><title>Updating Details</title>");
         	    out.println("<link rel='stylesheet' href='edit.css'>");
         	    out.println("</head><body><div class='container'>");
         	    out.println("<h2>Students Record has Been Deleted Successfully</h2>");
         	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Ok</button>");
         	    out.println("</div></body></html>");
            }
            else
            {
            	out.println("<html><head><title>Updating Details</title>");
         	    out.println("<link rel='stylesheet' href='edit.css'>");
         	    out.println("</head><body><div class='container'>");
         	    out.println("<h2>Details are not found for roll no"+roll+"</h2>");
         	    out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Ok</button>");
         	    out.println("</div></body></html>");
            }
		}
		 catch(Exception ex)
		{
			 ex.printStackTrace();
			 out.println("<pre>" + ex.getMessage() + "</pre></body></html>");
		}
	}

}
