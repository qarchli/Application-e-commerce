package com.sdzee.tp.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.dao.CommandeDAO;
import com.sdzee.tp.dao.DAOException;
import com.sdzee.tp.dao.DAOFactory;

public class SuppressionCommande extends HttpServlet {
	public static final String	URL_REDIRECTION		= "http://localhost:8080/TP1/listercommandes";
	public static final String	CONF_DAO_FACTORY	= "daofactory";
	private static final String	PARAM_ID_COMMANDE	= "id";
	private static final String	SESSION_COMMANDES	= "commandes";
	private CommandeDAO			commandeDAO;

	public void init() throws ServletException {
		this.commandeDAO = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {

		String idCommande = getValeurParametre( request, PARAM_ID_COMMANDE );
		Long id = Long.parseLong( idCommande );
		HttpSession session = request.getSession();
		Map<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDES );

		if ( id != null && commandes != null ) {
			try {
				commandeDAO.supprimer( id );
				commandes.remove( id );
			} catch ( DAOException e ) {
				e.printStackTrace();
			}
			session.setAttribute( SESSION_COMMANDES, commandes );
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
