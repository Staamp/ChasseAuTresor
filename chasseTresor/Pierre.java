/**
* La classe Pierre etend de la classe Case 
*/
public class Pierre extends Case{
  
  /**
  * Attribut static qui determine le nombre de pierre pose
  */
  static int nbrPierrePlace=0;
  
  /**
  * Constructeur d'une case pierre.
  * @param int x : Coordonnee en abscisse de la case.
  * @param int y : Coordonnee en ordonnee de la case.
  * @param Grille grille : Grille ou sera posee la pierre.
  */

  Pierre(int x,int y,Grille g){
    super(x,y,g.getHauteur()*g.getLargeur(),g);
    nbrPierrePlace++;
  }

  /**
  * Methode toString qui retourne un caractere pour l'affichage.
  * @return String : ""+getDistance() pour contaner la case celle-ci prend une valeur plus grande que la taille de la grille.
  */
  public String toString(){
    return ""+getDistance();
  }
}
