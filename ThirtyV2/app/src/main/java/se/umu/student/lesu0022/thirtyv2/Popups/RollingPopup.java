package se.umu.student.lesu0022.thirtyv2.Popups;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import se.umu.student.lesu0022.thirtyv2.R;

/**
 * Created by leifthysellsundqvist on 2017-06-29.
 */

/**
 * The rollingPopup is the view that appears when the die are being rolled. It displays an image with an animationdrawable
 * of a text that shows the text "rolling..."
 */
public class RollingPopup extends RelativeLayout {

    private ImageView iv_rollinglogo;

    public RollingPopup(Context context) {
        super(context);
        init();
    }

    void init() {
        inflate(getContext(), R.layout.popup_rolling, this);

        setClickable(true);
        setVisibility(View.GONE);
        bringToFront();


        iv_rollinglogo = findViewById(R.id.iv_rolling);
        iv_rollinglogo.setBackgroundResource(R.drawable.rolling_animation);
        ((AnimationDrawable)iv_rollinglogo.getBackground()).start();

    }

}
