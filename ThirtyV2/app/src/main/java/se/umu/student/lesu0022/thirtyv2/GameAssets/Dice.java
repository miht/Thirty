package se.umu.student.lesu0022.thirtyv2.GameAssets;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.io.Serializable;
import java.util.Random;

import se.umu.student.lesu0022.thirtyv2.DiceButton;
import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-27.
 *
 * The Dice objects serve as the buttons that the user selects, deselects and rolls within the main activity.
 * The reason that I chose to extend the ImageButton class was that I wanted to avoid
 * using too many data structures containing the Dice objects; I wanted to keep them assembled and at the same time
 * wanted to be able to access them. Therefore, I designed them to be ImageButtons and to store them within the GridLayout
 * in the main activity.
 *
 *  * This class implements the Serializable class. The reason for this is so that it may be saved to bundle as a serializable object.
 */

public class Dice implements Serializable {

    public static int[] SIDES = new int[] {
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
    };

    public static int NUMBER_OF_FRAMES = 15;
    public static int FRAME_DURATION = 100;

    private boolean selected = false;
    private int number = 1;

    private DiceButton diceButton;

    public Dice() {
    }

    public boolean getSelected() {
        return selected;
    }

    /**
     * When a dice is clicked on, select it.
     */
    public void setSelected(boolean val) {
        selected = val;
        diceButton.setLocked(val);
    }

    public int getNumber() {
        return number;
    }

    /**
     * Adjusts the face number on this dice as well as changing the background image to reflect this number.
     * @param showing_number
     */
    public void setNumber(int showing_number) {
        this.number = showing_number;

        diceButton.setBackgroundResource(SIDES[showing_number - 1]);
    }

    /**
     * Roll this dice, by creating a sequence of drawable animations and assigning them with some delay as the background
     * resource for this dice.
     *
     * Set the face number of this die to the last number generated within the animation sequence.
     */
    public void roll() {
        Random rand = new Random();

        AnimationDrawable ad = new AnimationDrawable();

        ad.setOneShot(true);

        for(int i = 0; i < NUMBER_OF_FRAMES; i++) {
            int r = rand.nextInt(6);
            //We don't want the number for a frame to be the same as the one before, that'd make a lame "extra-long"
            //frame for the animation.
            while(r + 1 == number) {
                r = rand.nextInt(6);
            }
            Drawable d = ContextCompat.getDrawable(diceButton.getContext(), SIDES[r]);
            ad.addFrame(d, FRAME_DURATION);
            number = r + 1;
        }

        diceButton.setBackground(ad);
        ((AnimationDrawable)diceButton.getBackground()).start();
    }

    public void setDiceButton(DiceButton db) {
        diceButton = db;

        View.OnClickListener ocl_die = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(!getSelected());
            }
        };

        diceButton.setOnClickListener(ocl_die);
    }

    public DiceButton getDiceButton() {
        return diceButton;
    }


}
