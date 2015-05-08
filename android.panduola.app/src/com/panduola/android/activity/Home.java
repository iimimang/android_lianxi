package com.panduola.android.activity;

import com.panduola.android.BaseFragment;
import com.panduola.android.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Home extends BaseFragment {

	@Override
	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sinha) {
		View view = inflater.inflate(R.layout.home, null);

	
		return view;
	}

	@Override
	public void onActivityCreated(Bundle sinha) {
		super.onActivityCreated(sinha);
	}

	@Override
	public void onClick(View v) {
		int desireType = 1;
		switch (v.getId()) {
		
		default:
			desireType = 1;
			break;
		}
		Bundle bundle = new Bundle();
		bundle.putInt("desire_type", desireType);
		goFragment(new ListTemple(), bundle);
	}
}