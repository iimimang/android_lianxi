package com.panduola.android.activity;


import com.panduola.android.BaseFragment;

import com.panduola.android.R;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class My extends BaseFragment {

	private Button login;
	@Override
	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sinha) {
		View view = inflater.inflate(R.layout.my, null);

		this.login = (Button) view.findViewById(R.id.login_button);
		
		
		this.login.setOnClickListener(this);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	
	}

	@Override
	public void onClick(View v) {
		
	
		if (v == this.login) {
			goActivity(Login.class);
//			Bundle bundle = new Bundle();
//			bundle.putString("title", getActivity().getString(R.string.about_title_text));
//			bundle.putString("url", "http://www.shangxiang.com");
			//goFragment(new Browser(), bundle);
		}
		
		
	}
}