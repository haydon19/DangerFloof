/*
    There is a lot of space in the GUI that isn't used.
    This is so due to the fact that this assignment may grow in the future.
    It is stated within the assignment that this assignment will be used again.
    I took a liberty with the name DangerFloof. It's current and has a nice ring.
    Floof is a hip word for a dog of any species.
*/
import javax.swing.*;
public class DangerFloof extends JFrame{
    boolean loss;
    JOptionPane hello;
    JFrame window;
    public static void main(String args[]){

        new DangerFloof();
    }


    public DangerFloof() {
        window = new JFrame();
        JOptionPane.showMessageDialog(window,"Welcome to DangerFloof. \n" +
                "The object of the game is to herd the sheep" +
                "and avoid the wolf. \n" +
                "Clicking the start game button will populate the grid with 4 types of tiles.\n" +
                "Wolf Tile - Click on this and you lose the game.\n" +
                "Sheep Tile - Click on this and you progress to the next level. \n" +
                "Dog Tile - Click on this and gain strength to fight the wolf. \n" +
                "Neutral Tile - Nothing to special. \n" +
                "Every time a tile is clicked, the wolf moves closer to the sheep. If the wolf finds the sheep," +
                "its game over. \n" +
                "Created by John McGuire.");

        loss = false;
        //Creates the grid of the game.
        GameGrid grid = new GameGrid();
        GameLogic logic = new GameLogic();
        int end = 0;
        System.out.println("Initializing board..");
        while(end != -1) {
            logic.mainMenu(grid);
            logic.gameStart(grid);

        }
    }
}
