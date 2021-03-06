package com.panduola.android;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseFragment extends Fragment implements OnClickListener {
	
	private Fragment mContent;
	@Override
	public void onActivityCreated(Bundle sinha) {
		super.onActivityCreated(sinha);
		PanDuoLa.classCurr = getClass();
	}

	public void goActivity(Class<?> target, Bundle bundle) {
		goActivity(PanDuoLa.APP, target, bundle);
	}

	public void goActivity(Class<?> target) {
		goActivity(PanDuoLa.APP, target, null);
	}

	public void goActivity(Context content, Class<?> target, Bundle bundle) {
		Intent intent = new Intent(PanDuoLa.APP, target);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		getActivity().startActivityForResult(intent, 0);
	}

	public void goFragment(Fragment fragment, Bundle bundle) {
		if (null != bundle) {
			fragment.setArguments(bundle);
		}
		goFragment(fragment);
	}

	public void goFragment(Fragment fragment) {
		
		  if (mContent != fragment) {
              FragmentTransaction transaction = getFragmentManager()
                              .beginTransaction();
              if (!fragment.isAdded()) { // 先判断是否被add过
                      transaction.hide(mContent).add(PanDuoLa.tabContent, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
              } else {
                      transaction.hide(mContent).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
              }
              mContent = fragment;
      }
	
//		FragmentTransaction transFrogment = getFragmentManager().beginTransaction();
//		transFrogment.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
//		transFrogment.replace(PanDuoLa.tabContent, fragment);
//		transFrogment.addToBackStack(Consts.TAG);
//		transFrogment.commit();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
	}
}
