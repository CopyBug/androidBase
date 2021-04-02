package com.sx.widget.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

import androidx.annotation.RequiresApi;

public class AddressListView extends ExpandableListView {
    public AddressListView(Context context) {
        super(context);
    }

    public AddressListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddressListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void scroll(int groupIndex, int childIndex) {
        if(groupIndex==0){
            childIndex=0;
        }
        int position = 0;
        for (int i = 0; i < groupIndex; i++) {
            position++;
            if (isGroupExpanded(i)){
                position = position + getExpandableListAdapter().getChildrenCount(i);
            }
        }
        position++;
        position = position + childIndex;
        super.smoothScrollToPosition(position);
    }
}
