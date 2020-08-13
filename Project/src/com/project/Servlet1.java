package com.project;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    public Servlet1()
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        boolean flag = false;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?allowPublicKeyRetrieval=true&useSSL=false", "root", "Aisha@2019");
            Statement stmt = conn.createStatement();
            for(ResultSet rs = stmt.executeQuery("select * from user_credentials"); rs.next();)
            {
                if(username.equals(rs.getString(1)) && password.equals(rs.getString(2)))
                {
                    session.setAttribute("username", username);
                    flag = true;
                    if("admin".equals(rs.getString(3)))
                    {
                        response.sendRedirect("AdminSearch.html");
                    } else
                    {
                        response.sendRedirect("NormalSearch.html");
                    }
                }
            }

            if(!flag)
            {
                out.print("Invalid username or password");
            }
        }
        catch(Exception e)
        {
            out.print(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doGet(request, response);
    }
}
