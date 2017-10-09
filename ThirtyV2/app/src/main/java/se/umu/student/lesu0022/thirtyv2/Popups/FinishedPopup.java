package se.umu.student.lesu0022.thirtyv2.Popups;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import se.umu.student.lesu0022.thirtyv2.GameAssets.Game;
import se.umu.student.lesu0022.thirtyv2.Prefs;
import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-30.
 */

public class FinishedPopup extends LinearLayout {

    //The textview displaying the score along with some encouraging words about the players performance
    TextView tv_scoretext;
    private Button btn_newgame;

    private Button btn_summary;

    public FinishedPopup(Context context) {
        super(context);
        init();
    }

    void init() {
        inflate(getContext(), R.layout.popup_finished, this);
        tv_scoretext = findViewById(R.id.tv_popup_scoretext);
        btn_newgame = findViewById(R.id.btn_finished_newgame);
        btn_summary = findViewById(R.id.btn_seesummary);

        initScoreText();

        if(Prefs.GAME.getGameState() != Game.State.FINISHED) {
            setVisibility(View.GONE);
        }

        setClickable(false);
    }

    void initScoreText() {
        int score = Prefs.GAME.getScore();
        tv_scoretext.setText("You got a total of " + score + " points.");
        if(score <= 30) {
            //Pretty poor, dog...
            tv_scoretext.setText("You got a total of " + score + " points. \n Give it another go!");
        }
        else if(score > 30 && score <= 60) {
           // Poor dog...
            tv_scoretext.setText("You got a total of " + score + " points. \nThat's not bad, but it's not great either.");
        }
        else if(score > 60 && score <= 90) {
            //Entirely OK dog...
            tv_scoretext.setText("You got a total of " + score + " points. \nIs gambling a hobbit of yours?");
        }
        else if(score > 90 && score <= 120) {
            //Solid dog
            tv_scoretext.setText("You got a total of " + score + " points. \nGreat job!");
        }
        else if(score > 120) {
            //That's awesome dog!
            tv_scoretext.setText("You got a total of " + score + " points. \nThat's impressive!");
        }
    }


    public Button getBtn_newgame() {
        return btn_newgame;
    }

    public Button getBtn_summary() {
        return btn_summary;
    }


}