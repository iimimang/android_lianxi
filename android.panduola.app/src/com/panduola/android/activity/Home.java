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

		((ImageButton) view.findViewById(R.id.home_desire_0_button)).setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.home_desire_1_button)).setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.home_desire_2_button)).setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.home_desire_3_button)).setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.home_desire_4_button)).setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.home_desire_5_button)).setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.home_desire_6_button)).setOnClickListener(this);

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
		case R.id.home_desire_0_button:
			desireType = 1;
			break;
		case R.id.home_desire_1_button:
			desireType = 2;
			break;
		case R.id.home_desire_2_button:
			desireType = 3;
			break;
		case R.id.home_desire_3_button:
			desireType = 4;
			break;
		case R.id.home_desire_4_button:
			desireType = 5;
			break;
		case R.id.home_desire_5_button:
			desireType = 6;
			break;
		case R.id.home_desire_6_button:
			desireType = 7;
			break;
		default:
			desireType = 1;
			break;
		}
		Bundle bundle = new Bundle();
		bundle.putInt("desire_type", desireType);
		goFragment(new ListTemple(), bundle);
	}
}