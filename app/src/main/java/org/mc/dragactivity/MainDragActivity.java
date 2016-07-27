package org.mc.dragactivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by xiyoumc on 16/7/27.
 */
public class MainDragActivity extends Activity {
    int mActivityWindowWidth = 600; //activity宽度
    int mActivityWindowHeight = 500; //activity高度
    int width = 0; //屏幕宽度
    int height = 0; //屏幕高度
    private TextView titleDrag = null;
    private float x;
    private float y;
    private float startX;
    private float startY;
    private View view;
    private WindowManager.LayoutParams lp;
    private String TAG = MainDragActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_activity);
        titleDrag = (TextView)findViewById(R.id.title_drag);

        titleDrag.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                x = event.getRawX();
                y = event.getRawY();
                Log.d(TAG, "------X: "+ x +"------Y:" + y);
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updatePosition();
                        break;
                    case MotionEvent.ACTION_UP:
                        updatePosition();
                        startX = startY = 0;
                        break;
                }
                return true;
            }
        });

    }

    private void updatePosition(){
        // View的当前位置
        int xmove = (int)( x - startX);
        int ymove = (int) (y - startY);
        Log.i(TAG, "计算位置:" + xmove + " " + ymove);

        lp.x = mActivityWindowWidth/2 - width/2 + xmove;
        lp.y = mActivityWindowHeight/2 - height/2 + ymove;

        getWindowManager().updateViewLayout(view, lp);

    }


    @Override
    public void onAttachedToWindow() {
        // TODO Auto-generated method stub

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        super.onAttachedToWindow();
        view = getWindow().getDecorView();
        lp = (WindowManager.LayoutParams) view.getLayoutParams();

        //lp.gravity = Gravity.CENTER;
        //lp.gravity = Gravity.TOP;

        lp.width = mActivityWindowWidth;
        lp.height = mActivityWindowHeight;
        lp.x = mActivityWindowWidth/2 - width/2;
        lp.y = mActivityWindowHeight/2 - height/2;

        getWindowManager().updateViewLayout(view, lp);
    }

}
