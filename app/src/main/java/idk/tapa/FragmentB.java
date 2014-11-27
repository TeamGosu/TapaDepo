package idk.tapa;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Mark on 20/11/2014.
 */
public class FragmentB extends Fragment implements View.OnClickListener {
    TextView wattCount;
    ImageButton tapButton;

    //sound pool
    SoundPool soundPool;
    int soundID;
    boolean loaded = false;
    //float volume = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test, container, false);
        wattCount = (TextView) v.findViewById(R.id.textView);
        tapButton = (ImageButton) v.findViewById(R.id.btnTap);
        tapButton.setOnClickListener(this);
       // wattCount.setText(((main)getActivity()).getScore().toString());
        return v;
    }

    public void updateView()
    {
        wattCount.setText(((main)getActivity()).getScore().toString());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTap:

                if (loaded) {
                    soundPool.play(soundID, ((main)getActivity()).audioVolume(), ((main)getActivity()).audioVolume(), 1, 0, 1f);
                    //Log.e("Test", "Played sound");
                }

                //int i = score.
                //score.in


                //score.increaseScore();

                ((main)getActivity()).increaseScore();
                wattCount.setText(((main)getActivity()).getScore().toString());

                Log.e("Test", "TAP");


                getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

                soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int SampleId, int status) {
                        loaded=true;

                    }
                });
                soundID = soundPool.load(getActivity(), R.raw.shortclick, 1);
        }
    }
}