package com.example.ngratzi.nicecatchtiger;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class InstaAutoComplete extends AutoCompleteTextView {

    public InstaAutoComplete(Context context) {
        super(context);
    }

    public InstaAutoComplete(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public InstaAutoComplete(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering(getText(), 0);
        }
    }

}