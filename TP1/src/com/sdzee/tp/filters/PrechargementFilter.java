package com.sdzee.tp.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sdzee.tp.beans.Client;
import com.sdzee.tp.beans.Commande;
import com.sdzee.tp.dao.ClientDAO;
import com.sdzee.tp.dao.CommandeDAO;
import com.sdzee.tp.dao.DAOFactory;

public class PrechargementFilter implements Filter {
	private static final String	CONF_DAO_FACTORY	= "daofactory";
	private static final String	SESSION_CLIENTS		= "clients";
	private static final String	SESSION_COMMANDES	= "commandes";
	private CommandeDAO			commandeDAO;
	private ClientDAO			clientDAO;

	public void init( FilterConfig config ) throws ServletException {
		this.clientDAO = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
		this.commandeDAO = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) )
				.getCommandeDao();
	}

	@Override
	public void doFilter( ServletRequest req, ServletResponse rep, FilterChain chain )
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();

		if ( session.getAttribute( SESSION_CLIENTS ) == null ) {
			Map<Long, Client> mapClients = clientDAO.lister();
			session.setAttribute( SESSION_CLIENTS, mapClients );
		}
		if ( session.getAttribute( SESSION_COMMANDES ) == null ) {
			Map<Long, Commande> mapCommandes = commandeDAO.lister();
			session.setAttribute( SESSION_COMMANDES, mapCommandes );
		}
		chain.doFilter( request, rep );
	}

}
