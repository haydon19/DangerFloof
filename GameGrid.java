import java.awt.*;
import java.util.Random;
import javax.swing.*;

/*
    This class holds the entirety of the GUI. At one point it included a JOptionPane...but it looked really messy.
    I'm hoping you can look past the missing StringBuilder and JOptionPane uses...as I didn't need to utilize them.
    I had a lot of testing strings. But decided it was cleaner to include them within the GUI as a visual source of
    information. Seemed like the right thing to do, as most games do this.
    Also, didn't exactly need to use a lot of toString methods as I put relevant info in the GUI.
    If I had a bit more time, I might have included a method that takes all the buttons and sets them to
    isClicked = false. Would have saved a bit of code space.
 */


public class GameGrid extends JFrame{
    //Initializes a JPanel to work with a visual GUI.
    JPanel gameGrid = new JPanel();
    //Initializes the buttons needed to play the game.
    GameButtons buttons[][] = new GameButtons[5][5];
    GameButtons sheep = new GameButtons(0,0);
    GameButtons dog = new GameButtons(0,0);
    GameButtons wolf = new GameButtons(0,0);
    MenuButtons startGame = new MenuButtons("Start Game");
    JButton optionsButton = new JButton("Options");
    JLabel lifeTotal = new JLabel("Dog Strength: 0");
    JLabel level = new JLabel("Level: 0");
    JLabel difficulty = new JLabel("Difficulty: 0");
    JLabel mainGraphic = new JLabel();
    JLabel wolfMove = new JLabel("Wolf's Last Move");
    JLabel wolfMoveDesc = new JLabel();
    public GameGrid(){

        //PANEL GUI CREATION
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //MAIN GUI GRAPHIC CREATION
        mainGraphic.setBounds(95,25,350,320);
        mainGraphic.setIcon(new ImageIcon(this.getClass().getResource("resources/DangerFloof.png")));
        //OPTIONS GUI CREATION
        startGame.setBounds(8,150,100,30);
        optionsButton.setBounds(375,150,100,30);
        //LEVEL GUI CREATION
        level.setBounds(8,200,100,30);
        //DIFFICULTY GUI CREATION
        difficulty.setBounds(8,230,100,30);
        //LIFE GUI CREATION
        lifeTotal.setBounds(8,260,100,30);
        //WOLF MOVED CREATION
        wolfMove.setBounds(375,200,100,30);
        wolfMoveDesc.setBounds(375,230,100,30);

        //GRID GUI CREATION
        //gridX and gridY are used as an offset to make the buttons appear in a 5,5 matrix.
        int gridX,gridY = 210;

        for(int i=0;i<5;i++){
            gridX = 115;
            for(int j=0;j<5;j++) {
                buttons[i][j] = new GameButtons(i,j);
                buttons[i][j].setBounds(gridX,gridY,50,50);
                buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource("resources/tile.jpg")));
                gameGrid.add(buttons[i][j]);
                gridX = gridX + 50;
            }
            gridY = gridY + 50;
        }

        //Got to actually add them to the Panel.
        add(mainGraphic);
        add(startGame);
        add(optionsButton);
        add(level);
        add(difficulty);
        add(lifeTotal);
        add(wolfMove);
        add(wolfMoveDesc);
        //This has to be the final thing added. I had played with a Layout...but it just made things more difficult.
        add(gameGrid);


        gameGrid.setBackground(Color.PINK);
        gameGrid.setLayout(null);

        setVisible(true);
    }


    /*
        Used to populate the board with meaningful tile information.
        Sets all values for all tiles as neutral.
        Uses a loop to create and place a tile for wolf,sheep, and dog randomly.
     */
    public GameGrid initializeBoard(GameGrid grid, int life){

        lifeTotal.setText("Dog Strength: " + life);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                grid.buttons[i][j].value = 3;
                grid.buttons[i][j].isClicked = false;
            }
        }

        int i = 0;
        while(i < 3){
            Random random = new Random();
            int row = random.nextInt(5);
            int col = random.nextInt(5);
            if(grid.buttons[row][col].value == 3) {
                grid.buttons[row][col].setValue(i);

                //This just places the special pieces. (ART too)
                if(grid.buttons[row][col].value == 0){
                    grid.sheep = grid.buttons[row][col];
                    grid.buttons[row][col].setIcon(new ImageIcon(this.getClass().getResource("resources/sheep.jpg")));
                }

                if(grid.buttons[row][col].value == 1){
                    grid.dog = grid.buttons[row][col];
                    grid.buttons[row][col].setIcon(new ImageIcon(this.getClass().getResource("resources/dog.jpg")));
                }

                if(grid.buttons[row][col].value == 2){
                    grid.wolf = grid.buttons[row][col];
                    grid.buttons[row][col].setIcon(new ImageIcon(this.getClass().getResource("resources/wolf.png")));
                }
                i++;
            }
        }

        return grid;
    }

    //Utilized to update changes made within the game. If pieces move or are changed, this updates the art and value
    //of the tiles.
    public void Update(GameButtons buttons[][]){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(buttons[i][j].value == 2) {
                    buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource("resources/wolf.png")));
                }
                if(buttons[i][j].value == 0) {
                    buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource("resources/sheep.jpg")));
                }
                if(buttons[i][j].value == 1) {
                    buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource("resources/dog.jpg")));
                }
                if(buttons[i][j].value == 3){
                    buttons[i][j].setIcon(new ImageIcon(this.getClass().getResource("resources/tile.jpg")));
                }
            }
        }
    }



}
