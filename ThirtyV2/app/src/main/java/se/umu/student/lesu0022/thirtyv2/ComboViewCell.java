package se.umu.student.lesu0022.thirtyv2;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import se.umu.student.lesu0022.thirtyv2.GameAssets.Combination;
import se.umu.student.lesu0022.thirtyv2.GameAssets.Dice;

/**
 * Created by leifthysellsundqvist on 2017-06-28.
 *
 * This class corresponds to the custom cells within the activity_selectcombo layout. Each custom cell
 * represents a combination, and consists of a textview for name, a layout in the middle in which
 * the sequence of die comprising the combo will be shown, and a textview showing the number of points
 * that the combo is worth.
 *
 * If the combo is worth no points, make it greyed out
 */

public class ComboViewCell extends LinearLayout {

    private Combination combination;
    private TextView tv_comboname;
    private LinearLayout ll_comboSequence;
    private TextView tv_combopoints;

    public ComboViewCell(Context context, Combination combination) {
        super(context);
        this.combination = combination;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.customcell_combo, this);
        tv_comboname = findViewById(R.id.tv_comboname);
        ll_comboSequence =  findViewById(R.id.ll_combodie);
        tv_combopoints = findViewById(R.id.tv_combopoints);

        tv_comboname.setText(combination.getName());

        //Worth no points -> not selectable
        if(combination.getPoints() <= 0) {
            setAlpha(0.3f);
            TextView tv_nocombo = new TextView(getContext());
            tv_nocombo.setText("<No combo>");
            tv_nocombo.setTextSize(24);
            ll_comboSequence.addView(tv_nocombo);
        }

        //For each die within the subsets of this combination, assign a series of dice images within
        // the linear layout corresponding to the dice face
        for(ArrayList<Integer> subset : combination.getDieSequence()) {

            for(Integer number : subset) {
                ImageView iv = new ImageView(getContext());
                iv.setImageResource(Dice.SIDES[number - 1]);

                LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                iv.setLayoutParams(lp);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setAdjustViewBounds(true);
                ll_comboSequence.addView(iv);
            }

            //if the current subset of the combination is not the last, append a "+" character for looks.
            if(subset != combination.getDieSequence().get(combination.getDieSequence().size() - 1)) {

                TextView tv_plus = new TextView(getContext());
                tv_plus.setText(" + ");
                tv_plus.setTextSize(14);
                tv_plus.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ll_comboSequence.addView(tv_plus);
            }

        }
        tv_combopoints.setText("" + combination.getPoints());

    }

    public Combination getCombination() {
        return combination;
    }
}
