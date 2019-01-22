/**
* La classe Vide etend de la classe Case 
*/
public class Vide extends Case{
  
  /**
  * Constructeur d'une case vide.
  * @param int x : Coordonnee en abscisse de la case.
  * @param int y : Coordonnee en ordonnee de la case.
  * @param Grille grille : Grille ou sera posee le chasseur.
  */
  public Vide(int x,int y,Grille g){
    super(x,y,g);
  }

  /**
  * Methode toString qui retourne un caractere pour l'affichage.
  * @return String : retourne la distance au tresor pour une case vide.
  */
  public String toString(){
    return getDistance()+"";
  }
}
