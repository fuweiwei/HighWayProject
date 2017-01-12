package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ty.highway.frameworklibrary.support.percent.PercentLinearLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.check.CTHistroyDiseasePhotoResultBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckPositionDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetAsyncTask;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.FileUtils;
import com.ty.highway.highwaysystem.support.utils.ImageUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fuweiwei on 2015/9/10.
 *
 */
public class HistroyDamageActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTvType,mTvNum,mTvPostion,mTvdes,mTvjudge,mTvdisdesc,mTvRemarks,mTvCoentent,mTvCheckWay,mTvCheckDate,mCheckMan;
    private Button mButAlter;
    private CTHistroyDiseaseInfoBean mHistroyDiseaseInfo;
    private LinearLayout mPlImage;
    private Context mContext = this;
    private final ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private List<CTHistroyDiseasePhotoBean> mList;
    private CTDiseaseInfoBean mDiseaseInfo;
    private CTTunnelVsItemBean mBean;
    private String mTaskId;
    private TaskInfoBean mTaskInfo;
    /*图片存储位置*/
    private static final String savePath = Environment.getExternalStorageDirectory().getPath()+Constants.SD_HISTROYDAMAGE_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        setContentView(R.layout.activity_histroy_damage_info);
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("历史病害详情");
        setTitleVisiable(View.VISIBLE);
    }
    public  void initView(){
        mTvType = (TextView) findViewById(R.id.activity_histroy_demage_type);
        mTvNum = (TextView) findViewById(R.id.activity_histroy_demage_num);
        mTvPostion = (TextView) findViewById(R.id.activity_histroy_demage_postion);
        mTvdes = (TextView) findViewById(R.id.activity_histroy_demage_des);
        mTvjudge = (TextView) findViewById(R.id.activity_histroy_demage_judge);
        mTvdisdesc = (TextView) findViewById(R.id.activity_histroy_demage_disdesc);
        mTvRemarks = (TextView) findViewById(R.id.activity_histroy_demage_remarks);
        mTvCoentent = (TextView) findViewById(R.id.activity_histroy_demage_content);
        mTvCheckWay = (TextView) findViewById(R.id.activity_histroy_demage_checkway);
        mTvCheckDate = (TextView) findViewById(R.id.activity_histroy_demage_date);
        mCheckMan = (TextView) findViewById(R.id.activity_histroy_demage_checkman);
        mButAlter = (Button) findViewById(R.id.activity_histroy_damage_but);
        mPlImage = (LinearLayout) findViewById(R.id.activity_histroy_damage_imagelin);
        mButAlter.setOnClickListener(this);
        mHistroyDiseaseInfo = (CTHistroyDiseaseInfoBean) getIntent().getExtras().getSerializable("diseaseInfo");
        mBean = (CTTunnelVsItemBean) getIntent().getExtras().getSerializable("checkItemInfo");
        mTaskId = getIntent().getExtras().getString("taskId");
        mTaskInfo = DBTaskDao.getInstance(mContext).getTaskById(mTaskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        if(mHistroyDiseaseInfo ==null){
            return;
        }
        mTvType.setText(mHistroyDiseaseInfo.getContentName());
        mTvCoentent.setText(mHistroyDiseaseInfo.getItemName());
        mTvNum.setText(mHistroyDiseaseInfo.getStartMileageNum());
        mTvdes.setText(mHistroyDiseaseInfo.getDiseaseName());
        if(TextUtils.isEmpty(mHistroyDiseaseInfo.getDiseaseDescribe())){
            mTvdisdesc.setText("无");
        }else{
            mTvdisdesc.setText(mHistroyDiseaseInfo.getDiseaseDescribe());
        }
        if(TextUtils.isEmpty(mHistroyDiseaseInfo.getSpaceId())){
            mTvPostion.setText("无");
        }else{
            mTvPostion.setText( DBCTCheckPositionDao.getInstance(mContext).getPosrionString(mHistroyDiseaseInfo.getSpaceId()));
        }
        mTvjudge.setText(mHistroyDiseaseInfo.getLevelName());
        mTvRemarks.setText(mHistroyDiseaseInfo.getRemark());
        mTvCheckDate.setText(mHistroyDiseaseInfo.getCheckDate());
        mTvCheckWay.setText(HWApplication.getCheckNameType());
        mCheckMan.setText(mHistroyDiseaseInfo.getCheckName());
        if(CommonUtils.isNetworkConnected(mContext)){
            getImageData(mHistroyDiseaseInfo.getNewId());
        }else{
            TextView textView = new TextView(mContext);
            textView.setText("没有网络连接，无法查看图片");
            textView.setTextColor(getResources().getColor(R.color.common_theme_color));
            mPlImage.addView(textView);
        }
    }

    /**
     * 获取图片
     * @param newId 病害id
     */
    public  void getImageData(String newId){
        showSpotsDialog("正在加载...",false);
        String key = PreferencesUtils.getString(mContext, Constants.SP_LOGIN_KEY, Constants.KEY);
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapName = new HashMap<String, String>();
        HashMap<String, String> mapType = new HashMap<String, String>();
        map.put("key", key);
        map.put("objId", newId);
        mapName.put("methodName", Constants.METHOD_GETPHOTODATA);
        mapType.put("urlType", Constants.SERVICEURL_TYPE_CHECK);
        BaseNetAsyncTask asyncTask = new BaseNetAsyncTask(new NetResquestListener() {
            @Override
            public void onSuccess(String response) {
                if(response != null){
                    if(response.contains("\"r\":\"ok")) {
                        CTHistroyDiseasePhotoResultBean resultbean = new Gson().fromJson(response, CTHistroyDiseasePhotoResultBean.class);
                        List<CTHistroyDiseasePhotoBean> list = resultbean.getS();
                        mList = list;
                        addPic(list);
                    }else{
                        mPlImage.removeAllViews();
                        TextView textView = new TextView(mContext);
                        textView.setText("没有图片信息");
                        textView.setTextColor(getResources().getColor(R.color.common_theme_color));
                        mPlImage.addView(textView);
                    }
                }
                hideSpotsDialog();
            }

            @Override
            public void onError(String errormsg) {
                hideSpotsDialog();
                ToastUtils.show(mContext,errormsg);
            }
        },mContext);
        asyncTask.execute(map, mapName, mapType);
    }

    /**
     * 添加图片
     * @param list
     */
    public void addPic(List<CTHistroyDiseasePhotoBean> list){
        if(list!=null&&list.size()>0) {
            mPlImage.removeAllViews();
            LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.topMargin = 10;
            params.bottomMargin = 10;
            TextView textView = new TextView(mContext);
            textView.setText("病害图片：");
            mPlImage.addView(textView);
            for (final CTHistroyDiseasePhotoBean info : list) {
                final String picUrl = savePath + info.getNewId() + info.getExtensionName();
                if (FileUtils.isFileExist(picUrl)) {
                    Bitmap bitmap = new ImageUtils().getZoomBmpByDecodePath(picUrl, 100, 100);
                    ImageView img = new ImageView(mContext);
                    img.setTag(info.getNewId());
                    img.setImageBitmap(bitmap);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent it = new Intent(Intent.ACTION_VIEW);
                            Uri mUri = Uri.parse("file://" + picUrl);
                            it.setDataAndType(mUri, "image/*");
                            startActivity(it);
                        }
                    });
                    mPlImage.addView(img, params);
                } else {
                    final ImageView img = new ImageView(mContext);
                    img.setTag(info.getNewId());
                    img.setImageResource(R.drawable.ic_photo_light);
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtils.show(mContext, "正在下载图片...");
                            mExecutorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    downloadPic(img, info.getDocumentPath(), picUrl);
                                }
                            });

                        }
                    });
                    mPlImage.addView(img, params);
                }
            }
        }else{
            return;
        }
    }
    public  void downloadPic(ImageView img,String url,String file){
        try {
            URL picUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) picUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int length = conn.getContentLength();
            InputStream isInputStream = conn.getInputStream();
            if( FileUtils.writeFile(file,isInputStream)){
                sendMessage(Constants.SUCCESS);
            }else{
                sendMessage(Constants.ERRER);
            }

        } catch (MalformedURLException e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        } catch (ProtocolException e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        } catch (IOException e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_histroy_damage_but:
                mDiseaseInfo = new CTDiseaseInfoBean();
                mDiseaseInfo.setIsRepeat(true);
                mDiseaseInfo.setJudgeLevel(mHistroyDiseaseInfo.getLevelId());
                mDiseaseInfo.setLengths(String.valueOf(mHistroyDiseaseInfo.getLengths()));
                mDiseaseInfo.setTaskId(mTaskId);
                mDiseaseInfo.setRemarks(mHistroyDiseaseInfo.getRemark());
                mDiseaseInfo.setCheckItemId(mBean.getNewId());
                mDiseaseInfo.setAngles(String.valueOf(mHistroyDiseaseInfo.getAngles()));
                if (mTaskInfo.getIsNearTask() == 1) {
                    mDiseaseInfo.setIsNearDisease(1);
                } else {
                    mDiseaseInfo.setIsNearDisease(0);
                }
                mDiseaseInfo.setAreas(String.valueOf(mHistroyDiseaseInfo.getAreas()));
                mDiseaseInfo.setBelongPro(mHistroyDiseaseInfo.getItemName());
                mDiseaseInfo.setCheckData(mHistroyDiseaseInfo.getDiseaseName());
                mDiseaseInfo.setCheckPostion(mHistroyDiseaseInfo.getSpaceId());
                mDiseaseInfo.setCheckType(mHistroyDiseaseInfo.getContentName());
                mDiseaseInfo.setCutCount(String.valueOf(mHistroyDiseaseInfo.getCutCount()));
                mDiseaseInfo.setDeeps(String.valueOf(mHistroyDiseaseInfo.getDeeps()));
                mDiseaseInfo.setDirection(mHistroyDiseaseInfo.getDirection());
                mDiseaseInfo.setDiseaseTypeId(mHistroyDiseaseInfo.getContentId());
                mDiseaseInfo.setDType(mHistroyDiseaseInfo.getDType());
                mDiseaseInfo.setErrorDeformation(String.valueOf(mHistroyDiseaseInfo.getErrorDeformation()));
                mDiseaseInfo.setGuid(CommonUtils.getGuid());
                mDiseaseInfo.setHeight(String.valueOf(mHistroyDiseaseInfo.getHeight()));
                mDiseaseInfo.setIsRepeatId(mHistroyDiseaseInfo.getNewId());
                mDiseaseInfo.setLevelContent(mHistroyDiseaseInfo.getLevelName());
                mDiseaseInfo.setMileage(mHistroyDiseaseInfo.getEndMileageNum());
                mDiseaseInfo.setNewId(mHistroyDiseaseInfo.getDiseaseId());
                mDiseaseInfo.setPercentage(String.valueOf(mHistroyDiseaseInfo.getPercentage()));
                mDiseaseInfo.setTheDeformation(String.valueOf(mHistroyDiseaseInfo.getTheDeformation()));
                mDiseaseInfo.setTheRate(String.valueOf(mHistroyDiseaseInfo.getTheRate()));
                mDiseaseInfo.setTunnelId(mTaskInfo.getTunnelId());
                mDiseaseInfo.setWidths(String.valueOf(mHistroyDiseaseInfo.getWidths()));
                mDiseaseInfo.setItemId(mHistroyDiseaseInfo.getItemId());
                mDiseaseInfo.setDiseaseDescribe(mHistroyDiseaseInfo.getDiseaseDescribe());
                Intent intent = new Intent(mContext, AddDamageActivity.class);
                intent.putExtra("diseaseInfo", mDiseaseInfo);
                intent.putExtra("taskId", mTaskId);
                intent.putExtra("info", mBean);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void handleOtherMessage(int flag) {
        super.handleOtherMessage(flag);
        switch (flag){
            case Constants.SUCCESS:
                addPic(mList);
                break;
            case Constants.ERRER:
                ToastUtils.show(mContext,"下载失败，请重试");
                break;
        }
    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }
}
