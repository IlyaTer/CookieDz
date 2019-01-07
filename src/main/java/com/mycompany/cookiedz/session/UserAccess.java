package com.mycompany.cookiedz.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAccess extends HttpServlet {

    static Map<String, String> users;
    static Map<String, Integer> usersLog = new HashMap<>();

    /*getServletContext().getRealPath("books");*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (users == null) {
            users = getUsers(getServletContext().getRealPath("users"));
        }
        String login = (String) request.getSession().getAttribute("Login");
        String password = (String) request.getSession().getAttribute("Password");

        if (login == null && password == null) {
            String tLogin = request.getParameter("login");
            String tPassword = request.getParameter("password");

            if (users.containsKey(tLogin)) {
                if (tPassword.equals(users.get(tLogin))) {
                    PrintWriter pw = response.getWriter();
                    pw.println("Welcome " + tLogin);
                    pw.close();
                    session.setAttribute("Login", tLogin);
                    session.setAttribute("Password", tPassword);
                    return;
                } else {
                    if (usersLog.containsKey(tLogin)) {
                        if (usersLog.get(tLogin) > 1) {
                            response.setStatus(302);
                            response.addHeader("Location",
                                    "http://localhost:8080/CookieDz/ChangePassword.html");
                            session.setAttribute("Login", tLogin);
                            return;
                        }
                        usersLog.put(tLogin, (usersLog.get(tLogin) + 1));
                    } else {
                        usersLog.put(tLogin, 1);
                    }
                    PrintWriter pw = response.getWriter();
                    pw.println("Incorrect password! (You can try again)");
                    pw.close();
                    return;
                }
            } else {
                response.setStatus(302);
                response.addHeader("Location",
                        "http://localhost:8080/CookieDz/CreateUser.html");
                response.setContentType("text/html;charset=UTF-8");
                return;
            }
        } else {
            if (password == null) {
                response.setStatus(302);
                response.addHeader("Location",
                        "http://localhost:8080/CookieDz/ChangePassword.html");
                session.setAttribute("Login", login);
                return;
            }

            PrintWriter pw = response.getWriter();
            pw.println("Welcome " + login);
            pw.close();
            return;
        }
        /*
        response.setStatus(302);
        response.addHeader("Location", "http://localhost:8080/CookieDz/newhtml.html");
        response.setContentType("text/html;charset=UTF-8");
         */
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

    static Map<String, String> getUsers(String path)
            throws IOException {

        Map<String, String> map = new HashMap<>();

        try (Scanner sc = new Scanner(Paths.get(path), "UTF-8")) {
            while (sc.hasNext()) {
                String[] strData = sc.nextLine().split(":");
                map.put(strData[0], strData[1]);
            }
            return map;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }//end getUser

}
