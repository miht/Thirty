package se.umu.student.lesu0022.thirtyv2.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import se.umu.student.lesu0022.thirtyv2.GameAssets.Combination;
import se.umu.student.lesu0022.thirtyv2.ComboViewCell;
import se.umu.student.lesu0022.thirtyv2.GameAssets.Game;
import se.umu.student.lesu0022.thirtyv2.Popups.NewGameQueryPopup;
import se.umu.student.lesu0022.thirtyv2.Prefs;
import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-30.
 *
 * This activity corresponds to the summary view that the player will reach if he/she choses
 * to view the summary of the game played.
 *
 * Similar to the ComboScreenActivity in which the player sees the available combinations before finishing
 * each round, this activity will show the player all the used combinations and how many points were awarded
 * for each one during the game.
 *
 * By clicking the new game button in the bottom of the screen the player will be prompted to start a new
 * game, after which the game restarts.
 */

public class GameSummaryActivity extends AppCompatActivity {

    private FrameLayout fl_content_summary;
    private LinearLayout ll_gamecombos;
    private NewGameQueryPopup rl_newgamequery;
    private Button btn_newGame;
    private TextView tv_totalScore;

    public GameSummaryActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //Initialise the game combination. If exists in saved bundle, recreate
        if(savedInstanceState != null) {
            Prefs.GAME = (Game) savedInstanceState.getSerializable(Prefs.GAME_STATE);
        }

        fl_content_summary = (FrameLayout) findViewById(R.id.content_summary);

        init();
    }

    private void init() {
        ll_gamecombos = (LinearLayout)  findViewById(R.id.ll_summarycombos);
        rl_newgamequery = new NewGameQueryPopup(this);
        btn_newGame = (Button) findViewById(R.id.btn_newgame);
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_newgamequery.setVisibility(View.VISIBLE);
            }
        });

        rl_newgamequery.getBtn_no().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_newgamequery.setVisibility(View.GONE);
            }
        });

        rl_newgamequery.getBtn_yes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });

        rl_newgamequery.setVisibility(View.GONE);
        rl_newgamequery.bringToFront();
        fl_content_summary.addView(rl_newgamequery);

        for(Combination c : Prefs.GAME.getCombinations()) {

            ComboViewCell cvc = new ComboViewCell(this, c);
            if(c.getUsed()) {
                ll_gamecombos.addView(cvc);
            }
        }
        tv_totalScore = (TextView) findViewById(R.id.tv_totalscore);
        tv_totalScore.setText("Total score: " + Prefs.GAME.getScore() + " points.");
    }

    @Override
    public void onBackPressed() {
        //Show the window prompting user  if he/she wants to start a new game
        rl_newgamequery.setVisibility(View.VISIBLE);
    }

    /**
     * Start a new game
     */
    private void startNewGame() {
//  If the user presses yes, then start a new game
        setResult(Prefs.RESULTCODE_SUMMARYSCREEN);
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
