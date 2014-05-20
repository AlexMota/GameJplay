/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scenariotest;

import java.awt.Window;
import jplay.Keyboard;
import jplay.Sprite;

/**
 *
 * @author David
 */
public class Player extends Sprite
{
    private boolean horizontalMoveKeyPressed;
    boolean move = false;
    int direction = 3;
    int movement = 2;

    public Player()
    {
        super("boneco.png",28);
        setTotalDuration(1960);
    }

    public boolean moveuNaHorizontal()
    {
        return horizontalMoveKeyPressed;
    }

    public void move(Keyboard keyboard, Window window)
    {
        horizontalMoveKeyPressed = true;
        if(keyboard.keyDown(Keyboard.LEFT_KEY) == true)
        {
            if (this.x - movement > 0)
                this.x -= movement;
            if (direction != 1){
                setSequence(0, 13);
                direction = 1;
            }
            move = true;
        }
        else if(keyboard.keyDown(Keyboard.RIGHT_KEY) == true)
        {
            if (this.x + movement < window.getWidth() - width)
                this.x += movement;
            if (direction != 2){
                setSequence(14, 27);
                direction = 2;
            }
            move = true;
        }
        else
            horizontalMoveKeyPressed = false;

        if (move){
            update();
            move = false;
        }
        jump();
    }

    public int getMovementSpeed(){
        return (movement);
    }
}
