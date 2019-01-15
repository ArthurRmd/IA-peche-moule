import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class Client{

	public static List tabPosX = new LinkedList();
	public static List tabPosY = new LinkedList();
	public static Position anciennePosition = new Position(0,0);

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

		test +=1;
		return msg;
	}


	public static String cheminAleatoire( Labyrinthe laby, String ancienneDirection, int x, int y ){

		Position[] position = new Position[4];
		position[0] = new Position(0,0);
		position[1] = new Position(0,0);
		position[2] = new Position(0,0);
		position[3] = new Position(0,0);
		int positionIndex = 0;


		String msg="";

		boolean deplaceNord;
		boolean deplaceSud;
		boolean deplaceOuest;
		boolean deplaceEst;



		Position Nord = new Position(x, y - 1, "N");
		Position Sud = new Position(x, y + 1, "S");
		Position Ouest = new Position(x - 1, y,  "O");
		Position Est = new Position(x + 1, y, "E");


		deplaceNord = laby.marchable(x, y - 1);
		deplaceSud = laby.marchable(x, y + 1);
		deplaceOuest = laby.marchable(x - 1, y);
		deplaceEst = laby.marchable(x + 1, y);


		if (deplaceNord && ancienneDirection !="N") {
			position[positionIndex].setX(Nord.getX());
			position[positionIndex].setY(Nord.getY());
			position[positionIndex].setDirection("N");
			position[positionIndex].setValide(true);
			positionIndex++;
		}
		if (deplaceSud && ancienneDirection !="S") {
			position[positionIndex].setX(Sud.getX());
			position[positionIndex].setY(Sud.getY());
			position[positionIndex].setDirection("S");
			position[positionIndex].setValide(true);
			positionIndex++;
		}
		if (deplaceOuest && ancienneDirection !="O") {
			position[positionIndex].setX(Ouest.getX());
			position[positionIndex].setY(Ouest.getY());
			position[positionIndex].setDirection("O");
			position[positionIndex].setValide(true);
			positionIndex++;
		}
		if (deplaceEst && ancienneDirection !="E") {
			position[positionIndex].setX(Est.getX());
			position[positionIndex].setY(Est.getY());
			position[positionIndex].setDirection("E");
			position[positionIndex].setValide(true);
			positionIndex++;
		}



		int nombreAléatoire = (int)(Math.random()* positionIndex);

		msg = position[nombreAléatoire].getDirection();
		return msg;

	}

	public static  String cheminPlusCourt(Labyrinthe laby , int x, int y, Position anciennePosition ){

		int placeMoule = -1;
		String direction = "";
		String directionDepart ="";
		String msg="N";
		boolean trouveMoule;

		int compteurPas;

		int xx;
		int yy;
		int j;



		for (int i=0; i<10000; i++){

			j=0;
			trouveMoule = false;
			xx = x;
			yy = y;
			direction = cheminAleatoire(laby,anciennePosition.getDirection(),xx, yy);
			directionDepart =direction;

			if (direction == "N"){
				yy--;
			}
			else if (direction == "S"){
				yy++;
			}
			else if (direction == "O"){
				xx++;
			}
			else{
				xx--;
			}
			compteurPas = 0;


			while(j<100 && !trouveMoule){

				System.out.println("  -->" + direction);
				if ( laby.getXY(xx,yy).getType()==Case.MOULE){
					trouveMoule = true;

					if (j < placeMoule){
						placeMoule = j;
						msg= directionDepart;
					}

				}
				else {
					direction = cheminAleatoire(laby,anciennePosition.getDirection(),xx, yy);

					if (direction == "N"){
						yy--;
					}
					else if (direction == "S"){
						yy++;
					}
					else if (direction == "O"){
						xx++;
					}
					else{
						xx--;
					}
				}
				j++;
			}



		}



		return msg;


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
		int nombreBoucle= 0;


		boolean trouveMoule = false;



		while (!trouveMoule) {



			postionTableau = 0;
			while (postionTableau < 100 &&  !fin) {
				System.out.println("Ca tourne" + postionTableau);

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

			nombreBoucle++;
		}
		msg = chemin[0];
		return msg;
	}





	public static void transformLaby (Labyrinthe laby , int x, int y) {

		int tailleX = laby.getTailleX() - 1; // 24
		int tailleY = laby.getTailleY() - 1;
		int nbDune;

		boolean duneNord = false;
		boolean duneSud = false;
		boolean duneOuest = false;
		boolean duneEst = false;
		boolean test;

		for (int i = 1; i <= tailleX - 1; i++) {

			for (int j = 1; j <= tailleY - 1; j++) {


				duneNord = false;
				duneSud = false;
				duneOuest = false;
				duneEst = false;


				nbDune = 0;

				if (x == i && y == j) {
					test = true;
				} else {
					test = false;
				}


				if (!test && (laby.getXY(i, j).getType() == Case.SABLE || laby.getXY(i, j).getType() == Case.FRITE || laby.getXY(i, j).getType() == Case.BIERE)) {


					if (laby.getXY(i, j - 1).getType() == Case.DUNE) {
						duneNord = true;
						nbDune++;
					}
					if (laby.getXY(i, j + 1).getType() == Case.DUNE) {
						duneSud = true;
						nbDune++;
					}
					if (laby.getXY(i - 1, j).getType() == Case.DUNE) {
						duneEst = true;
						nbDune++;
					}
					if (laby.getXY(i + 1, j).getType() == Case.DUNE) {
						duneOuest = true;
						nbDune++;
					}


					if (nbDune == 3) {
						laby.setXY(i, j);

					}

				}




				// Anti mur en bas
				//
				// XOO
				// XOO
				//  X
				if ( !test && laby.getXY(i, j + 1).getType() == Case.DUNE && laby.getXY(i - 1, j).getType() == Case.DUNE && laby.getXY(i, j).getType() != Case.DUNE) {


					if (laby.getXY(i, j - 1).getType() != Case.DUNE && laby.getXY(i + 1, j).getType() != Case.DUNE && laby.getXY(i +1, j - 1).getType() != Case.DUNE) {

						if (laby.getXY(i, j - 1).getType() != Case.MOULE && laby.getXY(i + 1, j).getType() != Case.MOULE && laby.getXY(i +1, j - 1).getType() != Case.MOULE) {
							laby.setXY(i, j);
						}
					}

				}


				// Anti mur en Haut
				//
				//  X
				// XOO
				//  OO
				if ( !test && laby.getXY(i, j - 1).getType() == Case.DUNE && laby.getXY(i - 1, j ).getType() == Case.DUNE &&  laby.getXY(i, j).getType() != Case.DUNE) {


					if (laby.getXY(i, j + 1).getType() != Case.DUNE && laby.getXY(i + 1, j).getType() != Case.DUNE && laby.getXY(i +1, j + 1).getType() != Case.DUNE) {

						if (laby.getXY(i, j + 1).getType() != Case.MOULE && laby.getXY(i + 1, j).getType() != Case.MOULE && laby.getXY(i +1, j + 1).getType() != Case.MOULE) {
							laby.setXY(i, j);
						}
					}

				}







			}
		}

	}




	public static  Boolean mouleVoisine(Labyrinthe laby, int x, int y){


		if (laby.getXY(x,y).getType() == Case.MOULE){
			return true;
		}
		if (laby.getXY(x+1,y).getType() == Case.MOULE){
			return true;
		}
		if (laby.getXY(x-1,y).getType() == Case.MOULE){
			return true;
		}
		if (laby.getXY(x,y+1).getType() == Case.MOULE){
			return true;
		}if (laby.getXY(x,y-1).getType() == Case.MOULE){
			return true;
		}

		return false;


	}


	public static  String rechercheMoule(Labyrinthe laby, int x, int y){


		String msg ="PAS";

		boolean deplaceNord;
		boolean deplaceSud;
		boolean deplaceOuest;
		boolean deplaceEst;

		boolean deplaceNordEst;
		boolean deplaceNordOuest;
		boolean deplaceSudEst;
		boolean deplaceSudOuest;

		Position Nord = new Position(x, y - 1, "N");
		Position Sud = new Position(x, y + 1, "S");
		Position Ouest = new Position(x - 1, y,  "O");
		Position Est = new Position(x + 1, y, "E");

		Position NordEst = new Position(x+1, y - 1, "N");
		Position NordOuest = new Position(x-1, y - 1, "N");
		Position SudEst = new Position(x +1, y+1,  "S");
		Position SudOuest = new Position(x - 1, y+1, "S");


		deplaceNord = laby.marchable(x, y - 1);
		deplaceSud = laby.marchable(x, y + 1);
		deplaceOuest = laby.marchable(x - 1, y);
		deplaceEst = laby.marchable(x + 1, y);


		deplaceNordEst = laby.marchable(x+1, y - 1);
		deplaceNordOuest = laby.marchable(x-1, y - 1);
		deplaceSudEst = laby.marchable(x + 1, y+1);
		deplaceSudOuest = laby.marchable(x - 1, y+1);



		if (deplaceNord && laby.getXY( Nord.getX(), Nord.getY()).getType() == Case.MOULE){
			msg = "N";
		}
		else if (deplaceSud && laby.getXY( Sud.getX(), Sud.getY()).getType() == Case.MOULE){
			msg = "S";
		}
		else if (deplaceOuest && laby.getXY( Ouest.getX(), Ouest.getY()).getType() == Case.MOULE){
			msg = "O";
		}
		else if (deplaceEst && laby.getXY( Est.getX(), Est.getY()).getType() == Case.MOULE){
			msg = "E";
		}
		else if (deplaceEst && laby.getXY( Est.getX(), Est.getY()).getType() == Case.MOULE){
			msg = "E";
		}


		//

		else if (deplaceOuest && laby.getXY( x-1, y-1).getType() == Case.MOULE){
			msg = "O";
		}
		else if (deplaceOuest && laby.getXY( x-1, y+1).getType() == Case.MOULE){
			msg = "O";
		}


		else if (deplaceEst && laby.getXY( x+1, y-1).getType() == Case.MOULE){
			msg = "E";
		}
		else if (deplaceEst && laby.getXY( x+1, y+1).getType() == Case.MOULE){
			msg = "E";
		}

		else if (deplaceNord && laby.getXY( x-1, y-1).getType() == Case.MOULE){
			msg = "N";
		}
		else if (deplaceNord && laby.getXY( x+1, y-1).getType() == Case.MOULE){
			msg = "N";
		}

		else if (deplaceSud && laby.getXY( x-1, y+1).getType() == Case.MOULE){
			msg = "S";
		}
		else if (deplaceSud && laby.getXY( x+1, y+1).getType() == Case.MOULE){
			msg = "S";
		}

		//

		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && mouleVoisine(laby, x-2, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && mouleVoisine(laby, x+2, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && mouleVoisine(laby, x, y-2)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && mouleVoisine(laby, x, y+2)){
			msg = "S";
		}









		return "PAS";
		//return msg;

	}


	public static String chemin3 (int x, int y, Labyrinthe laby){

		List l = new LinkedList();
		Position[] position = new Position[4];
		position[0] = new Position(0,0);
		position[1] = new Position(0,0);
		position[2] = new Position(0,0);
		position[3] = new Position(0,0);
		int positionIndex = 0;

		int i;
		int j;

		String msg="";

		boolean deplaceNord;
		boolean deplaceSud;
		boolean deplaceOuest;
		boolean deplaceEst;

		int xx;
		int yy;

		Position Nord = new Position(x, y - 1, "N");
		Position Sud = new Position(x, y + 1, "S");
		Position Ouest = new Position(x - 1, y,  "O");
		Position Est = new Position(x + 1, y, "E");


		deplaceNord = laby.marchable(x, y - 1);
		deplaceSud = laby.marchable(x, y + 1);
		deplaceOuest = laby.marchable(x - 1, y);
		deplaceEst = laby.marchable(x + 1, y);



		String deplacement = "";
		if (deplaceNord) {
			l.add("N");
			position[positionIndex].setX(Nord.getX());
			position[positionIndex].setY(Nord.getY());
			position[positionIndex].setDirection("N");
			position[positionIndex].setValide(true);
			positionIndex++;
		}
		if (deplaceSud) {
			l.add("S");
			position[positionIndex].setX(Sud.getX());
			position[positionIndex].setY(Sud.getY());
			position[positionIndex].setDirection("S");
			position[positionIndex].setValide(true);
			positionIndex++;
		}
		if (deplaceOuest) {
			l.add("O");
			position[positionIndex].setX(Ouest.getX());
			position[positionIndex].setY(Ouest.getY());
			position[positionIndex].setDirection("O");
			position[positionIndex].setValide(true);
			positionIndex++;
		}
		if (deplaceEst) {
			l.add("E");
			position[positionIndex].setX(Est.getX());
			position[positionIndex].setY(Est.getY());
			position[positionIndex].setDirection("E");
			position[positionIndex].setValide(true);
			positionIndex++;
		}



		System.out.println("Voici l'historique : ");
		for ( i=0; i<tabPosX.size(); i++){

			System.out.print(" ("+tabPosX.get(i)+",");
			System.out.print(tabPosY.get(i)+") ");

		}
		System.out.println("\nLa taile de tabPosX est : " + tabPosX.size());





		int positionIndex2 = positionIndex;


		if (positionIndex > 1 &&  (tabPosX.size() !=0) ){

			i=(tabPosX.size() -1 );
			while (i >= 0 && positionIndex2>1 ) {
				xx = (int) tabPosX.get(i);
				yy = (int) tabPosY.get(i);

				j=0;
				while (j < positionIndex){

					if (position[j].getX() == xx && position[j].getY()==yy){
						position[j].setX(0);
						position[j].setY(0);
						position[j].setValide(false);
						position[j].setDirection("");
						positionIndex2--;
					}


					j++;
				}

				i--;

			}

		}




		System.out.println("\n Les 4 positions sont : ");
		System.out.println(position[0].toString());
		System.out.println(position[1].toString());
		System.out.println(position[2].toString());
		System.out.println(position[3].toString());




		int nbAleatoire = (int) (Math.random() * l.size());

		msg = String.valueOf(l.get(nbAleatoire));



		if (position[0].getValide()){
			msg= position[0].getDirection();
		}
		else if (position[1].getValide()){
			msg= position[1].getDirection();
		}
		else if (position[2].getValide()){
			msg= position[2].getDirection();
		}
		else {
			msg= position[3].getDirection();
		}




		// Code si moule à cotès


		String temp= rechercheMoule(laby,x,y);
		if(temp != "PAS"){
			msg = temp;
		}





		if (msg=="N"){
			tabPosX.add(Nord.getX());
			tabPosY.add(Nord.getY());
		}
		else if (msg=="S"){
			tabPosX.add(Sud.getX());
			tabPosY.add(Sud.getY());
		}
		else if (msg=="O"){
			tabPosX.add(Ouest.getX());
			tabPosY.add(Ouest.getY());
		}
		else {
			tabPosX.add(Est.getX());
			tabPosY.add(Est.getY());
		}



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

			int x = laby.getJoueur(Integer.parseInt(numJoueur)).getPosX();
			int y  = laby.getJoueur(Integer.parseInt(numJoueur)).getPosY();



			for (int i=0 ; i<100; i++) {
				transformLaby(laby, x, y);
			}

			msg = chemin3( x,y, laby);



			/*
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

*/


				//msg = chemin();









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
