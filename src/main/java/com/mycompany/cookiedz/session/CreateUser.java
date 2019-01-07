package com.mycompany.cookiedz.session;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserAccess.users == null) {
            UserAccess.users = UserAccess.getUsers(getServletContext().getRealPath("users"));
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login.matches("[a-zA-Z]+")) {
            if(UserAccess.users.containsKey(login))
            {
                PrintWriter pw = response.getWriter();
                pw.println("Server contains this user!");
                pw.close();
                return;
            }
            if (password.matches("[a-zA-Z_0-9]+")) {
                
                UserAccess.users.put(login, password);
                PrintWriter pw = response.getWriter();
                pw.println("User has created!");
                pw.close();
                return;
            } else {
                PrintWriter pw = response.getWriter();
                pw.println("Incorrect Password! (It must contains only a-z A-Z 0-9)");
                pw.close();
                return;
            }
        } else {
            PrintWriter pw = response.getWriter();
            pw.println("Incorrect Login! (It must contains only a-z A-Z)");
            pw.close();
            return;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
