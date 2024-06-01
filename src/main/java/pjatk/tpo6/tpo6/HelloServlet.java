package pjatk.tpo6.tpo6;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.naming.Context;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
           message = "huh";
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
    public void destroy() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder req = new StringBuilder();
        BufferedReader reader = request.getReader();
        String json = reader.lines().collect(Collectors.joining());
        requestJson requestJson = new Gson().fromJson(json, HelloServlet.requestJson.class);
    }

    class requestJson{
        String search;
        Features[] features;
        Cuisine[] cuisines;
        Prices[] prices;
    }
    class Features{
        String feature;
    }
    class Cuisine{
        String cuisine;
    }
    class Prices{
        String price;
    }
}