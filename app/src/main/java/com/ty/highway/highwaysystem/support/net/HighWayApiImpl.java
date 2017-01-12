package com.ty.highway.highwaysystem.support.net;

import android.content.Context;

import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.listener.NetResquestListener;
import com.ty.highway.highwaysystem.support.utils.PreferencesUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by fuweiwei on 2015/9/6.
 * API接口实现类
 */
public class HighWayApiImpl implements HighWayApi {
    private static final String TAG = "HighWayApiImpl";
    private Context mContext;
    public  HighWayApiImpl(Context context){
        mContext = context;
    }
    public  String  getUrl(){
        String url = "http://"+ PreferencesUtils.getString(mContext, Constants.SP_WEBURL, Constants.WEBURL)+"/WebService/" ;
        return url;
    }
    @Override
    public void doPost(HashMap<String, String> map,String name,String urlType, NetResquestListener listener) {

        String methodName = name;
        SoapObject soapObject = new SoapObject(Constants.SOAP_URL, methodName);
        if(map!=null && map.size()>0){
            for(Map.Entry<String, String> entry:map.entrySet()){
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle = Constants.ENCODINGSTYLE;
        (new MarshalBase64()).register(envelope);
        HttpTransportSE http = new HttpTransportSE(getUrl()+urlType);
        http.debug = true;
        try {
            http.call(Constants.SOAP_URL+methodName, envelope);
            listener.onSuccess(envelope.getResponse().toString());
        } catch (Exception e) {
            listener.onError(e.toString());
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }

    }

    @Override
    public void upLoadExecption(HashMap<String, String> map, String name, NetResquestListener listener) {
        SoapObject request = new SoapObject(Constants.SOAP_URL, name);
        request.addProperty("publicKey",Constants.SERVER_COMMON_PUBLIC_KEY);
        if(map != null && !map.isEmpty()) {
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                request.addProperty(String.valueOf(key), String.valueOf(val));
            }
        }

        //获得序列化的Envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut=request;
        envelope.dotNet = true;
        //envelope.setOutputSoapObject(request);
        envelope.encodingStyle="UTF-8";
        new MarshalBase64().register(envelope);
        //Android传输对象
        HttpTransportSE transport = new HttpTransportSE(Constants.SERVER_COMMON_WEBSERVICE);
        transport.debug = true;
        try {
            transport.call(Constants.SOAP_URL + name, envelope);
            if(envelope.getResponse() != null){
                listener.onSuccess(envelope.getResponse().toString());
            }
        } catch (SoapFault soapFault) {
            listener.onError(soapFault.toString());
            new ExceptionUtils().doExecInfo(soapFault.toString(), mContext);
        }catch (IOException e) {
            listener.onError(e.toString());
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        } catch (XmlPullParserException e) {
            listener.onError(e.toString());
            new ExceptionUtils().doExecInfo(e.toString(), mContext);
        }
    }

}
