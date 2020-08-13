package com.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
@WebServlet("/Servlet2")
@MultipartConfig
public class Servlet2 extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public Servlet2()
    {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String dob1 = request.getParameter("dob");
        String age1 =  request.getParameter("age");
        String mob1 =  request.getParameter("mob");
        String address =  request.getParameter("address");
        
        int age = age1 != null ? Integer.valueOf(Integer.parseInt(age1)) : Integer.valueOf(Integer.parseInt("0"));
        Long mob = Long.valueOf(mob1 != null ? Long.parseLong(mob1, 10) : 0L);
        java.sql.Date dob = null;
        try
        {
            DateFormat dformat = new SimpleDateFormat("yyyy-mm-dd");
            if(dob1!=null) {
            Date dobj = dformat.parse(dob1);
            //long timeInMilli = dobj.getTime();
            dob = new java.sql.Date(dobj.getTime());
        }
        }
        catch(ParseException e1)
        {
            e1.printStackTrace();
        }
        InputStream inputStream = null;
        Part photo = request.getPart("image");
        if(photo != null)
        {
            System.out.println(photo.getName());
            System.out.println(photo.getSize());
            System.out.println(photo.getContentType());
            inputStream = photo.getInputStream();
        }
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(name!=null || dob1!=null || age1!=null || mob1!=null ||address!=null ) {
            	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?allowPublicKeyRetrieval=true&useSSL=false", "root", "Aisha@2019");
            conn.setAutoCommit(true);
            
            String query = "insert into test (name,dob,age,mob,address,image) values (?,?,?,?,?,?)";
            PreparedStatement prstmt = conn.prepareStatement(query);
            prstmt.setString(1, name);
            prstmt.setDate(2, dob);
            prstmt.setInt(3, age);
            prstmt.setLong(4, mob.longValue());
            prstmt.setString(5, address);
            if(inputStream != null)
            {
                prstmt.setBlob(6, inputStream);
            } else
            {
                prstmt.setNull(6, 2004);
            }
            prstmt.executeUpdate();
            out.print("Record inserted successfully");
            out.print("<br></br><a href='Search.html'> Search </a>");
            prstmt.close();
            conn.close();
            }
            else
            	out.print("All the values can not be null, please enter again");
            out.print("<br></br><a href='Details.html'> Details </a>");	
        }
        catch(Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

   /* protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }*/
}
