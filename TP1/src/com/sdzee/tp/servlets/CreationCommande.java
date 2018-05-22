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
import com.sdzee.tp.dao.ClientDAO;
import com.sdzee.tp.dao.CommandeDAO;
import com.sdzee.tp.dao.DAOFactory;
import com.sdzee.tp.forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {

	// Vues paths
	public static final String	VUE_CREER_COMMANDE		= "/WEB-INF/creerCommande.jsp";
	public static final String	VUE_AFFICHER_COMMANDE	= "/WEB-INF/afficherCommande.jsp";

	// Request attributes
	public static final String	ATT_FORM				= "form";
	public static final String	ATT_COMMANDE			= "commande";
	public static final String	SESSION_COMMANDES		= "commandes";
	private static final String	CONF_DAO_FACTORY		= "daofactory";
	private CommandeDAO			commandeDAO;
	private ClientDAO			clientDAO;

	public void init() throws ServletException {

		this.commandeDAO = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCommandeDao();
		this.clientDAO = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();

	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( VUE_CREER_COMMANDE ).forward( request, response );
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {

		CreationCommandeForm form = new CreationCommandeForm( commandeDAO, clientDAO );
		Commande commande = form.ajouterCommande( request );

		request.setAttribute( ATT_FORM, form );
		request.setAttribute( ATT_COMMANDE, commande );

		if ( form.getErreurs().isEmpty() ) {
			HttpSession session = request.getSession();
			Map<Long, Commande> commandes = (HashMap<Long, Commande>) session.getAttribute( SESSION_COMMANDES );
			if ( commandes == null ) {
				commandes = new HashMap<Long, Commande>();
			}
			commandes.put( commande.getId(), commande );
			session.setAttribute( SESSION_COMMANDES, commandes );
			this.getServletContext().getRequestDispatcher( VUE_AFFICHER_COMMANDE ).forward( request, response );
		} else {
			this.getServletContext().getRequestDispatcher( VUE_CREER_COMMANDE ).forward( request, response );
		}
	}

}
