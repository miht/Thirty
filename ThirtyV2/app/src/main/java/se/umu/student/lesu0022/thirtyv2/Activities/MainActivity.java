package se.umu.student.lesu0022.thirtyv2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import se.umu.student.lesu0022.thirtyv2.GameAssets.Dice;
import se.umu.student.lesu0022.thirtyv2.DiceButton;
import se.umu.student.lesu0022.thirtyv2.GameAssets.Game;
import se.umu.student.lesu0022.thirtyv2.Popups.FinishedPopup;
import se.umu.student.lesu0022.thirtyv2.Popups.NewRoundPopup;
import se.umu.student.lesu0022.thirtyv2.Popups.NoDieSelectedPopup;
import se.umu.student.lesu0022.thirtyv2.Popups.RollingPopup;
import se.umu.student.lesu0022.thirtyv2.Prefs;
import se.umu.student.lesu0022.thirtyv2.R;

/**
 * The main activity is where the majority of the game will be played.
 *
 * On the start of a round, a popup will appear signifying the start of the round, and when the user
 * passes this popup on the round will begin with an initial roll of all die.
 *
 * The player may select which die to keep by clicking them, after which a lock will appear on
 * the aforementioned dice. Those dice not selected will be rolled and receive a new number.
 */
public class MainActivity extends AppCompatActivity {

    private FrameLayout fl_main_contents;

    private Button btn_reroll;
    private TextView tv_rollsleft;
    private TextView tv_currentRound;
    private NewRoundPopup rl_rounds;
    private RollingPopup rl_rolling;
    private NoDieSelectedPopup rl_noDieSelectedPopup;
    private FinishedPopup rl_finishedPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise the game combination. If exists in saved bundle, recreate
        if(savedInstanceState != null) {
            Prefs.GAME = (Game) savedInstanceState.getSerializable(Prefs.GAME_STATE);
        }
        else {
            Prefs.GAME = new Game();
        }

        init();
    }

    /**
     * Initialises the views within the this views layout
     */
    private void init() {
        fl_main_contents = (FrameLayout) findViewById(R.id.content);

        initPopups();
        initTextViews();
        initButtons();
        initDie();

        refreshLayout();
    }

    /**
     * Initiates the popups within the main activity view. If no rolls has been
     */
    private void initPopups() {
        rl_rounds = new NewRoundPopup(this);

        //Whenever this button is clicked, roll all die and start the game!
        rl_rounds.getBtn_start().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_rounds.rollAction();
                reroll();
            }
        });
        fl_main_contents.addView(rl_rounds);

        rl_rolling = new RollingPopup(this);
        fl_main_contents.addView(rl_rolling);

        //Initialise the no rolls popup alert
        rl_noDieSelectedPopup = new NoDieSelectedPopup(this);
        fl_main_contents.addView(rl_noDieSelectedPopup);

        //instead add the popup signifying the end of the game to the main content view
        rl_finishedPopup = new FinishedPopup(this);

        //Add onclicked routine handlers for this popup. See the FinishedPopup class for reference
        //If the user presses the new game button, start a new game
        rl_finishedPopup.getBtn_newgame().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        //If the user presses the summary button, go to the summary screen to view the total score
        rl_finishedPopup.getBtn_summary().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSummaryScreen();
            }
        });

        fl_main_contents.addView(rl_finishedPopup);
    }

    /**
     * Lock the UI, disabling any user interaction. Displays the "rolling... screen in the meantime"
     */
    void lockUI() {
        rl_rolling.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Unlock the UI, revoking the effects of lockUI()
     */
    void unlockUI() {
        rl_rolling.setVisibility(View.GONE);
        for(Dice d : Prefs.GAME.getDie()) {
            d.setSelected(false);
        }
        refreshLayout();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * initializes all the text views within this activity
     */
    void initTextViews() {
        tv_rollsleft = (TextView) findViewById(R.id.tv_rollsleft);
        tv_rollsleft.setText("" + Prefs.GAME.getRollsLeft());

        tv_currentRound = (TextView) findViewById(R.id.tv_currentround);
        tv_currentRound.setText("Round " + Prefs.GAME.getCurrentRound());
    }

    void initButtons() {
        Button btn_choosecombo = (Button)findViewById(R.id.btn_choosecombo);
        btn_choosecombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Move to combo screen in order to pick a combination based on the die
                moveToComboScreen();
            }
        });

        btn_reroll = (Button)findViewById(R.id.btn_reroll);
        btn_reroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reroll();
            }
        });
    }

    /**
     * Initialises the die by assigning them to the DIE array for easy access.
     */
    void initDie() {
        //Wire the die to the ImageButtons within the main activity layout
        Prefs.GAME.getDie()[0].setDiceButton((DiceButton) findViewById(R.id.die1));
        Prefs.GAME.getDie()[1].setDiceButton((DiceButton) findViewById(R.id.die2));
        Prefs.GAME.getDie()[2].setDiceButton((DiceButton) findViewById(R.id.die3));
        Prefs.GAME.getDie()[3].setDiceButton((DiceButton) findViewById(R.id.die4));
        Prefs.GAME.getDie()[4].setDiceButton((DiceButton) findViewById(R.id.die5));
        Prefs.GAME.getDie()[5].setDiceButton((DiceButton) findViewById(R.id.die6));
    }

    /**
     * Reroll the currently selected die. If 6 or more die are selected, display a popup signifying this to the player
     */
    private void reroll() {
        int numberOfSelectedDie = Prefs.GAME.getNumberOfSelectedDie();
        for(Dice d : Prefs.GAME.getDie()) {
            if(!d.getSelected()) {
                d.roll();
            }
        }

        if(numberOfSelectedDie < 6) {
            Prefs.GAME.setRollsLeft(Prefs.GAME.getRollsLeft() - 1);
            //Lock the UI during the dice rolling animation
            roll_lock(Dice.NUMBER_OF_FRAMES * Dice.FRAME_DURATION);
        }
        else {
            rl_noDieSelectedPopup.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     * @param duration, the duration that the UI should be locked in milliseconds
     */
    private void roll_lock(int duration) {
        lockUI();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unlockUI();
            }
        }, duration);
    }

    private void refreshLayout() {
        if(Prefs.GAME.getRollsLeft() <= 0) {
            btn_reroll.setText("No rolls left");
            btn_reroll.setAlpha(0.5f);
            btn_reroll.setClickable(false);
        }

        for(Dice d : Prefs.GAME.getDie()) {
            d.setNumber(d.getNumber());
            d.setSelected(d.getSelected());
        }

        tv_currentRound.setText("Round " + Prefs.GAME.getCurrentRound());
        tv_rollsleft.setText("" + Prefs.GAME.getRollsLeft());
    }

    public void restartLayout() {
        btn_reroll.setAlpha(1.0f);
        btn_reroll.setClickable(true);
        btn_reroll.setText("Reroll");

        rl_rounds.setVisibility(View.VISIBLE);

        initDie();
        refreshLayout();
    }

    /**
     * Prepare to intent over to the combo screen, in order to choose a combination with the given combination of die.
     */
    private void moveToComboScreen() {
        Intent intent = new Intent(getApplicationContext(), ComboScreenActivity.class);
        startActivityForResult(intent, Prefs.REQUESTCODE_COMBOSCREEN);
    }

    /**
     * Prepare to intent over to the summary screen, in order to view the summary of the game before starting a new game
     */
    private void moveToSummaryScreen() {
        Intent intent = new Intent(getApplicationContext(), GameSummaryActivity.class);
        startActivityForResult(intent, Prefs.REQUESTCODE_SUMMARYSCREEN);
    }

    /**
     * If we return from comboscreen:
     *      - if added combo -> restart round
     *      - if by pressing back button, do nothing
     * If we return from summary screen, then restart Game
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Prefs.REQUESTCODE_COMBOSCREEN) {
            if(resultCode == Prefs.RESULTCODE_COMBOSCREEN_ADDEDCOMBO) {
                //Choosing a combo means starting a new round
                Prefs.GAME.startNewRound();

                if(Prefs.GAME.getCurrentRound() > Prefs.GAME.getMaxRounds()) {
                    //game is finished
                    Prefs.GAME.setGameState(Game.State.FINISHED);
                    rl_finishedPopup.setVisibility(View.VISIBLE);
                }
                else {
                    //Restart the layout since we're starting a new round
                    restartLayout();
                }
            }
        }
        else if(requestCode == Prefs.REQUESTCODE_SUMMARYSCREEN) {
            //We arrived from the summary screen. we'll be starting a new game now
            if(resultCode == Prefs.RESULTCODE_SUMMARYSCREEN) {
                restartGame();
            }
        }
    }

    /**
     * Restart the game by restarting the Game object and recreating this very activity
     */
    private void restartGame() {
        Prefs.GAME = new Game();
        this.recreate();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current game state
        savedInstanceState.putSerializable(Prefs.GAME_STATE, Prefs.GAME);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Prefs.GAME = (Game) savedInstanceState.getSerializable(Prefs.GAME_STATE);
    }

    /**
     * Can't press back button within this view
     */
    @Override
    public void onBackPressed() {

    }
}
