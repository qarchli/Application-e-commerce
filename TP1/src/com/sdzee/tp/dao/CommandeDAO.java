package com.sdzee.tp.dao;

import java.util.Map;

import com.sdzee.tp.beans.Commande;

public interface CommandeDAO {

	void creer( Commande commande );

	Commande trouver( Long id );

	Map<Long, Commande> lister();

	void supprimer( Long id );

}
