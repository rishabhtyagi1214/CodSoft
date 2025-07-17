 package atm;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/Checkbalance")
public class Checkbalance extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		ServletContext context=getServletContext();
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String account=(String)context.getAttribute("account");
		try
		{
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root1214");
			PreparedStatement ps=con.prepareStatement("SELECT name,amount FROM account WHERE accountno=?" );
			ps.setString(1, account);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				String nm=rs.getString("name");
				BigDecimal amnt=rs.getBigDecimal("amount");
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset='UTF-8'>");
				out.println("<title>Withdraw Page</title>");
				out.println("<link rel='stylesheet' href='any.css'>");
				out.println("<style>");
				out.println("  h1 {");
				out.println("    color: #90cff2;");
				out.println("    text-shadow: 0 0 12px #90caf9;");
				out.println("  }");
			    out.println("</style>");
				out.println("</head>");
				out.println("<body>");
				out.println("<div class=\"stars\" id=\"stars\"></div>");
				out.println("  <div class='info'>");
				out.println("    <h1 >Account Number: " + account + "</h1>");
			    out.println("    <h1>Your Name: " + nm + "</h1>");
			    out.println("    <h1>Current Balance: " + amnt + "</h1>");
			    out.println("<button onclick=\"window.location.href='index.html'\">Thank You </button>");
			    out.println("  </div>");    
				out.println("</body>");
				out.println("</html>");
				
				out.println("<script>");
				out.println("    // Generate stars dynamically");
				out.println("    const starsContainer = document.getElementById('stars');");
				out.println("    const numStars = 100;");
				out.println("    for (let i = 0; i < numStars; i++) {");
				out.println("        const star = document.createElement('div');");
				out.println("        star.classList.add('star');");
				out.println("        star.style.top = Math.random() * 100 + '%';");
				out.println("        star.style.left = Math.random() * 100 + '%';");
				out.println("        star.style.animationDuration = (1 + Math.random() * 2) + 's';");
				out.println("        star.style.animationDelay = Math.random() * 2 + 's';");
				out.println("        starsContainer.appendChild(star);");
				out.println("    }");
				out.println("</script>");

					
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
	    	out.println("<html><body><h3>Database error: " + e.getMessage() + "</h3></body></html>");
		}
	}
}
