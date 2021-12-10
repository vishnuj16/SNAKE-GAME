import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener
{

    private Image apple;
    private Image dot;
    private Image head;
    
    private final int DOT_SIZE = 10;    
    private final int ALL_DOTS = 900;
    private final int RANDOM_POSITION = 29;
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private boolean leftDirection = false;
    private boolean rightDirection =  true;
    private boolean upDirection =  false;
    private boolean downDirection =  false;
    private boolean inGame = true;
    
    private int dots;
    
    private Timer timer;

    Board()
    {
        
        addKeyListener(new TAdapter());                   //setting up the windows
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);
        loadImages();
        initGame();
    }
    
    public void loadImages()
    {
        //loading apple and the snake in
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("apple.png"));
        apple  = i1.getImage(); 
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("head.png"));
        head = i3.getImage();
    }
    
    public void initGame()
    {
        
        dots = 3;                              //initialising the game by initialising the
                                               // snake as an array
        int i=0;
        while (i<dots)
        {
            x[i] = 50 - (i*DOT_SIZE); 
            y[i] = 50;
            i++;
        }
        
        locateApple();                          //loading up apples all over the place
        
        timer = new Timer(140, this);
        timer.start();
    }
    
    
    public void locateApple()
    {
        // randomising apple positions   
        int r = (int)(Math.random() * RANDOM_POSITION); // 0 and 1 =>  0.6 * 20 = 12* 10 = 120
        apple_x = (r * DOT_SIZE); 
        
        r = (int)(Math.random() * RANDOM_POSITION); // 0 and 1 =>  0.6 * 20 = 12* 10 = 120
        apple_y = (r * DOT_SIZE); 
    }
    
    public void paintComponent(Graphics g)
    {
    
        super.paintComponent(g);                    //painting the window after every frame
        
        draw(g);
    }
    
    public void draw(Graphics g)
    {
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            
            for(int z = 0; z < dots ; z++){
                if(z == 0){
                    g.drawImage(head, x[z], y[z], this);     //paint function
                }else{
                    g.drawImage(dot, x[z], y[z], this);
                }
            }
            
            Toolkit.getDefaultToolkit().sync();
        }
        else
        {
             gameOver(g);   
        }
    }
        
        
    public void gameOver(Graphics g)
    {
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);            //gameover graphic funtion
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2 , 300/2);
    }
    
    public void checkApple()
    {
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple();                       //check if head has collided with apple so that
        }                                        // snake becomes bigger
    }
    
    public void checkCollision()
    {

        int i=dots;
        while (i>0)
        {
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i]))
            {                                       //check if snake collides with itself
                inGame = false;
            }
            i--;
        }
        
        if(y[0] >= 300){
            inGame = false;
        }
        
        if(x[0] >= 300){
            inGame = false;                   // snake slithering out of bounds check
        }
        
        if(x[0] < 0){
            inGame = false;
        }
        
        if(y[0] < 0 ){
            inGame = false;
        }
        
        if(!inGame){
            timer.stop();
        }
    }
    
    public void move()
    {
        int i=dots;
        while (i>0)
        {
            x[i] = x[i - 1]; 
            y[i] = y[i - 1];
            i--;
        }
        
        if(leftDirection){
            x[0] = x[0] -  DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] = y[0] -  DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(inGame){
            checkApple();
            checkCollision();                 //analysing input from gamer
            move();
        }
        
        repaint();
    }
    
    
    private class TAdapter extends KeyAdapter{
    
        @Override
        public void keyPressed(KeyEvent e){
           int key =  e.getKeyCode();
           
           if(key == KeyEvent.VK_LEFT && (!rightDirection)){
               leftDirection = true;
               upDirection = false;
               downDirection = false;
           }
           
           if(key == KeyEvent.VK_RIGHT && (!leftDirection)){      //working with input
               rightDirection = true;
               upDirection = false;
               downDirection = false;
           }
           
           if(key == KeyEvent.VK_UP && (!downDirection)){
               leftDirection = false;
               upDirection = true;
               rightDirection = false;
           }
           
           if(key == KeyEvent.VK_DOWN && (!upDirection)){
               downDirection = true;
               rightDirection = false;
               leftDirection = false;
           }
        }
    }
    
}
