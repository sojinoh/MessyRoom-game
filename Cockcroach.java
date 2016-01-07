import javax.swing.*;
import java.awt.*;
public class Cockcroach extends JPanel{
    public double xpos, ypos;
    public double xvel, yvel;
    public double diameter;
    public Cockcroach(double xpos, double ypos, double xvel, double yvel, double diameter){
        this.xpos = xpos;
        this.ypos = ypos;
        this.xvel = xvel;
        this.yvel = yvel;
        this.diameter = diameter;
    }
    public void update(long dt){
        xpos += dt/1000.0 * xvel;
        ypos += dt/1000.0 * yvel;
        //check for collison
        if(xpos < 0){
            xvel = -1 * xvel;
            xpos += 2*(0-xpos);
        }
        if(ypos < 0){
            yvel = -1 * yvel;
            ypos += 2*(0-ypos);
        }
        if(xpos > (1100 - diameter)){
            xvel = -1 * xvel;
            xpos -= 2*(xpos+diameter-1100);
        }
        if(ypos > (700 - diameter)){
            yvel = -1 * yvel;
            ypos -= 2*(ypos+diameter-700);
        }
    }
}