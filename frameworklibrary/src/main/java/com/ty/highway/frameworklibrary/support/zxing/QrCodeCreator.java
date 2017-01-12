package com.ty.highway.frameworklibrary.support.zxing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ty.highway.frameworklibrary.R;

/**
 * 
 * ClassName:	QrCodeCreator
 * Description:	生成二维码、合成带水印的二维码图片
 *
 * @author   	Gaojian
 *           	email:    areful1997@163.com
 * @version  	
 * @since    	Ver 1.1
 * @Date	 	2013 2013-3-6 下午5:03:54
 *
 */
public class QrCodeCreator {
	/**
	 * 
	 * getQrCode:根据传入的字符串生成带水印的二维码
	 *
	 * @param  @param context
	 * @param  @param qrcodeString
	 * @param  @param qrcodeText
	 * @param  @return
	 * @param  @throws Exception    设定文件
	 * @return Bitmap    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public Bitmap createWatermarkFixedQrCode(Context context, String qrcodeString, String qrcodeText) throws Exception {
		//获取屏幕参数
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		float density = metrics.density;
		
		// 获取二维码图片大小和文字大小
		Resources resources = context.getApplicationContext()
				.getResources();
		int bitmapWidth = (int) resources.getDimension(R.dimen.QRCode_width);
		int textSize = (int) resources.getDimension(R.dimen.QRCode_text_size);

		Bitmap bm = create2DCode(qrcodeString, bitmapWidth);
		Bitmap watermark = BitmapFactory.decodeResource(context.getResources(), R.drawable.watermark);


		Bitmap bm_water = fixWatermarkToQrCode(bm, watermark, qrcodeText, textSize,density);
		
		return bm_water;
	}
	/**
	 * 用字符串生成二维码
	 * 
	 * @param str
	 * @author zhouzhe@lenovo-cw.com
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap create2DCode(String str, int qrLength)
			throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, qrLength, qrLength);

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
	
	/**
	 * 图片合成
	 * 
	 * @param bitmap
	 * @return
	 */
	private static Bitmap fixWatermarkToQrCode(Bitmap src, Bitmap watermark, String str,
			int textSize, float density) {
		try {
			if (src == null) {
				return null;
			}

			Paint paint = new Paint();
			paint.setColor(Color.BLACK); // 颜色
			// 设置字体大小
			paint.setAntiAlias(true);
			paint.setTextSize(textSize);
			// 获取文字长和宽
			Rect rect = new Rect();
			paint.getTextBounds(str, 0, str.length(), rect);
			int textWidth = rect.width();
			int textHight = rect.height();
			// 拿到二维码图片长宽
			int w = src.getWidth();
			int h = src.getHeight();
			// 获取水印宽度
			int wh = watermark.getHeight();
			Bitmap newb = Bitmap.createBitmap(w + 2 * textHight, h + wh + 2
					* textHight, Config.ARGB_8888); // 创建一个新的和SRC长度宽度一样的位图

			int newWith = w + 2 * textHight;
			int newHight = h + wh + 2 * textHight;
			int[] pixels = new int[newWith * newHight];
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = 0xffffffff;
			}
			// 通过像素数组生成bitmap,一张白色的bitmap
			newb.setPixels(pixels, 0, newWith, 0, 0, newWith, newHight);

			Canvas cv = new Canvas(newb);
			cv.drawBitmap(watermark, textHight, 0, null); // 在src的坐上角画入水印

			cv.drawBitmap(src, textHight, wh + textHight / 2, null); // 在
																		// 0，0坐标开始画入src

			for (;;) {
				if (str == null)
					break;
				if (str.equals(""))
					break;

				// 计算文字宽度是否超出二维码宽度，进行宽度压缩
				if (rect.width() * density > src.getWidth() * density) {
					float scaleX = (src.getWidth() * density)
							/ (rect.width() * density) - 0.01f;
					paint.setTextScaleX(scaleX);
					Rect scaleRect = new Rect();
					paint.getTextBounds(str, 0, str.length(), scaleRect);
					textWidth = scaleRect.width();
				}
				// 画出文字
				cv.drawText(str, newb.getWidth() / 2 - textWidth / 2,
						newb.getHeight() - textHight / 4, paint);

				break;
			}

			cv.save(Canvas.ALL_SAVE_FLAG); // 保存

			cv.restore(); // 存储
			return newb;
		} catch (Exception e) {
			return null;
		}
	}
}
