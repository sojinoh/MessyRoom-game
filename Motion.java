import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class Motion extends JPanel implements KeyListener,  MouseListener{
    private int [][] board;
    private double mx = 0;
    //x position of the vaccuum
    private double my = 0;
    //y position of the vaccuum
    private int realL = 0;
    private int realR = 0;
    private int realU = 0;
    private int realD = 0;
    private int realX = 0;
    //these are all for correct operation of keypressed
    private int blocksize = 20;
    //size of one block in the array
    private double xvel, yvel = 0;
    //x,y velocity of the vaccuum
    private ArrayList<Life> life;
    private ArrayList<Cockcroach> CockcroachList;
    private ArrayList<Booster> BoosterList;
    private ArrayList<Reducer> ReducerList;
    private ArrayList<Transformer> TransformerList;
    //booster,reducer, transformer are the items
    private BufferedImage cockcroach;
    private BufferedImage messyroom;
    private BufferedImage vaccuum;
    private BufferedImage lifep;
    private BufferedImage gameover;
    private BufferedImage booster;
    private BufferedImage reducer;
    private BufferedImage Retry;
    private BufferedImage Transformer;
    private BufferedImage missioncomplete;
    private BufferedImage nextlevel;
    private BufferedImage instruction1;
    private BufferedImage instruction2;  
    private BufferedImage supervacuum;    
    private int timer = 300;
    //pause timer for the game to start after the vaccuum collides with cockcroaches
    private int itemtimer = 1;
    //keep track of the time when items are added 
    private int introtimer = 2300;
    //pause timer before the game starts 
    private int nextleveltimer = 300;
    //pause timer before moving to the next level
    private int transformertimer = 1000;
    //keeps track of the time that supervaccuum appears
    private int score = 0;
    private boolean gameoverb = false;
    //when true, game is over, lives are all lost
    private boolean missioncompleteb = false;
    //when true, game is complete
    private boolean transformerb = false;
    //when true, vaccuum turns into supervaccuum
    Random rng = new Random();
    public Motion(){
        board = new int [1100/blocksize][700/blocksize];
        CockcroachList = new ArrayList<Cockcroach>();
        BoosterList = new ArrayList<Booster>();
        ReducerList = new ArrayList<Reducer>();
        TransformerList= new ArrayList<Transformer>();
        life = new ArrayList<Life>();
        life.add(new Life(1120,50,50));
        life.add(new Life(1190,50,50));
        life.add(new Life(1260,50,50));
        //three lives are added
        for(int i = 0; i < 5; i++){
            CockcroachList.add(new Cockcroach(100 + rng.nextInt(150), 50 + rng.nextInt(200), 50 + rng.nextInt(200), 150 +rng.nextInt(200), 50));
            //initially five cockcroaches are added
        }
        try{
            cockcroach = ImageIO.read(new File("cockcroach.png"));
            messyroom = ImageIO.read(new File("messyroom.jpg"));
            vaccuum = ImageIO.read(new File("vaccuum.png"));
            lifep = ImageIO.read(new File("life.png"));
            gameover = ImageIO.read(new File("gameover.png"));
            booster = ImageIO.read(new File("booster.png"));
            reducer = ImageIO.read(new File("reducer.png"));
            Retry = ImageIO.read(new File("Retry.png"));
            Transformer = ImageIO.read(new File("transformer.png"));
            missioncomplete = ImageIO.read(new File("missioncomplete.png"));
            nextlevel = ImageIO.read(new File("nextlevel.png"));
            instruction1 = ImageIO.read(new File("instruction1.png"));
            instruction2= ImageIO.read(new File("instruction2.png"));
            supervacuum = ImageIO.read(new File("supervacuum.gif"));
        }
        catch(Exception e){
            
        }
    }
    public boolean collide(){
        //checks if the vaccuum collided with cockcroaches
        boolean collide = false;
        if(transformerb == false){
            for(int i = 0; i < CockcroachList.size(); i++){
                if((CockcroachList.get(i).xpos + 7 <= mx && mx <= CockcroachList.get(i).xpos + 50 - 7 && CockcroachList.get(i).ypos + 7 <= my && my <=CockcroachList.get(i).ypos + 50 - 7)
                    || (mx + 7 <= CockcroachList.get(i).xpos && CockcroachList.get(i).xpos <= mx + 60 - 7 && my + 7 <= CockcroachList.get(i).ypos && CockcroachList.get(i).ypos <= my + 60 - 7)){
                    collide = true;
                    return collide;
                    //vaccuum collided with cockcroaches 
                }
            }
        }
        return collide;
        //did not collide
    }
    public void collidebooster(){
        for(int i = 0; i < BoosterList.size(); i++){
            if((BoosterList.get(i).xpos <= mx && mx <= BoosterList.get(i).xpos + 50 && BoosterList.get(i).ypos <= my && my <=BoosterList.get(i).ypos + 50)
                || (mx <= BoosterList.get(i).xpos && BoosterList.get(i).xpos <= mx + 60 && my <= BoosterList.get(i).ypos && BoosterList.get(i).ypos <= my + 60)){
                BoosterList.remove(i);
                score += 10000;
                //vaccuum earned booster item, 10000 is added to the score
            }
        }
        return;
        //vaccuum did not earn booster item
    }
    public void collidereducer(){
        for(int i = 0; i < ReducerList.size(); i++){
            if((ReducerList.get(i).xpos <= mx && mx <= ReducerList.get(i).xpos + 50 && ReducerList.get(i).ypos <= my && my <=ReducerList.get(i).ypos + 50)
                || (mx <= ReducerList.get(i).xpos && ReducerList.get(i).xpos <= mx + 60 && my <= ReducerList.get(i).ypos && ReducerList.get(i).ypos <= my + 60)){
                ReducerList.remove(i);
                score = 0;
                //vaccuum earned reducer item, score becomes 0
            }
        }
        return;
        //vaccuum did not earn reducer item
    }
    public void collidetransformer(){
            for(int i = 0; i < TransformerList.size(); i++){
                if((TransformerList.get(i).xpos <= mx && mx <= TransformerList.get(i).xpos + 50 && TransformerList.get(i).ypos <= my && my <=TransformerList.get(i).ypos + 50)
                    || (mx <= TransformerList.get(i).xpos && TransformerList.get(i).xpos <= mx + 60 && my <= TransformerList.get(i).ypos && TransformerList.get(i).ypos <= my + 60)){
                    TransformerList.remove(i);
                    transformerb = true;
                    return;
                    //vaccuum earned transformer item, vaccuum changes to supervaccuum
                }
            }
            return;
            //vaccuum did not earn transformer item
    }
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(messyroom, 0,0,1100,700,null);
        //draws background
        for(int i = 0; i < life.size(); i++){
            g.drawImage(lifep,life.get(i).xpos, life.get(i).ypos, life.get(i).size, life.get(i).size, null);
        }    
        //draws lives
        g.fillRect(1120, 550, 190, 100);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("S C O R E", 1145, 580);
        g.setColor(Color.black);
        g.drawString("L I F E", 1170, 40);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(""+score, 1150, 620);
        //draws score box
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j ++){
                if(board[i][j] == 1){
                    g.setColor(Color.white);
                    g.fillOval(i*blocksize,j*blocksize,blocksize,blocksize);
                }
            }
        }
        //when a block in the 2d array is 1, it is colored to white
        for(int i = 0; i < CockcroachList.size(); i++){
             g.drawImage(cockcroach,(int)CockcroachList.get(i).xpos,(int)CockcroachList.get(i).ypos,50,50,null);
        }
        for(int i = 0; i < BoosterList.size(); i++){
             g.drawImage(booster,(int)BoosterList.get(i).xpos,(int)BoosterList.get(i).ypos,50,50,null);
        }
        for(int i = 0; i < ReducerList.size(); i++){
             g.drawImage(reducer,(int)ReducerList.get(i).xpos,(int)ReducerList.get(i).ypos,50,50,null);
        }
        for(int i = 0; i < TransformerList.size(); i++){
             g.drawImage(Transformer,(int)TransformerList.get(i).xpos,(int)TransformerList.get(i).ypos,50,50,null);
        }
        //draws items
        if(transformerb==false){
            g.drawImage(vaccuum, (int)mx, (int)my, 60, 60, null);
            //when not earned transformer item, draws original vaccuum
        }
        else{
            g.drawImage(supervacuum, (int)mx, (int)my, 60, 60, null);
            //when earned transformer item, draws supervaccuum
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        if(gameoverb){
            //when there is no life left
            g.drawImage(gameover, 0,0, 1100, 700, null);
            g.drawImage(Retry, 900, 100, 100, 100, null);
        }
        if(missioncompleteb){
            //when the game is complete
            g.drawImage(missioncomplete, 0,0, 1100, 700, null);
            g.drawImage(Retry, 900, 100, 100, 100, null);
        }
        if(0 < nextleveltimer && nextleveltimer < 300){
            //indicating the next level
            g.drawImage(nextlevel, 0,0, 1100, 700, null);
        }
        if(1300<= introtimer && introtimer <= 2300){
            //showing instruction1
            g.drawImage(instruction1, 0,0, 1100, 700, null);
        }
        if(0 < introtimer && introtimer < 1300){
            //showing instruction2
            g.drawImage(instruction2, 0,0, 1100, 700, null);
        }
    }
    public boolean cleaned(){
        //checks if there is any place in the room that is not cleaned
        boolean cleaned = true;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j ++){
                if(board[i][j] == 0){
                    cleaned = false;
                    return cleaned;
                    //the whole room is cleaned
                }
            }
        }
        return cleaned;
        //the room is not completely cleaned
    }
    public void update(long dt){//in milliseconds
        if(introtimer > 0){
            introtimer -= 5;
        }
        //takes time to show instructions
        if(introtimer == 0){
            //starts the game
            if(!cleaned() && 0 <= mx && mx <= 1100-60 && 0 <= my && my <= 700-60 && !collide()&&!life.isEmpty()){
                if(itemtimer%500==0){
                    BoosterList.add(new Booster(rng.nextInt(1100-50), rng.nextInt(700-50),5, 5, 50));
                }
                if(itemtimer%700==0){
                    ReducerList.add(new Reducer(rng.nextInt(1100-50), rng.nextInt(700-50),5, 5, 50));
                }
                if(itemtimer%900==0){
                    TransformerList.add(new Transformer(rng.nextInt(1100-50), rng.nextInt(700-50),5, 5, 50));
                }
                //adds items at a certain point
                itemtimer += 1;
                mx += dt/1000.0 * xvel;
                my += dt/1000.0 * yvel;
                if(mx < 0){
                    mx = 0;
                }
                if(mx > 1100 - 60){
                    mx = 1100 - 60;
                }
                if(my < 0){
                    my = 0;
                }
                if(my > 700 - 60){
                    my = 700 - 60;
                }
                //vaccuum does not go over the border line
                if(realX > 0){
                    for(int i = (int)mx; i <= (int)mx + 60; i++){
                            for(int j = (int)my; j <= (int)my + 60; j++){ 
                                if(i/blocksize < board.length && j/blocksize < board[i/blocksize].length){
                                    if(board[i/blocksize][j/blocksize] != 1){
                                        board[i/blocksize][j/blocksize] = 1;
                                        //if the z key is pressed down and the vaccuum is moving, the frame is painted to white and the score is added by 1
                                        score = score + 1;
                                    }
                                }
                            }
                        }
                }             
                //update items
                for(int i = 0; i < CockcroachList.size(); i++){
                    CockcroachList.get(i).update(dt);
                } 
                for(int i = 0; i < BoosterList.size(); i++){
                    BoosterList.get(i).update(dt);
                } 
                for(int i = 0; i < ReducerList.size(); i++){
                    ReducerList.get(i).update(dt);
                } 
                for(int i = 0; i < TransformerList.size(); i++){
                    TransformerList.get(i).update(dt);
                }
                collidebooster();
                collidereducer();
                collidetransformer();
                //checks if vaccuum earns item
                if(transformerb == true){
                    transformertimer -= 5;
                    //supervaccuum lasts for certain time
                    if(transformertimer == 0){
                        transformerb = false;
                        //supervaccuum comes back to original vaccuum
                        transformertimer = 1000;
                    }
                }
                if(itemtimer%1000==0 && ReducerList.size()!= 0){
                    ReducerList.remove(0);
                }
                if(itemtimer%1300==0 && TransformerList.size()!= 0){
                    TransformerList.remove(0);
                }
                //when the vaccuum does not earn existing reducer or transformer item, those items disappear after few seconds
            }
            if(!life.isEmpty()&& collide()){                 
                //if vaccuum collides with cockcroaches, it loses one life and the game starts after few seconds
                timer -= 5;
                if(timer == 0){
                    score = score * 99/100;     
                    if(!life.isEmpty()){
                        life.remove(0);
                    }   
                    timer = 300;
                    mx = 0;
                    my = 0;
                    for(int i = 0; i < CockcroachList.size(); i++){
                        CockcroachList.get(i).xpos = 500;
                        CockcroachList.get(i).ypos = 500;
                    }
                }                
            }
            if(cleaned() && (gameoverb==false)){          
                if(CockcroachList.size()==5 && !missioncompleteb){
                    //when the first level's room is cleaned it moves to the next level after few seconds.The next level has three more cockcroaches
                    nextleveltimer -= 5;
                    if(nextleveltimer == 0){
                        nextleveltimer = 300;
                        mx = 0;
                        my = 0;
                        for(int i = 0; i < board.length; i++){
                            for(int j = 0; j < board[i].length; j ++){
                                board[i][j] = 0;
                            }
                        }            
                        for(int i = 0; i < 3; i++){
                            Random rng = new Random();
                            CockcroachList.add(new Cockcroach(100 + rng.nextInt(150), 50 + rng.nextInt(200), 50 + rng.nextInt(200), 150 +rng.nextInt(200), 50));
                        }
                        for(int i = 0; i < CockcroachList.size(); i++){
                            CockcroachList.get(i).xpos = 500;
                            CockcroachList.get(i).ypos = 500;
                        }
                    } 
                }   
                else{
                    //second level is completed
                    missioncompleteb = true;
                    mx = 0;
                    my = 0;
                    for(int i = 0; i < CockcroachList.size(); i++){
                        CockcroachList.remove(i);
                    } 
                }
            }
            if(life.isEmpty()){
                    //there is no life left, and the game is over
                    gameoverb = true;
                    mx = 0;
                    my = 0;
                    for(int i = 0; i < board.length; i++){
                        for(int j = 0; j < board[i].length; j ++){
                            board[i][j] = 1;
                        }
                    }            
                    for(int i = 0; i < CockcroachList.size(); i++){
                        CockcroachList.remove(i);
                    } 
            }
        }
    }
    public static void main(String [] args){
        JFrame jf = new JFrame("Motion Demo");
        Motion m = new Motion();
        jf.setSize(1350,735);
        jf.add(m);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.addKeyListener(m);
        jf.addMouseListener(m);
        long prevframe = System.currentTimeMillis();
        while(true){
            long curframe = System.currentTimeMillis();
            if(curframe - prevframe < 20){//ideal frame time
                try{
                    Thread.sleep(20 - curframe + prevframe);
                }
                catch(Exception e){
                    
                }
            }
            else{
                m.update(curframe - prevframe);
                prevframe = curframe;
                m.repaint(); 
            }
        }
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            realL = realL + 1;
            if(realL == 1){
                xvel -= 500;
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            realR = realR + 1;
            if(realR == 1){
                xvel += 500;
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_UP){
            realU = realU + 1;
            if(realU == 1){
                yvel -= 500;
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_DOWN){
            realD = realD + 1;
            if(realD == 1){
                yvel += 500;
            }
        }
        //movement of the vaccuum
        if((e.getKeyCode()==KeyEvent.VK_Z)){
            realX = realX + 1;
            if(realX == 1){
                for(int i = (int)mx; i <= (int)mx + 60; i++){
                    for(int j = (int)my; j <= (int)my + 60; j++){ 
                        if(i/blocksize < board.length && j/blocksize < board[i/blocksize].length){
                            if(board[i/blocksize][j/blocksize] != 1){
                                board[i/blocksize][j/blocksize] = 1;
                            }
                        }
                    }
                }
            }
        }
        //when press z, that spot is cleaned
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            realL = 0;
            xvel += 500;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            realR = 0;
            xvel -= 500;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
            realU = 0;
            yvel += 500;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            realD = 0;
            yvel -= 500;
        }
        if((e.getKeyCode()==KeyEvent.VK_Z)){
            realX = 0;
        }
        //movement of the vaccuum
    }
    public void keyTyped(KeyEvent e){
        
    }
    public void mouseClicked(MouseEvent e){
        
    }
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        
    }
    public void mousePressed(MouseEvent e){
        if(gameoverb == true){
            //when the game is over and no life is left
            if((900<= e.getX() && e.getX() <=1000) && (100<= e.getY() && e.getY() <=200)){
                //if replay button is pressed, game restarts
                for(int i = 0; i < board.length; i++){
                    for(int j = 0; j < board[i].length; j ++){
                        board[i][j] = 0;
                    }
                }        
                for(int i = 0; i < 5; i++){
                    CockcroachList.add(new Cockcroach(100 + rng.nextInt(150), 50 + rng.nextInt(200), 50 + rng.nextInt(200), 150 +rng.nextInt(200), 50));
                }
                for(int i = 0; i < BoosterList.size(); i++){
                    BoosterList.remove(i);
                } 
                for(int i = 0; i < ReducerList.size(); i++){
                    ReducerList.remove(i);
                } 
                for(int i = 0; i <TransformerList.size(); i++){
                    TransformerList.remove(i);
                }
                score = 0;
                itemtimer = 1;
                life.add(new Life(1120,50,50));
                life.add(new Life(1190,50,50));
                life.add(new Life(1260,50,50));
                for(int i = 0; i < CockcroachList.size(); i++){
                    CockcroachList.get(i).xpos = 500;
                    CockcroachList.get(i).ypos = 500;
                }
                transformerb = false;
                gameoverb = false;
            }
        }
        if(missioncompleteb == true){
            //when game is completed
            if((900<= e.getX() && e.getX() <=1000) && (100<= e.getY() && e.getY() <=200)){
                //if replay button is pressed, game restarts
                for(int i = 0; i < board.length; i++){
                    for(int j = 0; j < board[i].length; j ++){
                        board[i][j] = 0;
                    }
                }        
                for(int i = 0; i < 5; i++){
                    CockcroachList.add(new Cockcroach(100 + rng.nextInt(150), 50 + rng.nextInt(200), 50 + rng.nextInt(200), 150 +rng.nextInt(200), 50));
                }
                for(int i = 0; i < BoosterList.size(); i++){
                    BoosterList.remove(i);
                } 
                for(int i = 0; i < ReducerList.size(); i++){
                    ReducerList.remove(i);
                } 
                for(int i = 0; i <TransformerList.size(); i++){
                    TransformerList.remove(i);
                }
                for(int i = 0; i < life.size(); i++){
                    life.remove(i);
                }
                score = 0;
                itemtimer = 1;
                life.add(new Life(1120,50,50));
                life.add(new Life(1190,50,50));
                life.add(new Life(1260,50,50));
                for(int i = 0; i < CockcroachList.size(); i++){
                    CockcroachList.get(i).xpos = 500;
                    CockcroachList.get(i).ypos = 500;
                }
                transformerb = false;
                missioncompleteb = false;
            }
        }
    }
    public void mouseReleased(MouseEvent e){

    }
}
