package teleporting.teleport.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import teleporting.teleport.R;

public class OnDemandFragment extends Fragment implements View.OnClickListener {
    TextView mSource, mDestination;

    private OnDemandFragmentInteractionListener mListener;

    private static OnDemandFragment sInstance = null;

    public OnDemandFragment() {
        // Required empty public constructor
    }

    public static OnDemandFragment getInstance() {
        if (sInstance == null) {
            sInstance = new OnDemandFragment();
        }
        return sInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_demand, container, false);
        mSource = (TextView)view.findViewById(R.id.source);
        mDestination = (TextView)view.findViewById(R.id.destination);
        mSource.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDemandFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDemandFragmentInteractionListener) {
            mListener = (OnDemandFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLauncherFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.source:
                break;
            case R.id.destination:
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDemandFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDemandFragmentInteraction(Uri uri);
    }
}
