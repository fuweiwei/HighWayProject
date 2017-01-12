package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ty.highway.frameworklibrary.support.percent.PercentLinearLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckPositionBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckPositionDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.utils.ImageUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.activity.other.VideoSurfaceActivity;
import com.ty.highway.highwaysystem.ui.widget.ImageViewLayout;

import java.io.File;
import java.lang.ref.SoftReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/2.
 * 查看病害信息activity
 */
public class LookDamageActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvType,mTvNum,mTvPostion,mTvdes,mTvjudge,mTvdisdesc,mTvRemarks,mTvCoentent;
    private CTDiseaseInfoBean mDiseaseInfo;
    private CTTunnelVsItemBean mBean;
    private Context mContext = this;
    private List<CTCheckPositionBean> mLvCheckPosition = new ArrayList<CTCheckPositionBean>();
    private LinearLayout mLayImg,mLayVideo;
    private List<CTDiseasePhotoBean> mListDiseasePhoto;
    private static final String PHOTOSUFFIX = ".jpg";//图片文件后缀
    private static final String VIDEOSUFFIX = ".mp4";//视屏文件后缀
    //图片Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> imageBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    //视频Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> videoBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_damage);
        initTitle();
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("病害详情");
        setTitleVisiable(View.VISIBLE);
    }
    public  void initView(){
        mTvType = (TextView) findViewById(R.id.activity_look_demage_type);
        mTvNum = (TextView) findViewById(R.id.activity_look_demage_num);
        mTvPostion = (TextView) findViewById(R.id.activity_look_demage_postion);
        mTvdes = (TextView) findViewById(R.id.activity_look_demage_des);
        mTvjudge = (TextView) findViewById(R.id.activity_look_demage_judge);
        mTvdisdesc = (TextView) findViewById(R.id.activity_look_demage_disdesc);
        mTvRemarks = (TextView) findViewById(R.id.activity_look_demage_remarks);
        mTvCoentent = (TextView) findViewById(R.id.activity_look_demage_content);
        mLayImg = (LinearLayout) findViewById(R.id.activity_look_damage_image_lin);
        mLayVideo = (LinearLayout) findViewById(R.id.activity_look_demage_video_lin);
        mDiseaseInfo = (CTDiseaseInfoBean) getIntent().getExtras().getSerializable("diseaseInfo");
        mBean = (CTTunnelVsItemBean) getIntent().getExtras().getSerializable("info");
        if(mDiseaseInfo==null||mBean==null){
            return;
        }
        mListDiseasePhoto = DBCTDiseasePhotoDao.getInstance(mContext).getAllByDisId(mDiseaseInfo.getGuid());
        mTvType.setText(mDiseaseInfo.getCheckType());
        mTvCoentent.setText(mDiseaseInfo.getBelongPro());
        String mileage = mDiseaseInfo.getMileage();
        if(!TextUtils.isEmpty(mileage)){
            double m  = Double.parseDouble(mileage);
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String sizeTemp = fnum.format(m % 1000);
            mileage =("K"+(int)m/1000+"+"+sizeTemp);
        }
        mTvNum.setText(mileage);
        mTvdes.setText(mDiseaseInfo.getCheckData());
        mTvdisdesc.setText(mDiseaseInfo.getDiseaseDescribe());
        mLvCheckPosition = DBCTCheckPositionDao.getInstance(mContext).getAllInfoById(mBean.getItemId());
        if(mLvCheckPosition!=null&&mLvCheckPosition.size()>0){
            mTvPostion.setText(DBCTCheckPositionDao.getInstance(mContext).getPosrionString(mDiseaseInfo.getCheckPostion()));
        }else{
            mTvPostion.setText("无");
        }
        mTvjudge.setText(mDiseaseInfo.getLevelContent());
        mTvRemarks.setText(mDiseaseInfo.getRemarks());
        addView();
    }
    public void addView(){
        mLayImg.removeAllViews();
        mLayVideo.removeAllViews();
        LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.topMargin = 10;
        params.bottomMargin = 10;
        for(CTDiseasePhotoBean bean:mListDiseasePhoto){
            File file = new File(bean.getPosition());
            if(file.exists()) {
                final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                Bitmap bitmap = null;
                if(PHOTOSUFFIX.equals(bean.getLatterName())){
                    bitmap = new ImageUtils().getZoomBmpByDecodePath(bean.getPosition(), 80, 80);
                    SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                    imageBitmapCache.add(softReference);
                    imageViewLayout.setTag(bean.getPosition());
                    imageViewLayout.setImageBitmap(softReference.get());
                    mLayImg.addView(imageViewLayout, params);
                    imageViewLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 用系统图片浏览器
                            Intent it = new Intent(Intent.ACTION_VIEW);
                            Uri mUri = Uri.parse("file://" + imageViewLayout.getTag());
                            it.setDataAndType(mUri, "image/*");
                            startActivity(it);
                        }
                    });
                }else if(VIDEOSUFFIX.equals(bean.getLatterName())){
                    bitmap = ThumbnailUtils.createVideoThumbnail(bean.getPosition(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                    videoBitmapCache.add(softReference);
                    imageViewLayout.setTag(bean.getPosition());
                    imageViewLayout.setImageBitmap(softReference.get());
                    imageViewLayout.showImageView();
                    mLayVideo.addView(imageViewLayout, params);
                    imageViewLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, VideoSurfaceActivity.class);
                            intent.putExtra("path",(String) imageViewLayout.getTag());
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        if(mLayImg.getChildCount()==0){
            TextView textView = new TextView(mContext);
            textView.setText("没有图片信息");
            textView.setTextColor(getResources().getColor(R.color.common_theme_color));
            mLayImg.addView(textView,params);
        }
        if(mLayVideo.getChildCount()==0){
            TextView textView = new TextView(mContext);
            textView.setText("没有视频信息");
            textView.setTextColor(getResources().getColor(R.color.common_theme_color));
            mLayVideo.addView(textView,params);
        }
    }
    //清除所有图片内存
    private void clearAllBitmapCache(ArrayList<SoftReference<Bitmap>> bitmapCache) {
        if (bitmapCache != null && bitmapCache.size() > 0) {
            //这里不能使用迭代器 否则ConcurrentModificationException
            for (int i = 0;i<bitmapCache.size();i++) {
                SoftReference<Bitmap> softReference = bitmapCache.get(i);
                if (softReference != null && softReference.get()!=null) {
                    softReference.get().recycle();
                    bitmapCache.remove(softReference);
                }
            }

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearAllBitmapCache(imageBitmapCache);
        clearAllBitmapCache(videoBitmapCache);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }
}
