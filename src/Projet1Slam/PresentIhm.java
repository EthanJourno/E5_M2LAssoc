package Projet1Slam;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ComposantBdd.BddAccess;

public class PresentIhm extends JFrame implements ActionListener {

	// Attributs 
	
	// Creation d'un objet JPanel (toile)
	private JPanel pan;
	
	// Les labels
	
	private JLabel labelNom = new JLabel("Nom");
	private JLabel labelLogin = new JLabel("Login");
	private JLabel labelMdp = new JLabel("Mot de passe");
		
	private JLabel labelCategorie = new JLabel("Categorie conseils");
	
	private JLabel labelVisuConseils = new JLabel("Visualisation conseils");
	private JLabel labelSaisieConseils = new JLabel("Saisie conseil");
	
	// Les zones de saisie
	private JTextField zoneNom = new JTextField(20);
	private JTextField zoneLogin = new JTextField(20);
	private JTextField zonePassword = new JTextField(20);
	private JTextField zoneSaisieConseils = new JTextField(20);
	
	// Zones de texte Area
	private JTextArea zoneAffichConseils = new JTextArea();
	// Ascenseurs
	private JScrollPane scroll = new JScrollPane(zoneAffichConseils);
	
	// Radio boutons
	private JRadioButton radMembre = new JRadioButton("Membre");
	private JRadioButton radResp = new JRadioButton("Responsable");
		
	// Pour grouper les radio boutons
	private ButtonGroup bg = new ButtonGroup();
	
	// Les boutons
	private JButton boutonValid = new JButton("Validation");
	private JButton boutonCnx = new JButton("Connexion");
	
	private String[] comboTypeConseils = {"Juridiques", "Frais deplacements", "Litiges"};
	private JComboBox comboConseils = new JComboBox(comboTypeConseils);
	
	// Les CheckBox
	private JCheckBox jCheckJournalisation = new JCheckBox("Activer la journalisation");
	
	private String typeConseil;
	
	private Membre refMembre;
	private Responsable refResp;
	private BddAccess refBdd;
	private boolean cnxMembre = false;
	private boolean cnxResp = false;
	
	private boolean membreReconnu = false;
	private boolean respReconnu = false;
	
	private String nom;
	private String login;
	private String motDePasse;
	
	private Journalisation journal;
	
	private boolean isJournalisationActivated = false;
	
	
	public PresentIhm() {
		
		// Creation du panel
		pan = new JPanel();
		
		// Ajoute un titre a la fenetre
		setTitle("Projet Slam 1");
		
		// Dimensionner la fenetre (300 pixels de large 
		// sur 400 de haut
		setSize(300, 400);
						
		// Rend la fenetre visible a l'ecran
		setVisible(true);
				
		// A la fermeture de la fenetre fermer le programme
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Associer le panel au cadre defini
		setContentPane(pan);
		
		// Associer une couleur de fond au Panel
		pan.setBackground(Color.green);
		
		pan.add(radMembre);
		pan.add(radResp);
		
		// Grouper les radio boutons
		bg.add(radMembre);
		bg.add(radResp);
		
		pan.add(labelNom);
		pan.add(zoneNom);
		
		pan.add(labelLogin);
		pan.add(zoneLogin);
		
		pan.add(labelMdp);
		pan.add(zonePassword);
		
		pan.add(labelVisuConseils);
		zoneAffichConseils.setBackground(Color.lightGray);
		zoneAffichConseils.setForeground(Color.blue);
		zoneAffichConseils.setRows(20);
		zoneAffichConseils.setColumns(30);
		scroll.setAutoscrolls(true);
		pan.add(scroll);
		
		pan.add(labelCategorie);
		pan.add(comboConseils);
		pan.add(labelSaisieConseils);
		pan.add(zoneSaisieConseils);
		
		pan.add(jCheckJournalisation);
		
		pan.add(boutonCnx);
		pan.add(boutonValid);
		
		// Associer l'evenement bouton (click) au traitement
		// correspondant defini dans actionPerformed()
		boutonValid.addActionListener(this);
		boutonCnx.addActionListener(this);
		radMembre.addActionListener(this);
		radResp.addActionListener(this);
		comboConseils.addActionListener(this);
		jCheckJournalisation.addActionListener(this);
		
		// Creation de la Journalisation
		journal = new Journalisation();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ResultSet rs;
		
		// Determiner la source de l'evenement
		if(e.getSource() == boutonValid) {
			//System.out.println("Bouton valide");
			// Verifier si membre ou responsable
			if(cnxMembre == true) {
				if(membreReconnu == true) {
					// Demander au membre de visualiser les
					// conseils
					refMembre.visualiserConseils(typeConseil);
					
					if(isJournalisationActivated == true) {
						Date now = new Date(); // crée une date au jour et à l'heure d'aujourd'hui
						DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
						        	DateFormat.SHORT,
						        	DateFormat.SHORT);
						journal.journaliserAction("Membre", nom, login, typeConseil, "Visualisation",shortDateFormat.format(now).toString());
					}
				}
				else {
					System.out.println("Vous etes membre mais n'avez pas acces a la BDD!!");
				}
			}
			else {
				if(respReconnu == true) {
					// Recuperer le nouveau conseil
					String newConseil = zoneSaisieConseils.getText();
					
					Date now = new Date(); // crée une date au jour et à l'heure d'aujourd'hui
					DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
					        	DateFormat.SHORT,
					        	DateFormat.SHORT);
					refResp.ajouterConseils(newConseil, typeConseil, shortDateFormat.format(now).toString());
					
					if(isJournalisationActivated == true) {
						
						/*
						Date now = new Date(); // crée une date au jour et à l'heure d'aujourd'hui
						DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
						        	DateFormat.SHORT,
						        	DateFormat.SHORT);
						*/
						
						journal.journaliserAction("Responsable", nom, login, typeConseil, "Insertion conseil",shortDateFormat.format(now).toString());
					}
				}
				else {
					System.out.println("Vous etes responsable mais n'avez pas acces a la BDD!!");
				}
			}
		}
		else if(e.getSource() == boutonCnx) {
			//System.out.println("Bouton connexion");
			
			// Recuperer les nom, login et mot de passe
			// des zones de saisie
			nom = zoneNom.getText();
			login = zoneLogin.getText();
			motDePasse = zonePassword.getText();
			
			if(cnxMembre == true) {
				// Formuler la requete d'authentification
				String req = "SELECT nom from connexionmember where nom =";
				req = req +"'"+nom+"'";
				req = req + " and login="+"'"+login+"'";
				req = req + " and mdp="+"'"+motDePasse+"'";
				rs = refBdd.executeReqSelect(req);
				
				// Traiter la reponse
				try {
					if(rs.next()) {
						System.out.println("Membre reconnu");
						membreReconnu = true;
					}
					else {
						System.out.println("Desole vous n'avez pas acces en tant que membre!!");
						respReconnu = false;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else if(cnxResp == true) {
				// Formuler la requete d'authentification
				String req = "SELECT nom from connexionresp where nom =";
				req = req +"'"+nom+"'";
				req = req + " and login="+"'"+login+"'";
				req = req + " and mdp="+"'"+motDePasse+"'";
				rs = refBdd.executeReqSelect(req);
				
				// Traiter la reponse
				try {
					if(rs.next()) {
						System.out.println("Responsable reconnu");
						respReconnu = true;
					}
					else {
						System.out.println("Desole vous n'avez pas acces en tant que responsable!!");
						respReconnu = false;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource() == radMembre) {
			//System.out.println("Vous ete membre !!");
			cnxMembre = true;
			cnxResp = false;
		}
		else if(e.getSource() == radResp) {
			//System.out.println("Vous etes responsable !!");
			cnxResp = true;
			cnxMembre = false;
		}
		else if(e.getSource() == comboConseils) {
			
			typeConseil = (String) comboConseils.getSelectedItem();
			System.out.println(typeConseil);
		}
		else if(e.getSource() == jCheckJournalisation) {
			if(jCheckJournalisation.isSelected()) {
				isJournalisationActivated = true;
			}
			else {
				isJournalisationActivated = false;
			}
		}
	} 

	public void afficheConseils(String msg) {
		zoneAffichConseils.append(msg);
		//zoneAffichConseils.setText("\n");
	}

	public void setRefMembre(Membre m) {
		refMembre  = m;
	}

	public void setRefResp(Responsable resp) {
		refResp = resp;
	}

	public void setRefBdd(BddAccess bdd) {
		refBdd = bdd;
	}

	public void setRefBddToJournal() {
		
		journal.setRefBdd(refBdd);
	}
}

