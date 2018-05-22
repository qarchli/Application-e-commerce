package com.sdzee.tp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sdzee.tp.dao.DAOFactory;

public class InitialisationDAOFactory implements ServletContextListener {
	private static final String	ATT_DAO_FACTORY	= "daofactory";
	private DAOFactory			daoFactory;

	public void contextInitialized( ServletContextEvent event ) {
		/*
		 * Récuperation du ServletContext (objet implicite 'application') lors du
		 * chargement de l'application
		 */
		ServletContext servletContext = event.getServletContext();

		/* Instanciation de la DAOFactory */
		daoFactory = DAOFactory.getInstance();

		/*
		 * Enregistrement de la DAOFactory dans un attribut qui a pour portée toute
		 * l'application
		 */
		servletContext.setAttribute( ATT_DAO_FACTORY, daoFactory );
	}
}
