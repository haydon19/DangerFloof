import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Was easier to just extend the JButton class and make my own custom buttons, the actionListener took a while
    for me to comprehend. This was the easiest alternative to fiddling with how to listen to the default JButtons
    a crossed multiple classes.
 */

public class GameButtons extends JButton implements ActionListener {

    int value;
    int row;
    int col;
    boolean isClicked;


    public GameButtons(int i, int j) {
        value = 3;
        row = i;
        col = j;
        this.addActionListener(this);
        isClicked = false;
    }

    public void actionPerformed(ActionEvent e){
        //System.out.println("GAMEBUTTONS TEST: Clicked: [" + row + "][" + col + "]: Value:" + this.value());
        this.isClicked = true;
    }


    public void setValue(int value){
       this.value = value;
    }

    public String value(){
        if(this.value == 0){
            return("Sheep");
        }
        if(this.value == 1){
            return("Dog");
        }
        if(this.value == 2){
            return("Wolf");
        }
        return("Neutral");
    }

}
