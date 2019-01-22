/**
* La classe Chasseur etend de la classe Case 
*/
public class Chasseur extends Case{
  
  /**
  * Constructeur d'une case chasseur.
  * @param int x : Coordonnee en abscisse de la case.
  * @param int y : Coordonnee en ordonnee de la case.
  * @param Grille grille : Grille ou sera posee le chasseur.
  */
  public Chasseur(int x,int y,Grille grille){
    super(x,y,grille);
  }

  /**
  * Methode toString qui retourne un caractere pour l'affichage.
  * @return String : caractere qui identifie un chasseur.
  */
  public String toString(){
    return "X";
  }
}
