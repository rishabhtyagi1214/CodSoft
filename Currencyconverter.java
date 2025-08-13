package currency;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/Currencyconverter")
public class Currencyconverter extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        double amt = Double.parseDouble(request.getParameter("amount"));
        String f = request.getParameter("from");
        String t = request.getParameter("to");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/currency", "root", "root1214");

            String sql = "SELECT c.rate, cm.symbol FROM convertrate c " +
                         "JOIN currency_master cm ON c.targetcurrency = cm.code " +
                         "WHERE c.basecurrency = ? AND c.targetcurrency = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, f);
            ps.setString(2, t);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double rate = rs.getDouble("rate");
                String symbol = rs.getString("symbol");
                double converted = amt * rate;

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Currency Conversion</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='converter-box'>");
                out.println("<h2>Converted Amount:</h2>");
                out.printf("<h3>%.2f %s = %s%.2f %s</h3>", amt, f, symbol, converted, t);
                out.println("<button onclick=\"window.location.href='index.html'\">Go Back</button>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
            	out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Currency Conversion</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h3>Conversion rate not found!</h3>");
                out.println("<button onclick=\"window.location.href='index.html'\">Go Back</button>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Error</title>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='converter-box'>");
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            out.println("<button onclick=\"window.location.href='index.html'\">Go Back</button>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
