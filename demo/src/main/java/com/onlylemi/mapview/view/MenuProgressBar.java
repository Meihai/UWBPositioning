package com.onlylemi.mapview.view;

import android.content.Context;
import android.widget.ProgressBar;

import com.onlylemi.mapview.utils.ViewUtils;


/**
 * Created by renaud on 01/01/16.
 */
public class MenuProgressBar extends ProgressBar {

    public MenuProgressBar(Context context) {
        super(context);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(ViewUtils.dpToPx(48), ViewUtils.dpToPx(24));
    }
}
