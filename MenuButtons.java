import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
    That Start Button over in GameLogic really didn't want to cooperate for the longest time.
    So I made my own button akin to the GameButtons.
    Learning and utilizing what I learned ;).
 */

public class MenuButtons extends JButton implements ActionListener{

    boolean isClicked;

    public MenuButtons(String label){
        super(label);
        isClicked = false;
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        this.isClicked = true;
    }

}
