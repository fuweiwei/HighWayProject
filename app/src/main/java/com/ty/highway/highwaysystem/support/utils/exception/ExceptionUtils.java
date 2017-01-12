package com.ty.highway.highwaysystem.support.utils.exception;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.net.BaseNetworkHelper;
import com.ty.highway.highwaysystem.support.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 * @author fww
 * @desc 异常信息上传并删除异常文件
 */
public class ExceptionUtils {
	//用于格式化日期,作为日志文件名的一部分
	private SimpleDateFormat formatter = new SimpleDateFormat(Constants.FULL_DATE_FORMAT, Locale.CHINESE);
	/**
	 * 上传所有异常信息并删除异常文件
	 * @param mContext
	 * @param UserName
	 * @throws IOException
	 * @throws JSONException
	 */
	public void upAndDelFile(Context mContext,String UserName) throws IOException, JSONException{
		String path = Environment.getExternalStorageDirectory().getPath() + Constants.SD_EXCEPTION_PATH;
		File src = new File(path);
		upLoadAllFile(src, mContext, UserName);

	}
	/**
	 * 上传所有异常信息并删除异常文件
	 * @param file
	 * @param mContext
	 * @param UserName
	 * @throws IOException
	 * @throws JSONException
	 */
	public void upLoadAllFile(File file,Context mContext,String UserName) throws IOException, JSONException{
		if(file.isFile()){
			return;
		}
		File[] files = file.listFiles();
		if(files != null && files.length > 0) {
			for (File f : files) {
				if(f != null) {
					if(f.isDirectory()){
						upLoadAllFile(f, mContext, UserName);
					}else{
						final String name = f.getName();
						String time = "";
						try {
							time = name.split("_")[1];
						} catch (Exception e) {
							time = new SimpleDateFormat(Constants.DATE_FORMAT_TO_SECONDS_NO_LINE).format(new Date());
						}
						FileInputStream fis = new FileInputStream(f);
						InputStreamReader streamReader = new InputStreamReader(fis);
						BufferedReader reader = new BufferedReader(streamReader);
						String line = "";
						StringBuffer buffer = new StringBuffer();
						while((line = reader.readLine()) != null){
							buffer.append(line);
							buffer.append("\n");
						}
						/**地铁线基础信息获取*/
						HashMap<String, String> mMetroLineMap = new HashMap<String, String>();
						JSONArray array = new JSONArray();
						JSONObject obj = new JSONObject();
						obj.put("CustomerCode", Constants.EXCEPTION_CUSTOMERCODE);
						obj.put("SWCode", "JGJC");
						obj.put("Version", CommonUtils.getVersionName(mContext));
						obj.put("Account", UserName);
						obj.put("IMEI",  CommonUtils.getDeviceId(mContext));
						obj.put("SN",  CommonUtils.getSN(mContext));
						obj.put("ExceptionType", "");
						obj.put("ExceptionContent", buffer.toString());
						obj.put("ExceptionTime", time);
						array.put(obj);
						mMetroLineMap.put("ExceptionInfos", array.toString());
						BaseNetworkHelper .getInstance(new NetResquestListener() {
							@Override
							public void onSuccess(String response) {
								if (response.startsWith("{\"s\":\"Ok\"")){
									deleteEexcFile(name);
								}
							}

							@Override
							public void onError(String errormsg) {

							}
						},mContext).upLoadExecption(mMetroLineMap, Constants.METHOD_OF_COMMON_EXCEPTIONUP);
					}
				}
			}
		}
	}
	/**
	 * 上传某个异常信息
	 * @param fileName
	 * @param mContext
	 * @param content
	 * @throws JSONException
	 */
	public void upLoadFile(final String fileName,Context mContext,String content,String userName) throws JSONException{
		String	time;
		try {
			time = fileName.split("_")[1];
		} catch (Exception e) {
			time = new SimpleDateFormat(Constants.DATE_FORMAT_TO_SECONDS_NO_LINE).format(new Date());
		}
		/**地铁线基础信息获取*/
		HashMap<String, String> mMetroLineMap = new HashMap<String, String>();
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("CustomerCode", Constants.EXCEPTION_CUSTOMERCODE);
		obj.put("SWCode", "JGJC");
		obj.put("Version", CommonUtils.getVersionName(mContext));
		obj.put("Account", userName);
		obj.put("IMEI",  CommonUtils.getDeviceId(mContext));
		obj.put("SN",  CommonUtils.getSN(mContext));
		obj.put("ExceptionType", "");
		obj.put("ExceptionContent", content);
		obj.put("ExceptionTime", time);
		array.put(obj);
		mMetroLineMap.put("ExceptionInfos", array.toString());
		BaseNetworkHelper.getInstance(new NetResquestListener() {
			@Override
			public void onSuccess(String response) {
				Log.v("Test",response);
				if (response.startsWith("{\"s\":\"Ok\"")){
					deleteEexcFile(fileName);
				}
			}

			@Override
			public void onError(String errormsg) {

			}
		}, mContext).upLoadExecption(mMetroLineMap, Constants.METHOD_OF_COMMON_EXCEPTIONUP);
	}
	/**
	 * 删除异常信息
	 */
	public void deleteEexcFile(String fleName){
		String path = Environment.getExternalStorageDirectory().getPath() + Constants.SD_EXCEPTION_PATH+"/"+fleName;
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
	/**
	 * 将异常写入SD卡
	 */
	public void writeToSD(String fileName,String execString){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().getPath() + Constants.SD_EXCEPTION_PATH;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(execString.getBytes());
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对捕捉的异常进行处理
	 * @param execString
	 * @param context
	 */
	public  void doExecInfo(String execString,Context context){
		/*long timestamp = System.currentTimeMillis();
		String time = formatter.format(new Date());
		String fileName = "crash_" + time + "_" + timestamp + ".log";
		writeToSD(fileName,execString);
		if(!CommonUtils.isNetworkConnected(context)) {//无网
		}else {//有网
			try {
				upLoadFile(fileName,context,execString, PreferencesUtils.getString(context, Constants.SP_USER_NAME, ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}*/
	}
}
