package com.y.w.ywker.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.y.w.ywker.R;


/**
 * 自定义的dialog 可填写内容的提示框
 * @author lxs
 * 2016年8月29日
 */
public class MyDialog extends Dialog implements
		android.view.View.OnClickListener {
	private DialogClickListener listener;
	Context context;
	private TextView tv_restinfo_pop_tel_content;
	private TextView dialog_textViewID;
	private TextView dialog_textViewID1;
	private EditText edtext;
	private String leftBtnText;
	private String rightBtnText;
	private String content;

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 自定义dialog
	 * @param context
	 * @param theme
	 *            主题
	 * @param content
	 *            主体文字
	 * @param leftBtnText
	 *            左按钮文字，若为""则隐藏
	 * @param rightBtnText
	 *            右按钮文字，若为""则隐藏
	 * @param listener
	 *            回调接口
	 */
	public MyDialog(Context context, int theme, String content,
			String leftBtnText, String rightBtnText,
			DialogClickListener listener) {
		super(context, theme);
		this.context = context;
		this.content = content;
		this.leftBtnText = leftBtnText;
		this.rightBtnText = rightBtnText;
		this.listener = listener;
	}

	public void setTextSize(int size) {
		dialog_textViewID.setTextSize(size);
		dialog_textViewID1.setTextSize(size);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog);
		tv_restinfo_pop_tel_content = (TextView) findViewById(R.id.tv_restinfo_pop_tel_content);
		edtext = (EditText) findViewById(R.id.et_xinghaoName);
		dialog_textViewID1 = (TextView) findViewById(R.id.dialog_textViewID1);
		dialog_textViewID = (TextView) findViewById(R.id.dialog_textViewID);
		dialog_textViewID.setOnClickListener(this);
		dialog_textViewID1.setOnClickListener(this);
		initView();
		initDialog(context);
	}

	/**
	 * 设置dialog的宽为屏幕的3分之1
	 */
	private void initDialog(Context context) {
		setCanceledOnTouchOutside(false);
		setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getRepeatCount() == 0) {
					return true;
				} else {
					return false;
				}
			}
		});
		WindowManager windowManager = this.getWindow().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) (display.getWidth() / 6 * 5); //// 设置宽度
		this.getWindow().setAttributes(lp);
	}

	private void initView() {
		tv_restinfo_pop_tel_content.setText(content);
		if (leftBtnText.equals(""))
			dialog_textViewID.setVisibility(View.GONE);
		else
			dialog_textViewID.setText(leftBtnText);
		if (rightBtnText.equals(""))
			dialog_textViewID1.setVisibility(View.GONE);
		else
			dialog_textViewID1.setText(rightBtnText);
	}

	public interface DialogClickListener {
		

		void onRightBtnClick(Dialog dialog);

		void onLeftBtnClick(MyDialog myDialog, String reslut);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_textViewID:
			String s = edtext.getText().toString().trim();
			if(TextUtils.isEmpty(s)){
				Toast.makeText(context, "填写的内容不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			Log.e("lxs", s);
			listener.onLeftBtnClick(this,s);
			break;
		case R.id.dialog_textViewID1:
			listener.onRightBtnClick(this);
			break;
		}
	}
}