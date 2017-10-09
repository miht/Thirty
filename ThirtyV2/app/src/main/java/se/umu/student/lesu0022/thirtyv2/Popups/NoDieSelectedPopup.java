package se.umu.student.lesu0022.thirtyv2.Popups;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-30.
 *
 * This popup will be shown if the user attempts to roll the die without having selected any die.
 */

public class NoDieSelectedPopup extends RelativeLayout {

    private Button btn_close;

    public NoDieSelectedPopup(Context context) {
        super(context);
        init();
    }

    void init() {
        inflate(getContext(), R.layout.norolls_popup, this);
        btn_close = (Button) findViewById(R.id.btn_norolls_close);

        setVisibility(View.GONE);
        setClickable(false);
        bringToFront();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(View.GONE);
            }
        });

    }

    public Button getBtn_close() {
        return btn_close;
    }
}
