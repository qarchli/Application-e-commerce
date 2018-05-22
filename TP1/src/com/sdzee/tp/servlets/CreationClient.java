package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.dao.ClientDAO;
import com.sdzee.tp.dao.DAOFactory;
import com.sdzee.tp.forms.CreationClientForm;;

public class CreationClient extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	public static final String	CONF_DAO_FACTORY	= "daofactory";
	private static final String	SESSION_CLIENTS		= "clients";

	// Vues paths
	public static final String	VUE_CREER_CLIENT	= "/WEB-INF/creerClient.jsp";
	public static final String	VUE_AFFICHER_CLIENT	= "/WEB-INF/afficherClient.jsp";

	// Request attributes
	public static final String	ATT_FORM			= "form";
	public static final String	ATT_CLIENT			= "client";

	private ClientDAO			clientDAO;

	public void init() throws ServletException {
		this.clientDAO = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE_CREER_CLIENT ).forward( request, response );
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		CreationClientForm form = new CreationClientForm( clientDAO );
		Client client = form.ajouterClient( request );

		request.setAttribute( ATT_FORM, form );
		request.setAttribute( ATT_CLIENT, client );

		if ( form.getErreurs().isEmpty() ) {
			HttpSession session = request.getSession();
			Map<Long, Client> clients = (HashMap<Long, Client>) ( session.getAttribute( SESSION_CLIENTS ) );
			if ( clients == null ) {
				clients = new HashMap<Long, Client>();
			}
			clients.put( client.getId(), client );
			session.setAttribute( SESSION_CLIENTS, clients );
			this.getServletContext().getRequestDispatcher( VUE_AFFICHER_CLIENT ).forward( request, response );
		} else {
			this.getServletContext().getRequestDispatcher( VUE_CREER_CLIENT ).forward( request, response );
		}
	}

}
