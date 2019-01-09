public class Position {

    public int x;
    public int y;
    public String direction;
    boolean valide;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
        this.direction = "";
        valide = false;

    }

    public Position(int x, int y, String direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        valide = false;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        String texte = "";
        texte = texte +("Voici la position");
        texte = texte + (" (" + this.x +"," + this.y + ") " + " -> direction : " + this.direction);
        return texte;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public boolean getValide() {
        return this.valide;
    }
}
