import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class Client{


	public static String voisinGauche(Labyrinthe laby , int x ,int y){
		return laby.getXY(x-1,y).toString();
	}



	public static String chemin (){

		String msg="";
		int test = (int)(Math.random()*4);

		if(test == 0){
			msg="E";
		}
		else if (test == 1) {
			msg="O";
		}
		else if (test == 2) {
			msg="N";
		}
		else if (test == 3) {
			msg="S";
		}


		return msg;
	}


	public static boolean culDeSac (Labyrinthe laby , int x , int y , String direction ){

		boolean culDeSac = false;

		if ( direction == "N"){

			if (laby.getXY(x-1,y).getType()==Case.DUNE && laby.getXY(x+1,y).getType()==Case.DUNE && laby.getXY(x,y-1).getType()==Case.DUNE){
				culDeSac = true;
			}
		}
		else if ( direction == "S"){

			if (laby.getXY(x-1,y).getType()==Case.DUNE && laby.getXY(x+1,y).getType()==Case.DUNE && laby.getXY(x,y+1).getType()==Case.DUNE){
				culDeSac = true;
			}
		}
		else if ( direction == "O"){

			if (laby.getXY(x+1,y).getType()==Case.DUNE && laby.getXY(x,y-1).getType()==Case.DUNE && laby.getXY(x,y+1).getType()==Case.DUNE){
				culDeSac = true;
			}
		}
		else if ( direction == "E"){

			if (laby.getXY(x-1,y).getType()==Case.DUNE && laby.getXY(x,y-1).getType()==Case.DUNE && laby.getXY(x,y+1).getType()==Case.DUNE){
				culDeSac = true;
			}
		}





		return culDeSac;
	}

	public static String chemin2 (Labyrinthe laby, int x, int y){


		String msg ="";
		String[] chemin = new String[50];
		List l = new LinkedList();

		int postionTableau = 0;
		boolean deplaceNord;
		boolean deplaceSud;
		boolean deplaceOuest;
		boolean deplaceEst;
		String deplacement ="";
		String positionJoueur = "";
		boolean fin = false;


		boolean trouveMoule = false;



		while (trouveMoule) {

			postionTableau = 0;
			while (postionTableau < 50 && !culDeSac(laby, x,y, positionJoueur) && !fin) {


				deplaceNord = laby.marchable(x, y - 1);
				deplaceSud = laby.marchable(x, y + 1);
				deplaceOuest = laby.marchable(x - 1, y);
				deplaceEst = laby.marchable(x + 1, y);

				if (deplaceNord) {
					l.add("N");
				}
				if (deplaceSud) {
					l.add("S");
				}
				if (deplaceOuest) {
					l.add("O");
				}
				if (deplaceEst) {
					l.add("E");
				}

				int nbAleatoire = (int) (Math.random() * l.size());

				deplacement = String.valueOf(l.get(nbAleatoire));
				chemin[postionTableau] = deplacement;

				if (deplacement == "N") {
					x++;
					positionJoueur = "S";
				} else if (deplacement == "S") {
					x--;
					positionJoueur = "N";
				} else if (deplacement == "O") {
					y--;
					positionJoueur = "E";
				} else {
					y++;
					positionJoueur = "O";
				}

				if(laby.getXY(x,y).getType()==Case.MOULE){
					trouveMoule = true;
					fin = true;
				}

				postionTableau++;
			}


		}
		msg = chemin[0];
		return msg;
	}


    public static void main(String[] args){
	if(args.length!=3){
	    System.out.println("Il faut 3 arguments : l'adresse ip du serveur, le port et le nom d'équipe.");
	    System.exit(0);
	}
	Random rand=new Random();
		
	try{
	    Socket s = new Socket(args[0],Integer.parseInt(args[1]));
	    boolean fin=false;
	    
	    // ecriture
	    OutputStream os  = s.getOutputStream();
	    PrintWriter pw = new PrintWriter(os);
	    //lecture
	    InputStream is = s.getInputStream();
	    BufferedReader bf = new BufferedReader(
						   new InputStreamReader(is));

	    pw.println(args[2]);
	    pw.flush();
	    
	    String numJoueur = bf.readLine();

	    System.out.println("Numero de joueur : "+numJoueur);
			
	    while(!fin){
		String msg = bf.readLine();
		
		System.out.println("Message recu : "+msg);
		System.out.println();
		fin=msg.equals("FIN");

		if(!fin){

		    /*-----------------------------------------------------------------------*/
		    
		    /*TODO - mettre votre stratégie en place ici*/
		    /*Quelques lignes de code pour vous aider*/

		    //Creation du labyrinthe en fonction des informations recues
		    //Bande de veinards, c'est déjà écrit ! Par contre la doc de cette classe n'est pas complète.
		    //Faut pas trop en demander non plus !
		    Labyrinthe laby = new Labyrinthe(msg);

		    System.out.println(msg);

		    System.out.println("voici le get XY : " + laby.getXY(1,1));

		    int x = laby.getJoueur(Integer.parseInt(numJoueur)).getPosX();
			int y  = laby.getJoueur(Integer.parseInt(numJoueur)).getPosY();


			//Informations sur le joueur
		    System.out.println("Je me trouve en : ("+laby.getJoueur(Integer.parseInt(numJoueur)).getPosX()+","+laby.getJoueur(Integer.parseInt(numJoueur)).getPosY()+")");
		    ArrayList<Integer> infosMoule = new ArrayList<Integer>();
		    //Parcours du plateau pour trouver toutes les moules et leur valeur
		    for(int j=0;j<laby.getTailleY();j++)
			for(int i=0;i<laby.getTailleX();i++)
			    if(laby.getXY(i,j).getType()==Case.MOULE){
				infosMoule.add(i);infosMoule.add(j);infosMoule.add(laby.getXY(i,j).getPointRapporte());
			    }

		    //Affichage des informations sur les moules du plateau
		    for(int i=0;i<infosMoule.size()/3;i++)
			System.out.println("Moule en ("+infosMoule.get(i*3)+","+infosMoule.get(i*3+1)+") pour "+infosMoule.get(i*3+2)+" points");

		    //Je prépare le message suivant à envoyer au serveur : je vais me déplacer vers l'Est.
		    //Pourquoi ? Aucune idée mais faut bien envoyer quelque chose au serveur alors pourquoi pas ?
		    //A vous de faire mieux ici :-)





				msg = chemin2(laby, x,y);







		    /*-----------------------------------------------------------------------*/
		    
		    //Envoi du message au serveur.
		    pw.println(msg);
		    pw.flush();
		}
		
	    }
	    s.close();
	    
	}catch(Exception e){
	    System.err.println(e);
	    e.printStackTrace();
	}
    }



	
}
