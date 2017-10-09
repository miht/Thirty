package se.umu.student.lesu0022.thirtyv2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import java.io.Serializable;

/**
 * Created by leifthysellsundqvist on 2017-07-05.
 *
 * I initially stored information about the die (selected, number, etc) within the actual Dice objects themselves
 * as they extended the ImageButton class, but this didn't work out since that meant that they were
 * destroyed upon changes in orientation. In order to store that information I chose to create
 * this additional class to handle the View part of the Dice objects, and instead
 * store the imagebuttons as attributes within each Dice object.
 * That way I can destroy the views how many times I'd like, but the information will remain
 * within the Dice objects and may thus be saved to bundle along with the rest of the game information.
 */

public class DiceButton extends ImageButton implements Serializable {

    private boolean locked = false;

    public DiceButton(Context context) {
        super(context);
    }

    public DiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLocked(boolean val) {
        locked = val;
        if(locked) {
            //When a dice is selected, a dice icon will be be shown on the active dice
            setImageResource(R.drawable.dice_locked);

        }
        else {
            //deselect => clear the image
            setImageResource(0);
        }
    }

    public boolean isLocked() {
        return locked;
    }
}
