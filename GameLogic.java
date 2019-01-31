import java.util.Random;

public class GameLogic {
    boolean menu;
    boolean gameLoop;
    boolean loss;
    int turnNum;
    int difficulty;
    int level;
    int playerLives;
    Animal dog,wolf,sheep;

    public GameLogic(){
        //Game starts in the menu.
        menu = true;
        gameLoop = true;
        loss = false;
        turnNum = 0;
        difficulty = 0;
        level = 0;
        playerLives = 0;
        dog = new Animal("Dog",0);
        wolf = new Animal("Wolf",0);
        sheep = new Animal("Sheep",0);
    }

    public void defaultSettings(GameGrid grid){
        menu = true;
        gameLoop = true;
        loss = false;
        turnNum = 0;
        difficulty = 0;
        grid.difficulty.setText("Difficulty: " + difficulty);
        level = 0;
        grid.level.setText("Level: " + level);
        playerLives = 0;
        grid.lifeTotal.setText("Dog Strength: " + playerLives);
        dog = new Animal("Dog",0);
        wolf = new Animal("Wolf",0);
        sheep = new Animal("Sheep",0);
    }

    public void mainMenu(GameGrid grid){
        defaultSettings(grid);
        while(menu) {
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            if(grid.startGame.isClicked) {
                //CONSTRUCTS THE GUI (ALL OF IT)
                grid.initializeBoard(grid,playerLives);
                setDifficulty(difficulty,grid);
                //UPDATES THE BUTTON GRAPHICS
                grid.Update(grid.buttons);
                menu = false;
                grid.startGame.isClicked = false;
            }
        }
    }

    public void gameStart(GameGrid grid){
        gameLoop = true;
        while(gameLoop){
            playerTurn(grid, difficulty);
            if(grid.startGame.isClicked){
                turnNum = 0;
                defaultSettings(grid);
                grid.initializeBoard(grid,playerLives);
                setDifficulty(difficulty,grid);
                grid.Update(grid.buttons);
                grid.startGame.isClicked = false;
            }
            if (grid.dog.isClicked) {
                grid.dog.isClicked = false;
                playerTurn(grid, difficulty);
            }

            turnNum++;

            //GAME ENDS
            //1. IF WOLF IS PRESSED WITH NO LIVES.
            //2. IF WOLF MOVES ONTO THE SHEEP TILE.
            if (lossCondition(grid) || loss) {
                System.out.println("You Lose!");
                level = 0;
                grid.level.setText("Level: " + level);
                turnNum = 0;
                gameLoop = false;
            }
            //NEXT LEVEL
            //1. IF SHEEP IS PRESSED.
            else if(winCondition(grid)){
                level++;
                grid.level.setText("Level: " + level);
                System.out.println("Sheep Successfully Herded.");
                if(level >= 4){
                    setDifficulty(1,grid);
                }

                if(level >= 10){
                    setDifficulty(2,grid);
                }
                turnNum = 0;
                grid.wolfMoveDesc.setText("");
                grid.initializeBoard(grid, playerLives);
                grid.Update(grid.buttons);
            }
        }
    }

    public GameGrid playerTurn(GameGrid grid,int difficulty){
        //CASUAL RESET SO PLAYERS TURN IS NEVER FALSIFIED FROM THE BEGINNING OF THEIR TURN.
        boolean endTurn = false;
        while(!endTurn){
            if(grid.startGame.isClicked){
                break;
            }
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(grid.buttons[i][j].isClicked) {
                        //***********SWITCH CASE NEEDED FOR ASSIGNMENT***************
                        /* 0: PLAYER HAS CLICKED A SHEEP.
                           1: PLAYER HAS CLICKED A DOG.
                                GUI UPDATED TO SHOW DOGS STRENGTH.
                                DOG TILE IS GIVEN A NEUTRAL TILE VALUE. (TILE CHANGED IN UPDATE)
                                DOG STRENGTH = PLAYER LIFE.
                           2: PLAYER HAS CLICKED A WOLF.
                                IF PLAYER HAS NOT DISCOVERED A WOLF. LOSS CONDITION IS MET.
                                OTHERWISE, PLAYER LIFE - WOLF STRENGTH
                                WOLF MOVES.
                                BUTTON IS CLICKED IS RESET. (STOPS WOLF MOVING MULTIPLE TIMES.)
                           3: PLAYER HAS CLICKED A NEUTRAL TILE.
                                WOLF MOVES.
                         */
                        switch (grid.buttons[i][j].value) {
                            case 0:
                                endTurn = true;
                                break;
                            case 1:
                                grid.dog.value = 3;
                                playerLives += dog.strength;
                                grid.lifeTotal.setText("Dog Strength: " + playerLives);
                                endTurn = true;
                                break;
                            case 2:
                                System.out.println("Clicked Wolf");
                                if(playerLives <= 0) {
                                    loss = true;
                                }
                                playerLives -= wolf.strength;
                                grid.lifeTotal.setText("Dog Strength: " + playerLives);
                                moveWolf(grid,difficulty);
                                grid.buttons[i][j].isClicked = false;
                                endTurn = true;
                                break;
                            case 3:
                                moveWolf(grid,difficulty);
                                grid.buttons[i][j].isClicked = false;
                                endTurn = true;
                                break;
                        }
                        //*************** END OF SWITCH *****************
                    }
                }
            }
        }
        grid.Update(grid.buttons);
        return grid;
    }

    public boolean lossCondition(GameGrid grid){
        //IF WOLF TILE IS THE SAME COORDINATE AS SHEEP TILE, LOSS CONDITION MET.
        if(grid.wolf.row == grid.sheep.row && grid.wolf.col == grid.sheep.col || loss){
            return true;
        }
        return false;
    }

    public boolean winCondition(GameGrid grid){
        //IF SHEEP TILE IS CLICKED, WIN CONDITION MET.
        if(grid.sheep.isClicked){
            return true;
        }
        return false;
    }

    public GameGrid moveWolf(GameGrid grid,int difficulty){
        int sniffSheepX = grid.sheep.row;
        int sniffSheepY = grid.sheep.col;
        int move;
        Random random = new Random();
        if(difficulty == 0) {
            move = random.nextInt(2);
        }else{
            move = random.nextInt(3);
        }
        //Difficulty
        if(difficulty >= 0) {
            //Attempt to make a difficulty 1 move.
            if(move == 2){
                if(grid.wolf.row < sniffSheepX && grid.wolf.col < sniffSheepY){
                    grid.buttons[grid.wolf.row + 1][grid.wolf.col + 1].value = 2;
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    grid.wolf = grid.buttons[grid.wolf.row + 1][grid.wolf.col + 1];
                    grid.wolfMoveDesc.setText("Moved Down Right");

                }
                else if(grid.wolf.row < sniffSheepX && grid.wolf.col > sniffSheepY){
                    grid.buttons[grid.wolf.row + 1][grid.wolf.col - 1].value = 2;
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    grid.wolf = grid.buttons[grid.wolf.row + 1][grid.wolf.col - 1];
                    grid.wolfMoveDesc.setText("Moved Up Right");

                }
                else if(grid.wolf.row > sniffSheepX && grid.wolf.col > sniffSheepY){
                    grid.buttons[grid.wolf.row - 1][grid.wolf.col - 1].value = 2;
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    grid.wolf = grid.buttons[grid.wolf.row - 1][grid.wolf.col - 1];
                    grid.wolfMoveDesc.setText("Moved Up Left");
                }
                else if(grid.wolf.row > sniffSheepX && grid.wolf.col < sniffSheepY){
                    grid.buttons[grid.wolf.row - 1][grid.wolf.col + 1].value = 2;
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    grid.wolf = grid.buttons[grid.wolf.row - 1][grid.wolf.col + 1];
                    grid.wolfMoveDesc.setText("Moved Down Left");
                }
                else if(grid.wolf.row != sniffSheepX){
                    move = 0;
                }else{
                    move = 1;
                }


            }
            //Easy Moveset.
            //WOLF MOVES ALONG THE X-AXIS
            if (move == 0) {
                if (grid.wolf.row < sniffSheepX) {
                    //Set new Wolf Tile.
                    grid.buttons[grid.wolf.row + 1][grid.wolf.col].value = 2;
                    //Replace old Wolf Tile with Neutral Tile.
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    //Change the Wolf Tile reference to the new Wolf Tile.
                    grid.wolf = grid.buttons[grid.wolf.row + 1][grid.wolf.col];
                    grid.wolfMoveDesc.setText("Moved Down");
                }

                else if (grid.wolf.row > sniffSheepX) {
                    //Set new Wolf Tile.
                    grid.buttons[grid.wolf.row - 1][grid.wolf.col].value = 2;
                    //Replace old Wolf Tile with Neutral Tile.
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    //Change the Wolf Tile reference to the new Wolf Tile.
                    grid.wolf = grid.buttons[grid.wolf.row - 1][grid.wolf.col];
                    grid.wolfMoveDesc.setText("Moved Up");
                }
                else if (grid.wolf.row == sniffSheepX) {
                    grid.wolfMoveDesc.setText("Has lost scent.");
                    return grid;
                }
            //WOLF MOVES ALONG THE Y-AXIS
            } else if (move == 1){
                if (grid.wolf.col < sniffSheepY) {
                    grid.buttons[grid.wolf.row][grid.wolf.col + 1].value = 2;
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    grid.wolf = grid.buttons[grid.wolf.row][grid.wolf.col + 1];
                    grid.wolfMoveDesc.setText("Moved Right");
                }

                else if (grid.wolf.col > sniffSheepY) {
                    grid.buttons[grid.wolf.row][grid.wolf.col - 1].value = 2;
                    grid.buttons[grid.wolf.row][grid.wolf.col].value = 3;
                    grid.wolf = grid.buttons[grid.wolf.row][grid.wolf.col - 1];
                    grid.wolfMoveDesc.setText("Moved Left");
                }

                else if (grid.wolf.col == sniffSheepY) {
                    grid.wolfMoveDesc.setText("Has lost scent.");
                    return grid;
                }
            }//Easy Difficulty

        }//Normal Difficulty
        return grid;
    }

    public void setDifficulty(int diff,GameGrid grid){
        difficulty = diff;
        grid.difficulty.setText("Difficulty: " + difficulty);
        //DOG ALWAYS BEATS WOLF
        if(diff == 0){
            wolf.setStrength(1);
            dog.setStrength(2);
            sheep.setStrength(0);
        }
        //DOG HAS A CHANCE AGAINST THE WOLF
        if(diff == 1){
            Random rand = new Random();
            int wolfStr = rand.nextInt(4);
            int dogStr = rand.nextInt(4);
            wolf.setStrength(wolfStr);
            dog.setStrength(dogStr);
            sheep.setStrength(0);
        }
        //DOG DIES TO WOLF.
        if(diff == 2){
            wolf.setStrength(2);
            dog.setStrength(1);
            sheep.setStrength(0);
        }
    }
}
