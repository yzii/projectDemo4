package com.example.projectdemo4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 
 * �Զ��������
 */
public class TitleActivity extends Activity implements  OnClickListener{
 
    //private RelativeLayout mLayoutTitleBar;
    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private Button mForwardButton;
    private FrameLayout mContentLayout;
 
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        //mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
    }
 
    /**
     * �Ƿ���ʾ���ذ�ť
     * @param backwardResid  ����
     * @param show  true����ʾ
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }
 
    /**
     * �ṩ�Ƿ���ʾ�ύ��ť
     * @param forwardResId  ����
     * @param show  true����ʾ
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (mForwardButton != null) {
            if (show) {
                mForwardButton.setVisibility(View.VISIBLE);
                mForwardButton.setText(forwardResId);
            } else {
                mForwardButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }
 
    /**
     * ���ذ�ť����󴥷�
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        Toast.makeText(this, "������ؿ��ڴ˴�����finish()", Toast.LENGTH_LONG).show();
        //finish();
    }
 
    /**
     * �ύ��ť����󴥷�
     * @param forwardView
     */
    protected void onForward(View forwardView) {
        Toast.makeText(this, "����ύ", Toast.LENGTH_LONG).show();
    }
 
 
    //���ñ�������
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }
 
    //���ñ�������
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }
 
    //���ñ���������ɫ
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }
 
 
    //ȡ��FrameLayout�����ø���removeAllViews()����
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }
 
    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }
 
    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }
 
 
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * ��ť������õķ���
     */
    @Override
    public void onClick(View v) {
 
        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;
 
            case R.id.button_forward:
                onForward(v);
                break;
 
            default:
                break;
        }
    }
}