package edu.unh.cs.cs619_2015_project2.g5;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import edu.unh.cs.cs619_2015_project2.g5.ui.GridAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReplayActivityFragment extends DialogFragment {

    private GridView gridReplayView;

    protected Button nSpeed, oSpeed, tSpeed;
    protected GridAdapter mGridAdapter;

    public ReplayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_replay, container, false);
        Intent intent = getActivity().getIntent();

	    gridReplayView = (GridView) rootView.findViewById(R.id.gridReplayView);

        nSpeed = (Button) rootView.findViewById(R.id.normalSpeed);

        nSpeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                changeNormalSpeed();
            }
        });

        oSpeed = (Button) rootView.findViewById(R.id.oneSpeed);
        oSpeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                changeOneSpeed();
            }
        });

        tSpeed = (Button) rootView.findViewById(R.id.twospeed);
        tSpeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                changeTwoSpeed();
            }
        });

        return rootView;
    }
    /**
     * Change to normal speed.
     */
    void changeNormalSpeed() {
        Toast.makeText(getActivity(), "normalSpeed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Change to one speed.
     */
    void changeOneSpeed() {
        Toast.makeText(getActivity(), "oneSpeed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Change to two speed.
     */
    void changeTwoSpeed() {
        Toast.makeText(getActivity(), "twoSpeed", Toast.LENGTH_SHORT).show();
    }
}
