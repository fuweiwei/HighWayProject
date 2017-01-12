package com.ty.highway.highwaysystem.ui.activity.check;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ty.highway.frameworklibrary.support.percent.PercentLinearLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.base.HWApplication;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckItemVsDiseaseTypeBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTCheckPositionBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseLevelBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeByDescriptBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTDiseaseTypeVsDamageDescBean;
import com.ty.highway.highwaysystem.support.bean.basedata.CTTunnelVsItemBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseaseInfoBean;
import com.ty.highway.highwaysystem.support.bean.check.CTDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.check.TaskInfoBean;
import com.ty.highway.highwaysystem.support.comparator.DamageLevelComparator;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckItemVsDiseaseTypeDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTCheckPositionDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDiseaseLevelDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDiseaseTypeByDescriptDao;
import com.ty.highway.highwaysystem.support.db.basedata.DBCTDiseaseTypeVsDamageDescDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseaseInfoDao;
import com.ty.highway.highwaysystem.support.db.check.DBCTDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.check.DBTaskDao;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.FileUtils;
import com.ty.highway.highwaysystem.support.utils.ImageUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.StringUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.activity.other.VideoSurfaceActivity;
import com.ty.highway.highwaysystem.ui.dialog.AddPictrueDialog;
import com.ty.highway.highwaysystem.ui.widget.ImageViewLayout;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fuweiwei on 2015/9/10.
 */
public class AddDamageActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int CAMERA = 0x001;
    private static final int ALBUM = 0x002;
    private static final int VIDEO = 0x003;

    private File file; // 照片或视屏文件
    private String mFileName; // 文件的名字
    private String mFilePath; // 存放照片和视屏的路径
    private static final String PHOTOSUFFIX = ".jpg";//图片文件后缀
    private static final String VIDEOSUFFIX = ".mp4";//视屏文件后缀
    private ArrayList<String> paths = new ArrayList<String>();
    private AlertDialog mDesDialog, mMieDialog, mExDialog, mImDialog;
    private LinearLayout imageContent,mLinJb;
    private Button btnAddImage;
    private Button btnDeleteImage;
    private LinearLayout videoContent;
    private Button btnAddVideo;
    private Button btnDeleteVideo;
    private Button mButFinish;
    private List<CTCheckItemVsDiseaseTypeBean> mLvDiseaseType = new ArrayList<CTCheckItemVsDiseaseTypeBean>();
    private List<CTCheckPositionBean> mLvCheckPosition = new ArrayList<CTCheckPositionBean>();
    private List<CTDiseaseTypeVsDamageDescBean> mLvDamageDesc = new ArrayList<CTDiseaseTypeVsDamageDescBean>();
    private List<CTDiseaseLevelBean> mLvDiseaseLeve = new ArrayList<CTDiseaseLevelBean>();
    private List<CTDiseaseLevelBean> mLvStep = new ArrayList<CTDiseaseLevelBean>();
    private List<CTDiseaseTypeByDescriptBean> mLvDisDesc = new ArrayList<>();
    private CTTunnelVsItemBean mBean;
    private Context mContext = this;
    private Spinner mSpType, mSpPostion, mSpLevel, mSpDesc,mSpStep;
    private EditText mEtMileage, mEtRemarks, mEtDisDesc, mEtImport,mEtExport;
    private List<String> mListType = new ArrayList<String>();
    private List<String> mListPostion = new ArrayList<String>();
    private List<String> mListLeve = new ArrayList<String>();
    private List<String> mListStep = new ArrayList<String>();
    private List<String> mListDesc = new ArrayList<String>();
    private TextView mTvJudge;
    private ArrayAdapter<String> mAdapterType, mAdapterPostion, mAdapterLevel,mAdapterStep, mAdapterDesc;
    //要上传的数据
    private String mUpItemId, mUpTypeId, mUpCheckData, mUpCheckPostion, mUpMileage, mUpJudgeLevel, mUpLevelContent,
            mUpCheckType, mUpTaskId, mUpRemarks, mUpTunnelId, mUpBelong, mUpNewId,mUpStepId;

    //图片Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> imageBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    //视频Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> videoBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    private String mTaskId;
    private TaskInfoBean mTaskInfo;
    private String tunnelName;
    private String mGuid;//病害唯一id
    private double mStartMileage, mEndMileage;
    private CTDiseaseInfoBean mDiseaseBean = new CTDiseaseInfoBean();
    private static final String TAG = "AddDamageActivity";
    //查看修改病害信息
    private CTDiseaseInfoBean mDiseaseInfo;
    private Boolean isLookDisease = false;
    private int isLookDiseaseFrist = 0;
    private List<CTDiseasePhotoBean> mListDiseasePhoto;
    //定期
    private PercentLinearLayout mDescLin, mPostionLin,mStepLin;
    //是否开启动画
    private boolean isStartPhotoAnim = false, isStartVideoAnim = false;
    //是否修改病害描述
    private boolean isChangeFlag = false;
    //定期病害描述输入的正则表达式
    private String mPattern = "\\-?\\d{1,8}(\\.\\d{1,2})?";
    private DecimalFormat mFnum = new DecimalFormat("##0.00");
    private boolean isHistroyDamage = false;//是否是历史病害

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_damage);
        initTitle();
        mDiseaseInfo = (CTDiseaseInfoBean) getIntent().getExtras().getSerializable("diseaseInfo");
        if (mDiseaseInfo != null) {
            isHistroyDamage = mDiseaseInfo.isRepeat();
            isLookDisease = true;
            mGuid = mDiseaseInfo.getGuid();
            mListDiseasePhoto = DBCTDiseasePhotoDao.getInstance(mContext).getAllByDisId(mDiseaseInfo.getGuid());
        } else {
            mGuid = CommonUtils.getGuid();
        }
        mBean = (CTTunnelVsItemBean) getIntent().getExtras().getSerializable("info");
        mTaskId = getIntent().getExtras().getString("taskId");
        mTaskInfo = DBTaskDao.getInstance(mContext).getTaskById(mTaskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        tunnelName = DBBTunnelDao.getInstance(mContext).getTunnelById(mTaskInfo.getTunnelId());
        mLvDiseaseType = DBCTCheckItemVsDiseaseTypeDao.getInstance(mContext).getAllInfoById(mBean.getNewId());
        if (mLvDiseaseType.size() == 0) {
            ToastUtils.show(mContext, "该检查项没有配置数据");
            finish();
            return;
        }
        if (isLookDisease) {
            mLvDiseaseType = sortType(mLvDiseaseType, mDiseaseInfo.getDiseaseTypeId());
        }

        mLvCheckPosition = DBCTCheckPositionDao.getInstance(mContext).getAllInfoById(mBean.getItemId());
        mLvDiseaseLeve = DBCTDiseaseLevelDao.getInstance(mContext).getAllInfoByType(HWApplication.getCheckNameType());
        if("经常检查".equals(HWApplication.getCheckNameType())){
            mLvStep = DBCTDiseaseLevelDao.getInstance(mContext).getAllInfoByType("经常养护措施");
        }
        Collections.sort(mLvDiseaseLeve, new DamageLevelComparator());
        mLvDamageDesc = DBCTDiseaseTypeVsDamageDescDao.getInstance(mContext).getAllInfoById(mLvDiseaseType.get(0).getNewId());
        mLvDisDesc = DBCTDiseaseTypeByDescriptDao.getInstance(mContext).getAllById(mLvDiseaseType.get(0).getDiseaseId());
        if (isLookDisease) {
            mLvCheckPosition = sortPosition(mLvCheckPosition, mDiseaseInfo.getCheckPostion());
            //  mLvDiseaseLeve = sortLevel(mLvDiseaseLeve, mDiseaseInfo.getJudgeLevel());
            mLvDamageDesc = sortDesc(mLvDamageDesc, mDiseaseInfo.getNewId());
        }

        for (CTCheckItemVsDiseaseTypeBean bean : mLvDiseaseType) {
            mListType.add(bean.getContentName());
        }
        for (CTCheckPositionBean bean : mLvCheckPosition) {
            mListPostion.add(bean.getRangeName());
        }
        for (CTDiseaseLevelBean bean : mLvDiseaseLeve) {
            mListLeve.add(bean.getName());
        }
        for (CTDiseaseLevelBean bean : mLvStep) {
            mListStep.add(bean.getName());
        }
        for (CTDiseaseTypeVsDamageDescBean bean : mLvDamageDesc) {
            mListDesc.add(bean.getDiseaseName());
        }
        initView();
    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("添加病害");
        setTitleVisiable(View.VISIBLE);
    }
    public List<CTCheckItemVsDiseaseTypeBean> sortType(List<CTCheckItemVsDiseaseTypeBean> list, String id) {
        List<CTCheckItemVsDiseaseTypeBean> listInfo = new ArrayList<>();
        for (CTCheckItemVsDiseaseTypeBean info : list) {
            if (info.getContentId().equals(id)) {
                listInfo.add(info);
            }
        }
        for (CTCheckItemVsDiseaseTypeBean info : list) {
            if (!info.getContentId().equals(id)) {
                listInfo.add(info);
            }
        }
        return listInfo;
    }

    public List<CTCheckPositionBean> sortPosition(List<CTCheckPositionBean> list, String id) {
        List<CTCheckPositionBean> listInfo = new ArrayList<>();
        for (CTCheckPositionBean info : list) {
            if (info.getNewId().equals(id)) {
                listInfo.add(info);
            }
        }
        for (CTCheckPositionBean info : list) {
            if (!info.getNewId().equals(id)) {
                listInfo.add(info);
            }
        }
        return listInfo;
    }

    public List<CTDiseaseLevelBean> sortLevel(List<CTDiseaseLevelBean> list, String id) {
        List<CTDiseaseLevelBean> listInfo = new ArrayList<>();
        for (CTDiseaseLevelBean info : list) {
            if (info.getNewId().equals(id)) {
                listInfo.add(info);
            }
        }
        for (CTDiseaseLevelBean info : list) {
            if (!info.getNewId().equals(id)) {
                listInfo.add(info);
            }
        }
        return listInfo;
    }
    public int getSortLevel(List<CTDiseaseLevelBean> list, String id) {
        int item=0;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getNewId().equals(id)) {
                item = i;
                break;
            }
        }
        return item;
    }
    public int getSortStep(List<CTDiseaseLevelBean> list, String id) {
        int item=0;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getNewId().equals(id)) {
                item = i;
                break;
            }
        }
        return item;
    }

    public List<CTDiseaseTypeVsDamageDescBean> sortDesc(List<CTDiseaseTypeVsDamageDescBean> list, String id) {
        List<CTDiseaseTypeVsDamageDescBean> listInfo = new ArrayList<>();
        for (CTDiseaseTypeVsDamageDescBean info : list) {
            if (info.getDiseaseId().equals(id)) {
                listInfo.add(info);
            }
        }
        for (CTDiseaseTypeVsDamageDescBean info : list) {
            if (!info.getDiseaseId().equals(id)) {
                listInfo.add(info);
            }
        }
        return listInfo;
    }

    public void initView() {
        videoContent = (LinearLayout) findViewById(R.id.activity_add_damage_video_content);
        imageContent = (LinearLayout) findViewById(R.id.activity_add_damage_image_content);
        btnAddImage = (Button) findViewById(R.id.activity_add_damage_btn_add_image);
        btnAddVideo = (Button) findViewById(R.id.activity_add_damage_btn_add_video);
        btnDeleteImage = (Button) findViewById(R.id.activity_add_damage_btn_delete_image);
        btnDeleteVideo = (Button) findViewById(R.id.activity_add_damage_btn_delete_video);
        mButFinish = (Button) findViewById(R.id.activity_add_damage_finish);
        mTvJudge = (TextView) findViewById(R.id.activity_add_demage_judge_tv);
        btnDeleteVideo.setOnClickListener(this);
        btnDeleteImage.setOnClickListener(this);
        btnAddVideo.setOnClickListener(this);
        btnAddImage.setOnClickListener(this);
        mButFinish.setOnClickListener(this);
        mSpType = (Spinner) findViewById(R.id.activity_add_demage_sptype);
        mSpPostion = (Spinner) findViewById(R.id.activity_add_demage_sppostion);
        mSpLevel = (Spinner) findViewById(R.id.activity_add_demage_spjudge);
        mSpStep = (Spinner) findViewById(R.id.activity_add_demage_spstep);
        mSpDesc = (Spinner) findViewById(R.id.activity_add_demage_spdes);
        mEtMileage = (EditText) findViewById(R.id.activity_add_demage_etnum);
        mEtRemarks = (EditText) findViewById(R.id.activity_add_demage_etremarks);
        mEtDisDesc = (EditText) findViewById(R.id.activity_add_demage_disdesc);
        mEtImport = (EditText) findViewById(R.id.activity_add_demage_et_import);
        mEtExport = (EditText) findViewById(R.id.activity_add_demage_et_export);
        mDescLin = (PercentLinearLayout) findViewById(R.id.activity_add_demage_desclin);
        mStepLin = (PercentLinearLayout) findViewById(R.id.activity_add_demage_steplin);
        mPostionLin = (PercentLinearLayout) findViewById(R.id.activity_add_demage_postionlin);
        mLinJb = (PercentLinearLayout) findViewById(R.id.activity_add_demage_linjb);
        mEtDisDesc.setOnClickListener(this);
        mEtDisDesc.setHint("点击输入");
        mEtDisDesc.setInputType(InputType.TYPE_NULL);
        mEtDisDesc.setText("");
        mEtMileage.setOnClickListener(this);
        mEtImport.setOnClickListener(this);
        mEtExport.setOnClickListener(this);
        if (HWApplication.getmCheckType() == Constants.CHECK_TYPE_OFTEN) {
            mTvJudge.setText("判定：");
        }
        if (isHistroyDamage) {
            mSpType.setClickable(false);
            mSpPostion.setClickable(false);
            ToastUtils.show(mContext, "历史病害不能修改类型、桩号、位置");
        } else {
            mSpType.setClickable(true);
            mSpPostion.setClickable(true);
        }
        if (mLvDisDesc != null && mLvDisDesc.size() > 0) {
            mDescLin.setVisibility(View.VISIBLE);
        } else {
            mDescLin.setVisibility(View.GONE);
        }
        if (mLvCheckPosition != null && mLvCheckPosition.size() == 0) {
            mPostionLin.setVisibility(View.GONE);
        } else {
            mPostionLin.setVisibility(View.VISIBLE);
        }
        if(mLvStep!=null&&mLvStep.size()>0){
            mStepLin.setVisibility(View.VISIBLE);
        }else{
            mStepLin.setVisibility(View.GONE);
        }
        String tunnelId;
        if (TextUtils.isEmpty(mBean.getTunnelId())) {
            tunnelId = DBTaskDao.getInstance(mContext).getTaskById(mTaskId, PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getTunnelId();
        } else {
            tunnelId = mBean.getTunnelId();
        }
        if (DBBTunnelDao.getInstance(mContext).getInfoById(tunnelId,PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getStartMileageNum() != 0) {
            mStartMileage = DBBTunnelDao.getInstance(mContext).getInfoById(tunnelId,PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getStartMileageNum();
            mEndMileage = DBBTunnelDao.getInstance(mContext).getInfoById(tunnelId,PreferencesUtils.getString(mContext, Constants.SP_USER_ID)).getEndMileageNum();
            mEtMileage.setHint("营运、局部桩号输一个即可");
            mLinJb.setVisibility(View.VISIBLE);
        }else{
            mEtMileage.setHint("点击输入");
            mLinJb.setVisibility(View.GONE);
        }
        mAdapterType = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListType);
        mAdapterPostion = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListPostion);
        mAdapterLevel = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListLeve);
        mAdapterStep = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListStep);
        mAdapterDesc = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListDesc);
        mAdapterPostion.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterLevel.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterType.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterStep.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterDesc.setDropDownViewResource(R.layout.simple_spinner_item);
        mSpType.setAdapter(mAdapterType);
        mSpPostion.setAdapter(mAdapterPostion);
        mSpLevel.setAdapter(mAdapterLevel);
        mSpDesc.setAdapter(mAdapterDesc);
        mSpStep.setAdapter(mAdapterStep);
        mSpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (0 == isLookDiseaseFrist++) {
                } else {
                    mLvDamageDesc = DBCTDiseaseTypeVsDamageDescDao.getInstance(mContext).getAllInfoById(mLvDiseaseType.get(i).getNewId());
                    mLvDisDesc = DBCTDiseaseTypeByDescriptDao.getInstance(mContext).getAllById(mLvDiseaseType.get(i).getDiseaseId());
                    if (mLvDisDesc != null && mLvDisDesc.size() > 0) {
                        mDescLin.setVisibility(View.VISIBLE);
                    } else {
                        mDescLin.setVisibility(View.GONE);
                    }
                    mListDesc.clear();
                    for (CTDiseaseTypeVsDamageDescBean bean : mLvDamageDesc) {
                        mListDesc.add(bean.getDiseaseName());
                    }
                    mAdapterDesc.notifyDataSetChanged();
                    //重置描述信息
                    if (isLookDisease && !isChangeFlag) {
                        mEtDisDesc.setText("");
                        mDiseaseInfo.setLengths(null);
                        mDiseaseInfo.setWidths(null);
                        mDiseaseInfo.setDeeps(null);

                        mDiseaseInfo.setAreas(null);
                        mDiseaseInfo.setAngles(null);
                        mDiseaseInfo.setCutCount(null);
                        mDiseaseInfo.setTheDeformation(null);
                        mDiseaseInfo.setErrorDeformation(null);
                        mDiseaseInfo.setPercentage(null);
                        mDiseaseInfo.setDirection(null);
                        mDiseaseInfo.setDType(null);
                        mDiseaseInfo.setHeight(null);
                        mDiseaseInfo.setTheRate(null);
                        mDiseaseInfo.setDiseaseRemark(null);

                    } else {
                        mEtDisDesc.setText("");
                        mDiseaseBean.setLengths(null);
                        mDiseaseBean.setWidths(null);
                        mDiseaseBean.setDeeps(null);
                        mDiseaseBean.setAreas(null);
                        mDiseaseBean.setAngles(null);
                        mDiseaseBean.setCutCount(null);
                        mDiseaseBean.setTheDeformation(null);
                        mDiseaseBean.setErrorDeformation(null);
                        mDiseaseBean.setPercentage(null);
                        mDiseaseBean.setDirection(null);
                        mDiseaseBean.setDType(null);
                        mDiseaseBean.setHeight(null);
                        mDiseaseBean.setTheRate(null);
                        mDiseaseBean.setDiseaseRemark(null);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (isLookDisease) {
            //设置病害等级
            mSpLevel.setSelection(getSortLevel(mLvDiseaseLeve, mDiseaseInfo.getJudgeLevel()));
            mSpStep.setSelection(getSortStep(mLvStep, mDiseaseInfo.getConservationMeasuresId()));
            String mileage = mDiseaseInfo.getMileage();
            if (!TextUtils.isEmpty(mileage)) {
                mEtMileage.setTag(mileage);
                double m = Double.parseDouble(mileage);
                String sizeTemp = mFnum.format(m % 1000);
                mileage = ("K" + (int) m / 1000 + "+" + sizeTemp);
                mEtImport.setText(mFnum.format(m - mStartMileage));
                mEtExport.setText(mFnum.format(mEndMileage-m));
                mEtMileage.setText(mileage);
            }
            mEtRemarks.setText(mDiseaseInfo.getRemarks());
            setTitleText("修改病害");
            mButFinish.setText("完成修改信息");
           /* StringBuffer stringBuffer = new StringBuffer();
            if(!TextUtils.isEmpty(mDiseaseInfo.getAngles())){
                stringBuffer.append("角度："+mDiseaseInfo.getAngles()+"，");
                mDiseaseBean.setAngles(mDiseaseInfo.getAngles());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getLengths())){
                stringBuffer.append("长度（米）："+mDiseaseInfo.getLengths()+"，");
                mDiseaseBean.setLengths(mDiseaseInfo.getLengths());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getWidths())){
                stringBuffer.append("宽度（毫米）：" + mDiseaseInfo.getWidths() + "，");
                mDiseaseBean.setWidths(mDiseaseInfo.getWidths());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getDeeps())){
                stringBuffer.append("深度（毫米）："+mDiseaseInfo.getDeeps() +"，");
                mDiseaseBean.setDeeps(mDiseaseInfo.getDeeps());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getAreas())){
                stringBuffer.append("面积（平方米）："+mDiseaseInfo.getAreas() +"，");
                mDiseaseBean.setAreas(mDiseaseInfo.getAreas());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getCutCount())){
                stringBuffer.append("数量（处/个）："+mDiseaseInfo.getCutCount()+"，");
                mDiseaseBean.setCutCount(mDiseaseInfo.getCutCount());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getTheDeformation())){
                stringBuffer.append("变形量（毫米）："+mDiseaseInfo.getTheDeformation()+"，");
                mDiseaseBean.setTheDeformation(mDiseaseInfo.getTheDeformation());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getErrorDeformation())){
                stringBuffer.append("错位量（毫米）："+mDiseaseInfo.getErrorDeformation()+"，");
                mDiseaseBean.setErrorDeformation(mDiseaseInfo.getErrorDeformation());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getPercentage())){
                stringBuffer.append("百分比（%）："+mDiseaseInfo.getPercentage()+"，");
                mDiseaseBean.setPercentage(mDiseaseInfo.getPercentage());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getDirection())){
                stringBuffer.append("裂缝走向："+mDiseaseInfo.getDirection()+"，");
                mDiseaseBean.setDirection(mDiseaseInfo.getDirection());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getDType())){
                stringBuffer.append("类型："+mDiseaseInfo.getDType() +"，");
                mDiseaseBean.setDType(mDiseaseInfo.getDType());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getHeight())){
                stringBuffer.append("高度（米）："+mDiseaseInfo.getHeight()+"，");
                mDiseaseBean.setHeight(mDiseaseInfo.getHeight());
            }
            if(!TextUtils.isEmpty(mDiseaseInfo.getTheRate())){
                stringBuffer.append("滴水速率（ml/分钟）："+mDiseaseInfo.getTheRate()+"，");
                mDiseaseBean.setTheRate(mDiseaseInfo.getTheRate());
            }*/
            mEtDisDesc.setText(mDiseaseInfo.getDiseaseDescribe());
            LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.topMargin = 10;
            params.bottomMargin = 10;
            for (CTDiseasePhotoBean bean : mListDiseasePhoto) {
                File file = new File(bean.getPosition());
                if (file.exists()) {
                    final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                    Bitmap bitmap = null;
                    if (PHOTOSUFFIX.equals(bean.getLatterName())) {
                        bitmap = new ImageUtils().getZoomBmpByDecodePath(bean.getPosition(), 80, 80);
                        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                        imageBitmapCache.add(softReference);
                        imageViewLayout.setTag(bean.getPosition());
                        imageViewLayout.setImageBitmap(softReference.get());
                        paths.add(bean.getPosition());
                        imageContent.addView(imageViewLayout, params);
                        imageViewLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 用系统图片浏览器
                                Intent it = new Intent(Intent.ACTION_VIEW);
                                Uri mUri = Uri.parse("file://" + imageViewLayout.getTag());
                                it.setDataAndType(mUri, "image/*");
                                startActivity(it);
                                /*Intent intent = new Intent(AddDamageActivity.this, ShowImageActivity.class);
                                intent.putStringArrayListExtra("paths",paths);
                                intent.putExtra("currIndex", paths.indexOf(imageViewLayout.getTag()));
                                startActivity(intent);*/
                            }
                        });
                    } else if (VIDEOSUFFIX.equals(bean.getLatterName())) {
                        bitmap = ThumbnailUtils.createVideoThumbnail(bean.getPosition(), MediaStore.Video.Thumbnails.MICRO_KIND);
                        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                        videoBitmapCache.add(softReference);
                        imageViewLayout.setTag(bean.getPosition());
                        imageViewLayout.setImageBitmap(softReference.get());
                        imageViewLayout.showImageView();
                        videoContent.addView(imageViewLayout, params);
                        imageViewLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               /* try{
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    Log.d(TAG,(String) imageViewLayout.getTag());
                                    intent.setDataAndType(Uri.parse((String) imageViewLayout.getTag()), "video/mp4");
                                    startActivity(intent);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }*/
                                Intent intent = new Intent(mContext, VideoSurfaceActivity.class);
                                intent.putExtra("path", (String) imageViewLayout.getTag());
                                startActivity(intent);
                            }
                        });
                    }
                    imageViewLayout.setOnLongClickListener(this);
                }
            }
        }
    }

    public void recordVideo(Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtils.show(context, "SD卡不可用");
            return;
        }
        String fileDir = Environment.getExternalStorageDirectory().getPath() + Constants.SD_DAMAGE_PATH + mTaskInfo.getCheckNo() + "-" + tunnelName + "/" + mBean.getItemName() + "/" + "病害ID：" + mGuid + "/";
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        mFileName = new Date().getTime() + VIDEOSUFFIX;
        mFilePath = fileDir + mFileName;
        this.file = new File(mFilePath);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                new ExceptionUtils().doExecInfo(e.toString(), mContext);
            }
        }
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //设置视频大小
        intent.putExtra(android.provider.MediaStore.EXTRA_SIZE_LIMIT, Constants.VIDEO_MAX_SIZE);
        //设置视频时间  毫秒单位
        intent.putExtra(android.provider.MediaStore.EXTRA_DURATION_LIMIT, Constants.VIDEO_MAX_TIME);
        startActivityForResult(intent, VIDEO);

    }


    public void openCamera(Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtils.show(context, "SD卡不可用");
            return;
        }
        String fileDir = null;
        if (mTaskInfo.getIsNearTask() == 1) {
            fileDir = Environment.getExternalStorageDirectory().getPath() + Constants.SD_DAMAGE_PATH + "临时任务-" + mTaskInfo.getTunnelId() + "/" + mBean.getItemName() + "/" + "病害ID：" + mGuid + "/";
        } else {
            fileDir = Environment.getExternalStorageDirectory().getPath() + Constants.SD_DAMAGE_PATH + mTaskInfo.getCheckNo() + "-" + tunnelName + "/" + mBean.getItemName() + "/" + "病害ID：" + mGuid + "/";
        }
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        mFileName = new Date().getTime() + PHOTOSUFFIX;
        mFilePath = fileDir + mFileName;
        this.file = new File(mFilePath);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                new ExceptionUtils().doExecInfo(e.toString(), mContext);
            }
        }

        AddPictrueDialog addPicDialog = new AddPictrueDialog(this);
        addPicDialog.setTitle("照片获取方式选择");
        addPicDialog.show();

        addPicDialog.setOnclickListener(new AddPictrueDialog.AddPicDialogActListener() {

            @Override
            public void onClickDialog(int listenerType) {

                Intent intent = null;
                switch (listenerType) {
                    case AddPictrueDialog.CARMERA: // 打开相机
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(AddDamageActivity.this.file));
                        startActivityForResult(intent, CAMERA);
                        break;
                    case AddPictrueDialog.ALBUM: // 打开相册
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, ALBUM);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            if (file != null && file.exists()) {
                file.delete();
            }
            return;
        }
        if (CAMERA == requestCode) {
            if (file != null && file.exists()) {
                //图片存在 存储图片到本地 图片名、路径
                Bitmap bitmap = new ImageUtils().getZoomBmpByDecodePath(mFilePath, 80, 80);
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                imageBitmapCache.add(softReference);
                LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                params.topMargin = 10;
                params.bottomMargin = 10;
                final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                imageViewLayout.setImageBitmap(softReference.get());
                imageViewLayout.setTag(mFilePath);
                paths.add(mFilePath);
                imageContent.addView(imageViewLayout, params);
                clearAllAnimation(imageContent);
                imageViewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 用系统图片浏览器
                        Intent it = new Intent(Intent.ACTION_VIEW);
                        Uri mUri = Uri.parse("file://" + imageViewLayout.getTag());
                        it.setDataAndType(mUri, "image/*");
                        startActivity(it);
                  /*      Intent intent = new Intent(AddDamageActivity.this, ShowImageActivity.class);
                        intent.putStringArrayListExtra("paths", paths);
                        intent.putExtra("currIndex", paths.indexOf(imageViewLayout.getTag()));
                        startActivity(intent);*/
                    }
                });
                imageViewLayout.setOnLongClickListener(this);
            }
        }
        if (ALBUM == requestCode) {
            // 把照片压缩保存到指定的文件下
            if (data != null) {
                Uri uri = data.getData();
                String sourceFilePath = getPath(uri);
                ImageUtils utils = new ImageUtils();
                //ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(utils.Bitmap2Bytes(utils.getZoomBmpByDecodePath(sourceFilePath, 480, 800)));
                //FileUtils.writeFile(mFilePath,arrayInputStream);
                File sourceFile = new File(sourceFilePath);
                if(!sourceFile.exists()){
                    ToastUtils.show(mContext,"文件不存在，请重新选择");
                    return;
                }
                FileUtils.copyFile(sourceFilePath, mFilePath);
                if (file != null && file.exists()) {
                    //图片存在 保存图片到本地 图片名、路径
                    Bitmap bitmap = new ImageUtils().getZoomBmpByDecodePath(sourceFilePath, 80, 80);
                    SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                    imageBitmapCache.add(softReference);
                    LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = 10;
                    params.topMargin = 10;
                    params.bottomMargin = 10;
                    final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                    imageViewLayout.setImageBitmap(softReference.get());
                    imageViewLayout.setTag(mFilePath);
                    paths.add(mFilePath);
                    imageContent.addView(imageViewLayout, params);
                    clearAllAnimation(imageContent);
                    imageViewLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 用系统图片浏览器
                            Intent it = new Intent(Intent.ACTION_VIEW);
                            Uri mUri = Uri.parse("file://" + imageViewLayout.getTag());
                            it.setDataAndType(mUri, "image/*");
                            startActivity(it);
                            /*Intent intent = new Intent(AddDamageActivity.this, ShowImageActivity.class);
                            intent.putStringArrayListExtra("paths",paths);
                            intent.putExtra("currIndex", paths.indexOf(imageViewLayout.getTag()));
                            startActivity(intent);*/
                        }
                    });
                    imageViewLayout.setOnLongClickListener(this);
                }
            }
        }

        if (VIDEO == requestCode) {
            Uri uri = data.getData();
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                //int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));
                //获取视屏路径
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                FileUtils.copyFile(filePath, mFilePath);
                //Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                //获取视屏第一个画面
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(mFilePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                videoBitmapCache.add(softReference);
                LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                params.topMargin = 10;
                params.bottomMargin = 10;
                final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                imageViewLayout.setImageBitmap(softReference.get());
                imageViewLayout.showImageView();
                imageViewLayout.setTag(mFilePath);
                videoContent.addView(imageViewLayout, params);
                clearAllAnimation(videoContent);
                imageViewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* Log.d(TAG,(String) imageViewLayout.getTag());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse((String) imageViewLayout.getTag()), "video/mp4");
                        startActivity(intent);*/
                        Intent intent = new Intent(mContext, VideoSurfaceActivity.class);
                        intent.putExtra("path", (String) imageViewLayout.getTag());
                        startActivity(intent);
                    }
                });
                imageViewLayout.setOnLongClickListener(this);
                cursor.close();
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 400};// 停止 开启
        vibrator.vibrate(pattern, -1);//设置重复
        //设置动画
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.image_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (view.getParent() == imageContent) {
            if (isStartPhotoAnim) {
                clearAllAnimation(imageContent);
                isStartPhotoAnim = false;
            } else {
                ToastUtils.show(mContext, "请勾选要删除的图片");
                startAllAnimation(operatingAnim, imageContent);
                isStartPhotoAnim = true;
            }
        } else if (view.getParent() == videoContent) {
            if (isStartVideoAnim) {
                clearAllAnimation(videoContent);
                isStartVideoAnim = false;
            } else {
                ToastUtils.show(mContext, "请勾选要删除的视频");
                startAllAnimation(operatingAnim, videoContent);
                isStartVideoAnim = true;
            }
        }
        return true;
    }

    public void showSure() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("注意")
                .setMessage("是否保存内容吗？")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (isLookDisease) {
                                    DBCTDiseaseInfoDao.getInstance(mContext).clearDataById(mGuid, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                    DBCTDiseasePhotoDao.getInstance(mContext).clearDataById(mGuid);
                                    if (upLoadData()) {
                                        ToastUtils.show(mContext, "修改成功");
                                        finish();
                                    }
                                } else {
                                    if (upLoadData()) {
                                        ToastUtils.show(mContext, "添加成功");
                                        finish();
                                    }
                                }
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                            }
                        }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if(!TextUtils.isEmpty(mEtMileage.getText().toString())){
                    showSure();
                }else{
                    finish();
                }
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onRtBtnClick() {
        super.onRtBtnClick();
    }

    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        if(!TextUtils.isEmpty(mEtMileage.getText().toString())){
            showSure();
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_add_damage_btn_add_image:
                openCamera(getApplicationContext());
                break;
            case R.id.activity_add_damage_btn_add_video:
                recordVideo(getApplicationContext());
                break;
            case R.id.activity_add_damage_btn_delete_video:
                boolean flag =false;
                for (int i = videoContent.getChildCount() - 1; i >= 0; i--) {
                    ImageViewLayout imageViewLayout = (ImageViewLayout) videoContent.getChildAt(i);
                    if (imageViewLayout != null && imageViewLayout.isCheck()) {
                        flag = true;
                    }
                }
                if(!flag){
                    ToastUtils.show(mContext,"请选择要删除的视频");
                    return;
                }
                AlertDialog.Builder buildV = new AlertDialog.Builder(this);
                buildV.setTitle("注意")
                        .setMessage("确定要删除选择的视频吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        for (int i = videoContent.getChildCount() - 1; i >= 0; i--) {
                                            ImageViewLayout imageViewLayout = (ImageViewLayout) videoContent.getChildAt(i);
                                            if (imageViewLayout != null && imageViewLayout.isCheck()) {
                                                //先清除动画，再清除缓存，然后移除视图，最后删除文件
                                                imageViewLayout.clearAnimation();
                                                clearBitmapCache(videoBitmapCache, i);
                                                videoContent.removeViewAt(i);
                                                File file = new File((String) imageViewLayout.getTag());
                                                file.delete();
                                            }

                                        }

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();


                break;
            case R.id.activity_add_damage_btn_delete_image:
                boolean flag2 =false;
                for (int i = imageContent.getChildCount() - 1; i >= 0; i--) {
                    ImageViewLayout imageViewLayout = (ImageViewLayout) imageContent.getChildAt(i);
                    if (imageViewLayout != null && imageViewLayout.isCheck()) {
                        flag2 = true;
                    }
                }
                if(!flag2){
                    ToastUtils.show(mContext,"请选择要删除的图片");
                    return;
                }
                AlertDialog.Builder buildI = new AlertDialog.Builder(this);
                buildI.setTitle("注意")
                        .setMessage("确定要删除选择的图片吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        for (int i = imageContent.getChildCount() - 1; i >= 0; i--) {
                                            ImageViewLayout imageViewLayout = (ImageViewLayout) imageContent.getChildAt(i);
                                            if (imageViewLayout != null && imageViewLayout.isCheck()) {
                                                //先清除动画，再清除缓存，然后移除视图，最后删除文件
                                                imageViewLayout.clearAnimation();
                                                clearBitmapCache(imageBitmapCache, i);
                                                imageContent.removeViewAt(i);
                                                File file = new File((String) imageViewLayout.getTag());
                                                file.delete();
                                            }

                                        }

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).show();

                break;
            case R.id.activity_add_damage_finish:
                if (isLookDisease) {
                    AlertDialog.Builder build = new AlertDialog.Builder(this);
                    build.setTitle("注意")
                            .setMessage("确定要修改病害信息吗？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            DBCTDiseaseInfoDao.getInstance(mContext).clearDataById(mGuid, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                            DBCTDiseasePhotoDao.getInstance(mContext).clearDataById(mGuid);
                                            if (upLoadData()) {
                                                ToastUtils.show(mContext, "修改成功");
                                                finish();
                                            }

                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                        }
                                    }).show();
                } else {
                    if (upLoadData()) {
                        ToastUtils.show(mContext, "添加成功");
                        finish();
                    }

                }
                break;
            case R.id.activity_add_demage_disdesc:
                LayoutInflater factory = LayoutInflater.from(mContext);
                final View disDescView = factory.inflate(R.layout.dialog_disdesc, null);
                final LinearLayout linearLayout = (LinearLayout) disDescView.findViewById(R.id.dialog_disdesc_lin);
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                for (CTDiseaseTypeByDescriptBean bean : mLvDisDesc) {
                    LinearLayout linearLayoutinfo = new LinearLayout(disDescView.getContext());
                    EditText et_value = new EditText(disDescView.getContext());
                    TextView tv_name = new TextView(disDescView.getContext());
                    String name = bean.getPropertyValue();
                    String unit = bean.getMeasuringUnit();
                    if (TextUtils.isEmpty(unit)) {
                        tv_name.setText(name + ":");
                    } else {
                        tv_name.setText(name + "(" + unit + "):");
                    }
                    tv_name.setTag(bean.getPropertyName());
                    CTDiseaseInfoBean infoBean = new CTDiseaseInfoBean();
                    if (isChangeFlag) {
                        infoBean = mDiseaseBean;
                    } else {
                        infoBean = mDiseaseInfo;
                    }
                    if ("Lengths".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getLengths());
                    } else if ("TheRate".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getTheRate());
                    } else if ("Widths".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getWidths());
                    } else if ("Deeps".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getDeeps());
                    } else if ("Areas".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getAreas());
                    } else if ("Angles".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_TEXT);
                        if (infoBean != null)
                            et_value.setText(infoBean.getAngles());
                    } else if ("CutCount".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getCutCount());
                    } else if ("TheDeformation".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getTheDeformation());
                    } else if ("ErrorDeformation".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getErrorDeformation());
                    } else if ("Percentage".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getPercentage());
                    } else if ("Direction".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_TEXT);
                        if (infoBean != null)
                            et_value.setText(infoBean.getDirection());
                    } else if ("DType".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_TEXT);
                        if (infoBean != null)
                            et_value.setText(infoBean.getDType());
                    } else if ("Height".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        if (infoBean != null)
                            et_value.setText(infoBean.getHeight());
                    }else if ("DiseaseRemark".equals(bean.getPropertyName())) {
                        et_value.setInputType(InputType.TYPE_CLASS_TEXT);
                        if (infoBean != null)
                            et_value.setText(infoBean.getDiseaseRemark());
                    }
                    linearLayoutinfo.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayoutinfo.addView(tv_name);
                    linearLayoutinfo.addView(et_value, lParams);
                    linearLayout.addView(linearLayoutinfo);

                }
                mDesDialog = new AlertDialog.Builder(mContext)
                        .setTitle("输入病害描述")
                        .setView(disDescView)
                        .create();
                mDesDialog.show();
                Button but1 = (Button) disDescView.findViewById(R.id.btn_sure);
                but1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isChangeFlag = true;
                        StringBuffer etString = new StringBuffer();
                        for (int j = 0; j < linearLayout.getChildCount(); j++) {
                            LinearLayout lyout = (LinearLayout) linearLayout.getChildAt(j);
                            TextView tv_infovalue = (TextView) lyout.getChildAt(0);
                            EditText et_infovalue = (EditText) lyout.getChildAt(1);
                            String type = (String) tv_infovalue.getTag();
                            String value = et_infovalue.getText().toString().trim();
                            Pattern p = Pattern.compile(mPattern);
                            Matcher m = p.matcher(value);
                            if (!TextUtils.isEmpty(value)) {
                                if ("Direction".equals(type) || "DType".equals(type)|| "DiseaseRemark".equals(type)) {
                                    if (!TextUtils.isEmpty(et_infovalue.getText().toString().trim())) {
                                        etString.append(tv_infovalue.getText().toString() + et_infovalue.getText().toString() + "，");
                                    }
                                } else {
                                    if (!m.matches()) {
                                        et_infovalue.setError("数目必须在f(8,2)范围内");
                                        mEtDisDesc.setText(etString);
                                        return;
                                    } else {
                                        if (!TextUtils.isEmpty(et_infovalue.getText().toString().trim())) {
                                            etString.append(tv_infovalue.getText().toString() + et_infovalue.getText().toString() + "，");
                                        }
                                    }
                                }
                            }
                            if ("Lengths".equals(type)) {
                                mDiseaseBean.setLengths(value);
                            } else if ("TheRate".equals(type)) {
                                mDiseaseBean.setTheRate(value);
                            } else if ("Widths".equals(type)) {
                                mDiseaseBean.setWidths(value);
                            } else if ("Deeps".equals(type)) {
                                mDiseaseBean.setDeeps(value);
                            } else if ("Areas".equals(type)) {
                                mDiseaseBean.setAreas(value);
                            } else if ("Angles".equals(type)) {
                                mDiseaseBean.setAngles(value);
                            } else if ("CutCount".equals(type)) {
                                mDiseaseBean.setCutCount(value);
                            } else if ("TheDeformation".equals(type)) {
                                mDiseaseBean.setTheDeformation(value);
                            } else if ("ErrorDeformation".equals(type)) {
                                mDiseaseBean.setErrorDeformation(value);
                            } else if ("Percentage".equals(type)) {
                                mDiseaseBean.setPercentage(value);
                            } else if ("Direction".equals(type)) {
                                mDiseaseBean.setDirection(value);
                            } else if ("DType".equals(type)) {
                                mDiseaseBean.setDType(value);
                            } else if ("Height".equals(type)) {
                                mDiseaseBean.setHeight(value);
                            } else if ("DiseaseRemark".equals(type)) {
                                mDiseaseBean.setDiseaseRemark(value);
                            }
                        }
                        mEtDisDesc.setText(etString);
                        mDesDialog.dismiss();
                    }
                });
                Button but2 = (Button) disDescView.findViewById(R.id.btn_cancle);
                but2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDesDialog.dismiss();
                    }
                });

                break;
            case R.id.activity_add_demage_etnum:
                if (isHistroyDamage) {
                    return;
                }
                LayoutInflater factoryMie = LayoutInflater.from(mContext);
                final View disDescViewMie = factoryMie.inflate(R.layout.dialog_disdesc, null);
                final LinearLayout linearLayout1 = (LinearLayout) disDescViewMie.findViewById(R.id.dialog_disdesc_lin);
                TextView k = new TextView(v.getContext());
                k.setText("   K");
                TextView add = new TextView(v.getContext());
                add.setText("+");
                final EditText editText1 = new EditText(v.getContext());
                final EditText editText2 = new EditText(v.getContext());
                String mie = (String) mEtMileage.getTag();
                if (!TextUtils.isEmpty(mie)) {
                    editText1.setText((int) (Double.parseDouble(mie) / 1000) + "");
                    String sizeTemp = mFnum.format(Double.parseDouble(mie) % 1000);
                    editText2.setText(sizeTemp);
                }
                editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout1.addView(k);
                linearLayout1.addView(editText1, layoutParam);
                linearLayout1.addView(add);
                linearLayout1.addView(editText2, layoutParam);
                String sizeTemp1 = "K" +(int)(mStartMileage / 1000) + "+" +mFnum.format(mStartMileage % 1000 );
                String sizeTemp2 ="K" + (int)(mEndMileage / 1000) + "+" +mFnum.format(mEndMileage % 1000);
                mMieDialog = new AlertDialog.Builder(mContext)
                        .setTitle("隧道桩号：" + sizeTemp1 + " — " + sizeTemp2)
                        .setView(disDescViewMie)
                        .create();
                mMieDialog.show();
                Button butMie1 = (Button) disDescViewMie.findViewById(R.id.btn_sure);
                butMie1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s1 = editText1.getText().toString().trim();
                        String s2 = editText2.getText().toString().trim();
                        Pattern p = Pattern.compile(mPattern);
                        Matcher m1 = p.matcher(s1);
                        Matcher m2 = p.matcher(s2);
                        if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)) {
                            if (!m1.matches()) {
                                editText1.setError("数目必须在f(8,2)范围内");
                                return;

                            }
                            if (!m2.matches()) {
                                editText2.setError("数目必须在f(8,2)范围内");
                                return;

                            }
                        } else {
                            ToastUtils.show(mContext, "请输入里程");
                            return;
                        }
                        int etString1 = Integer.parseInt(s1);
                        double etString2 = Double.parseDouble(s2);
                        double mm = etString1 * 1000 + etString2;
                        if (mStartMileage <= 0) {
                            mEtMileage.setText("K" + etString1 + "+" + etString2);
                            mEtMileage.setTag(mm + "");
                            mEtMileage.setError(null);
                        } else {
                            if (mStartMileage - mm > 100 || mm - mEndMileage > 100) {
                                ToastUtils.show(mContext, "输入里程不能超过里程范围100米");
                                return;
                            } else {
                                mEtMileage.setText("K" + etString1 + "+" + etString2);
                                mEtMileage.setTag(mm + "");
                                mEtMileage.setError(null);
                                mEtImport.setText(mFnum.format(mm - mStartMileage));
                                mEtExport.setText(mFnum.format(mEndMileage - mm));
                            }
                        }
                        mMieDialog.dismiss();
                    }
                });
                Button butEt2 = (Button) disDescViewMie.findViewById(R.id.btn_cancle);
                butEt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMieDialog.dismiss();
                    }
                });
                break;
            case R.id.activity_add_demage_et_export:
                if (isHistroyDamage) {
                    return;
                }
                if(mStartMileage<=0){
                    ToastUtils.show(mContext,"临时任务不能选择局部桩号");
                    return;
                }
                LayoutInflater factoryEx = LayoutInflater.from(mContext);
                final View disDescViewEx = factoryEx.inflate(R.layout.dialog_disdesc, null);
                final LinearLayout linearLayoutc = (LinearLayout) disDescViewEx.findViewById(R.id.dialog_disdesc_lin);
                LinearLayout.LayoutParams layoutParamc = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textViewc = new TextView(v.getContext());
                textViewc.setText("   距离出口：");
                final EditText editTextc = new EditText(v.getContext());
                editTextc.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editTextc.setText(mEtExport.getText().toString());
                linearLayoutc.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutc.addView(textViewc);
                linearLayoutc.addView(editTextc, layoutParamc);
                mExDialog = new AlertDialog.Builder(mContext)
                        .setTitle("距离出口范围：" + mFnum.format(mEndMileage - mStartMileage) + "  --   -100 ")
                        .setView(disDescViewEx)
                        .create();
                mExDialog.show();
                Button butEx1 = (Button) disDescViewEx.findViewById(R.id.btn_sure);
                butEx1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s1 = editTextc.getText().toString().trim();
                        Pattern p = Pattern.compile(mPattern);
                        Matcher m1 = p.matcher(s1);
                        if (!TextUtils.isEmpty(s1) ) {
                            if (!m1.matches()) {
                                editTextc.setError("数目必须在f(8,2)范围内");
                                return;

                            }
                        } else {
                            editTextc.setError("请输入里程");
                            return;
                        }
                        double dou = Double.parseDouble(s1);
                        if(dou<-100||dou>(mEndMileage-mStartMileage)){
                            editTextc.setError("输入的数目不在里程范围内");
                            return;
                        }else{
                            mEtExport.setText(s1);
                            mEtImport.setText(mFnum.format((mEndMileage - mStartMileage) - dou));
                            double doub = mEndMileage-dou;
                            mEtMileage.setTag(doub + "");
                            String sizeTemp = mFnum.format(doub% 1000);
                            mEtMileage.setText("K" + (int) doub / 1000 + "+" + sizeTemp);

                        }
                        mExDialog.dismiss();
                    }
                });
                Button butEx2 = (Button) disDescViewEx.findViewById(R.id.btn_cancle);
                butEx2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExDialog.dismiss();
                    }
                });
                break;
            case R.id.activity_add_demage_et_import:
                if (isHistroyDamage) {
                    return;
                }
                if(mStartMileage<=0){
                    ToastUtils.show(mContext,"临时任务不能选择局部桩号");
                    return;
                }
                LayoutInflater factoryIm = LayoutInflater.from(mContext);
                final View disDescViewIm = factoryIm.inflate(R.layout.dialog_disdesc, null);
                final LinearLayout linearLayoutj = (LinearLayout) disDescViewIm.findViewById(R.id.dialog_disdesc_lin);
                LinearLayout.LayoutParams layoutParamj = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textViewj = new TextView(v.getContext());
                textViewj.setText("   距离进口：");
                final EditText editTextj = new EditText(v.getContext());
                editTextj.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editTextj.setText(mEtImport.getText().toString());
                linearLayoutj.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutj.addView(textViewj);
                linearLayoutj.addView(editTextj, layoutParamj);
                mImDialog = new AlertDialog.Builder(mContext)
                        .setTitle("距离进口范围：-100  --  "+mFnum.format(mEndMileage-mStartMileage))
                        .setView(disDescViewIm)
                        .create();
                mImDialog.show();
                Button butIm1 = (Button) disDescViewIm.findViewById(R.id.btn_sure);
                butIm1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s1 = editTextj.getText().toString().trim();
                        Pattern p = Pattern.compile(mPattern);
                        Matcher m1 = p.matcher(s1);
                        if (!TextUtils.isEmpty(s1)) {
                            if (!m1.matches()) {
                                editTextj.setError("数目必须在f(8,2)范围内");
                                return;

                            }
                        } else {
                            editTextj.setError("请输入里程");
                            return;
                        }
                        double dou = Double.parseDouble(s1);

                        if (dou < -100 || dou > (mEndMileage - mStartMileage)) {
                            editTextj.setError("输入的数目不在里程范围内");
                            return;
                        } else {
                            mEtImport.setText(s1);
                            mEtExport.setText(mFnum.format((mEndMileage - mStartMileage) - dou));
                            double doub = mStartMileage + dou;
                            mEtMileage.setTag(doub + "");
                            String sizeTemp = mFnum.format(doub % 1000);
                            mEtMileage.setText("K" + (int) doub / 1000 + "+" + sizeTemp);

                        }
                        mImDialog.dismiss();
                    }
                });
                Button butIm2 = (Button) disDescViewIm.findViewById(R.id.btn_cancle);
                butIm2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mImDialog.dismiss();
                    }
                });
                break;
        }
    }
    //隐藏软键盘
    public void hideInput() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                (AddDamageActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 上传数据
     */
    public boolean upLoadData() {
        mUpMileage = mEtMileage.getText().toString().trim();
        if (StringUtils.isEmpty(mUpMileage)) {
            mEtMileage.setError("请输入里程");
            return false;
        } else {
            mUpMileage = (String) mEtMileage.getTag();
        }
               /* if(mileage>mStartMileage&&mileage<mEndMileage){

                }else{
                    ToastUtils.show(mContext,"输入的里程不在范围里");
                    break;
                }*/
        mUpItemId = mBean.getItemId();
        mUpTaskId = mTaskId;
        mUpTunnelId = mTaskInfo.getTunnelId();
        mUpCheckData = mLvDamageDesc.get(mSpDesc.getSelectedItemPosition()).getDiseaseName();
        mUpCheckType = mLvDiseaseType.get(mSpType.getSelectedItemPosition()).getContentName();
        mUpTypeId = mLvDiseaseType.get(mSpType.getSelectedItemPosition()).getContentId();
        if (mLvCheckPosition != null && mLvCheckPosition.size() > 0) {
            mUpCheckPostion = mLvCheckPosition.get(mSpPostion.getSelectedItemPosition()).getNewId();
        }
        if (mLvDiseaseLeve != null && mLvDiseaseLeve.size() > 0) {
            mUpJudgeLevel = mLvDiseaseLeve.get(mSpLevel.getSelectedItemPosition()).getNewId();
            mUpLevelContent = mLvDiseaseLeve.get(mSpLevel.getSelectedItemPosition()).getName();
        }
        if(mLvStep != null && mLvStep.size() > 0){
            mUpStepId = mLvStep.get(mSpStep.getSelectedItemPosition()).getNewId();
        }
        mUpNewId = mLvDamageDesc.get(mSpDesc.getSelectedItemPosition()).getDiseaseId();
        mUpRemarks = mEtRemarks.getText().toString();
        if(mUpRemarks.length()>50){
            ToastUtils.show(mContext,"备注过长，请输入的字数小于50个");
            return false;
        }
        mUpBelong = mBean.getItemName();
        mDiseaseBean.setDiseaseTypeId(mUpTypeId);
        mDiseaseBean.setNewId(mUpNewId);
        mDiseaseBean.setItemId(mUpItemId);
        mDiseaseBean.setTunnelId(mUpTunnelId);
        mDiseaseBean.setBelongPro(mUpBelong);
        mDiseaseBean.setCheckData(mUpCheckData);
        mDiseaseBean.setCheckPostion(mUpCheckPostion);
        mDiseaseBean.setCheckType(mUpCheckType);
        mDiseaseBean.setJudgeLevel(mUpJudgeLevel);
        mDiseaseBean.setLevelContent(mUpLevelContent);
        mDiseaseBean.setMileage(mUpMileage);
        mDiseaseBean.setRemarks(mUpRemarks);
        mDiseaseBean.setTaskId(mUpTaskId);
        mDiseaseBean.setGuid(mGuid);
        mDiseaseBean.setCheckItemId(mBean.getNewId());
        if (mTaskInfo.getIsNearTask() == 1) {
            mDiseaseBean.setIsNearDisease(1);
        } else {
            mDiseaseBean.setIsNearDisease(0);
        }
        mDiseaseBean.setDiseaseDescribe(mEtDisDesc.getText().toString());
        if (mDiseaseInfo != null) {
            mDiseaseBean.setIsRepeat(mDiseaseInfo.isRepeat());
            mDiseaseBean.setIsRepeatId(mDiseaseInfo.getIsRepeatId());
        }
        mDiseaseBean.setConservationMeasuresId(mUpStepId);
        DBCTDiseaseInfoDao.getInstance(mContext).addData(mDiseaseBean, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        //将图片保存到本地数据库
        savePhoto(imageContent);
        savePhoto(videoContent);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearAllBitmapCache(imageBitmapCache);
        clearAllBitmapCache(videoBitmapCache);
    }

    //获取拍照时图片路径
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //清除指定图片内存
    private void clearBitmapCache(ArrayList<SoftReference<Bitmap>> bitmapCache, int index) {
        if (bitmapCache != null && bitmapCache.size() > 0) {
            SoftReference<Bitmap> softReference = bitmapCache.get(index);
            if (softReference != null && softReference.get() != null) {
                softReference.get().recycle();
                bitmapCache.remove(index);
            }
        }

    }

    //清除所有图片内存
    private void clearAllBitmapCache(ArrayList<SoftReference<Bitmap>> bitmapCache) {
        if (bitmapCache != null && bitmapCache.size() > 0) {
            //这里不能使用迭代器 否则ConcurrentModificationException
            for (int i = 0; i < bitmapCache.size(); i++) {
                SoftReference<Bitmap> softReference = bitmapCache.get(i);
                if (softReference != null && softReference.get() != null) {
                    softReference.get().recycle();
                    bitmapCache.remove(softReference);
                }
            }

        }
    }

    /**
     * 开启所有动画
     */
    private void startAllAnimation(Animation animation, LinearLayout content) {
        for (int i = content.getChildCount() - 1; i >= 0; i--) {
            ImageViewLayout imageViewLayout = (ImageViewLayout) content.getChildAt(i);
            imageViewLayout.showCheckbox();
            imageViewLayout.startAnimation(animation);
        }
    }

    /**
     * 清楚所有动画
     */
    private void clearAllAnimation(LinearLayout content) {
        for (int i = 0; i < content.getChildCount(); i++) {
            ImageViewLayout imageViewLayout = (ImageViewLayout) content.getChildAt(i);
            imageViewLayout.hideCheckbox();
            imageViewLayout.setCheck(false);
            imageViewLayout.clearAnimation();
        }
    }

    /**
     * 保存图片至本地数据库
     */
    private void savePhoto(LinearLayout content) {
        for (int i = 0; i < content.getChildCount(); i++) {
            ImageViewLayout imageViewLayout = (ImageViewLayout) content.getChildAt(i);
            CTDiseasePhotoBean diseasePhotoBean = new CTDiseasePhotoBean();
            String path = (String) imageViewLayout.getTag();//文件路径
            String fileName = path.substring(path.lastIndexOf("/") + 1);//文件名称
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀
            Log.v(TAG, path);
            Log.v(TAG, fileName);
            Log.v(TAG, fileSuffix);
            diseasePhotoBean.setDiseaseGuid(mGuid);
            diseasePhotoBean.setGuid(CommonUtils.getGuid());
            diseasePhotoBean.setPosition(path);
            diseasePhotoBean.setName(fileName);
            diseasePhotoBean.setLatterName(fileSuffix);
            diseasePhotoBean.setUpdateState(0);
            diseasePhotoBean.setTaskId(mTaskId);
            DBCTDiseasePhotoDao.getInstance(mContext).addData(diseasePhotoBean);
        }
    }

}