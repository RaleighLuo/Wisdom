package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;


public class CheckConfirmDialog extends Dialog {
	private View confirmView;
	private Button cancelBtn,yesBtn;
	private View.OnClickListener yesBtnListener,cancelBtnListener;
	private TextView titleTv,contentTv;
	private String tilte,content;
	private View gap;
	private View shortGap,hGap;;
	private LinearLayout buttonLinearLayout;


	public void setYesBtnListener(View.OnClickListener yesBtnListener) {
		this.yesBtnListener = yesBtnListener;
		if(yesBtn!=null){
			yesBtn.setOnClickListener(yesBtnListener);
		}
	}


	public void setCancelBtnListener(View.OnClickListener cancelBtnListener) {
		this.cancelBtnListener = cancelBtnListener;
		if(cancelBtn!=null){
			cancelBtn.setOnClickListener(cancelBtnListener);
		}
	}


	public CheckConfirmDialog(Context context) {
		super(context,R.style.checkConfirmDialog);
		confirmView = LayoutInflater.from(getContext()).inflate(R.layout.check_confirm_dialog_layout, null);
	}

	public CheckConfirmDialog(Context context, int theme) {
		super(context, theme);
		confirmView = LayoutInflater.from(getContext()).inflate(R.layout.check_confirm_dialog_layout, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(confirmView);
		cancelBtn = (Button)confirmView.findViewById(R.id.check_confirm_dialog_layout_btn_cancel);
		yesBtn = (Button)confirmView.findViewById(R.id.check_confirm_dialog_layout_btn_yes);
		gap = (View)confirmView.findViewById(R.id.check_confirm_dialog_layout_line);
		buttonLinearLayout = (LinearLayout)confirmView.findViewById(R.id.check_confirm_dialog_layout_panel_bottom_bar);
		shortGap=(View)confirmView.findViewById(R.id.check_confirm_dialog_layout_v_button_line);
		if(cancelBtnListener!=null)
			cancelBtn.setOnClickListener(cancelBtnListener);
		else
			cancelBtn.setOnClickListener(cancelListener);
		yesBtn.setOnClickListener(yesBtnListener);
		titleTv = (TextView)confirmView.findViewById(R.id.check_confirm_dialog_layout_tv_title);
		contentTv = (TextView)confirmView.findViewById(R.id.check_confirm_dialog_layout_tv_content);
		titleTv.setText(tilte);
		contentTv.setText(content);
		hGap=(View)confirmView.findViewById(R.id.check_confirm_dialog_layout_v_h_line);
	}


	public String getTilte() {
		return tilte;
	}



	public void setTilte(String tilte) {
		this.tilte = tilte;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
		if(contentTv!=null){
			contentTv.setText(content);
		}
	}


	public Button getCancelBtn() {
		return cancelBtn;
	}


	public void setCancelBtn(Button cancelBtn) {
		this.cancelBtn = cancelBtn;
	}
	public void setShortLineGone(){
		this.shortGap.setVisibility(View.GONE);
	}
	public void setCancelBtnGone(){
		this.cancelBtn.setVisibility(View.GONE);
	}

	public void setYesBtnGone(){
		this.yesBtn.setVisibility(View.GONE);
	}
	public Button getYesBtn() {
		return yesBtn;
	}


	public void setYesBtn(Button yesBtn) {
		this.yesBtn = yesBtn;
	}


	public void setGapGone(){
		this.gap.setVisibility(View.GONE);
	}
	public void  setHGapVisible() {
		this.hGap.setVisibility(View.VISIBLE);
	}

	public void setButtonLinearLayout(){
		this.buttonLinearLayout.setVisibility(View.GONE);
	}

	private View.OnClickListener cancelListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			dismiss();
		}
	};
}
