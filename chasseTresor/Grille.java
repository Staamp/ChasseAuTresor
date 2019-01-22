import java.lang.System.*;
/**
* classe Grille
*/
public class Grille {

  /**
  * Nombre de chasseur sur le grille
  */
  private int nbrChasseur;
  
  /**
  * Nombre de pierre pour la partie
  */
  private int nbrPierre;
  
  /**
  * Hauteur de la grille
  */
  private int hauteur;
  
  /**
  * Largeur de la grille
  */
  private int largeur;
  
  /**
  * Tableau de case pour faire la grille
  */
  public Case[][] tab;
  
  /**
  * Position du tresor
  */
  private Position tresor;
  
  /**
  * Condition pour arreter le jeu (quand on a gagne ou perdu)
  */
  private boolean stopGame;

  /**
  * Constructeur d'une grille
  * @param int nbrChasseur: Nombre de chasseurs sur la grille.
  * @param int nbrPierre : Nombre de pierres maximum sur la grille.
  * @param int hauteur : Hauteur de la grille.
  * @param int largeur : Largeur de la grille.
  */
  public Grille(int nbrChasseur,int nbrPierre,int hauteur,int largeur, boolean stopGame){
    this.nbrChasseur=nbrChasseur;
    this.nbrPierre=nbrPierre;
    this.hauteur=hauteur;
    this.largeur=largeur;
    this.stopGame = stopGame;
    //Declaration
    tab=new Case[largeur][hauteur];
    //On "pose" le tresor
    int xtresor=(int)(Math.random()*largeur);
    int ytresor=(int)(Math.random()*hauteur);
    tresor=new Position(xtresor,ytresor);
  }
  
  /**
  * Getter de la position du tresor.
  * @return Position: Position du tresor.
  */
  public Position getTresor(){
    return tresor;
  }
  
  /**
  * Getter de la hauteur de la grille.
  * @return int : Hauteur de la grille.
  */
  public int getHauteur(){
    return hauteur;
  }
  
  /**
  * Getter du nombre de pierre de la grille.
  * @return int: nombre de pierre de la grille.
  */
  public int getNbPierre(){
    return nbrPierre;
  }
  
  /**
  * Getter de la largeur de la grille.
  * @return int: Largeur de la grille.
  */
  public int getLargeur(){
    return largeur;
  }
  
  /**
  * Setter d'une fin de partie.
  * @param boolean: stopGame
  */
  public void setStopGame(boolean stopGame){
    this.stopGame = stopGame;
  }
  
  /**
  * Getter d'une fin de partie.
  * @return boolean : stopGame.
  */
  public boolean getStopGame(){
    return stopGame;
  }    
  
  /**
  * Deplace un chasseur de 1 fois sur 5 de façon aleatoire selon les cases disponibles (il ne peut bien sur pas aller sur une pierre ou sortir de la grille) et 3 fois sur 4 au plus court chemin.
  * @param int x: Coordonnee en x du Chasseur.
  * @param int y: Coordonnee en y du Chasseur.
  */
  public void deplacementChasseur(int x, int y){
    //Dans cette fonction on a utilisé 'g' pour case gauche, 'd' pour case droite, 'h' pour case haut, 'b' pour case bas
    char[] res=new char[4];
    char[] rdm=new char[4];
    char direction;
    boolean mauvaise_trajectoire=((int)(Math.random()*5)==0);
    int nbr_case=1;
    if(mauvaise_trajectoire){
      System.out.println("Le chasseur n'est pas sur de sa direction.");
      nbr_case=0;
      if(x>0&&(!estPierre(x-1,y))){
        rdm[nbr_case]='g';
        nbr_case++;
      }
      if(x<largeur-1&&(!estPierre(x+1,y))){
        rdm[nbr_case]='d';
        nbr_case++;
      }
      if(y<hauteur-1&&(!estPierre(x,y+1))){
        rdm[nbr_case]='h';
        nbr_case++;			
      }
      if(y>0&&(!estPierre(x,y-1))){
        rdm[nbr_case]='b';
        nbr_case++;		
      }
      direction=rdm[(int)(Math.random()*nbr_case)];	
    } else{
      System.out.println("Le chasseur est sur de lui.");
      res[0]='g';
      int min;
      if(x>0){
        min=tab[x-1][y].getDistance();
      }else{
        min=hauteur+largeur+1;
      }
      int distance;
      if(x<largeur-1){
        distance=tab[x+1][y].getDistance();
      }else{
        distance=hauteur+largeur+1;
      }
      if(distance<min){	
        min=distance;
        res[0]='d';
        nbr_case=1;
      } else {
        if(distance==min){
          res[nbr_case]='d';
          nbr_case++;
        }
      }
      if(y<hauteur-1){
        distance=tab[x][y+1].getDistance();
      } else {
        distance=hauteur+largeur+1;
      }
      if(distance<min){
        min=distance;
        res[0]='h';
        nbr_case=1;
      } else {
        if(distance==min){
          res[nbr_case]='h';
          nbr_case++;
        }
      }
      if(y>0){
        distance=tab[x][y-1].getDistance();
      } else {
        distance=hauteur+largeur+1;
      }
      if(distance<min){
        min=distance;
        res[0]='b';
        nbr_case=1;
      } else {
        if(distance==min){
          res[nbr_case]='b';
          nbr_case++;
        }
      }
      System.out.println("Nombre de case =>"+nbr_case);
      direction=res[(int)(Math.random()*nbr_case)];
    }
    //On choisi au hasard la case ou va se deplacer le chasseur
    System.out.println("La direction est :"+direction);
    switch (direction){
      case 'b':{
        Case c=tab[x][y];
        tab[x][y]=tab[x][y-1];
        tab[x][y-1]=c;
        tab[x][y-1].setPosition(x,y-1);
        tab[x][y].setPosition(x,y);
        if(estTresor(x,y-1)){
          //~ System.out.println("Le chasseur a trouvé le trésor");
          setStopGame(true);
          //~ System.exit(0);
        }
        break;
      }
      case 'h':{
        Case c=tab[x][y];
        tab[x][y]=tab[x][y+1];
        tab[x][y+1]=c;
        tab[x][y+1].setPosition(x,y+1);
        tab[x][y].setPosition(x,y);
        if(estTresor(x,y+1)){
          //~ System.out.println("Le chasseur a trouvé le trésor");
          setStopGame(true);
          //~ System.exit(0);
        }
        break;
      }
      case 'd':{
        Case c=tab[x][y];
        tab[x][y]=tab[x+1][y];
        tab[x+1][y]=c;
        tab[x+1][y].setPosition(x+1,y);
        tab[x][y].setPosition(x,y);
        if(estTresor(x+1,y)){
          //~ System.out.println("Le chasseur a trouvé le trésor");
          setStopGame(true);
          //~ System.exit(0);
        }
        break;
      }
      case 'g':{
        Case c=tab[x][y];
        tab[x][y]=tab[x-1][y];
        tab[x-1][y]=c;
        tab[x-1][y].setPosition(x-1,y);
        tab[x][y].setPosition(x,y);
        if(estTresor(x-1,y)){
          //~ System.out.println("Le chasseur a trouvé le trésor");
          setStopGame(true);
          //~ System.exit(0);
        }
        break;
      }
    }
  }
  
  /**
  * Determine si la case est une pierre.
  * @param int x: Coordonnee en x de la case.
  * @param int y: Coordonnee en y de la case.
  * @return boolean : true si la case est une pierre.
  */
  public boolean estPierre(int x,int y){
    return tab[x][y].getClass().getName()=="Pierre";
  }
  
  /**
  * Determine si la case est un chasseur.
  * @param int x: Coordonnee en x de la case.
  * @param int y: Coordonne en y de la case.
  * @return boolean : true si la case est un chasseur.
  */
  public boolean estChasseur(int x,int y){
    return tab[x][y].getClass().getName()=="Chasseur";
  }
  
  /**
  * Determine si la case est un tresor.
  * @param int x: Coordonnee en x de la case.
  * @param int y: Coordonnee en y de la case.
  * @return boolean : true si la case est un trésor.
  */
  public boolean estTresor(int x,int y){
    return tresor.getX()==x && tresor.getY()==y;
  }
  
  /**
  * Applique a une case de façon récursive sa distance actuelle en fonction des pierres posées.
  * @param int x: Coordonnée en x de la case.
  * @param int y: Coordonnée en y de la case.
  * @param int distance: Distance de la case.
  */
  public void calculDistance_rec(int x,int y,int distance){
    if(distance<tab[x][y].getDistance()&&!estPierre(x,y)){	
      tab[x][y].setDistance(distance);
      boolean gauche=true;
      boolean droite=true;
      boolean haut=true;
      boolean bas=true;
      
      if(x==0||estPierre(x-1,y))gauche=false;
      if(x==largeur-1||estPierre(x+1,y))droite=false;
      if(y==0||estPierre(x,y-1))bas=false;
      if(y==hauteur-1||estPierre(x,y+1))haut=false;
      
      if(gauche)calculDistance_rec(x-1,y,distance+1);
      if(droite)calculDistance_rec(x+1,y,distance+1);
      if(bas)calculDistance_rec(x,y-1,distance+1);
      if(haut)calculDistance_rec(x,y+1,distance+1);
    }

  }
  
  /**
  * Appelle la fonction calculDistance_rec apres avoir initialisee toutes les cases a hauteur*largeur
  */
  public void calculDistance(){
    for(int i=0;i<largeur;i++){
      for(int j=0;j<hauteur;j++){
        if(!estPierre(i,j)){
          tab[i][j].setDistance(hauteur*largeur);
        }
      }
    }
    calculDistance_rec(getTresor().getX(),getTresor().getY(),0);
  }
  
  /**
  * Methode toString qui retourne un caractere pour l'affichage.
  * @return String : retourne la grille de jeu.
  */
  public String toString(){
    String res="";
    for(int i=0;i<largeur;i++){
      for(int j=0;j<hauteur;j++){
        res+=tab[i][j].toString()+"|";
      }
      res+="\n";
    }
    return res;
  }
}
