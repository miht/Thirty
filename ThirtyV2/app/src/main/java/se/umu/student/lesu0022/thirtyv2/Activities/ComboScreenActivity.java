package se.umu.student.lesu0022.thirtyv2.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import se.umu.student.lesu0022.thirtyv2.GameAssets.Combination;
import se.umu.student.lesu0022.thirtyv2.ComboViewCell;
import se.umu.student.lesu0022.thirtyv2.GameAssets.Game;
import se.umu.student.lesu0022.thirtyv2.Prefs;
import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-28.
 *
 * This view corresponds to a scroll view consisting of all the available as well as unavailable combos to the player.
 * Should a combo be worth more than 0 points, the player may select it, add the points to the total score and end the round.
 * That combo will then be marked as used and may not be used once more within the current game. Should a combo not be worth any points
 * it will not be selectable and will appear greyed out (see the ComboViewCell class).
 *
 * All the used combos will be shown in the lower part of the scroll view should the player want to examine his/her possibilities.
 */

public class ComboScreenActivity extends AppCompatActivity {

    private LinearLayout ll_unused_combos;
    private LinearLayout ll_used_combos;
    private LinearLayout ll_used_header;

    public ComboScreenActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcombo);

        //Initialise the game combination. If exists in saved bundle, recreate
        if(savedInstanceState != null) {
            Prefs.GAME = (Game) savedInstanceState.getSerializable(Prefs.GAME_STATE);
        }
        init();
    }

    private void init() {
        ll_unused_combos = (LinearLayout)  findViewById(R.id.ll_unusedcombos);
        ll_used_combos = (LinearLayout) findViewById(R.id.ll_usedcombos);
        ll_used_header = (LinearLayout) findViewById(R.id.ll_header_used);

        //Generate the combinations based on the current faces of the die
        Prefs.GAME.generateCombinations();

        int numberOUsedCombinations = 0;

        for(Combination c : Prefs.GAME.getCombinations()) {
            ComboViewCell cvc = new ComboViewCell(this, c);

            if(c.getUsed()) {
                numberOUsedCombinations ++;
                //If the combination has been used, place it in the linear layout with the used combinations
                ll_used_combos.addView(cvc);

            }
            else {
                if(c.getPoints() > 0) {
                    cvc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ((ComboViewCell)view).getCombination().setUsed(true);

                            Prefs.GAME.incrementScore(((ComboViewCell)view).getCombination().getPoints());
                            setResult(Prefs.RESULTCODE_COMBOSCREEN_ADDEDCOMBO);

                            moveToMainScreen();
                        }
                    });
                }
                ll_unused_combos.addView(cvc);
            }
        }

        //If there are no combinations used, then there's no need to display the header for combo/sequence/points
        if(numberOUsedCombinations > 0) {
            ll_used_header.setVisibility(View.VISIBLE);
        }
        else {
            ll_used_header.setVisibility(View.GONE);
        }
    }

    /**
     * Overrides the onBackPressed() function, indicating the the user has pressed the back button.
     * Whenever this happens, signify that no action has been taken within this activity
     */
    @Override
    public void onBackPressed() {
        setResult(Prefs.RESULTSCODE_COMBOSCREEN_PRESSEDBACKBUTTON);
        moveToMainScreen();
    }

    /**
     * moves back to the mainactivity, making sure to refresh it up before transitioning and finishing the intent
     */
    private void moveToMainScreen() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current game state
        savedInstanceState.putSerializable(Prefs.GAME_STATE, Prefs.GAME);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
