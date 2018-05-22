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
import com.sdzee.tp.dao.DAOException;
import com.sdzee.tp.dao.DAOFactory;

public class SuppressionClient extends HttpServlet {
	public static final String	URL_REDIRECTION		= "http://localhost:8080/TP1/listerclients";
	public static final String	CONF_DAO_FACTORY	= "daofactory";
	public static final String	PARAM_ID_CLIENT		= "id";
	private static final String	SESSION_CLIENTS		= "clients";

	private ClientDAO			clientDAO;

	public void init() throws ServletException {
		this.clientDAO = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		String idClient = getValeurParametre( request, PARAM_ID_CLIENT );
		Long id = Long.parseLong( idClient );
		HttpSession session = request.getSession();
		Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( SESSION_CLIENTS );

		if ( id != null && clients != null ) {
			try {
				clientDAO.supprimer( id );
				clients.remove( id );
			} catch ( DAOException e ) {
				e.printStackTrace();
			}
			session.setAttribute( SESSION_CLIENTS, clients );
		}
		response.sendRedirect( URL_REDIRECTION );

	}

	private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
		String valeur = request.getParameter( nomChamp );
		if ( valeur == null || valeur.trim().length() == 0 ) {
			return null;
		} else {
			return valeur;
		}
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {

	}

}
