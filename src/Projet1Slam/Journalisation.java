package Projet1Slam;

import ComposantBdd.BddAccess;

public class Journalisation {

	private BddAccess refBdd;
	
	public void journaliserAction(String acteur, String name,
								  String login, String typeCons, 
								  String action, String dateAction) {
		
		String req = "INSERT INTO Journalisation(acteur,nom,login,typeConseil,action,dates) VALUES(";
		req = req + "'"+acteur+"',";
		req = req + "'"+name+"',";
		req = req + "'"+login+"',";
		req = req + "'"+typeCons+"',";
		req = req + "'"+action+"',";
		req = req + "'"+dateAction +"')";
		
		refBdd.executeReqUpdate(req);
		
	}

	public void setRefBdd(BddAccess bdd) {
		refBdd = bdd;
	}
}
