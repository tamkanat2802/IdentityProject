package com.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Servlet3
 */
@WebServlet("/Servlet3")
@MultipartConfig(maxFileSize = 16177215)
public class Servlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<head><title>Related details</title></head>");
	    out.println("<body>");
	    out.println("<center><h1>DETAILS</h1>");
	    
	    out.println("<table border=\"1\">");
	    out.println("<tr>\r\n" + 
  	  		"<td>NAME</td>\r\n" + 
  	  		"<td>DOB</td>\r\n" + 
  	  		"<td>AGE</td>\r\n" +
  	  		"<td>MOB</td>\r\n" + 
  	  		"<td>ADRESS</td>\r\n" +
  	  		"<td>IMAGE</td>\r\n" + 
  	  		"\r\n" + 
  	  		"</tr>");
	    
		
		//String img=request.getParameter("image");		
		String name=request.getParameter("name");
		String dob11=request.getParameter("dob");;
		String mob11=request.getParameter("mob");
		Long mob=mob11==null || mob11.isEmpty()?0:Long.parseLong(mob11,10);
		java.sql.Date dob = null;
		dob=dob11==null || dob11.isEmpty()?null:java.sql.Date.valueOf(dob11);
		
		InputStream inputStream = null; // input stream of the upload file
		// obtains the upload file part in this multipart request
		Part photo =  request.getPart("image");
		if (photo != null) {
            // prints out some information for debugging
            System.out.println(photo.getName());
            System.out.println(photo.getSize());
            System.out.println(photo.getContentType());
             
            // obtains input stream of the upload file
            inputStream = photo.getInputStream();
        }
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/login?allowPublicKeyRetrieval=true&useSSL=false","root","Aisha@2019");
			Statement stmt= conn.createStatement();
			String query;
						if(dob!=null) {
					query = "SELECT * FROM test WHERE ( name = '"+ name+"' AND name is not null) " +" OR (dob='"+ dob+"' AND dob is not null)"
							+"OR (mob='"+ mob+"' AND mob is not null AND mob!=0)";
						}
						else {
							query = "SELECT * FROM test WHERE ( name = '"+ name+"' AND name is not null) "
									+"OR (mob='"+ mob+"' AND mob is not null AND mob!=0)";
						}
			
			      ResultSet rs = stmt.executeQuery(query);
			      while (rs.next()) {
			    	  String name1 = rs.getString(1);
			    	  Date dob1=rs.getDate(2);
			    	  int age1=rs.getInt(3);
			    	  long mob1=rs.getLong(4);
			    	  String add1=rs.getString(5);
			    	 // Blob img1 = rs.getBlob(6);
						
			            
			    	  out.println("<tr>\r\n" + 
			    	  		"<td>" + name1 +"</td>"+
			    	  		"<td>" + dob1 +"</td>\r\n" + 
			    	  		"<td>" + age1 +"</td>\r\n" + 
			    	  		"<td>"+ mob1 + "</td>\r\n" + 
			    	  		"<td>"+ add1 + "</td>\r\n" + 
			    	  		"<td><img width='50' height='50' src=displayphotoutility?name=" + rs.getString(1) + "></img> <p/></td>\r\n" + 
			    	  		"</tr>");
			    	  					    	  
	}
			     
	 } catch (SQLException | ClassNotFoundException e) {
	      out.println("An error occured while retrieving " + e.toString());
	    } 

	    out.println("</center>");
	    out.println("</body>");
	    out.println("</html>");
	    
	    out.print("<a href='Search.html'> Search Again</a>");
	    out.close();
	    
	   }
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
