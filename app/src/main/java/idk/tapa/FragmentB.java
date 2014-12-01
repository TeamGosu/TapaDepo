package idk.tapa;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Created by Mark and Brendan, on going project from date 20/11/2014.
 */

public class FragmentB extends Fragment implements View.OnClickListener {
    TextView wattCount;
    ImageButton tapButton;

    //sound pool
    SoundPool soundPool;
    int soundID;
    boolean loaded = false;

    //drag variables
    private ImageView myImage;
    private static final String IMAGEVIEW_TAG = "The Android Logo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_fragmentb, container, false);

        //wattCount = (TextView) v.findViewById(R.id.textView);
       // tapButton = (ImageButton) v.findViewById(R.id.btnTap);
       // tapButton.setOnClickListener(this);

        // Sets the tag
      // myImage.setTag(IMAGEVIEW_TAG);
     //  myImage = (ImageView) v.findViewById(R.id.image);

        // set the listener to the dragging data
       // myImage.setOnLongClickListener(new MyClickListener());
        v.findViewById(R.id.toplinear).setOnDragListener(new MyDragListener());
        v.findViewById(R.id.bottomlinear).setOnDragListener(new MyDragListener());
        return v;
    }

    public void updateView() {
        //wattCount.setText(((main) getActivity()).getScore().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTap:

                if (loaded) {
                    soundPool.play(soundID, ((main) getActivity()).audioVolume(), ((main) getActivity()).audioVolume(), 1, 0, 1f);
                    //Log.e("Test", "Played sound");
                }

                //int i = score.
                //score.in
                //score.increaseScore();
                ((main) getActivity()).increaseScore();
                wattCount.setText(((main) getActivity()).getScore().toString());

                getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

                soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int SampleId, int status) {
                        loaded = true;

                    }
                });
                soundID = soundPool.load(getActivity(), R.raw.shortclick, 1);
        }
    }

       /* @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmentb);
        myImage = (ImageView) findViewById(R.id.image);

         // Sets the tag
         myImage.setTag(IMAGEVIEW_TAG);
    */

            // set the listener to the dragging data
        private final class MyClickListener implements View.OnLongClickListener {

            // called when the item is long-clicked
            @Override
            public boolean onLongClick(View view) {
                // TODO Auto-generated method stub

                // create it from the object's tag
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                view.startDrag(data, //data to be dragged
                        shadowBuilder, //drag shadow
                        view, //local data about the drag and drop operation
                        0   //no needed flags
                );


                view.setVisibility(View.INVISIBLE);
                return true;
            }
        }

        class MyDragListener implements View.OnDragListener {
            Drawable normalShape = getResources().getDrawable(R.drawable.normal_shape);
            Drawable targetShape = getResources().getDrawable(R.drawable.target_shape);

            @Override
            public boolean onDrag(View v, DragEvent event) {

                // Handles each of the expected events
                switch (event.getAction()) {

                    //signal for the start of a drag and drop operation.
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;

                    //the drag point has entered the bounding box of the View
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackground(targetShape);    //change the shape of the view
                        break;

                    //the user has moved the drag shadow outside the bounding box of the View
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackground(normalShape);    //change the shape of the view back to normal
                        break;

                    //drag shadow has been released,the drag point is within the bounding box of the View
                    case DragEvent.ACTION_DROP:
                        // if the view is the bottomlinear, we accept the drag item
                        if (v == v.findViewById(R.id.bottomlinear)) {
                            View view = (View) event.getLocalState();
                            ViewGroup viewgroup = (ViewGroup) view.getParent();
                            viewgroup.removeView(view);

                            //change the text
                            TextView text = (TextView) v.findViewById(R.id.text);
                            text.setText("The item is dropped");

                            LinearLayout containView = (LinearLayout) v;
                            containView.addView(view);
                            view.setVisibility(View.VISIBLE);
                        } else {
                            View view = (View) event.getLocalState();
                            view.setVisibility(View.VISIBLE);
                            Context context = getActivity(); // was initially getApplicationContext();
                            Toast.makeText(context, "You can't drop the image here",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }
                        break;

                    //the drag and drop operation has concluded.
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackground(normalShape);    //go back to normal shape

                    default:
                        break;
                }
                return true;
            }
        }
 }




