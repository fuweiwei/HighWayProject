package com.ty.highway.highwaysystem.ui.activity.machine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ty.highway.frameworklibrary.support.percent.PercentLinearLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDecisionLevelDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.utils.ImageUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.activity.other.VideoSurfaceActivity;
import com.ty.highway.highwaysystem.ui.widget.ImageViewLayout;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuweiwei on 2015/11/2.
 * 查看病害信息activity
 */
public class MachineLookDamageActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvStep,mTvdesc,mTvjudge,mTvRemarks,mTvCoentent,mTvIsUse;
    private ELMachineDiseaseBean mDiseaseInfo;
    private Context mContext = this;
    private LinearLayout mLayImg,mLayVideo;
    private List<ELMachineDiseasePhotoBean> mListDiseasePhoto;
    private static final String PHOTOSUFFIX = ".jpg";//图片文件后缀
    private static final String VIDEOSUFFIX = ".mp4";//视屏文件后缀
    //图片Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> imageBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    //视频Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> videoBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_look_damage);
        initTitle();
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("检修详情");
        setTitleVisiable(View.VISIBLE);
    }
    public  void initView(){
        mTvjudge = (TextView) findViewById(R.id.activity_machine_look_damage_judge);
        mTvRemarks = (TextView) findViewById(R.id.activity_machine_look_damage_remark);
        mTvCoentent = (TextView) findViewById(R.id.activity_machine_look_damage_content);
        mTvdesc = (TextView) findViewById(R.id.activity_machine_look_damage_desc);
        mTvIsUse = (TextView) findViewById(R.id.activity_machine_look_damage_isuse);
        mTvStep = (TextView) findViewById(R.id.activity_machine_look_damage_step);
        mLayImg = (LinearLayout) findViewById(R.id.activity_machine_look_damage_image_lin);
        mLayVideo = (LinearLayout) findViewById(R.id.activity_machine_look_demage_video_lin);
        mDiseaseInfo = (ELMachineDiseaseBean) getIntent().getExtras().getSerializable("diseaseInfo");
        if(mDiseaseInfo==null){
            return;
        }
        mListDiseasePhoto = DBELMachineDiseasePhotoDao.getInstance(mContext).getAllByDisId(mDiseaseInfo.getNewId());
        mTvCoentent.setText(mDiseaseInfo.getContentString());
        mTvjudge.setText(mDiseaseInfo.getLevelString());
        mTvRemarks.setText(mDiseaseInfo.getRemark());
        mTvdesc.setText(mDiseaseInfo.getDescripString());
        mTvStep.setText(DBELMachineDecisionLevelDao.getInstance(mContext).getNameById(mDiseaseInfo.getConservationMeasuresId()));
       if("1".equals(mDiseaseInfo.getIsUse())){
           mTvIsUse.setText("已使用");
       }else{
           mTvIsUse.setText("未使用");
       }
        addView();
    }
    public void addView(){
        mLayImg.removeAllViews();
        mLayVideo.removeAllViews();
        LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.topMargin = 10;
        params.bottomMargin = 10;
        for(ELMachineDiseasePhotoBean bean:mListDiseasePhoto){
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
            mLayVideo.addView(textView, params);
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
