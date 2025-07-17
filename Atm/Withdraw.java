package atm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException, IOException {
		ServletContext context=getServletContext();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String account=(String)context.getAttribute("account");
		String amount=request.getParameter("amount");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Withdraw Page</title>");
		out.println("<link rel='stylesheet' href='any.css'>");
		out.println("</head>");
		out.println("<body>");
		 out.println("<div class='container'>");
	        out.println("<h1>Withdraw Money</h1>");
	        out.println("<form action='Withdraw' method='post'>");
	        out.println("<label for='amount'>Enter Withdraw Amount:</label>");
	        out.println("<input type='number' id='amount' name='amount' required>");
	        out.println("<button type='submit'>Withdraw</button>");
	        out.println("</form>");
	        out.println("<button onclick=\"window.location.href='index.html'\" style=\"margin-top: 10px;\">Cancel</button>");
	        out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException, IOException {
		ServletContext context=getServletContext();
		
		String account=(String)context.getAttribute("account");
		//int accno=Integer.parseInt(account);
		String am=request.getParameter("amount");
		int balance;
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try
		{
			
			int amt=Integer.parseInt(am);
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root1214");
			PreparedStatement ps=con.prepareStatement("SELECT name,amount FROM account WHERE accountno=?" );
			ps.setString(1, account);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				String nm=rs.getString("name");
				int amnt=rs.getInt("amount");
//				System.out.println(am);
				if(amt<=amnt)
				{
					balance=amnt-amt;
					PreparedStatement pst = con.prepareStatement("UPDATE account SET amount = ? WHERE accountno = ?");
					 pst.setInt(1, balance);
					 pst.setString(2, account);
					int rowsUpdated = pst.executeUpdate();
					out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<meta charset='UTF-8'>");
					out.println("<title>Withdraw Page</title>");
					out.println("<link rel='stylesheet' href='any.css'>");
					out.println("</head>");
					out.println("<body>");
					 out.println("<div class='container'>");
					 out.println("<h1>Withdraw Successfully Completed</h1>");
			            out.println("<h2>Your name is <strong>" + nm + "</strong>.</h2>");
			            out.println("<h2>Withdrawn amount: ₹" + amt + "</h2>");
			            out.println("<h2>Your remaining balance is: ₹" + balance + "</h2>");
			            out.println("<button onclick=\"window.location.href='index.html'\">Thank You </button>");
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
					out.println("<title>Withdraw Page</title>");
					out.println("<link rel='stylesheet' href='any.css'>");
					out.println("</head>");
					out.println("<body>");
					 out.println("<div class='container'>");
					 out.println("<h1 style='color:blue;'>Insufficient Balance</h1>");
			            out.println("<p style='color:#80ff8f;'>Your balance is ₹" + amnt + ", but you tried to withdraw ₹" + amt + "</p>");
			            out.println("<button onclick=\"window.location.href='index.html'\">Thank You </button>");
				        out.println("</div>");
				        
					out.println("</body>");
					out.println("</html>");
					
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
	    	out.println("<html><body><h3>Database error: " + e.getMessage() + "</h3></body></html>");
		}
	}

}
