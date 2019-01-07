package com.mycompany.cookiedz.onlycookie;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePassword extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserAccessC.users == null) {
            UserAccessC.users = UserAccessC.getUsers(getServletContext().getRealPath("users"));
        }
        Cookie[] cookie = request.getCookies();

        String login = null;
         for (Cookie c : cookie) {
            if (c.getName().equals("Login")) {
                login = c.getValue();
            }

        }
        String password = request.getParameter("password");

        if (password.matches("[a-zA-Z_0-9]+")) {

            UserAccessC.users.put(login, password);
            PrintWriter pw = response.getWriter();
            pw.println("Password has changed!");
            Cookie cokp = new Cookie("Password", password);
            response.addCookie(cokp);
            pw.close();
            return;
        } else {
            PrintWriter pw = response.getWriter();
            pw.println("Incorrect Password! (It must contains only a-z A-Z 0-9)");
            pw.close();
            return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
