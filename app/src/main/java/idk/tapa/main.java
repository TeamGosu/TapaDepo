package idk.tapa;


import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import java.util.logging.LogRecord;

public class main extends FragmentActivity {

    private int mInternal = 1000;
    private Handler mHandler;

    CustomViewPager viewPager;
    FragmentPagerAdapter fm;

    //ViewPager viewPager=null;
    Score score;
    float volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);



        //ViewPager
        viewPager= (CustomViewPager) findViewById(R.id.pager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        //Fragment
        fm = new FragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fm);

        viewPager.setOnTouchListener(new OnSwipeTouchListener() {
            public void onSwipeLeft(){

                if (score.getWatts() > 10)
                {
                    CustomViewPager.enabled = true;
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
               // if (position <= 0)
               // {
               //     viewPager.setCurrentItem(0);
               // }
                //fm.getRegisteredFragment(viewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Audio Manager
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;

        //Initialise score
        score = new Score();

        //Initialise handler
        mHandler = new Handler();
        startRepeatingTask();
    }



    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            updateStatus();
            FragmentA fragmenta = (FragmentA)fm.getRegisteredFragment(0);
            FragmentB fragment = (FragmentB)fm.getRegisteredFragment(1);
            if (fragmenta != null) {
                fragmenta.updateView();
                fragment.updateView();
            }
            mHandler.postDelayed(mStatusChecker, mInternal);

        }


    };

    private void updateStatus() {
        score.passiveIncreaseScore();

    }

    void startRepeatingTask()
    {
        mStatusChecker.run();
    }
    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mStatusChecker);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class FragmentPagerAdapter extends FragmentStatePagerAdapter {
        //private Map<Integer, Fragment> mPageReferenceMap = new HashMap<Integer, Fragment>();
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment=null;

            if(i==0)
            {
                fragment=new FragmentA();

                //mPageReferenceMap.put(Integer.valueOf(i), fragment);
            }
            if(i==1)
            {
                fragment=new FragmentB();
                //mPageReferenceMap.put(Integer.valueOf(i), fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
//        Log.d("VIVZ", "get Count is called");
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position==0)
            {
                return "BUTTON";
            }
            if(position==1)
            {
                return "SPIN";
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        /**
        public Fragment getFragment(int key)
        {
            return mPageReferenceMap.get(key);
        }
        */
    }




    public Integer getScore()
    {
        return score.getWatts();
    }

    public void setScore(Integer newWatts)
    {
        score.setScore(newWatts);
    }

    public Integer getPwatt()
    {
        return score.getPwatts();
    }

    public void increaseScore()
    {
        score.increaseScore();
        FragmentA fragmenta = (FragmentA)fm.getRegisteredFragment(0);
        FragmentB fragment = (FragmentB)fm.getRegisteredFragment(1);
        if (fragmenta != null) {
            fragmenta.updateView();
            fragment.updateView();
        }

    }

    public void incPwatt(Integer amount)
    {
        score.incPwatts(amount);
    }

    public float audioVolume()
    {
        return volume;
    }

}
