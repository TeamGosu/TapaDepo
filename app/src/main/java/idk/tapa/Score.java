package idk.tapa;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Mark on 21/11/2014.
 */

public class Score {
    private Integer watts;

    public Score()
    {
        watts=0;
    }

    public Integer getWatts()
    {
        return watts;
    }

    public void increaseScore()
    {
        watts += 1;
    }

}
