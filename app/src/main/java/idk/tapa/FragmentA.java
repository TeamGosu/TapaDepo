package idk.tapa;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

/**
 * Fragment A - First page. Button Activity
 */
public class FragmentA extends Fragment implements View.OnClickListener {

    private static final int RESULT_OK = 1;

    TextView wattCount;
    TextView pwattCount;
    ImageView animate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        ImageButton tapButton = (ImageButton) v.findViewById(R.id.btnTap);
        tapButton.setOnClickListener(this);

        ImageButton btnUpgrades = (ImageButton) v.findViewById(R.id.btnUpgrades);
        btnUpgrades.setOnClickListener(this);

        wattCount = (TextView) v.findViewById(R.id.wattCount);
        pwattCount = (TextView) v.findViewById(R.id.pwattCount);

        animate = (ImageView) v.findViewById(R.id.square);

        RotateAnimation anim = new RotateAnimation(0f, 25f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(500);

        animate.startAnimation(anim);

        //FONTS
        Typeface ledFont = Typeface.createFromAsset( getActivity().getAssets(), "fonts/led.ttf");
        wattCount.setTypeface(ledFont);

        return v;



    }

    public void updateView()
    {
        wattCount.setText(((main)getActivity()).getScore().toString());
        pwattCount.setText(((main)getActivity()).getPwatt().toString() + " Watts /s");

    }


   @Override
    public void onClick(View v) {
       int targetId = v.getId();
       if (targetId == R.id.btnTap) {

           if (((main)getActivity()).audioLoaded()) {
               ((main)getActivity()).playClick();
           }

           ((main)getActivity()).increaseScore();

       } else if (targetId == R.id.btnUpgrades){
           Toast.makeText(getActivity().getApplicationContext(), "Upgrades", Toast.LENGTH_SHORT).show();
           Intent upgradesScreen = new Intent(getActivity().getApplicationContext(), upgrades.class);

           upgradesScreen.putExtra("score", ((main)getActivity()).getScore());
           startActivityForResult(upgradesScreen, 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == RESULT_OK)
            {
                String result = data.getStringExtra("result");
                String newWatts = data.getStringExtra("newScore");
                ((main)getActivity()).setScore(Integer.valueOf(newWatts));
                ((main)getActivity()).incPwatt(Integer.valueOf(result));
            }
        }

    }
}
