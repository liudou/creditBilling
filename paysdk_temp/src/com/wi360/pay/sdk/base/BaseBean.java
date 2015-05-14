package com.wi360.pay.sdk.base;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;

import com.wi360.pay.sdk.PayController;
import com.wi360.pay.sdk.R;
import com.wi360.pay.sdk.util.CommonUtil;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;

/**
 * 基类bean
 * 
 * @author Administrator
 * 
 */
public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String getAppCode(Context context) {
		int string_id = CommonUtil.getResourcesId(context, "appCode", "string");
		return context.getResources().getString(string_id);
	}

	protected String getToken(Context context) {
		return SharedPreferencesUtils.getString(context, Constants.token, "");
	}

	protected String getMobileNum(Context context) {
		return SharedPreferencesUtils.getString(context, Constants.mobileNum, "");
	}

	protected String getAppKey(Context context) {
		// int string_id = CommonUtil.getResourcesId(context, "appKey",
		// "string");
		// return context.getResources().getString(string_id);
		return PayController.appKey;
	}

	protected String getAppId(Context context) {
		// int id = CommonUtil.getResourcesId(context, "appId", "string");
		// return context.getResources().getString(id);
		return PayController.appId;
	}

	/**
	 * MD5 加密
	 */
	protected String getMD5Str(String str) {
		Log.i("MD5: ", str);
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	protected long genRandNum(int codeLen) {

		StringBuffer tmpBuff = new StringBuffer("1,2,3,4,5,6,7,8,9,0");

		java.util.Random r = new Random();
		String[] strArray = tmpBuff.toString().split(",");

		StringBuffer resultBuff = new StringBuffer();

		for (int i = 0; i < codeLen; i++) {
			int k = r.nextInt();
			resultBuff.append(String.valueOf(strArray[Math.abs(k % 10)]));
		}
		return Long.parseLong(resultBuff.toString());

	}

	/**
	 * 获取keyhash
	 * 
	 * @author yangxiongjie
	 * @return
	 * @since JDK 1.6
	 */
	public String getKeyHash(Context context) {
		String keyhash = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_SIGNATURES);
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(info.signatures[0].toByteArray());
			keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT).toString().trim();
			// Logger.d("keyHash=" + keyhash);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// return keyhash;
		return "0UfNqj46WpWqQdq+yhdgTK7B8cU=";
	}

}
