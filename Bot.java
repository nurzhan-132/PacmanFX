import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class Bot implements BotPlayer {
    private Food food;
    private Map map;
    private Circle circle;
    private Position position;
    public HashMap<Position, ArrayList<Position>> hashMap;
    public Position[][] white_cell_positions;

    public Bot(Map map) {

        this.map = map;
        circle = new Circle(map.getUnit() / 2);
        circle.setCenterX(map.getStartPosition().getX() * map.getUnit() + map.getUnit() / 2);
        circle.setCenterY(map.getStartPosition().getY() * map.getUnit() + map.getUnit() / 2);
        circle.setFill(Color.GREEN);

        map.getChildren().add(circle);
        position = map.getStartPosition();

    }

    @Override
    public void feed(Food f) {
        food = f;
    }

    @Override
    public void eat() {

        Runnable botmap1 = new Program1();
        Thread thr = new Thread(botmap1);
        thr.start();
    }

    @Override
    public void find() {

        Runnable botmap2 = new Program2();
        Thread thr = new Thread(botmap2);
        thr.start();
    }

    @Override
    public void moveRight() {
        circle.setCenterX(circle.getCenterX() + map.getUnit());
        position.setX(getPosition().getX() + 1);
    }

    @Override
    public void moveLeft() {
        circle.setCenterX(circle.getCenterX() - map.getUnit());
        position.setX(getPosition().getX() - 1);
    }

    @Override
    public void moveUp() {
        circle.setCenterY(circle.getCenterY() - map.getUnit());
        position.setY(getPosition().getY() - 1);
    }

    @Override
    public void moveDown() {
        circle.setCenterY(circle.getCenterY() + map.getUnit());
        position.setY(getPosition().getY() + 1);
    }

    @Override
    public Position getPosition() {
        return position;
    }


    public void Find() {
        hashMap = new HashMap<>();
        white_cell_positions = new Position[map.getSize()][map.getSize()];

        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                ArrayList<Position> positions = new ArrayList<>();
                positions.add(new Position(i, j));

                Position pos1 = new Position(i, j);
                white_cell_positions[i][j] = pos1;

                if (map.getValueAt(i, j) == 0) hashMap.put(pos1, positions);

            }
        }

        min(position);
    }

    public boolean CheckBounds(int x, int y) {

        if (x >= 0 && x < map.getSize() && y >= 0 && y < map.getSize()) {
            return true;
        } else {
            return false;
        }
    }


    public void min(Position pos) {
        Position start = white_cell_positions[pos.getX()][pos.getY()];

        if (CheckBounds(start.getX() + 1, start.getY()) && map.getValueAt(start.getX() + 1, start.getY()) == 0 && hashMap.get(white_cell_positions[start.getX() + 1][start.getY()]).size() == 1) {
            ArrayList<Position> positions = hashMap.get(white_cell_positions[start.getX() + 1][start.getY()]);
            positions.addAll(hashMap.get(start));

            hashMap.replace(white_cell_positions[start.getX() + 1][start.getY()], positions);

            min(white_cell_positions[start.getX() + 1][start.getY()]);
        }

        if (CheckBounds(start.getX() - 1, start.getY()) && map.getValueAt(start.getX() - 1, start.getY()) == 0 && hashMap.get(white_cell_positions[start.getX() - 1][start.getY()]).size() == 1) {
            ArrayList<Position> positions = hashMap.get(white_cell_positions[start.getX() - 1][start.getY()]);
            positions.addAll(hashMap.get(start));

            hashMap.replace(white_cell_positions[start.getX() - 1][start.getY()], positions);

            min(white_cell_positions[start.getX() - 1][start.getY()]);
        }

        if (CheckBounds(start.getX(), start.getY() + 1) && map.getValueAt(start.getX() , start.getY() + 1) == 0 && hashMap.get(white_cell_positions[start.getX()][start.getY() + 1]).size() == 1) {
            ArrayList<Position> positions = hashMap.get(white_cell_positions[start.getX()][start.getY() + 1]);
            positions.addAll(hashMap.get(start));

            hashMap.replace(white_cell_positions[start.getX()][start.getY() + 1], positions);

            min(white_cell_positions[start.getX()][start.getY() + 1]);
        }

        if (CheckBounds(start.getX(), start.getY() - 1) && map.getValueAt(start.getX() , start.getY() - 1) == 0 && hashMap.get(white_cell_positions[start.getX()][start.getY() - 1]).size() == 1) {
            ArrayList<Position> positions = hashMap.get(white_cell_positions[start.getX()][start.getY() - 1]);
            positions.addAll(hashMap.get(start));

            hashMap.replace(white_cell_positions[start.getX()][start.getY() - 1], positions);

            min(white_cell_positions[start.getX()][start.getY() - 1]);
        }


    }

    class Program1 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println();
                while(true) {
                    if (food.getPosition().getX() > position.getX()) moveRight(); else
                    if (food.getPosition().getY() > position.getY()) moveDown(); else
                    if (food.getPosition().getX() < position.getX()) moveLeft(); else
                    if (food.getPosition().getY() < position.getY()) moveUp();

                    Thread.sleep(300);
                }
            } catch (InterruptedException ex) {

            }
        }
    }

    class Program2 implements Runnable {
        @Override
        public void run() {

            try {

                while(true) {
                    Find();
                    ArrayList<Position> hey = hashMap.get(white_cell_positions[food.getPosition().getX()][food.getPosition().getY()]);
                    if (position.getX() == food.getPosition().getX() && position.getY() == food.getPosition().getY()) {
                        hey.remove(1);
                    }

                    if (map.getValueAt(food.getPosition().getX(), food.getPosition().getY()) == 0 && hey.size() > 0) {
                        for (int i = hey.size() - 2; i >= 0; i--) {

                                if (hey.get(i).getX() > position.getX()) moveRight(); else
                                if (hey.get(i).getY() > position.getY()) moveDown(); else
                                if (hey.get(i).getX() < position.getX()) moveLeft(); else
                                if (hey.get(i).getY() < position.getY()) moveUp();
                                Thread.sleep(100);
                        }

                    }

                    if (position.getX() == food.getPosition().getX() && position.getY() == food.getPosition().getY()) Thread.sleep(1000); else Thread.sleep(3000);
                }
            } catch (InterruptedException ex) {

            }
        }
    }
}


