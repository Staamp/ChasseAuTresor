import java.util.*;
import java.util.PriorityQueue;
import java.util.ArrayList;
/**
* La classe GoJeu integre la methode main pour pouvoir lancer le jeu.
*/
public class GoJeu {
  /**
  * Methode main qui lance la partie.
  */
  public static void main (String [] args){
    //SAISIE DES REGLAGES DU JEU
    int hauteur;
    int largeur;
    int nbrChasseur;
    int nbrPierre;
    int nbMax;
    Scanner sc=new Scanner(System.in);		
    fenetre f = new fenetre(-1, -1, false);
    do {  
      f.initGame();
      f.setRejouer(false);
      Pierre.nbrPierrePlace = 0;
      do {
        try{
          Thread.sleep(500);
        } 
        catch(InterruptedException ex){
          Thread.currentThread().interrupt();
        }
        hauteur = f.getHauteur();
        largeur = f.getLargeur();
        nbrChasseur = f.getNbChasseur();
        nbrPierre = f.getNbPierre();
        nbMax = (hauteur * largeur) / 2; //|| nbMax > nbrChasseur || nbMax > nbrPierre
      } while(hauteur < 1 || largeur < 1 || nbrChasseur < 1 || nbrPierre < 1);
      
      //Verification utile au developpement
      System.out.println("hauteur : "+hauteur);
      System.out.println("largeur : "+largeur);
      System.out.println("nbrChasseur : "+nbrChasseur);
      System.out.println("nbrPierre : "+nbrPierre);
      
      //creation de la grille
      Grille g=new Grille(nbrChasseur,nbrPierre,hauteur,largeur, false);
      //Initialisation par cases vides
      for(int i=0;i<largeur;i++){
        for(int j=0;j<hauteur;j++){
          g.tab[i][j]=new Vide(i,j,g);
        }
      }
      //creation des chasseurs
      PriorityQueue<Chasseur> chasseurs=new PriorityQueue(nbrChasseur);
      int compteur_chasseur=0;
      while(compteur_chasseur<nbrChasseur){
        int i;
        int j;
        do{
          i=(int)(Math.random()*g.getLargeur());
          j=(int)(Math.random()*g.getHauteur());
        } while(g.tab[i][j].getClass().getName()=="Chasseur"||g.estTresor(i,j));
        Chasseur c=new Chasseur(i,j,g);
        g.tab[i][j]=c;
        chasseurs.add(c);
        compteur_chasseur++;
      }
      
      //BOUCLE DE JEU
      System.out.println(g);
      f.initFondCase(hauteur, largeur, -1, -1, g);
      
      for(;;){
        //POSAGE DE PIERRES
        int x = -1;
        int y = -1;
        //initilisation
        f.setCaseCliqueRow(-1);
        f.setCaseCliqueColumn(-1);
        if(Pierre.nbrPierrePlace<nbrPierre){
          System.out.println("Cliquez sur une case pour placer une pierre.");
          do{
            //System.out.println(f.getPasserSonTour());
            try{
              Thread.sleep(500);
            } 
            catch(InterruptedException ex){
              Thread.currentThread().interrupt();
            }
            if (f.getPasserSonTour()) {
              break;
            }
            x = f.getCaseCliqueRow();
            y = f.getCaseCliqueColumn();
          } while((f.getCaseCliqueRow() == -1 || f.getCaseCliqueColumn() == -1) || g.tab[x][y].getClass().getName()=="Chasseur");
          
          
          /*if(estTresor(x, y)) {
            stopG
          }*/
          
          
          /*if (g.tab[x][y].getClass().getName()=="Pierre") {
            System.out.println("Vous avez clique sur une pierre");
            g.tab[x][y] = new Vide(x, y, g);
            if (g.tab[x][y].getClass().getName()=="Vide") {
              System.out.println("Reussi");
              Pierre.nbrPierrePlace--;
              do{
                System.out.println(f.getPasserSonTour());
                try{
                  Thread.sleep(500);
                } 
                catch(InterruptedException ex){
                  Thread.currentThread().interrupt();
                }
                if (f.getPasserSonTour()) {
                  f.setPasserSonTour(false);
                  break;
                }
                x = f.getCaseCliqueRow();
                y = f.getCaseCliqueColumn();
              } while((f.getCaseCliqueRow() == -1 || f.getCaseCliqueColumn() == -1) || g.tab[x][y].getClass().getName()=="Chasseur" || g.tab[x][y].getClass().getName()=="Pierre");
            }
          }*/
          
          if (f.getPasserSonTour()) {
            f.setPasserSonTour(false);
          } else {
            g.tab[x][y]=new Pierre(x,y,g);
          }
        }else{
          System.out.println("Vous n'avez plus de pierre");
        }
        
        //recalcul des distances
        g.calculDistance();
        System.out.println(g);
        //DEPLACEMENT DES CHASSEURS
        Iterator<Chasseur> it=chasseurs.iterator();
        int nbrChasseurBloque=0;
        while(it.hasNext()){
          Chasseur c=it.next();
          if(c.getDistance()<hauteur*largeur){
            g.deplacementChasseur(c.getPosition().getX(),c.getPosition().getY());
            g.calculDistance();
          }else{
            nbrChasseurBloque++;
          }  
          //On marque une seconde d'attente entre le deplacement des chasseurs
          try{
            Thread.sleep(1000);
          } 
          catch(InterruptedException ex){
            Thread.currentThread().interrupt();
          }
          System.out.println(g);
          f.setFondCase(largeur, hauteur, g);
        }          
        if (g.getStopGame()) {
          System.out.println("Le chasseur a trouve le tresor");
          f.affichageGagne(false);
          break;
        }
        if(nbrChasseurBloque==nbrChasseur){
          System.out.println("Bravo, vous avez gagne !");
          f.affichageGagne(true);
          break;
        }
      }
      boolean rejouer = false;
      //On marque une seconde d'attente
      do {
        try{
          Thread.sleep(1000);
        } 
        catch(InterruptedException ex){
          Thread.currentThread().interrupt();
        }
        rejouer = f.getRejouer();
      } while(!rejouer);
    } while(f.getRejouer());
  }
}
