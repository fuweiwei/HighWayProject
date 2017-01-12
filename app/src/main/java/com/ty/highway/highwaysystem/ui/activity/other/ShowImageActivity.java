package com.ty.highway.highwaysystem.ui.activity.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.utils.ImageUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.widget.TouchImageView;

import java.util.ArrayList;

/**
 * Created by ${dzm} on 2015/9/30 0030.
 */
public class ShowImageActivity extends BaseActivity {

    private ViewPager mViewPager;
    private ArrayList<String> mImgPaths = new ArrayList<String>();
    private ArrayList<ImageView> mImageViews = new ArrayList<ImageView>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private int currIndex;
    private TextView mTvTip;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        mImgPaths = getIntent().getStringArrayListExtra("paths");
        currIndex = getIntent().getIntExtra("currIndex", 0);
        if (mImgPaths.size() > 0) {
            for (String path : mImgPaths) {
                try {
                    Bitmap bitmap = ImageUtils.returnBmpByPath(path);
                    Bitmap newBitmap = ImageUtils.zoomBitmap(bitmap, 0.2f);
                    bitmaps.add(newBitmap);
                } catch (Exception e) {
                    new ExceptionUtils().doExecInfo(e.toString(), mContext);
                }

            }
        }

        mViewPager = (ViewPager) findViewById(R.id.activity_show_image_viewpage);
        mTvTip = (TextView) findViewById(R.id.activity_show_image_tip);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
               // mTvTip.setText(String.format("%d/%d", mViewPager.getCurrentItem()+1, mImgPaths.size()));
                TouchImageView imageView = new TouchImageView(getApplicationContext());
                imageView.setImageBitmap(bitmaps.get(position));
                container.addView(imageView);
                mImageViews.add(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {

                container.removeView((ImageView)object);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }


            @Override
            public int getCount() {
                return bitmaps.size();
            }
        });
        mViewPager.setCurrentItem(currIndex);

    }

    @Override
    public void finish() {
        super.finish();
        if (bitmaps.size() > 0) {
            for (Bitmap bitmap: bitmaps) {
                if(bitmap!=null && !bitmap.isRecycled()){
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            System.gc();
        }
    }
}
