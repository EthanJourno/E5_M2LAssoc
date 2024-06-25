package Projet1Slam;



import java.text.DateFormat;
import java.util.Date;

import ComposantBdd.BddAccess;

public class MainPresentProjet {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Date aujourdhui = new Date(); // crée une date au jour et à l'heure d'aujourd'hui
		
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
		        	DateFormat.SHORT,
		        	DateFormat.SHORT);
		
		System.out.println(shortDateFormat.format(aujourdhui));
		
		//System.out.println(aujourdhui.toLocaleString());
		//System.out.println(aujourdhui.toGMTString());
		
		// Creation de l'ihm
		PresentIhm ihmPresProject = new PresentIhm();
		
		// Creation de BddAccess
		BddAccess bdd = new BddAccess();
		
		// Charger le driver
		bdd.chargerDriver("com.mysql.jdbc.Driver");
		
		// Connexion a la BDD
		bdd.connexionBdd("jdbc:mysql://localhost/m2lassoc?autoReconnect=true&useSSL=false", "root", "");

		// Creation du statement
		bdd.creerStatement();
		
		// Creation de l'objet Membre
		Membre m = new Membre();
		
		// Creation de l'objet Responsable
		Responsable resp = new Responsable();
		
		// Donner a ihmPresProject la reference du membre
		ihmPresProject.setRefMembre(m);
		
		// Donner a ihmPresProject la reference du responsable
		ihmPresProject.setRefResp(resp);
		
		// Donner a ihmPresProject la reference du bddAccess
		ihmPresProject.setRefBdd(bdd);
		
		// Donner l'ordre a l'Ihm de donner la reference bdd a 
		// la journalisation
		ihmPresProject.setRefBddToJournal();
		
		// Donner a Responsable la reference de bdd
		resp.setRefBdd(bdd);
		
		// Donner au Membre la reference de bdd
		m.setRefBdd(bdd);
		
		// Donner a bdd la reference de ihmPresProject afin
		// d'afficher les conseils dans la zone de JTextArea
		bdd.setRefIhm(ihmPresProject);
	}
}
