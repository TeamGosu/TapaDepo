package idk.tapa;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Mark on 30/11/2014.
 */
public class CustomViewPager extends ViewPager {

    float mStartDragX;
    OnSwipeOutListener mListener;
    Context context;
    public static boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        enabled = false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (enabled) //view pager scrolling enable if true
        {
            return super.onTouchEvent(event);
        }
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (enabled)//enter code here
        {
            Log.e("ViewPager", "Intercept");
            return super.onInterceptTouchEvent(ev);
        } else  // view pager disable scrolling
        {
            float x = ev.getX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartDragX = x;

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mStartDragX < x - 100)//100 value velocity
                    {
                        //Left scroll
                        Log.e("ViewPager", "Left Scroll");
                        return super.onInterceptTouchEvent(ev);
                    } else if (mStartDragX > x + 100) {
                        //Right scroll
                        Log.e("ViewPager", "Right Scroll");
                        return super.onInterceptTouchEvent(ev);
                    }
                    break;
            }
        }

        return false;

    }


    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mListener = listener;
    }

    public interface OnSwipeOutListener {

        public void onSwipeOutAtStart();


        public void onSwipeOutAtEnd();
    }
}
