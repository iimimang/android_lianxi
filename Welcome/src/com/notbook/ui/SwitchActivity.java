package com.notbook.ui;


import com.example.notbook.R;
import com.notbook.swithLayout.OnViewChangeListener;
import com.notbook.swithLayout.SwitchLayout;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SwitchActivity extends BaseActivity{
    /** Called when the activity is first created. */
	SwitchLayout switchLayout;//�Զ���Ŀؼ�
	LinearLayout linearLayout;
	int mViewCount;//�Զ���ؼ����ӿؼ��ĸ���
	ImageView mImageView[];//�ײ���imageView
	int mCurSel;//��ǰѡ�е�imageView
	String cattle ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainselect);
        init();
    }
    
    public String cattle(){
    	return cattle;
    }
	private void init() {
		switchLayout = (SwitchLayout) findViewById(R.id.switchLayoutID);
		linearLayout = (LinearLayout) findViewById(R.id.linerLayoutID);
		
		//�õ��ӿؼ��ĸ���
		mViewCount = switchLayout.getChildCount();
		mImageView = new ImageView[mViewCount];
		//����imageView
		for(int i = 0;i < mViewCount;i++){
			//�õ�LinearLayout�е��ӿؼ�
			mImageView[i] = (ImageView) linearLayout.getChildAt(i);
			mImageView[i].setEnabled(true);//�ؼ�����
			mImageView[i].setOnClickListener(new MOnClickListener());
			mImageView[i].setTag(i);//������view��صı�ǩ
		}
		//���õ�һ��imageView��������
		mCurSel = 0;
		mImageView[mCurSel].setEnabled(false);
		switchLayout.setOnViewChangeListener(new MOnViewChangeListener());
		
	}
	
	//����¼��ļ�����
	private class MOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			int pos = (Integer) v.getTag();
			System.out.println("pos:--" + pos);
			//���õ�ǰ��ʾ��ImageView
			setCurPoint(pos);
			//�����Զ���ؼ��е��ĸ��ӿؼ�չʾ�ڵ�ǰ��Ļ��
			switchLayout.snapToScreen(pos);
		}
	}
	

	/**
	 * ���õ�ǰ��ʾ��ImageView
	 * @param pos
	 */
	private void setCurPoint(int pos) {
		if(pos < 0 || pos > mViewCount -1 || mCurSel == pos)
			return;
		//��ǰ��imgaeView�����Ա�����
		mImageView[mCurSel].setEnabled(true);
		//��Ҫ��ת��ȥ���Ǹ�imageView��ɲ��ɼ���
		mImageView[pos].setEnabled(false);
		mCurSel = pos;
	}
	
	//�Զ���ؼ���View�ı���¼�����
	private class MOnViewChangeListener implements OnViewChangeListener{
		@Override
		public void onViewChange(int view) {
			System.out.println("view:--" + view);
			if(view < 0 || mCurSel == view){
				return ;
			}else if(view > mViewCount - 1){
				//���������������ʱ��activity�ᱻ�ر�
				System.out.println("finish activity");
				finish();
			}
			setCurPoint(view);
		}
		
	}
	
}