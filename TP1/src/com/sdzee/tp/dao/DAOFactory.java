package com.sdzee.tp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

	private static final String	FICHIER_PROPERTIES	= "/com/sdzee/tp/dao/dao.properties";
	private String				url;
	private String				username;
	private String				password;

	public DAOFactory( String url, String username, String password ) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DAOFactory getInstance() {

		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;

		InputStream fichiersProperties = DAOFactory.class.getClassLoader().getResourceAsStream( FICHIER_PROPERTIES );

		// Loading data from dao.properties and
		if ( fichiersProperties == null ) {
			throw new DAOConfigurationException( "Le fichier " + FICHIER_PROPERTIES + " est introuvable." );
		}

		try {
			properties.load( fichiersProperties );
			url = properties.getProperty( "url" );
			driver = properties.getProperty( "driver" );
			username = properties.getProperty( "username" );
			password = properties.getProperty( "password" );
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier .properties.", e );
		}

		// Connecting jdbc driver
		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}

		// Creating an instance of DAO Factory
		DAOFactory instance = new DAOFactory( url, username, password );
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection( url, username, password );
	}

	public ClientDAO getClientDao() {
		return new ClientDAOImpl( this );
	}

	public CommandeDAO getCommandeDao() {
		return new CommandeDAOImpl( this );
	}
}
