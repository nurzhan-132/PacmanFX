import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map extends Pane{  

    //all data fields are private
    private int UNIT;
    private int size;
    private int[][] map;   
    private Position start;
    private Scanner scanner;

    //Constructor creates a map and grid lines, adds walls 
    public Map(String path){ 
        try{  //
            scanner = new Scanner(new File(path)); //adding a file to scanner
            this.size = scanner.nextInt(); //first line is size
            map = new int[size][size];  //we create a map with a size 

            for(int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    map[j][i] = scanner.nextInt();
                }
            }
            addWalls();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getSize() { //returns a size 
        return this.size;
    }

    public int getUnit(){
        this.UNIT = 40;   //returns an unit of one pixel 
        return this.UNIT;
    }

    public int getValueAt(int x, int y){ //returns specific value from 2d array

        return this.map[x][y];
    }

    public void setValue(int x, int y, int v) { //sets value 
        map[y][x] = v;
    }

    public Position getStartPosition(){   //returns the start position
        return start;
    }

    /* adding walls to the map, in my code rectangles are as walls
    if value equalы to 1 creates there a rectangle, 2 - start pos.*/
    public void addWalls() {  
        for(int i = 0; i < getSize(); i++) {
            for(int j = 0; j < getSize(); j++) {
                if(getValueAt(j, i) == 1)
                    CreateRectangle(j, i, 1);

                if (getValueAt(j, i) == 0)
                    CreateRectangle(j, i, 0);

                if(getValueAt(j, i) == 2) {
                    CreateRectangle(j, i, 2);
                    start = new Position(j , i);
                    map[j][i] = 0;
                }


            }
        }

    }

    public void CreateRectangle(int x, int y, int v) {
        Rectangle rectangle = new Rectangle(getUnit(), getUnit());
        if (v == 1) rectangle.setFill(Color.BLACK); else rectangle.setFill(Color.WHITE);
        rectangle.setX(x * getUnit());
        rectangle.setY(y * getUnit());
        this.getChildren().add(rectangle);
    }


}
