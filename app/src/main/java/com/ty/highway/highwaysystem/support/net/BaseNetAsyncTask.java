package com.ty.highway.highwaysystem.support.net;

import android.content.Context;
import android.os.AsyncTask;

import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuweiwei on 2015/9/7.
 * 网络请求异步操作类，会直接在主线程更新UI，等同于Handler+Thread机制
 */
public class BaseNetAsyncTask extends AsyncTask<HashMap<String,String>,Object,String>{
    private NetResquestListener mListener;
    private Context mContext;
    public BaseNetAsyncTask(NetResquestListener listener,Context context) {
        super();
        this.mListener = listener;
        mContext = context.getApplicationContext();
    }

    public  String  getUrl(){
        String url = "http://"+ PreferencesUtils.getString(mContext,Constants.SP_WEBURL,Constants.WEBURL)+"/WebService/" ;
        return url;
    }
    @Override
    protected String doInBackground(HashMap<String,String>[] params) {
        String result = null;
        HashMap<String,String> mapParams = params[0];
        HashMap<String,String> mapName = params[1];
        HashMap<String,String> mapType = params[2];
        String methodName = mapName.get("methodName");
        String urlType = mapType.get("urlType");
        SoapObject soapObject = new SoapObject(Constants.SOAP_URL, methodName);
        if(mapParams!=null && mapParams.size()>0){
            for(Map.Entry<String, String> entry:mapParams.entrySet()){
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle = Constants.ENCODINGSTYLE;


        (new MarshalBase64()).register(envelope);
        HttpTransportSE http = new HttpTransportSE(getUrl()+urlType,15000);
        http.debug = true;
        try {
            http.call(Constants.SOAP_URL + methodName, envelope);
            result = envelope.getResponse().toString();
        } catch (Exception e) {
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
            result = e.toString();
        }

        return result;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        if(o.contains("Exception")){
            mListener.onError(o);
        }else{
            mListener.onSuccess(o);
        }


    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
