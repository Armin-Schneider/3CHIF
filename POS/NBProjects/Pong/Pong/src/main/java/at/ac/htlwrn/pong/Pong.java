package at.ac.htlwrn.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Pong extends JFrame {
    MouseListener listener;
    KeyListener keys;

    static double playerL = 0;
    static double playerR = 0;
    double pongBallX = 1;
    double pongBallY = 1;
    double playerSpeed = 300;
    char[] playerLPoints = new char[1];
    char[] playerRPoints = new char[1];
    double pongSpeed = 300;
    boolean gameOn = false;
    boolean runin = false;
    boolean pauseOn = false;
    boolean pauseOff = false;
    boolean paused = false;
    boolean gameOver = false;
    boolean upL = false;
    boolean downL = false;
    boolean upR = false;
    boolean downR = false;
    boolean pongLU = false;
    boolean pongRU = false;
    boolean pongLD = false;
    boolean pongRD = true;

    Color color;

    public Pong() throws IOException {

        color = new Color(0, 0, 0);

        listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Sound("C:\\WINDOWS\\Media\\tada.wav").start();
            }

        };

        keys = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    downR = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    upR = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_S)
                {
                    downL = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_W)
                {
                    upL = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE && !runin && gameOn)
                {
                    runin = true;
                    System.out.println("Game Started");
                    playerLPoints[0] = 48;
                    playerRPoints[0] = 48;
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE && runin && gameOn && pauseOn)
                {
                    paused = true;
                    System.out.println("Game Paused");
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE && runin && paused && pauseOff)
                {
                    paused = false;
                    pauseOn = false;
                    pauseOff = false;
                    System.out.println("Game Continued");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(paused || gameOver)
                {
                    if(paused && e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pauseOff = true;   
                    }
                    if(gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameOver = false;   
                    }
                } else {

                    if(!downR && e.getKeyCode() == KeyEvent.VK_DOWN)
                    {
                        System.out.println("DownR");
                        downR = true;
                    }
                    if(!upR && e.getKeyCode() == KeyEvent.VK_UP)
                    {
                        System.out.println("UpR");
                        upR = true;
                    }
                    if(!downL && e.getKeyCode() == KeyEvent.VK_S)
                    {
                        System.out.println("DownL");
                        downL = true;
                    }
                    if(!upL && e.getKeyCode() == KeyEvent.VK_W)
                    {
                        System.out.println("UpL");
                        upL = true;
                    }
                    if(!runin && e.getKeyCode() == KeyEvent.VK_SPACE)
                    {
                        gameOn = true;
                    }
                    if(runin && e.getKeyCode() == KeyEvent.VK_SPACE)
                    {
                        pauseOn = true;
                    }
                }
            }
        };

        setLayout(null);
        addMouseListener(listener);
        addKeyListener(keys);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(1, 1, this.getWidth(), this.getHeight());
        g2.setColor(Color.WHITE);
        g2.drawChars(playerLPoints, 0, 1, 20, 50);
        g2.drawChars(playerRPoints, 0, 1, this.getWidth()-25, 50);
        g2.fillRect(10, (int) playerL, 15, 45);
        g2.setColor(Color.WHITE);
        g2.fillRect(this.getWidth()-26, (int) playerR, 15, 45);
        g2.setColor(Color.WHITE);
        g2.fillRect((int) pongBallX, (int) pongBallY, 10, 10);
        

    }
    
    public void update(double deltaInSeconds)
    {
        if(!paused && runin){
            if(upL&&playerL>35)
            {
                playerL -= deltaInSeconds * playerSpeed;
            }
            if(downL&&playerL<this.getHeight()-55)
            {
                playerL += deltaInSeconds * playerSpeed;
            }
            if(upR&&playerR>35)
            {
                playerR -= deltaInSeconds * playerSpeed;
            }
            if(downR&&playerR<this.getHeight()-55)
            {
                playerR += deltaInSeconds * playerSpeed;
            }

            if(pongLU){
                pongBallX -= deltaInSeconds * pongSpeed;
                pongBallY -= deltaInSeconds * pongSpeed/2;
            }
            if(pongLD){
                pongBallX -= deltaInSeconds * pongSpeed;
                pongBallY += deltaInSeconds * pongSpeed/2;
            }
            if(pongRU){
                pongBallX += deltaInSeconds * pongSpeed;
                pongBallY -= deltaInSeconds * pongSpeed/2;
            }
            if(pongRD){
                pongBallX += deltaInSeconds * pongSpeed;
                pongBallY += deltaInSeconds * pongSpeed/2;
            }

            if(pongBallY >= this.getHeight()-14 && pongRD){
               pongRD = false;
               pongRU = true;
            } else if (pongBallY >= this.getHeight()-14 && pongLD){
                pongLD = false;
                pongLU = true;
            } else if(pongBallY <= 28 && pongLU){
               pongLU = false;
               pongLD = true;
            } else if (pongBallY <= 28 && pongRU){
                pongRU = false;
                pongRD = true;
            }

            if(pongBallX >= this.getWidth()-14 && pongRD){
                playerLPoints[0] += 1;
                pongBallX = getWidth()/2;
                pongBallY = getHeight()/2;
            } else if(pongBallX <= 0){
                playerRPoints[0] += 1;
                pongBallX = getWidth()/2;
                pongBallY = getHeight()/2;
            }

            if((pongBallY >= playerR && pongBallY <= playerR+44 || pongBallY+10 >= playerR && pongBallY+10 <= playerR+44) && pongBallX >= this.getWidth()-35 && pongBallX <= this.getWidth()-33){
                if(pongRD){
                    pongRD = false;
                    pongLD = true;
                    System.out.println("Contact R");
                } else if (pongRU){
                    pongRU = false;
                    pongLU = true;
                    System.out.println("Contact R");
                }

            } else if((pongBallY >= playerL && pongBallY <= playerL+44 || pongBallY+10 >= playerL && pongBallY+10 <= playerL+44) && pongBallX >= 21 && pongBallX <= 24){
                if(pongLD){
                    pongLD = false;
                    pongRD = true;
                    System.out.println("Contact L");

                } else if (pongLU){
                    pongLU = false;
                    pongRU = true;
                    System.out.println("Contact L");
                }
            }

            if(playerLPoints[0] > 54 || playerRPoints[0] > 54){
                if(playerLPoints[0] >= 54){
                    System.out.println("Left Player won!");
                    runin = false;
                } else {
                    System.out.println("Right Player won!");
                    runin = false;
                }
            }
        }
    }
    


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Pong b = new Pong();
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.setTitle("Pong");
        b.setIgnoreRepaint(true);
        b.setSize(800, 600);
        playerL = b.getHeight()/2;
        playerR = b.getHeight()/2;
        
        b.setVisible(true);
        b.createBufferStrategy(2);
        BufferStrategy buffer = b.getBufferStrategy();
        Graphics graphics = null;
        long oldTime = System.nanoTime();
        long newTime = oldTime;
        while (true) {
            try {
                oldTime = newTime;
                newTime = System.nanoTime();
                long delta = (newTime - oldTime);
                //System.out.println("Update " + delta);
                b.update(delta / 1000000000.0);
                // clear back buffer...
                graphics = buffer.getDrawGraphics();
                graphics.setColor(Color.WHITE);
                b.paint(graphics);
                // blit the back buffer to the screen			
                if (!buffer.contentsLost()) {
                    buffer.show();
                }

                // Let the OS have a little time...
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (graphics != null) {
                    graphics.dispose();
                }
            }
        }
    }
}
