package ComposantBdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.ResultSetMetaData;

import Projet1Slam.PresentIhm;

public class BddAccess {

	// Attributs
	private Connection cnx;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData resMeta;
	
	private PresentIhm refIhm;
	
	// Methodes
	// Chargement du driver
	public void chargerDriver(String driverName) {
		try {
			Class.forName(driverName);
			System.out.println("Driver trouve !!");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver non trouve");
			e.printStackTrace();
		}
	}
	
	// Connexion a la BDD
	public void connexionBdd(String infosBdd, String login, String password) {
		try {
			cnx = DriverManager.getConnection(infosBdd,login,password);
			System.out.println("Connexion BDD OK!!");
		} catch (SQLException e) {
			System.out.println("Probleme connexion a la BDD !!");
			e.printStackTrace();
		}
	}
	
	// Creation du conteneur Statement
	public void creerStatement() {
		try {
			stmt = cnx.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Envoi d'une requete de type update
	public void executeReqUpdate(String req) {
		try {
			stmt.executeUpdate(req);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Envoi d'une requete select
	public ResultSet executeReqSelect(String req) {
		try {
			rs = stmt.executeQuery(req);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	// Recuperation de la reponse associee a une requete select
	public void recupereReponseSelect() {
		//Recuper de rs les meta data
		//la metadata va chercher le type de structuration de la BDD
		try {
			resMeta=(ResultSetMetaData) rs.getMetaData();
			
			//recuperer le nombre de colonne de la reponse
			int nbCols = resMeta.getColumnCount();
			
			//affichage des noms des colonnes
			for(int i = 1; i<= nbCols; i++) {
				System.out.print(resMeta.getColumnName(i) + " |"+" " );
				refIhm.afficheConseils(resMeta.getColumnName(i) + " |"+" ");
			}
			
			System.out.println();
			refIhm.afficheConseils("\n");
			System.out.println("----------------------------");
			refIhm.afficheConseils("----------------------------");
			refIhm.afficheConseils("\n");
			
			//traitement de la requete
			while(rs.next()){
				
				for(int i = 1; i <= nbCols; i++) {
					System.out.print(rs.getObject(i).toString() + " ");
					refIhm.afficheConseils(rs.getObject(i).toString() + " ");
				}
				System.out.println();
				refIhm.afficheConseils("\n");
				System.out.println("_____________________________");
				refIhm.afficheConseils("_____________________________");
				refIhm.afficheConseils("\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setRefIhm(PresentIhm ihm) {
		refIhm = ihm;
	}
}
