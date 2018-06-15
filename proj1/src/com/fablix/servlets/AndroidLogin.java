package com.fablix.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.User;

import com.database.access.Basic;
import com.database.access.RecaptchaVerifyUtils;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class AndroidLogin
 */
@WebServlet("/AndroidLogin")
public class AndroidLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String site_key = "6LfqflsUAAAAALpipz_wlztQR47r8M_nsVPDBHUD";
    private static final String secret_key = "6LfqflsUAAAAAE-gAcqLbP-dCKVGhhyyzGZa7wdX";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Fabflix");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Basic DBObj = new Basic();
		String id;
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        Map<String, String[]> map = request.getParameterMap();
        for (String key: map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key)[0]);
        }
        
        // verify recaptcha first
        try {
            //Basic.verify(gRecaptchaResponse, secret_key);
        } catch (Exception e) {
            JsonObject responseJsonObject = new JsonObject();
            responseJsonObject.addProperty("status", "fail");
            responseJsonObject.addProperty("message", e.getMessage());
            response.getWriter().write(responseJsonObject.toString());
            return;
        }
        
        // then verify username / password
        JsonObject loginResult = DBObj.verifyAndroidLogin(username, password);
        
        if (loginResult.get("status").getAsString().equals("success")) {
            request.getSession().setAttribute("user", username);
            response.getWriter().write(loginResult.toString());
        } else {
            response.getWriter().write(loginResult.toString());
        }

	}

}
