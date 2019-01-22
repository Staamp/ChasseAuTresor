/**
* La classe Case implemente "Comparable" afin d'avoir une relation d'ordre entre les differentes cases, cette relation sera utilisee pour les chasseurs.
*/
public abstract class Case implements Comparable<Case>{
  
  /**
  * Distance jusqu'au tresor de la case actuelle
  */
  private int distance;
  
  /**
  * Position de la case actuelle
  */
  private Position pos;
  
  /**
  * Grille actuelle
  */
  private Grille grille;

  
  /**
  * Constructeur d'un case avec distance en parametre (utile pour la pose de pierre)
  * @param int x : Coordonnee en abscisse de la case.
  * @param int y : Coordonnee en ordonnee de la case.
  * @param int distance : Distance au tresor de la case. (Hauteur + largeur si c'est une pierre)
  * @param Grille grille : Grille ou sera posee la case.
  */
  public Case(int x,int y,int distance,Grille grille){
    this.distance=distance;
    this.pos=new Position(x,y);
  }

  /**
  * Constructeur d'un case, la distance au tresor va etre calculee directement.
  * @param int x : Coordonnee en abscisse de la case.
  * @param int y : Coordonnee en ordonnee de la case.
  * @param Grille grille : Grille ou sera posee la case.
  */
  public Case(int x,int y,Grille grille){
    this.pos=new Position(x,y);
    distance=Math.abs(grille.getTresor().getX()-x)+Math.abs(grille.getTresor().getY()-y);
  }

  /**
  * Getter de la position de la case.
  * @return Position : La position actuelle de la case.
  */
  public Position getPosition() {
    return this.pos;
  }

  /**
  * Getter de la distance de la case.
  * @return int : La position actuelle de la case.
  */
  public int getDistance(){
    return distance;
  }

  /**
  * Setter de la distance de la case.
  * @param int distance : La distance que l'on veut attribuer a la case.
  */
  public void setDistance(int distance){
    this.distance=distance;
  }

  /**
  * Setter de la position de la case.
  * @param int x : Coordonnee en x de la case.
  * @param int y : Coordonnee en y de la case.
  */
  public void setPosition(int x,int y) {
    this.pos = new Position(x,y);
  }

  /**
  * Methode compareTo qui va donner une relation d'ordre entre les cases. 
  * @param Case o : La case a comparer.
  * @return int: Retourne -1 si la distance actuelle est plus grande que la distance de la case en parametre, 0 si egale et 1 sinon.
  */
  public int compareTo(Case o) {
    if(distance<o.getDistance()){
      return 1;
    }
    if(distance==o.getDistance()){
      return 0;
    }
    return -1;
  }
}
