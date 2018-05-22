package com.sdzee.tp.dao;

import java.util.Map;

import com.sdzee.tp.beans.Client;

public interface ClientDAO {

	void creer( Client client );

	Client trouver( Long id );

	Map<Long, Client> lister();

	void supprimer( Long id );

}
