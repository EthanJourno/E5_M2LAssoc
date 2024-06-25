package Projet1Slam;

import java.sql.ResultSet;
import java.sql.SQLException;

import ComposantBdd.BddAccess;

public class Membre {

	private BddAccess refBdd;
	private PresentIhm refIhm; 
	
	public void setRefBdd(BddAccess bdd) {
		refBdd = bdd;
	}
	
	public void visualiserConseils(String type) {
		
		ResultSet rs;
		String req = "SELECT libelle from conseils where type=";
		
		req = req + "'"+type+"'";
		
		rs = refBdd.executeReqSelect(req);
		
		// Recuperer la reponse
		refBdd.recupereReponseSelect();
	}

	public void setRefIhm(PresentIhm ihm) {
		refIhm = ihm;
	}
}
