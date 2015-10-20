package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TouchActivity extends Activity {
    /*声明ImageView变量*/
    private ImageView mImageView01;
    /*声明相关变量作为存储图片宽高,位置使用*/
    private int intWidth, intHeight, intDefaultX, intDefaultY;
    private float mX, mY;
    /*声明存储屏幕的分辨率变量 */
    private int intScreenX, intScreenY;
    private GestureDetector mDetector;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDetector = new GestureDetector(this, new MyGestureListener());
	    /* 取得屏幕对象 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
	    
	    /* 取得屏幕解析像素 */
        intScreenX = dm.widthPixels;
        intScreenY = dm.heightPixels;
	    
	    /* 设置图片的宽高 */
        intWidth = 100;
        intHeight = 100;
	    /*通过findViewById构造器创建ImageView对象*/
        mImageView01 =(ImageView) findViewById(R.id.myImageView1);
	    /*将图片从Drawable赋值给ImageView来呈现*/
        mImageView01.setImageResource(R.drawable.baby);
	    
	    /* 初始化按钮位置居中 */
        RestoreButton();
	    
	    /* 当点击ImageView，还原初始位置 */
        mImageView01.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RestoreButton();
            }
        });
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
              /*取得手指触控屏幕的位置*/
            float x = event.getX();
            float y = event.getY();
            picMove(x, y);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            float x = event1.getX();
            float y = event1.getY();
            picMove(x, y);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }
//    /*覆盖触控事件*/
//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//	    /*取得手指触控屏幕的位置*/
//        float x = event.getX();
//        float y = event.getY();
//
//        try
//        {
//	      /*触控事件的处理*/
//            switch (event.getAction())
//            {
//	        /*点击屏幕*/
//                case MotionEvent.ACTION_DOWN:
//                    picMove(x, y);
//                    break;
//	        /*移动位置*/
//                case MotionEvent.ACTION_MOVE:
//                    picMove(x, y);
//                    break;
//	        /*离开屏幕*/
//                case MotionEvent.ACTION_UP:
//                    picMove(x, y);
//                    break;
//            }
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return true;
//    }
    /*移动图片的方法*/
    private void picMove(float x, float y)
    {
	    /*默认微调图片与指针的相对位置*/
        mX=x-(intWidth/2);
        mY=y-(intHeight/2);

	    /*防图片超过屏幕的相关处理*/
	    /*防止屏幕向右超过屏幕*/
        if((mX+intWidth)>intScreenX)
        {
            mX = intScreenX-intWidth;
        }
	    /*防止屏幕向左超过屏幕*/
        else if(mX<0)
        {
            mX = 0;
        }
	    /*防止屏幕向下超过屏幕*/
        else if ((mY+intHeight)>intScreenY)
        {
            mY=intScreenY-intHeight;
        }
	    /*防止屏幕向上超过屏幕*/
        else if (mY<0)
        {
            mY = 0;
        }
	    /*通过log 来查看图片位置*/
        Log.i("jay", Float.toString(mX)+","+Float.toString(mY));
	    /* 以setLayoutParams方法，重新安排Layout上的位置 */
        mImageView01.setLayoutParams
                (
                        new AbsoluteLayout.LayoutParams
                                (intWidth,intHeight,(int) mX,(int)mY)
                );
    }

    /* 还原ImageView位置的事件处理 */
    public void RestoreButton()
    {
        intDefaultX = ((intScreenX-intWidth)/2);
        intDefaultY = ((intScreenY-intHeight)/2);
	    /*Toast还原位置坐标*/
        mMakeTextToast
                (
                        "("+
                                Integer.toString(intDefaultX)+
                                ","+
                                Integer.toString(intDefaultY)+")",true
                );
	    
	    /* 以setLayoutParams方法，重新安排Layout上的位置 */
        mImageView01.setLayoutParams
                (
                        new AbsoluteLayout.LayoutParams
                                (intWidth,intHeight,intDefaultX,intDefaultY)
                );
    }

    /*自定义一发出信息的方法*/
    public void mMakeTextToast(String str, boolean isLong)
    {
        if(isLong==true)
        {
            Toast.makeText(TouchActivity.this, str, Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(TouchActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    }
}