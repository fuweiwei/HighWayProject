package com.ty.highway.highwaysystem.ui.activity.machine;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ty.highway.frameworklibrary.support.percent.PercentLinearLayout;
import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineContentByDescriptBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDecisionLevelBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseaseBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineDiseasePhotoBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineItemByContentBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTaskBean;
import com.ty.highway.highwaysystem.support.bean.machine.ELMachineTypeByItemBean;
import com.ty.highway.highwaysystem.support.db.basedata.DBBTunnelDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineContentByDescriptDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDecisionLevelDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseaseDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineDiseasePhotoDao;
import com.ty.highway.highwaysystem.support.db.machine.DBELMachineItemByContentDao;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;
import com.ty.highway.highwaysystem.support.utils.FileUtils;
import com.ty.highway.highwaysystem.support.utils.ImageUtils;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.ToastUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;
import com.ty.highway.highwaysystem.ui.activity.basic.BaseActivity;
import com.ty.highway.highwaysystem.ui.activity.other.VideoSurfaceActivity;
import com.ty.highway.highwaysystem.ui.dialog.AddPictrueDialog;
import com.ty.highway.highwaysystem.ui.widget.ImageViewLayout;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fuweiwei on 2016/1/7.
 */
public class MachineAddDamageActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    private LinearLayout mLyImageContent,mLyVideoContent;
    private Button mBtnAddImage,mBtnDeleteImage,mBtnAddVideo,mBtnDeleteVideo,mBtnFinish;
    private ELMachineTaskBean mTaskInfo;
    private ELMachineTypeByItemBean mItemInfo;
    private ELMachineBean mMachineInfo;
    private Context mContext = MachineAddDamageActivity.this;
    private String mTunenlName;
    private String mGuid;//检修记录唯一id

    //拍照和视频
    private File mFile; // 照片或视屏文件
    private String mFileName; // 文件的名字
    private String mFilePath; // 存放照片和视屏的路径
    private static final String PHOTOSUFFIX = ".jpg";//图片文件后缀
    private static final String VIDEOSUFFIX = ".mp4";//视屏文件后缀
    private static final int CAMERA = 0x001;
    private static final int ALBUM = 0x002;
    private static final int VIDEO = 0x003;
    //图片Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> mImageBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    //视频Bitmap缓存
    private ArrayList<SoftReference<Bitmap>> mVideoBitmapCache = new ArrayList<SoftReference<Bitmap>>();
    //是否开启动画
    private boolean isStartPhotoAnim = false, isStartVideoAnim = false;
    //检修记录数据
    private Spinner mSpContent,mSpDesc,mSpJudge,mSpStep;
    private List<ELMachineItemByContentBean> mListContent = new ArrayList<>();
    private List <String> mListContentS = new ArrayList<>();
    private List<ELMachineContentByDescriptBean> mListDesc = new ArrayList<>();
    private List <String> mListDescS = new ArrayList<>();
    private List<ELMachineDecisionLevelBean> mListJudge = new ArrayList<>();
    private List <String> mListJudgeS = new ArrayList<>();
    private List<ELMachineDecisionLevelBean> mListStep = new ArrayList<>();
    private List <String> mListStepS = new ArrayList<>();
    private ArrayAdapter<String> mAdapterContent,mAdapterDesc,mAdapterJudge,mAdapterStep;
    private boolean isUser;
    private String mRemarks;
    private CheckBox mCbIsUse;
    private EditText mEtRemarks;
    private ELMachineDiseaseBean mELMachineDiseaseBean = new ELMachineDiseaseBean();
    private ELMachineDiseaseBean mELMachineDiseaseInfo;
    private List<ELMachineDiseasePhotoBean> mListDiseasePhoto;
    private boolean isLookDisease = false;


    private ArrayList<String> mPaths = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_add_damage);
        initTitle();
        if(getIntent()!=null){
            mTaskInfo = (ELMachineTaskBean) getIntent().getExtras().getSerializable("taskInfo");
            mItemInfo = (ELMachineTypeByItemBean) getIntent().getExtras().getSerializable("itemInfo");
            mMachineInfo = (ELMachineBean) getIntent().getExtras().getSerializable("machineInfo");
            mELMachineDiseaseInfo = (ELMachineDiseaseBean) getIntent().getExtras().getSerializable("diseaseInfo");
        }
        getData();
        initView();

    }
    public void initTitle(){
        setLTBtnText("返回");
        setLTBtnVisiable(View.VISIBLE);
        setRTBtnVisiable(View.INVISIBLE);
        setTitleText("添加检修记录");
        setTitleVisiable(View.VISIBLE);
    }
    public void getData() {
        if(mELMachineDiseaseInfo!=null){
            mGuid = mELMachineDiseaseInfo.getNewId();
            mListDiseasePhoto = DBELMachineDiseasePhotoDao.getInstance(mContext).getAllByDisId(mGuid);
            isLookDisease = true;
        }else{
            mGuid = CommonUtils.getGuid();
        }
        if(mTaskInfo!=null){
            mTunenlName = DBBTunnelDao.getInstance(mContext).getTunnelById(mTaskInfo.getTunnelId());
        }
        if (mItemInfo != null) {
            mListContent = DBELMachineItemByContentDao.getInstance(mContext).getDataByItemId(mItemInfo.getNewId());
            if(isLookDisease){
                mListContent= sortContent(mListContent,mELMachineDiseaseInfo.getMMachineContentId());
            }
            if(mListContent.size()==0){
                ToastUtils.show(mContext,"该检修项目没有检修内容");
                finish();
                return;
            }
            for (ELMachineItemByContentBean bean : mListContent) {
                mListContentS.add(bean.getMContentName());
            }
        }
        if (mListContent != null && mListContent.size() > 0) {
            mListDesc = DBELMachineContentByDescriptDao.getInstance(mContext).getDataByContentId(mListContent.get(0).getNewId());
            if(isLookDisease){
                mListDesc= sortDesc(mListDesc, mELMachineDiseaseInfo.getMMachineDescriptId());
            }
            for (ELMachineContentByDescriptBean bean : mListDesc) {
                mListDescS.add(bean.getMDescriptName());
            }
        }
        mListJudge = DBELMachineDecisionLevelDao.getInstance(mContext).getInfoByType("机电经常检查");
        for (ELMachineDecisionLevelBean bean : mListJudge) {
            mListJudgeS.add(bean.getName());
        }
        mListStep = DBELMachineDecisionLevelDao.getInstance(mContext).getInfoByType("机电养护检查");
        for (ELMachineDecisionLevelBean bean : mListStep) {
            mListStepS.add(bean.getName());
        }
        mAdapterContent = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListContentS);
        mAdapterDesc = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListDescS);
        mAdapterJudge = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListJudgeS);
        mAdapterStep = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mListStepS);
        mAdapterContent.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterDesc.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterJudge.setDropDownViewResource(R.layout.simple_spinner_item);
        mAdapterStep.setDropDownViewResource(R.layout.simple_spinner_item);
    }
    public List<ELMachineContentByDescriptBean> sortDesc(List<ELMachineContentByDescriptBean> list, String id) {
        List<ELMachineContentByDescriptBean> listInfo = new ArrayList<>();
        for (ELMachineContentByDescriptBean info : list) {
            if (info.getMMachineDescriptId().equals(id)) {
                listInfo.add(info);
            }
        }
        for (ELMachineContentByDescriptBean info : list) {
            if (!info.getMMachineDescriptId().equals(id)) {
                listInfo.add(info);
            }
        }
        return listInfo;
    }
    public List<ELMachineItemByContentBean> sortContent(List<ELMachineItemByContentBean> list, String id) {
        List<ELMachineItemByContentBean> listInfo = new ArrayList<>();
        for (ELMachineItemByContentBean info : list) {
            if (info.getMMachineContentId().equals(id)) {
                listInfo.add(info);
            }
        }
        for (ELMachineItemByContentBean info : list) {
            if (!info.getMMachineContentId().equals(id)) {
                listInfo.add(info);
            }
        }
        return listInfo;
    }
    public int getSortLevel(List<ELMachineDecisionLevelBean> list, String id) {
        int item=0;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getNewId().equals(id)) {
                item = i;
                break;
            }
        }
        return item;
    }
    public void initView(){
        mLyImageContent = (LinearLayout) findViewById(R.id.activity_machine_damage_image_content);
        mLyVideoContent = (LinearLayout) findViewById(R.id.activity_machine_damage_video_content);
        mBtnAddImage = (Button) findViewById(R.id.activity_machine_damage_btn_add_image);
        mBtnDeleteImage = (Button) findViewById(R.id.activity_machine_damage_btn_delete_image);
        mBtnAddVideo = (Button) findViewById(R.id.activity_machine_damage_btn_add_video);
        mBtnDeleteVideo = (Button) findViewById(R.id.activity_machine_damage_btn_delete_video);
        mBtnFinish = (Button) findViewById(R.id.activity_machine_damage_finish);
        mSpContent = (Spinner) findViewById(R.id.activity_machine_damage_sp_type);
        mSpDesc = (Spinner) findViewById(R.id.activity_machine_damage_sp_desc);
        mSpJudge = (Spinner) findViewById(R.id.activity_machine_damage_sp_judge);
        mSpStep= (Spinner) findViewById(R.id.activity_machine_damage_sp_step);
        mEtRemarks = (EditText) findViewById(R.id.activity_machine_damage_remarks);
        mCbIsUse = (CheckBox) findViewById(R.id.activity_machine_damage_isuse);
        mBtnAddImage.setOnClickListener(this);
        mBtnDeleteImage.setOnClickListener(this);
        mBtnAddVideo.setOnClickListener(this);
        mBtnDeleteVideo.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
        mSpContent.setAdapter(mAdapterContent);
        mSpDesc.setAdapter(mAdapterDesc);
        mSpJudge.setAdapter(mAdapterJudge);
        mSpStep.setAdapter(mAdapterStep);
        mSpContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mListDesc = DBELMachineContentByDescriptDao.getInstance(mContext).getDataByContentId(mListContent.get(i).getNewId());
                mListDescS.clear();
                for (ELMachineContentByDescriptBean bean : mListDesc) {
                    mListDescS.add(bean.getMDescriptName());
                }
                mAdapterDesc.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(isLookDisease){
            setTitleText("修改检修记录");
            mEtRemarks.setText(mELMachineDiseaseInfo.getRemark());
            if("1".equals(mELMachineDiseaseInfo.getIsUse())){
                mCbIsUse.setChecked(true);
            }else{
                mCbIsUse.setChecked(false);
            }
            if(isLookDisease){
                mSpJudge.setSelection(getSortLevel(mListJudge, mELMachineDiseaseInfo.getLevelId()));
                mSpStep.setSelection(getSortLevel(mListStep, mELMachineDiseaseInfo.getConservationMeasuresId()));
            }
            LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.topMargin = 10;
            params.bottomMargin = 10;
            for (ELMachineDiseasePhotoBean bean : mListDiseasePhoto) {
                File file = new File(bean.getPosition());
                if (file.exists()) {
                    final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                    Bitmap bitmap = null;
                    if (PHOTOSUFFIX.equals(bean.getLatterName())) {
                        bitmap = new ImageUtils().getZoomBmpByDecodePath(bean.getPosition(), 80, 80);
                        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                        mImageBitmapCache.add(softReference);
                        imageViewLayout.setTag(bean.getPosition());
                        imageViewLayout.setImageBitmap(softReference.get());
                        mPaths.add(bean.getPosition());
                        mLyImageContent.addView(imageViewLayout, params);
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
                    } else if (VIDEOSUFFIX.equals(bean.getLatterName())) {
                        bitmap = ThumbnailUtils.createVideoThumbnail(bean.getPosition(), MediaStore.Video.Thumbnails.MICRO_KIND);
                        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                        mVideoBitmapCache.add(softReference);
                        imageViewLayout.setTag(bean.getPosition());
                        imageViewLayout.setImageBitmap(softReference.get());
                        imageViewLayout.showImageView();
                        mLyVideoContent.addView(imageViewLayout, params);
                        imageViewLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mContext, VideoSurfaceActivity.class);
                                intent.putExtra("path", (String) imageViewLayout.getTag());
                                startActivity(intent);
                            }
                        });
                    }
                    imageViewLayout.setOnLongClickListener(this);
                }
            }
        }else{
            setTitleText("添加检修记录");
        }
    }
    public void openCamera(Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtils.show(context, "SD卡不可用");
            return;
        }
        String fileDir = null;
        fileDir = Environment.getExternalStorageDirectory().getPath() + Constants.SD_MACHINE_DAMAGE_PATH + mTaskInfo.getCheckNo()+"-"+mTunenlName +  "/"  + "病害ID：" + mGuid + "/";;
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        mFileName = new Date().getTime() + PHOTOSUFFIX;
        mFilePath = fileDir + mFileName;
        this.mFile = new File(mFilePath);
        if (!this.mFile.exists()) {
            try {
                this.mFile.createNewFile();
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
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(MachineAddDamageActivity.this.mFile));
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

    public void recordVideo(Context context) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            ToastUtils.show(context, "SD卡不可用");
            return;
        }
        String fileDir = Environment.getExternalStorageDirectory().getPath() +  Constants.SD_MACHINE_DAMAGE_PATH + mTaskInfo.getCheckNo()+"-"+mTunenlName +  "/"  + "病害ID：" + mGuid + "/";;
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        mFileName = new Date().getTime() + VIDEOSUFFIX;
        mFilePath = fileDir + mFileName;
        this.mFile = new File(mFilePath);
        if (!this.mFile.exists()) {
            try {
                this.mFile.createNewFile();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            if (mFile != null && mFile.exists()) {
                mFile.delete();
            }
            return;
        }
        if (CAMERA == requestCode) {
            if (mFile != null && mFile.exists()) {
                //图片存在 存储图片到本地 图片名、路径
                Bitmap bitmap = new ImageUtils().getZoomBmpByDecodePath(mFilePath, 80, 80);
                SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                mImageBitmapCache.add(softReference);
                LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                params.topMargin = 10;
                params.bottomMargin = 10;
                final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                imageViewLayout.setImageBitmap(softReference.get());
                imageViewLayout.setTag(mFilePath);
                mPaths.add(mFilePath);
                mLyImageContent.addView(imageViewLayout, params);
                clearAllAnimation(mLyImageContent);
                imageViewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 用系统图片浏览器
                        Intent it = new Intent(Intent.ACTION_VIEW);
                        Uri mUri = Uri.parse("file://" + imageViewLayout.getTag());
                        it.setDataAndType(mUri, "image/*");
                        startActivity(it);
                  /*      Intent intent = new Intent(AddDamageActivity.this, ShowImageActivity.class);
                        intent.putStringArrayListExtra("mPaths", mPaths);
                        intent.putExtra("currIndex", mPaths.indexOf(imageViewLayout.getTag()));
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
                if (mFile != null && mFile.exists()) {
                    //图片存在 保存图片到本地 图片名、路径
                    Bitmap bitmap = new ImageUtils().getZoomBmpByDecodePath(sourceFilePath, 80, 80);
                    SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
                    mImageBitmapCache.add(softReference);
                    LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = 10;
                    params.topMargin = 10;
                    params.bottomMargin = 10;
                    final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                    imageViewLayout.setImageBitmap(softReference.get());
                    imageViewLayout.setTag(mFilePath);
                    mPaths.add(mFilePath);
                    mLyImageContent.addView(imageViewLayout, params);
                    clearAllAnimation(mLyImageContent);
                    imageViewLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 用系统图片浏览器
                            Intent it = new Intent(Intent.ACTION_VIEW);
                            Uri mUri = Uri.parse("file://" + imageViewLayout.getTag());
                            it.setDataAndType(mUri, "image/*");
                            startActivity(it);
                            /*Intent intent = new Intent(AddDamageActivity.this, ShowImageActivity.class);
                            intent.putStringArrayListExtra("mPaths",mPaths);
                            intent.putExtra("currIndex", mPaths.indexOf(imageViewLayout.getTag()));
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
                mVideoBitmapCache.add(softReference);
                LinearLayout.LayoutParams params = new PercentLinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                params.topMargin = 10;
                params.bottomMargin = 10;
                final ImageViewLayout imageViewLayout = new ImageViewLayout(getApplicationContext());
                imageViewLayout.setImageBitmap(softReference.get());
                imageViewLayout.showImageView();
                imageViewLayout.setTag(mFilePath);
                mLyVideoContent.addView(imageViewLayout, params);
                clearAllAnimation(mLyVideoContent);
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
    /**
     * 保存图片至本地数据库
     */
    private void savePhoto(LinearLayout content) {
        for (int i = 0; i < content.getChildCount(); i++) {
            ImageViewLayout imageViewLayout = (ImageViewLayout) content.getChildAt(i);
            ELMachineDiseasePhotoBean diseasePhotoBean = new ELMachineDiseasePhotoBean();
            String path = (String) imageViewLayout.getTag();//文件路径
            String fileName = path.substring(path.lastIndexOf("/") + 1);//文件名称
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀
            diseasePhotoBean.setDiseaseGuid(mGuid);
            diseasePhotoBean.setGuid(CommonUtils.getGuid());
            diseasePhotoBean.setPosition(path);
            diseasePhotoBean.setName(fileName);
            diseasePhotoBean.setLatterName(fileSuffix);
            diseasePhotoBean.setUpdateState(0);
            diseasePhotoBean.setTaskId(mTaskInfo.getNewId());
            DBELMachineDiseasePhotoDao.getInstance(mContext).addData(diseasePhotoBean);
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.activity_machine_damage_btn_add_image:
                openCamera(mContext);
                break;
            case R.id.activity_machine_damage_btn_delete_image:
                boolean flag2 =false;
                for (int i = mLyImageContent.getChildCount() - 1; i >= 0; i--) {
                    ImageViewLayout imageViewLayout = (ImageViewLayout) mLyImageContent.getChildAt(i);
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
                                        for (int i = mLyImageContent.getChildCount() - 1; i >= 0; i--) {
                                            ImageViewLayout imageViewLayout = (ImageViewLayout) mLyImageContent.getChildAt(i);
                                            if (imageViewLayout != null && imageViewLayout.isCheck()) {
                                                //先清除动画，再清除缓存，然后移除视图，最后删除文件
                                                imageViewLayout.clearAnimation();
                                                clearBitmapCache(mImageBitmapCache, i);
                                                mLyImageContent.removeViewAt(i);
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
            case R.id.activity_machine_damage_btn_add_video:
                recordVideo(mContext);
                break;
            case R.id.activity_machine_damage_btn_delete_video:
                boolean flag =false;
                for (int i = mLyVideoContent.getChildCount() - 1; i >= 0; i--) {
                    ImageViewLayout imageViewLayout = (ImageViewLayout) mLyVideoContent.getChildAt(i);
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
                                        for (int i = mLyVideoContent.getChildCount() - 1; i >= 0; i--) {
                                            ImageViewLayout imageViewLayout = (ImageViewLayout) mLyVideoContent.getChildAt(i);
                                            if (imageViewLayout != null && imageViewLayout.isCheck()) {
                                                //先清除动画，再清除缓存，然后移除视图，最后删除文件
                                                imageViewLayout.clearAnimation();
                                                clearBitmapCache(mVideoBitmapCache, i);
                                                mLyVideoContent.removeViewAt(i);
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
            case R.id.activity_machine_damage_finish:
                if(isLookDisease){
                    AlertDialog.Builder build = new AlertDialog.Builder(this);
                    build.setTitle("注意")
                            .setMessage("是否保存内容吗？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            if (isLookDisease) {
                                                DBELMachineDiseaseDao.getInstance(mContext).clearDataByNewId(mGuid, PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
                                                DBELMachineDiseasePhotoDao.getInstance(mContext).clearDataById(mGuid);
                                                if (addDamage()) {
                                                    ToastUtils.show(mContext, "修改成功");
                                                    finish();
                                                }
                                            } else {
                                                if (addDamage()) {
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
                }else{
                    if(DBELMachineDiseaseDao.getInstance(mContext).isHasDiseaseByContent(mTaskInfo.getNewId(),mMachineInfo.getNewId(),mItemInfo.getMMachineItemId(),
                            mListContent.get(mSpContent.getSelectedItemPosition()).getMMachineContentId(),PreferencesUtils.getString(mContext, Constants.SP_USER_ID))){
                        ToastUtils.show(mContext,"该检查内容已经添加过了，您可以返回修改");
                        return;
                    }
                    if (addDamage()) {
                        ToastUtils.show(mContext, "添加成功");
                        finish();
                    }
                }

                break;
            default:
                break;
        }

    }
    @Override
    public void onLtBtnClick() {
        super.onLtBtnClick();
        finish();
    }

    public boolean addDamage(){
        mELMachineDiseaseBean.setNewId(mGuid);
        mRemarks = mEtRemarks.getText().toString().trim();
        if(mRemarks.length()>50){
            ToastUtils.show(mContext,"备注过长，请输入的字数小于50个");
            return false;
        }
        mELMachineDiseaseBean.setRemark(mRemarks);
        String contentName = mListContent.get(mSpContent.getSelectedItemPosition()).getMContentName();
        String contentId = mListContent.get(mSpContent.getSelectedItemPosition()).getMMachineContentId();
        String descId= mListDesc.get(mSpDesc.getSelectedItemPosition()).getMMachineDescriptId();
        String descS= mListDesc.get(mSpDesc.getSelectedItemPosition()).getMDescriptName();
        String judgeId = mListJudge.get(mSpJudge.getSelectedItemPosition()).getNewId();
        String judgeS = mListJudge.get(mSpJudge.getSelectedItemPosition()).getName();
        String stepS = mListStep.get(mSpStep.getSelectedItemPosition()).getNewId();
        mELMachineDiseaseBean.setContentString(contentName);
        mELMachineDiseaseBean.setMMachineContentId(contentId);
        if(mCbIsUse.isChecked()){
            mELMachineDiseaseBean.setIsUse("1");
        }else{
            mELMachineDiseaseBean.setIsUse("0");
        }
        mELMachineDiseaseBean.setMMachineDescriptId(descId);
        mELMachineDiseaseBean.setMMachineDeviceDId(mMachineInfo.getMMachineDeviceDId());
        mELMachineDiseaseBean.setDescripString(descS);
        mELMachineDiseaseBean.setLevelId(judgeId);
        mELMachineDiseaseBean.setLevelString(judgeS);
        mELMachineDiseaseBean.setMMachineItemId(mItemInfo.getMMachineItemId());
        mELMachineDiseaseBean.setItemString(mItemInfo.getMMachineItemName());
        mELMachineDiseaseBean.setMMachineDeviceId(mMachineInfo.getNewId());
        mELMachineDiseaseBean.setDeviceString(mMachineInfo.getDeviceName());
        mELMachineDiseaseBean.setTunnelId(mTaskInfo.getTunnelId());
        mELMachineDiseaseBean.setTunnelName(mTunenlName);
        mELMachineDiseaseBean.setUserId(PreferencesUtils.getString(mContext, Constants.SP_USER_ID));
        mELMachineDiseaseBean.setTaskId(mTaskInfo.getNewId());
        mELMachineDiseaseBean.setConservationMeasuresId(stepS);
        DBELMachineDiseaseDao.getInstance(mContext).addData(mELMachineDiseaseBean);
        //将图片保存到本地数据库
        savePhoto(mLyImageContent);
        savePhoto(mLyVideoContent);
        return true;
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
        if (view.getParent() == mLyImageContent) {
            if (isStartPhotoAnim) {
                clearAllAnimation(mLyImageContent);
                isStartPhotoAnim = false;
            } else {
                ToastUtils.show(mContext, "请勾选要删除的图片");
                startAllAnimation(operatingAnim, mLyImageContent);
                isStartPhotoAnim = true;
            }
        } else if (view.getParent() == mLyVideoContent) {
            if (isStartVideoAnim) {
                clearAllAnimation(mLyVideoContent);
                isStartVideoAnim = false;
            } else {
                ToastUtils.show(mContext, "请勾选要删除的视频");
                startAllAnimation(operatingAnim, mLyVideoContent);
                isStartVideoAnim = true;
            }
        }
        return true;
    }
}
