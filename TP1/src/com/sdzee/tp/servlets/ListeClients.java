package com.sdzee.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListeClients extends HttpServlet {

	public static final String	VUE_LISTE_CLIENTS	= "/WEB-INF/listerClients.jsp";
	public static final String	ATT_CLIENTS			= "clients";
	private static final String	CONF_DAO_FACTORY	= "daofactory";

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE_LISTE_CLIENTS ).forward( request, response );
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {

	}

}
