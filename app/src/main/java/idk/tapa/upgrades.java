package idk.tapa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Mark on 27/11/2014.
 */
public class upgrades extends Activity {

    Button button;
    Integer score;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgradeslayout);

        button = (Button)findViewById(R.id.increaseWatt);

        button.setEnabled(false);

        score = getIntent().getIntExtra("score", 1);
        Toast.makeText(getApplicationContext(), score.toString(), Toast.LENGTH_SHORT).show();

        if (score >= 100)
        {
            button.setEnabled(true);
            score = score-100;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Passive Watts Increased by 1", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "1");
                returnIntent.putExtra("newScore", score.toString());
                setResult(1, returnIntent);
                finish();

            }
        });
    }

}
