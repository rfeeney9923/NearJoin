package edu.osu.cse.nearjoin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fish on 11/11/2014.
 */
public class MapEventFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.event_map_fragment, container, false);
        return messageLayout;
    }
}
