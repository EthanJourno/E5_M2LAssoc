package ComposantBdd;

public class MainBddAccess {

	public static void main(String[] args) {
		// Creation de l'objet BddAcess
		BddAccess bdd = new BddAccess();
		
		// Charger le driver
		bdd.chargerDriver("com.mysql.jdbc.Driver");
		
		// Connexion a la BDD
		bdd.connexionBdd("jdbc:mysql://localhost/ecole", "root", "");
		
		// Creation du statement
		bdd.creerStatement();
		
		// Requete select
		//bdd.executeReqSelect("SELECT nomEleve, prenomEleve, age from profeleve where age<=12");
		
		// Recuperer la reponse
		//bdd.recupereReponseSelect();
	
		bdd.executeReqUpdate("INSERT INTO profeleve values('calvo','Herve','Terminale',50,'Martin','Philo','H')");
	}

}
