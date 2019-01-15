import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


// Arthur REMOND
// Océan LESECQ
// TPB


public class Client{


	public static List tabPosX = new LinkedList();
	public static List tabPosY = new LinkedList();


	public static void transformLaby (Labyrinthe laby , int x, int y) {

		int tailleX = laby.getTailleX() - 1; // 24
		int tailleY = laby.getTailleY() - 1;
		int nbDune;

		boolean duneNord = false;
		boolean duneSud = false;
		boolean duneOuest = false;
		boolean duneEst = false;
		boolean test;

		int compteur = 0;
		boolean continuer = true;


		while ( continuer && compteur < 500 ) {

			continuer = false;
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
							continuer = true;

						}

					}


					// Anti mur en bas
					//
					// XOO
					// XOO
					//  X
					if (!test && laby.getXY(i, j + 1).getType() == Case.DUNE && laby.getXY(i - 1, j).getType() == Case.DUNE && laby.getXY(i, j).getType() != Case.DUNE  && laby.getXY(i, j).getType() != Case.MOULE) {


						if (laby.getXY(i, j - 1).getType() != Case.DUNE && laby.getXY(i + 1, j).getType() != Case.DUNE && laby.getXY(i + 1, j - 1).getType() != Case.DUNE) {

							if (laby.getXY(i, j - 1).getType() != Case.MOULE && laby.getXY(i + 1, j).getType() != Case.MOULE && laby.getXY(i + 1, j - 1).getType() != Case.MOULE) {
								laby.setXY(i, j);
								continuer = true;
							}
						}

					}


					// Anti mur en Haut
					//
					//  X
					// XOO
					//  OO
					if (!test && laby.getXY(i, j - 1).getType() == Case.DUNE && laby.getXY(i - 1, j).getType() == Case.DUNE && laby.getXY(i, j).getType() != Case.DUNE && laby.getXY(i, j).getType() != Case.MOULE) {


						if (laby.getXY(i, j + 1).getType() != Case.DUNE && laby.getXY(i + 1, j).getType() != Case.DUNE && laby.getXY(i + 1, j + 1).getType() != Case.DUNE) {

							if (laby.getXY(i, j + 1).getType() != Case.MOULE && laby.getXY(i + 1, j).getType() != Case.MOULE && laby.getXY(i + 1, j + 1).getType() != Case.MOULE) {
								laby.setXY(i, j);
								continuer = true;
							}
						}

					}


				}
			}

			compteur ++;
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


		/// Verification gauche gauche gauche (x-3)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && mouleVoisine(laby, x-3, y) ){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && mouleVoisine(laby, x+3, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && mouleVoisine(laby, x, y-3)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && mouleVoisine(laby, x, y+3)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche (x-4)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && mouleVoisine(laby, x-4, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && mouleVoisine(laby, x+4, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && mouleVoisine(laby, x, y-4)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && mouleVoisine(laby, x, y+4)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-5)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && mouleVoisine(laby, x-5, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && mouleVoisine(laby, x+5, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && mouleVoisine(laby, x, y-5)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && mouleVoisine(laby, x, y+5)){
			msg = "S";
		}




		/// Verification gauche gauche gauche gauche gauche (x-6)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && mouleVoisine(laby, x-6, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && mouleVoisine(laby, x+6, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && mouleVoisine(laby, x, y-6)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && mouleVoisine(laby, x, y+6)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-7)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && mouleVoisine(laby, x-7, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && mouleVoisine(laby, x+7, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && mouleVoisine(laby, x, y-7)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && mouleVoisine(laby, x, y+7)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-8)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && mouleVoisine(laby, x-8, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && mouleVoisine(laby, x+8, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && mouleVoisine(laby, x, y-8)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && mouleVoisine(laby, x, y+8)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-9)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && mouleVoisine(laby, x-9, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && mouleVoisine(laby, x+9, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && mouleVoisine(laby, x, y-9)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && mouleVoisine(laby, x, y+9)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-10)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && laby.getXY( x-10, y).getType() != Case.DUNE && mouleVoisine(laby, x-10, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && laby.getXY( x+10, y).getType() != Case.DUNE && mouleVoisine(laby, x+10, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && laby.getXY( x, y-10).getType() != Case.DUNE && mouleVoisine(laby, x, y-10)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && laby.getXY( x, y+10).getType() != Case.DUNE && mouleVoisine(laby, x, y+10)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-11)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && laby.getXY( x-10, y).getType() != Case.DUNE && laby.getXY( x-11, y).getType() != Case.DUNE && mouleVoisine(laby, x-11, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && laby.getXY( x+10, y).getType() != Case.DUNE && laby.getXY( x+11, y).getType() != Case.DUNE && mouleVoisine(laby, x+11, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && laby.getXY( x, y-10).getType() != Case.DUNE && laby.getXY( x, y-11).getType() != Case.DUNE && mouleVoisine(laby, x, y-11)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && laby.getXY( x, y+10).getType() != Case.DUNE && laby.getXY( x, y+11).getType() != Case.DUNE && mouleVoisine(laby, x, y+11)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-12)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && laby.getXY( x-10, y).getType() != Case.DUNE && laby.getXY( x-11, y).getType() != Case.DUNE && laby.getXY( x-12, y).getType() != Case.DUNE && mouleVoisine(laby, x-12, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && laby.getXY( x+10, y).getType() != Case.DUNE && laby.getXY( x+11, y).getType() != Case.DUNE && laby.getXY( x+12, y).getType() != Case.DUNE && mouleVoisine(laby, x+12, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && laby.getXY( x, y-10).getType() != Case.DUNE && laby.getXY( x, y-11).getType() != Case.DUNE && laby.getXY( x, y-12).getType() != Case.DUNE && mouleVoisine(laby, x, y-12)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && laby.getXY( x, y+10).getType() != Case.DUNE && laby.getXY( x, y+11).getType() != Case.DUNE && laby.getXY( x, y+12).getType() != Case.DUNE && mouleVoisine(laby, x, y+12)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-13)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && laby.getXY( x-10, y).getType() != Case.DUNE && laby.getXY( x-11, y).getType() != Case.DUNE && laby.getXY( x-12, y).getType() != Case.DUNE && laby.getXY( x-13, y).getType() != Case.DUNE && mouleVoisine(laby, x-13, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && laby.getXY( x+10, y).getType() != Case.DUNE && laby.getXY( x+11, y).getType() != Case.DUNE && laby.getXY( x+12, y).getType() != Case.DUNE && laby.getXY( x+13, y).getType() != Case.DUNE && mouleVoisine(laby, x+13, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && laby.getXY( x, y-10).getType() != Case.DUNE && laby.getXY( x, y-11).getType() != Case.DUNE && laby.getXY( x, y-12).getType() != Case.DUNE && laby.getXY( x, y-13).getType() != Case.DUNE && mouleVoisine(laby, x, y-13)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && laby.getXY( x, y+10).getType() != Case.DUNE && laby.getXY( x, y+11).getType() != Case.DUNE && laby.getXY( x, y+12).getType() != Case.DUNE && laby.getXY( x, y+13).getType() != Case.DUNE && mouleVoisine(laby, x, y+13)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-14)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && laby.getXY( x-10, y).getType() != Case.DUNE && laby.getXY( x-11, y).getType() != Case.DUNE && laby.getXY( x-12, y).getType() != Case.DUNE && laby.getXY( x-13, y).getType() != Case.DUNE && laby.getXY( x-14, y).getType() != Case.DUNE && mouleVoisine(laby, x-14, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && laby.getXY( x+10, y).getType() != Case.DUNE && laby.getXY( x+11, y).getType() != Case.DUNE && laby.getXY( x+12, y).getType() != Case.DUNE && laby.getXY( x+13, y).getType() != Case.DUNE && laby.getXY( x+14, y).getType() != Case.DUNE && mouleVoisine(laby, x+14, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && laby.getXY( x, y-10).getType() != Case.DUNE && laby.getXY( x, y-11).getType() != Case.DUNE && laby.getXY( x, y-12).getType() != Case.DUNE && laby.getXY( x, y-13).getType() != Case.DUNE && laby.getXY( x, y-14).getType() != Case.DUNE && mouleVoisine(laby, x, y-14)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && laby.getXY( x, y+10).getType() != Case.DUNE && laby.getXY( x, y+11).getType() != Case.DUNE && laby.getXY( x, y+12).getType() != Case.DUNE && laby.getXY( x, y+13).getType() != Case.DUNE && laby.getXY( x, y+14).getType() != Case.DUNE && mouleVoisine(laby, x, y+14)){
			msg = "S";
		}

		/// Verification gauche gauche gauche gauche gauche (x-15)
		else if (laby.getXY( x-1, y).getType() != Case.DUNE && laby.getXY( x-2, y).getType() != Case.DUNE && laby.getXY( x-3, y).getType() != Case.DUNE && laby.getXY( x-4, y).getType() != Case.DUNE && laby.getXY( x-5, y).getType() != Case.DUNE && laby.getXY( x-6, y).getType() != Case.DUNE && laby.getXY( x-7, y).getType() != Case.DUNE && laby.getXY( x-8, y).getType() != Case.DUNE && laby.getXY( x-9, y).getType() != Case.DUNE && laby.getXY( x-10, y).getType() != Case.DUNE && laby.getXY( x-11, y).getType() != Case.DUNE && laby.getXY( x-12, y).getType() != Case.DUNE && laby.getXY( x-13, y).getType() != Case.DUNE && laby.getXY( x-14, y).getType() != Case.DUNE && laby.getXY( x-15, y).getType() != Case.DUNE && mouleVoisine(laby, x-15, y)){
			msg = "O";
		}
		else if (laby.getXY( x+1, y).getType() != Case.DUNE && laby.getXY( x+2, y).getType() != Case.DUNE && laby.getXY( x+3, y).getType() != Case.DUNE && laby.getXY( x+4, y).getType() != Case.DUNE && laby.getXY( x+5, y).getType() != Case.DUNE && laby.getXY( x+6, y).getType() != Case.DUNE && laby.getXY( x+7, y).getType() != Case.DUNE && laby.getXY( x+8, y).getType() != Case.DUNE && laby.getXY( x+9, y).getType() != Case.DUNE && laby.getXY( x+10, y).getType() != Case.DUNE && laby.getXY( x+11, y).getType() != Case.DUNE && laby.getXY( x+12, y).getType() != Case.DUNE && laby.getXY( x+13, y).getType() != Case.DUNE && laby.getXY( x+14, y).getType() != Case.DUNE && laby.getXY( x+15, y).getType() != Case.DUNE && mouleVoisine(laby, x+15, y)){
			msg = "E";
		}
		else if (laby.getXY( x, y-1).getType() != Case.DUNE && laby.getXY( x, y-2).getType() != Case.DUNE && laby.getXY( x, y-3).getType() != Case.DUNE && laby.getXY( x, y-4).getType() != Case.DUNE && laby.getXY( x, y-5).getType() != Case.DUNE && laby.getXY( x, y-6).getType() != Case.DUNE && laby.getXY( x, y-7).getType() != Case.DUNE && laby.getXY( x, y-8).getType() != Case.DUNE && laby.getXY( x, y-9).getType() != Case.DUNE && laby.getXY( x, y-10).getType() != Case.DUNE && laby.getXY( x, y-11).getType() != Case.DUNE && laby.getXY( x, y-12).getType() != Case.DUNE && laby.getXY( x, y-13).getType() != Case.DUNE && laby.getXY( x, y-14).getType() != Case.DUNE && laby.getXY( x, y-15).getType() != Case.DUNE && mouleVoisine(laby, x, y-15)){
			msg = "N";
		}
		else if (laby.getXY( x, y+1).getType() != Case.DUNE && laby.getXY( x, y+2).getType() != Case.DUNE && laby.getXY( x, y+3).getType() != Case.DUNE && laby.getXY( x, y+4).getType() != Case.DUNE && laby.getXY( x, y+5).getType() != Case.DUNE && laby.getXY( x, y+6).getType() != Case.DUNE && laby.getXY( x, y+7).getType() != Case.DUNE && laby.getXY( x, y+8).getType() != Case.DUNE && laby.getXY( x, y+9).getType() != Case.DUNE && laby.getXY( x, y+10).getType() != Case.DUNE && laby.getXY( x, y+11).getType() != Case.DUNE && laby.getXY( x, y+12).getType() != Case.DUNE && laby.getXY( x, y+13).getType() != Case.DUNE && laby.getXY( x, y+14).getType() != Case.DUNE && laby.getXY( x, y+15).getType() != Case.DUNE && mouleVoisine(laby, x, y+15)){
			msg = "S";
		}




		return msg;

	}

	public static String okGoogle (int x, int y, Labyrinthe laby){

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
			/*
				- okGoogle est une qui retient pour les positions pour essayer d'éviter d'aller deux fois au meme endroit
				- transformLaby permet d'enlever tout les culs de sac d'un laby mais égalment d'enlever certain coin
				- rechercheMoule permet de rechercher les moules autour de sois (efficace quand il y a très peu de mur

			*/
		    Labyrinthe laby = new Labyrinthe(msg);

			int x = laby.getJoueur(Integer.parseInt(numJoueur)).getPosX();
			int y  = laby.getJoueur(Integer.parseInt(numJoueur)).getPosY();





			transformLaby(laby, x, y);
			msg = okGoogle( x,y, laby);

















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


/*

if (bieres){
					String newMsg = "";
					msg = (chemin3( x,y, laby));

					if (msg == "S"){
						newMsg = "F-" + msg + "-" ;
						msg = (chemin3( x+1,y, laby));
						newMsg = newMsg + msg + "-" ;

						if (msg == "S"){

							msg = (chemin3( x+1,y, laby));
							newMsg += msg ;


						}
						else if (msg == "N"){
							msg = (chemin3( x-1,y, laby));
							newMsg += msg ;
						}
						else if (msg == "O"){
							msg = (chemin3( x,y-1, laby));
							newMsg += msg ;
						}
						else if (msg == "E"){
							msg = (chemin3( x,y+1, laby));
							newMsg += msg ;
						}


					}
					else if (msg == "N"){
						newMsg = "F-" + msg + "-" ;
						msg = (chemin3( x-1,y, laby));
						newMsg = newMsg + msg + "-" ;

						if (msg == "S"){

							msg = (chemin3( x+1,y, laby));
							newMsg += msg ;


						}
						else if (msg == "N"){
							msg = (chemin3( x-1,y, laby));
							newMsg += msg ;
						}
						else if (msg == "O"){
							msg = (chemin3( x,y-1, laby));
							newMsg += msg ;
						}
						else if (msg == "E"){
							msg = (chemin3( x,y+1, laby));
							newMsg += msg ;
						}

					}
					else if (msg == "O"){
						newMsg = "F-" + msg + "-" ;
						msg = (chemin3( x,y-1, laby));
						newMsg = newMsg + msg + "-" ;

						if (msg == "S"){

							msg = (chemin3( x+1,y, laby));
							newMsg += msg ;


						}
						else if (msg == "N"){
							msg = (chemin3( x-1,y, laby));
							newMsg += msg ;
						}
						else if (msg == "O"){
							msg = (chemin3( x,y-1, laby));
							newMsg += msg ;
						}
						else if (msg == "E"){
							msg = (chemin3( x,y+1, laby));
							newMsg += msg ;
						}
					}
					else if (msg == "E"){
						newMsg = "F-" + msg + "-" ;
						msg = (chemin3( x,y+1, laby));
						newMsg = newMsg + msg + "-" ;

						if (msg == "S"){

							msg = (chemin3( x+1,y, laby));
							newMsg += msg ;


						}
						else if (msg == "N"){
							msg = (chemin3( x-1,y, laby));
							newMsg += msg ;
						}
						else if (msg == "O"){
							msg = (chemin3( x,y-1, laby));
							newMsg += msg ;
						}
						else if (msg == "E"){
							msg = (chemin3( x,y+1, laby));
							newMsg += msg ;
						}
					}

					msg = newMsg;
					bieres = false;

					System.out.println("\n*****\n*****\nJe joue 3 fois !!!");
				}else{
					));
					if (biere(laby,msg,x,y)){
						bieres = true;
					}
				}

 */