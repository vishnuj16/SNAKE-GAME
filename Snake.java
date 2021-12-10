/*
SNAKE GAME PROGRAM
*/

import javax.swing.*;

public class Snake extends JFrame
{
    Snake()
    {
        super("Snake Game");               //set the window
        add(new Board());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    public static void main(String args[])
    {
        new Snake().setVisible(true);
    }
}
