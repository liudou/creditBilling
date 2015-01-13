package com.wi360.pay.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.wi360.pay.sdk.base.MyAsyncTask;
import com.wi360.pay.sdk.base.QidaDialog;
import com.wi360.pay.sdk.bean.PayOrderBean.Pay;
import com.wi360.pay.sdk.bean.ResultBean;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.CommonUtil;
import com.wi360.pay.sdk.util.GsonTools;

/**
 * 选择支付方式
 * 
 * @author Administrator
 * 
 */
public class PaySelectController {
	private View view;
	private Activity context;

	private String url;
	private String json;
	private ResponseCallback responseCallback;
	private Pay payBean;

	public PaySelectController(Activity context, final Dialog dialog, View view, Pay payBean,
			ResponseCallback responseCallback) {
		this.context = context;
		this.view = view;
		this.payBean = payBean;
		this.responseCallback = responseCallback;
		RelativeLayout rl_network = (RelativeLayout) this.view.findViewById(CommonUtil.getResourcesId(context,
				"rl_network", "id"));

		RelativeLayout rl_sms = (RelativeLayout) this.view.findViewById(CommonUtil.getResourcesId(context, "rl_sms",
				"id"));
		// 开启网络,进行网络支付
		rl_network.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 选择网络支付,弹出打开网络界面
				PaySelectController.this.context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				// 跳转到支付页面
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						com.wi360.pay.sdk.interfaces.Pay payin = PayFactory
								.getInstance(PaySelectController.this.context);
						payin.creditPay(PayController.appId, PayController.appKey,
								PaySelectController.this.payBean.productName, PaySelectController.this.payBean.sum,
								PaySelectController.this.payBean.alias, PaySelectController.this.payBean.sellerUserId,
								PaySelectController.this.responseCallback);
						dialog.dismiss();
					}
				}.sendEmptyMessageDelayed(0, 1500);

			}
		});
		// 短信支付
		rl_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int layout_id = CommonUtil.getResourcesId(PaySelectController.this.context, "dialog_confirmation",
						"layout");
				int style_id = CommonUtil.getResourcesId(PaySelectController.this.context, "QidaDialog", "style");
				QidaDialog dialog2 = new QidaDialog(PaySelectController.this.context, layout_id, style_id,
						PaySelectController.this.responseCallback);
				new SendSmsController(PaySelectController.this.context, dialog2, dialog2.view,
						PaySelectController.this.payBean, PaySelectController.this.responseCallback);
				dialog2.show();
				dialog.dismiss();
			}
		});
		// 点击取消

		this.view.findViewById(CommonUtil.getResourcesId(context, "bt_cancel", "id")).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View view) {
						dialog.dismiss();
						((QidaDialog) dialog).onClickCancleBut();
					}
				});
	}

	public void send(String url, String json) {

		new MyAsyncTask<ResultBean>(context, null, null, responseCallback) {
			@Override
			public String connectNetWorkSuccess(int statusCode, String responseInfo) {
				if (statusCode == 200) {
					resBean = GsonTools.changeGsonToBean(responseInfo, ResultBean.class);
					if (resBean.errcode == 0) {

					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				super.onPostExecute(msg);
			}
		}.execute(new String[] { url, json });

	}

}
