/**
* Classe positon pour creer des positions dans la grille
*/
public class Position{
  /**
  * Coordonnee en abscisse de la case
  */
  private int x;
  
  /**
  * Coordonnee en ordonnee de la case
  */
  private int y;

  /**
  * Getter d'une coordonnee x de la grille.
  * @return int x : retourne la position en x.
  */  
  public int getX(){
    return x;
  }
  
  /**
  * Getter d'une coordonnee y de la grille.
  * @return int y : retourne la position en y.
  */ 
  public int getY(){
    return y;
  }
  
  /**
  * Constructeur d'une position.
  * @param int x : Coordonnee en abscisse de la case.
  * @param int y : Coordonnee en ordonnee de la case.
  */
  public Position(int x, int y){
    if(x>=0 && y>=0){
      this.x=x;
      this.y=y;	
    }
  }
  
  /**
  * Methode equals qui compare deux position et determine si elles sont egales.
  * @param Position o : recoit une position en parametre.
  * @return boolean : vrai si les positions sont egales et faux sinon.
  */
  public boolean equals(Position o){
    if(x==o.getX()&&y==o.getY()){
      return true;
    }
    return false;
  }
}
