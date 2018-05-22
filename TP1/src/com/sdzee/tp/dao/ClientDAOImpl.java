package com.sdzee.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sdzee.tp.beans.Client;

public class ClientDAOImpl implements ClientDAO {

	private static final String	SQL_INSERT_QUERY		= "INSERT INTO Client(nom, prenom, adresse, telephone, email) values (?,?,?,?,?)";
	private static final String	SQL_SELECT_BY_ID_QUERY	= "SELECT id, nom, prenom, adresse, telephone, email FROM Client WHERE id = ?";
	private static final String	SQL_SELECT_ALL_QUERY	= "SELECT * FROM Client ORDER BY id";
	private static final String	SQL_DELETE_BY_ID_QUERY	= "DELETE FROM Client WHERE id=?";
	private DAOFactory			daoFactory;

	public ClientDAOImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void creer( Client client ) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connection, SQL_INSERT_QUERY, true,
					client.getNom(), ( client.getPrenom() == null ? "" : client.getPrenom() ), client.getAdresse(),
					client.getTelephone(), client.getEmail() );
			int statut = preparedStatement.executeUpdate();

			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création du client, aucune ligne ajoutée en table." );
			} else {
				valeursAutoGenerees = preparedStatement.getGeneratedKeys();
				if ( valeursAutoGenerees.next() ) {
					client.setId( valeursAutoGenerees.getLong( 1 ) );
				} else {
					throw new DAOException( "Échec de la création du client, aucun ID auto-génerée n'est retourné." );
				}
			}
		} catch ( SQLException e ) {
			throw new DAOException( "Une erreur est survenue. Échec de création du client.", e );
		} finally {
			DAOUtilitaire.fermetureRessources( valeursAutoGenerees, preparedStatement, connection );
		}
	}

	@Override
	public Client trouver( Long id ) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Client client = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connection, SQL_SELECT_BY_ID_QUERY, false,
					id );
			resultSet = preparedStatement.executeQuery();

			if ( resultSet.next() ) {
				client = DAOUtilitaire.mapClient( resultSet );
			} else {
				throw new DAOException( "Aucun client trouvé avec l'ID entré." );
			}

		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			DAOUtilitaire.fermetureRessources( resultSet, preparedStatement, connection );
		}

		return client;
	}

	@Override
	public Map<Long, Client> lister() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Client client = null;
		Map<Long, Client> clients = new HashMap<Long, Client>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connection, SQL_SELECT_ALL_QUERY, false );
			resultSet = preparedStatement.executeQuery();
			while ( resultSet.next() ) {
				client = DAOUtilitaire.mapClient( resultSet );
				clients.put( client.getId(), client );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			DAOUtilitaire.fermetureRessources( resultSet, preparedStatement, connection );
		}

		return clients;
	}

	@Override
	public void supprimer( Long id ) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		if ( this.trouver( id ) != null ) {

			try {
				connection = this.daoFactory.getConnection();
				preparedStatement = DAOUtilitaire.initialisationRequetePreparee( connection, SQL_DELETE_BY_ID_QUERY,
						false, id );
				int statut = preparedStatement.executeUpdate();
				if ( statut == 0 ) {
					throw new DAOException( "Échec de la suppression du client, ID non trouvé." );
				}
			} catch ( SQLException e ) {
				throw new DAOException( e );
			} finally {
				DAOUtilitaire.fermetureStatement( preparedStatement );
				DAOUtilitaire.fermetureConnection( connection );
			}
		} else {
			throw new DAOException( "Id introuvable. Aucun client supprimé." );
		}

	}

}
