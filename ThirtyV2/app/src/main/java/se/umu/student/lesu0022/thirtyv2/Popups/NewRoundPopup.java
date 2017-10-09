package se.umu.student.lesu0022.thirtyv2.Popups;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import se.umu.student.lesu0022.thirtyv2.GameAssets.Dice;
import se.umu.student.lesu0022.thirtyv2.GameAssets.Game;
import se.umu.student.lesu0022.thirtyv2.Prefs;
import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-26.
 *
 * This popup appears when the user starts a new round. A button will be displayed in the middle of the screen,
 * and when the player clicks this button the new round will start and the die will be rolled.
 *
 * Should the orientation change, this view will hide depending on whether the game has started or not, as it
 * is only shown once (in the very first round).
 */

public class NewRoundPopup extends RelativeLayout {

    private Button btn_start;

    public NewRoundPopup(Context context) {
        super(context);
        init();
    }

    void init() {
       inflate(getContext(), R.layout.popup_newround, this);
        btn_start = findViewById(R.id.btn_start);


        setClickable(true);
        bringToFront();
       if(Prefs.GAME.getRollsLeft() < Prefs.GAME.getMax_rolls() || Prefs.GAME.getGameState() == Game.State.FINISHED) {
           setVisibility(View.GONE);
        }
    }

    public void rollAction() {
        for(Dice d : Prefs.GAME.getDie()) {
            d.setSelected(false);
        }
        Prefs.GAME.setGameState(Game.State.STARTED);
        setVisibility(View.GONE);
    }

    public Button getBtn_start() {
        return btn_start;
    }

}
