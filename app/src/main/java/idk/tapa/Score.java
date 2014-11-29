package idk.tapa;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Mark on 21/11/2014.
 */

public class Score {
    private Integer watts;
    private Integer Pwatts;

    public Score()
    {
        watts=0;
        Pwatts=1;
    }

    public void incPwatts(Integer incAmount)
    {
        Pwatts = Pwatts + incAmount;
    }

    public Integer getPwatts()
    {
        return Pwatts;
    }

    public Integer getWatts()
    {
        return watts;
    }

    public void increaseScore()
    {
        watts += 1;
    }

    public void passiveIncreaseScore()
    {
        watts += Pwatts;
    }


}
