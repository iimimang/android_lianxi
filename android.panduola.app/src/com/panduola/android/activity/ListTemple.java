package com.panduola.android.activity;



import com.panduola.android.BaseFragment;

import com.panduola.android.R;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListTemple extends BaseFragment {


	@Override
	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sinha) {
		View view = inflater.inflate(R.layout.list_temple, null);

		

		return view;
	}

	@Override
	public void onActivityCreated(Bundle sinha) {
		super.onActivityCreated(sinha);
	
	}



	
	@Override
	public void onClick(View v) {
//		if (v == this.buttonBack) {
//			getActivity().onBackPressed();
//		}
	}
}