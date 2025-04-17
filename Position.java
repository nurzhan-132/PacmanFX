public class Position {

    private int x;
    private int y;

    public Position(int x, int y){  //Constructor 
        this.x = x; this.y = y;
    }

    public int getX(){ //returns x
        return this.x;
    }

    public void setX(int x){ //sets x
        this.x = x;
    }

    public int getY(){ //returns y
        return this.y;
    }

    public void setY(int y){ //sets y
        this.y = y;
    }

    public boolean equals(Position position) { //returns whether they equals or not 
        return (this.getX() == position.getX() && this.getY() == position.getY());
    }
}
