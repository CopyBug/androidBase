package com.sx.widget.view.floatWIndow.floatingview;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.sx.widget.R;


/**
 * @ClassName EnFloatingView
 * @Description 悬浮窗
 * @Author Yunpeng Li
 * @Creation 2018/3/15 下午5:04
 * @Mender Yunpeng Li
 * @Modification 2018/3/15 下午5:04
 */
public class EnFloatingView extends FloatingMagnetView {

//    private final ImageView mIcon;
//    private TextView float_api;
    private String  imageUrl;



    public EnFloatingView(Context context) {
        this(context, R.layout.float_window);
    }

    public EnFloatingView(Context context, int resource) {
        super(context, null);
        inflate(context, resource, this);
/*        mIcon = findViewById(R.id.ic_welfare_flow);
        float_api = findViewById(R.id.float_api);
        float_api.setText("内测版本");*/

//        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
        /*Glide.with(this)
                .load(R.mipmap.ic_bug)
                .circleCrop()
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(mIcon);*/


    }

    FragmentActivity activity;

    public void setActivity(Activity activity) {
        this.activity = (FragmentActivity) activity;
//        mDialog = new PortSelectDialog.Builder(this.activity);
    }
}
