package se.umu.student.lesu0022.thirtyv2.Popups;

import android.content.Context;
import android.widget.Button;
import android.widget.RelativeLayout;

import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-30.
 *
 * This popup pops up when the user is asked if he/she wants to start a new game.
 */

public class NewGameQueryPopup extends RelativeLayout {

    private Button btn_yes;
    private Button btn_no;

    public NewGameQueryPopup(Context context) {
        super(context);
        init();
    }

    void init() {
        inflate(getContext(), R.layout.popup_newgamequery, this);
        btn_yes = findViewById(R.id.btn_newgamequery_yes);
        btn_no = findViewById(R.id.btn_newgamequery_no);

    }

    public Button getBtn_yes() {
        return btn_yes;
    }

    public Button getBtn_no() {
        return btn_no;
    }
}
