package se.umu.student.lesu0022.thirtyv2.GameAssets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leifthysellsundqvist on 2017-06-28.
 *
 * This class represents the game object responsible for storing the die and combinations used within the game,
 * information about how many rounds and rolls are permitted as well as the status of the player.
 *
 * The game class also provides the mechanics and procedures for parsing the data for the available combos
 * for a sequence of die after the player has rolled and chosen to view the available combinations for a round,
 * according to an automated score-calculating principle. For more details about how these combinations
 * are calculated, see the SubsetFinder class.
 */
public class Game implements Serializable {

    public static enum State {
            NOT_STARTED,
            STARTED,
            FINISHED
    }

    private State gameState = State.NOT_STARTED;

    private Dice[] die = new Dice[6];

    private Combination[] combinations = new Combination[10];

    private int maxRounds = 10;
    private int currentRound = 1;
    private int max_rolls = 3;
    private int rollsLeft = 3;
    private int score = 0;

    public Game() {
        initCombinations();
        initDie();
    }

    private void initDie() {
        for(int i = 0; i < die.length; i++) {
            die[i] = new Dice();
        }
    }

    /**
     * Initialises the possible combinations by assigning them their names and initial status.
     */
    private void initCombinations() {
        combinations[0] = new Combination("Low", 0, null, false);
        for(int i = 1; i < 10; i++) {
            combinations[i] = new Combination("" + (i + 3), 0, null,  false);
        }
    }

    /**
     * Starts a new round by incrementing the current round, resetting the number of rolls left and
     * setting all the die to deselected.
     */
    public void startNewRound() {
        currentRound ++;
        rollsLeft = max_rolls;
        for(Dice d : die) {
            d.setSelected(false);
        }
    }

    /**
     * Generates the combinations (initialises them properly) by calculating the combo for each based on
     * the sides currently shown on the die.
     */
    public void generateCombinations() {
        //we need a list of integers comprising all the values of the die's sides
        Integer[] die_sequence = new Integer[die.length];
        //At the same time, we can calculate the points for the low combo
        int low_sum = 0;
        ArrayList<Integer> low_die = new ArrayList<>();

        for(int i = 0; i < die_sequence.length; i++) {
            int j = die[i].getNumber();
            if(j <= 3) {
                low_sum += j;
                low_die.add(j);
            }
            die_sequence[i] = j;
        }

        ArrayList<ArrayList<Integer>> low_subsets = new ArrayList<>();
        low_subsets.add(low_die);

        //If the combinations have not been used, calculate their combo points! otherwise, do nothing!
        if(!combinations[0].getUsed()) {
            combinations[0].setDieSequence(low_subsets);
            combinations[0].setPoints(low_sum);
        }

        for(int i = 1; i < combinations.length; i++) {
            if(!combinations[i].getUsed()) {
                ArrayList<ArrayList<Integer>> calculatedCombo = calculateCombination(die_sequence, i + 3);
                combinations[i].setDieSequence(calculatedCombo);
                combinations[i].setPoints(calculatePoints(calculatedCombo));
            }

        }
    }

    /**
     * Calculates and returns the combinations (subsets) of die fulfilling the requested sum (sum)
     * @param sum the sum requested
     * @return
     */
    ArrayList<ArrayList<Integer>> calculateCombination(Integer[] sequence, int sum) {
        Integer[] sortedSequence = Arrays.copyOf(sequence, sequence.length);
        Arrays.sort(sortedSequence);

        SubsetFinder sf = new SubsetFinder(sortedSequence, sum);
        sf.findSubset(0);

        ArrayList<ArrayList<Integer>> chosenNumbers = new ArrayList<>();
        int max = 0;

        for(ArrayList<ArrayList<Integer>> s : sf.getCombos()) {
            int tmpMax = 0;
            for(ArrayList a : s) {
                tmpMax += sumOf(a);
            }
            if(tmpMax > max) {
                max = tmpMax;
                chosenNumbers = s;
            }
        }
        return chosenNumbers;
    }

    /**
     * Return the sum of each sum within a combination added together.
     * For instance, a combination of the subsets {1, 2}, {1, 1, 1}3 would yield a sum fo 3 + 3 = 6.
     * @return sum, the sum of each sequence within seq
     */
    public int calculatePoints(ArrayList<ArrayList<Integer>> seq) {
        int sum = 0;
        for(ArrayList<Integer> seq2 : seq) {
            {
                sum += sumOf(seq2);
            }
        }
        return sum;
    }

    /**
     * Returns the sum of an arraylist of integers
     * @param seq
     * @return the sum of each integer within seq
     */
    private int sumOf(ArrayList<Integer> seq) {
        int sum = 0;
        for(Integer i : seq) {
            sum += i;
        }
        return sum;
    }

    public void setGameState(State state) {
        gameState = state;
    }

    public State getGameState() {
        return gameState;
    }

    public Dice[] getDie() {
        return die;
    }

    public Combination[] getCombinations() {
        return combinations;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getRollsLeft() {
        return rollsLeft;
    }

    public void setRollsLeft(int rollsLeft) {
        this.rollsLeft = rollsLeft;
    }

    public int getMax_rolls() {
        return max_rolls;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int points) {
        score += points;
    }

    public int getNumberOfSelectedDie() {
        int counter = 0;
        for(Dice d : die) {
            if(d.getSelected()) {
                counter ++;
            }
        }
        return counter;
    }
}
