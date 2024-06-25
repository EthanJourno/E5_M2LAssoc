package Projet1Slam;

import ComposantBdd.BddAccess;

public class Responsable {

	private BddAccess refBdd;
	
	public void setRefBdd(BddAccess bdd) {
		refBdd = bdd;
	}
	
	public void ajouterConseils(String libelle, String typConseil, String dateParution) {
		
		/* 
		 * Ancienne façon
		 */
		String req = "INSERT INTO conseils(libelle,type,dateParution) VALUES(";
		req = req + "'"+libelle+"',";
		req = req + "'"+typConseil+"',";
		req = req + "'"+dateParution+"')";
		
		refBdd.executeReqUpdate(req);
	}
}
