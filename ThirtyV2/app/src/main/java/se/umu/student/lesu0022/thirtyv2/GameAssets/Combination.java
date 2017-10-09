package se.umu.student.lesu0022.thirtyv2.GameAssets;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leifthysellsundqvist on 2017-06-28.
 *
 * The combination class represents the actual combinations within the game, such as the "Low" or the "4" combination.
 * Every combination has its own name, sequence of die comprising the actual combination and a number
 * determining how many points the combination is worth.
 * The combinations also have a boolean showing if it has been used up or not.
 *
 * This class implements the Serializable class. The reason for this is so that it may be saved to bundle as a serializable object.
 */
public class Combination implements Serializable {

    private String name = "";
    private boolean beenUsed = false;
    private ArrayList<ArrayList<Integer>> die_sequence;
    private int points = 0;

    public Combination(String name, int points, ArrayList<ArrayList<Integer>> die_sequence, boolean isUsed) {
        setUsed(isUsed);
        this.name = name;
        this.die_sequence = die_sequence;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setUsed(boolean val) {
        beenUsed = val;
    }

    public boolean getUsed() { return beenUsed;}

    public int getPoints() {
        return points;
    }

    public void setPoints(int val) {
        points = val;
    }

    public ArrayList<ArrayList<Integer>> getDieSequence() {
        return die_sequence;
    }

    public void setDieSequence(ArrayList<ArrayList<Integer>> die_sequence) {
        this.die_sequence = die_sequence;
    }
}