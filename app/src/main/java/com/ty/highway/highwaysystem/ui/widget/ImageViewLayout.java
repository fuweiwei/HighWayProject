package com.ty.highway.highwaysystem.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ty.highway.highwaysystem.R;

/**
 * 自定义CheckBox和ImageView控件
 * Created by ${dzm} on 2015/9/16 0016.
 */
public class ImageViewLayout extends RelativeLayout{

    private CheckBox checkBox;
    private ImageView imageView;
    private ImageView imageStart;
    private RelativeLayout layoutContent;

    public ImageViewLayout(Context context) {
        super(context);
        init(context);
    }

    public ImageViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.imageview_layout, this);
        checkBox = (CheckBox) findViewById(R.id.image_view_layout_checkbox);
        imageView = (ImageView) findViewById(R.id.image_view_layout_imageView);
        imageStart = (ImageView) findViewById(R.id.image_view_layout_imageStart);
        layoutContent = (RelativeLayout) findViewById(R.id.layout_content);
    }

    public boolean isCheck() {
        return checkBox.isChecked();
    }
    public  void setCheck(boolean flag){
        checkBox.setChecked(flag);
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }

    public void hideImageView() {
        imageStart.setVisibility(View.GONE);
    }

    public void showImageView() {
        imageStart.setVisibility(View.VISIBLE);
    }

    public void hideCheckbox(){
        checkBox.setVisibility(View.GONE);
    }

    public void showCheckbox(){
        checkBox.setVisibility(View.VISIBLE);
    }

}
