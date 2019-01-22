import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
* La classe Fenetre etend de la classe JFrame
*/
public class fenetre extends JFrame {
  
  /**
  * Numero de la case clique.
  */
  private int caseClique;
  /**
  * Coordonnee de la ligne de la case clique.
  */
  private int caseCliqueRow;
  /**
  * Coordonnee de la colonne de la case clique.
  */
  private int caseCliqueColumn;
  /**
  * JButton  utile au programme.
  */
  private JButton bQuitter, bPasserSonTour, bRejouer, bJouer;
  /**
  * Parametre de jeu saisi par l'utilisateur.
  */
  private JTextField hauteur, largeur, nbChasseur, nbPierre;
  /**
  * Parametre de jeu saisi par l'utilisateur transforme en type int.
  */
  private int iHauteur, iLargeur, iNbChasseur, iNbPierre;
  /**
  * Booleen pour determiner si le joueur veut rejouer.
  */
  private boolean rejouer;
  /**
  * Booleen pour determiner si le joueur veut passer son tour.
  */
  private boolean passerSonTour;
  /**
  * Tableau de bouton pour detecter les cliques des joueurs.
  */
  JButton[][] bouton;
  
  
  /**
  * Constructeur d'une fenetre
  * @param int caseCliqueRow : coordonnee ligne clique par le joueur.
  * @param int caseCliqueColumn : coordonnee colonne clique par le joueur.
  * @param boolean rejouer : Choix du joueur s'il veut rejouer.
  */
  public fenetre(int caseCliqueRow, int caseCliqueColumn, boolean rejouer) {
    this.caseCliqueRow = caseCliqueRow;
    this.caseCliqueColumn = caseCliqueColumn;
    this.rejouer = rejouer;
    this.setTitle("Chasse au tresor");
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }


  /**
  * Fonction qui initialise le debut d'une partie
  * Elle demande au joueur les parametres de jeu
  */
  public void initGame() {
	this.setSize(400, 200);
    iHauteur = -1;
    iLargeur = -1;
    iNbChasseur = -1;
    iNbPierre = -1;
    
    hauteur = null;
    largeur = null;
    nbChasseur = null;
    nbPierre = null;
    
    bJouer = new JButton("Jouer");
    bQuitter = new JButton("Quitter");
    
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.removeAll();
    
    JPanel z = new JPanel();
	JLabel titre=new JLabel ("Entrez les parametres de la grille :");
	titre.setFont(new Font("Serif", Font.BOLD, 20));
	titre.setForeground(Color.red);
    z.add (titre);
	
    cp.add("North", z);
   
    JPanel z1 = new JPanel();
    
    z1.add (new JLabel ("   Hauteur de la grille (>2):"));

    hauteur = new JTextField(2);
    z1.add(hauteur);
    z1.add (new JLabel ("   Largeur de la grille (>2):"));
    largeur = new JTextField(2);
    z1.add(largeur);
    z1.add (new JLabel ("  Nombre de chasseurs:"));
    nbChasseur = new JTextField(2);
    z1.add(nbChasseur);
    JLabel label1=new JLabel ("   Nombre de pierres:");
	label1.setHorizontalTextPosition(JLabel.CENTER);
    label1.setVerticalTextPosition(JLabel.CENTER);
    z1.add (label1);
    nbPierre = new JTextField(1);
    z1.add(nbPierre);
	z1.setLayout(new GridLayout(4,2));
    cp.add(z1, BorderLayout.CENTER);
    
    JPanel z2 = new JPanel();
    z2.add(bJouer);
    z2.add(bQuitter);
    z2.setLayout(new FlowLayout(FlowLayout.CENTER));
	z.setBackground(new Color(245, 200, 113));
	z1.setBackground(new Color(245, 200, 113));
	z2.setBackground(new Color(245, 200, 113));
    cp.add("South", z2);  
    
    //CREATION D'UN ECOUTEUR
    ActionListener EcouteurBoutonJQ = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Jouer")) {
			int testNbChasseur= Integer.parseInt(nbChasseur.getText());
			int testNbPierre= Integer.parseInt(nbPierre.getText());
			int testHauteur= Integer.parseInt(hauteur.getText());
			int testLargeur= Integer.parseInt(largeur.getText());
			if(testNbChasseur<testHauteur*testLargeur&&testHauteur>2 && testLargeur>2){
				iNbChasseur = testNbChasseur;
				iNbPierre =testNbPierre;
				iHauteur = testHauteur;
				iLargeur = testLargeur;
			}
          System.out.println(e.getActionCommand());
          return;
        }
        if (e.getActionCommand().equals("Quitter")) {
          System.out.println(e.getActionCommand());
          System.exit(0);
        }
      }
    };
    bJouer.addActionListener(EcouteurBoutonJQ);
    bQuitter.addActionListener(EcouteurBoutonJQ);
    validate();
    repaint();
  } 

  /**
  * Initialise la grille de depart
  * @param int height : hauteur de la grille.
  * @param int width : largeur de la grille.
  * @param int caseCliqueRow : coordonnee ligne clique par le joueur.
  * @param int caseCliqueColumn : coordonnee colonne clique par le joueur.
  * @param Grille gr : toute la grille utile pour positionner le tresor et les chasseurs.
  */
  public void initFondCase(int height, int width, int caseCliqueRow, int caseCliqueColumn, Grille gr) {
    this.setSize(600, 500);
    bouton = new JButton[width][height];
    bQuitter = new JButton("Quitter");
    bPasserSonTour = new JButton("Passer son tour");
    
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.removeAll();
    
    JPanel z1 = new JPanel();
    cp.add(z1, BorderLayout.CENTER);
    z1.setLayout(new GridLayout(width,height,0,0));
    z1.setPreferredSize(new Dimension(15, 350));
    z1.setMaximumSize(new Dimension(15, 350));
    
    int t = 0;
    for (int r = 0; r < width; r++) {
      for (int c = 0; c < height; c++) { 
        bouton[r][c] = new JButton(""+t+""); 
        z1.add(bouton[r][c]);
        t++;
        
        if (gr.estChasseur(r, c)) {
          //~ bouton[r][c].setBackground(Color.red);
          bouton[r][c].setIcon(new ImageIcon("image/pirate2.png"));
        }
          
        if (gr.estTresor(r, c)) {
          //~ bouton[r][c].setBackground(Color.yellow);
          bouton[r][c].setIcon(new ImageIcon("image/piece.jpg"));
        }
        if (!gr.estPierre(r, c) && !gr.estTresor(r, c) && !gr.estChasseur(r, c)) {
          //~ bouton[r][c].setBackground(Color.green);
          bouton[r][c].setIcon(new ImageIcon("image/dirt.jpg"));
        }
        
        //CREATION D'UN ECOUTEUR
        ActionListener EcouteurBoutonGrille = new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            caseClique = Integer.parseInt(e.getActionCommand());
            System.out.println("Vs avez selectionne la case : "+caseClique+".");
            transformeEnCoordonnee(caseClique, height);
            System.out.println("Row : "+caseCliqueRow);
            System.out.println("Col : "+caseCliqueColumn);
          }
        };
        //ASSOCIER L'ECOUTEUR AU BOUTON[r][c]
        bouton[r][c].addActionListener(EcouteurBoutonGrille);
      }
    }
    
    
    JPanel z2 = new JPanel();
    cp.add(z2, BorderLayout.SOUTH);
    z2.add(bQuitter); 
    z2.add(bPasserSonTour);
    int pDispo = gr.getNbPierre() - Pierre.nbrPierrePlace;
	JLabel pierreDispo=new JLabel ("Pierre disponible : "+pDispo);
	pierreDispo.setFont(new Font("Serif", Font.BOLD, 15));
	pierreDispo.setForeground(Color.red);
    z2.add(pierreDispo);
    z2.setLayout(new FlowLayout(FlowLayout.CENTER));
	z2.setBackground(new Color(245, 200, 113));
    ActionListener EcouteurBoutonQPST = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Quitter")) {
          System.out.println(e.getActionCommand());
          System.exit(0);
        }
        if (e.getActionCommand().equals("Passer son tour")) {
          System.out.println(e.getActionCommand());
          setPasserSonTour(true);
          System.out.println(passerSonTour);
          return;
        }
      }
    };
    bQuitter.addActionListener(EcouteurBoutonQPST);
    bPasserSonTour.addActionListener(EcouteurBoutonQPST);
    
    
    validate();
    repaint();
  }

  /**
  * Setter pour le fond des cases, cette methode realise les changements graphiques du jeu.
  * @param int height : hauteur de la grille.
  * @param int width : largeur de la grille.
  * @param Grille gr : toute la grille utile pour deplacer les chasseurs.
  */
  public void setFondCase(int width, int height, Grille gr) {
    
    bQuitter = new JButton("Quitter");
    bPasserSonTour = new JButton("Passer son tour");
    //setPasserSonTour(false);
    
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    
    for (int r = 0; r<width; r++) {
      for (int c = 0; c<height; c++) {
        if (gr.estChasseur(r, c)) {
          bouton[r][c].setIcon(new ImageIcon("image/pirate2.png"));
        }
        if (gr.estTresor(r, c)) {
          bouton[r][c].setIcon(new ImageIcon("image/piece.jpg"));
        }
        if (gr.estPierre(r, c)) {
          bouton[r][c].setIcon(new ImageIcon("image/pierre.jpg"));
        }
        if (!gr.estPierre(r, c) && !gr.estTresor(r, c) && !gr.estChasseur(r, c)) {
          bouton[r][c].setIcon(new ImageIcon("image/dirt.jpg"));
        }
      }
    }
    JPanel z2 = new JPanel();
    cp.add(z2, BorderLayout.SOUTH);
    z2.add(bQuitter); 
    z2.add(bPasserSonTour);
    int pDispo = gr.getNbPierre() - Pierre.nbrPierrePlace;
	JLabel pierreDispo=new JLabel ("Pierre disponible : "+pDispo);
	pierreDispo.setFont(new Font("Serif", Font.BOLD, 15));
	pierreDispo.setForeground(Color.red);
    z2.add(pierreDispo);
    z2.setLayout(new FlowLayout(FlowLayout.CENTER));
	z2.setBackground(new Color(245, 200, 113));
    
    ActionListener EcouteurBoutonQPST = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Quitter")) {
          System.out.println(e.getActionCommand());
          System.exit(0);
        }
        if (e.getActionCommand().equals("Passer son tour")) {
          System.out.println(e.getActionCommand());
          setPasserSonTour(true);
          System.out.println(passerSonTour);
          return;
        }
      }
    };
    bQuitter.addActionListener(EcouteurBoutonQPST);
    bPasserSonTour.addActionListener(EcouteurBoutonQPST);
  }

  /**
  * Transforme le numero d'une case en coordonnee de point.
  * @param int width : largeur de la grille.
  * @param int nb : numero de case clique par le joueur.
  */
  public void transformeEnCoordonnee(int nb, int height) {
    caseCliqueColumn = nb % height;
    caseCliqueRow = (nb - caseCliqueColumn) / height;
    System.out.println("Coord("+caseCliqueRow+","+caseCliqueColumn+")");
  }

  /**
  * Methode qui affiche un message si le joueur a gagne ou perdu et proposer de rejouer ou de quitter le jeu.
  * @param boolean gagne : variable pour determiner si le joueur a perdu ou gagne.
  */
  public void affichageGagne(boolean gagne) {
	this.setSize(300, 200);
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    
    cp.removeAll();
    
    JButton affGagne = new JButton();
    bRejouer = new JButton("Rejouer");
    bQuitter = new JButton("Quitter");
    
    JPanel z = new JPanel();
	cp.setBackground(new Color(245, 200, 113));
	z.setBackground(new Color(245, 200, 113));
    if (gagne) {
	  JLabel labelGain=(new JLabel ("BRAVO!"));
      z.add (labelGain);
	  labelGain.setFont(new Font("Serif", Font.BOLD, 40));
	  labelGain.setForeground(Color.red);
    } else {
          JLabel labelGain=(new JLabel ("PERDU!"));
		  z.add (labelGain);
		  labelGain.setFont(new Font("Serif", Font.BOLD, 40));
		  labelGain.setForeground(Color.red);
    }
    cp.add("North", z);
    
   
    
    JPanel z2 = new JPanel();
	z2.setBackground(new Color(245, 200, 113));
    z2.add(bRejouer);
    z2.add(bQuitter);
    cp.add("South", z2);
    ActionListener EcouteurBoutonRQ = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Rejouer")) {
          System.out.println(e.getActionCommand());
          setRejouer(true);
          System.out.println(rejouer);
        }
        if (e.getActionCommand().equals("Quitter")) {
          System.out.println(e.getActionCommand());
          System.exit(0);
        }
      }
    };
    bRejouer.addActionListener(EcouteurBoutonRQ);
    bQuitter.addActionListener(EcouteurBoutonRQ);
    
    validate();
    repaint();
  }
  
  /**
  * Setter pour passer son tour ou non.
  * @param boolean passerSonTour : variable pour determiner si le joueur passe son tour ou non.
  */
  public void setPasserSonTour(boolean passerSonTour) {
    this.passerSonTour = passerSonTour;
  }
  
  /**
  * Setter pour rejouer ou non.
  * @param boolean rejouer : variable pour determiner si le joueur rejoue ou non.
  */
  public void setRejouer(boolean rejouer) {
    this.rejouer = rejouer;
  }

  /**
  * Setter de la coodonnee de la ligne clique.
  * @param int caseCliqueRow : numero de la coodonnee de la ligne clique.
  */
  public void setCaseCliqueRow(int caseCliqueRow) {
    this.caseCliqueRow = caseCliqueRow;
  }

  /**
  * Setter de la coodonnee de la colonne clique.
  * @param int caseCliqueColumn : numero de la coodonnee de la colonne clique.
  */
  public void setCaseCliqueColumn(int caseCliqueColumn) {
    this.caseCliqueColumn = caseCliqueColumn;
  }

  /**
  * Getter pour savoir si on passe notre tour ou non.
  * @return boolean passerSontTour : retourne la decision.
  */
  public boolean getPasserSonTour() {
    return passerSonTour;
  }

  /**
  * Getter pour savoir si on rejoue ou non.
  * @return boolean rejouer : retourne la decision.
  */
  public boolean getRejouer() {
    return rejouer;
  }

  /**
  * Getter pour savoir la hauteur saisie par l'utilisateur au type entier.
  * @return int iHauteur : retourne la hauteur.
  */
  public int getHauteur() {
    return iHauteur;
  }

  /**
  * Getter pour savoir la largeur saisie par l'utilisateur au type entier.
  * @return int iLargeur : retourne la largeur.
  */
  public int getLargeur() {
    return iLargeur;
  }  

  /**
  * Getter pour savoir le nombre de chasseur saisie par l'utilisateur au type entier.
  * @return int iNbChasseur : retourne le nombre de chasseur.
  */
  public int getNbChasseur() {
    return iNbChasseur;
  }

  /**
  * Getter pour savoir le nombre de pierre saisie par l'utilisateur au type entier.
  * @return int iNbPierre : retourne le nombre de pierre.
  */
  public int getNbPierre() {
    return iNbPierre;
  }  

  /**
  * Getter pour savoir la ligne que le joueur a clique.
  * @return int caseCliqueRow : retourne la ligne.
  */
  public int getCaseCliqueRow() {
    return caseCliqueRow;
  }

  /**
  * Getter pour savoir la colonne que le joueur a clique.
  * @return int caseCliqueColumn : retourne la colonne.
  */
  public int getCaseCliqueColumn() {
    return caseCliqueColumn;
  }  
}





