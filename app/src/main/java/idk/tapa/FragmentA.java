package idk.tapa;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Markasdcasdad on 20/11/2014.
	testest
 */
public class FragmentA extends Fragment implements View.OnClickListener {

    View v;
	int test;

    TextView wattCount;

    //sound pool
    SoundPool soundPool;
    int soundID;
    boolean loaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        ImageButton tapButton = (ImageButton) v.findViewById(R.id.btnTap);
        tapButton.setOnClickListener(this);

        Button btnUpgrades = (Button) v.findViewById(R.id.btnUpgrades);
        btnUpgrades.setOnClickListener(this);

        wattCount = (TextView) v.findViewById(R.id.wattCount);

        return v;


        //FONTS
        //Typeface mFont = Typeface.createFromAsset(getAssets(), "fonts/arial.tff");
        //wattCount.setTypeface(mFont);
    }

    public void updateView()
    {

        wattCount.setText(((main)getActivity()).getScore().toString());
    }


   @Override
    public void onClick(View v) {
       int targetId = v.getId();
       if (targetId == R.id.btnTap) {

           getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
           soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
           soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
               @Override
               public void onLoadComplete(SoundPool soundPool, int SampleId, int status) {
                   loaded = true;
               }
           });

           soundID = soundPool.load(getActivity(), R.raw.shortclick, 1);
           if (loaded) {
               soundPool.play(soundID, ((main) getActivity()).audioVolume(), ((main) getActivity()).audioVolume(), 1, 0, 1f);
           }

           ((main) getActivity()).increaseScore();

       } else if (targetId == R.id.btnUpgrades){
           Toast.makeText(getActivity().getApplicationContext(), "Upgrades", Toast.LENGTH_SHORT).show();
           Intent addFoodScreen = new Intent(getActivity().getApplicationContext(), upgrades.class);
           startActivityForResult(addFoodScreen, 1);
        }
    }
}
