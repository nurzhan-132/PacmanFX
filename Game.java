import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Game extends Application {

    private Map map;           
    private Player player;
    private Food food;

    public static void main(String[] args){ //main method
        Application.launch(args);
    }
    @Override
    public void start(Stage stage){
        stage.setTitle("Eater");
        map = new Map(getParameters().getRaw().get(0)); //creates a map
        //player = new MyPlayer(map);     //polymorphism
        Player bot = new Bot(map);
        food = new Food(map, bot);   //creates a Food instance
        ((Bot) bot).feed(food);

        int number = 1;

        for (int i = 0; i < map.getSize(); i++) { //Checking all cells, but you may change on yourself method, without checking
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getValueAt(i, j) == 1) {
                    number = 2;
                    break;
                }
            }
            System.out.println();
        }


        switch (number) {
            case 1: ((Bot) bot).find(); break;
            case 2: ((Bot) bot).find(); break;
        }

        Scene scene = new Scene(map,420,420);  //adds map to Scene
        stage.setScene(scene);  //Scene to Stage        
        stage.show();

        scene.setOnKeyPressed(new KeyHandler()); //when arrow keys pressed program creates KeyHandler
    }

    class KeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent e) {            
            switch(e.getCode()){  
                case RIGHT: player.moveRight(); //when right key pressed calls the moveRight()
                    break;
                case LEFT: player.moveLeft(); //when left key pressed calls the moveLeft()
                    break;
                case UP: player.moveUp(); //when up key pressed calls the moveUp()
                    break;
                case DOWN: player.moveDown(); //when down key pressed calls the moveDown()
                    break;
            }   
        }
    }
}