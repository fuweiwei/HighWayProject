package com.ty.highway.frameworklibrary.support.zxing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ty.highway.frameworklibrary.R;



public class ShowActivity extends BaseActivity{

	private TextView txtResult;
	private Button reScan,goHome,openUrl;
	private static String regex = "(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	private String result;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_show);
		result = getIntent().getStringExtra("result");
		txtResult = (TextView) findViewById(R.id.scan_result);
		reScan = (Button) findViewById(R.id.go_on_scan);
		goHome = (Button) findViewById(R.id.go_home);
		openUrl = (Button) findViewById(R.id.open_url);
		txtResult.setText("设备id："+result);
		reScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShowActivity.this, CaptureActivity.class);
				ShowActivity.this.finish();
				startActivity(intent);
			}
		});
		goHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowActivity.this.finish();
			}
		});

			openUrl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						/*Uri uri = Uri.parse(result);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri                                               );
						startActivity(intent);*/
						PreferencesUtils.putString(ShowActivity.this,"ZXingId",result);
						ShowActivity.this.finish();
					} catch (Exception e) {
						Toast.makeText(ShowActivity.this, "无法打开！", Toast.LENGTH_SHORT).show();
					}
    				}
			});
	}
	public static boolean isURL(String result){  
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(result);  
		return m.matches();
	}  
	
}