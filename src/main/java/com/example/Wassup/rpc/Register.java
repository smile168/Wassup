package com.example.Wassup.rpc;

import com.example.Wassup.db.DBConnection;
import com.example.Wassup.db.DBConnectionFactory;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection connection = DBConnectionFactory.getConnection();

        try {
            JSONObject object = RpcHelper.readJSONObject(request);
            String userId = object.getString("user_id");
            String password = object.getString("password");
            String firstname = object.getString("first_name");
            String lastname = object.getString("last_name");

            JSONObject result = new JSONObject();
            if (connection.registerUser(userId, password, firstname, lastname)) {
                result.put("status", "OK");
            } else {
                result.put("status", "User already exists");
            }
            RpcHelper.writeJsonObject(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

}
