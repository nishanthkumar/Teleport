package teleporting.teleport.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import teleporting.teleport.R;


public class LauncherFragment extends Fragment implements View.OnClickListener {


    private OnLauncherFragmentInteractionListener mListener;

    private static LauncherFragment sInstance = null;

    Button mOnDemand, mMedicines;
    public LauncherFragment() {
        // Required empty public constructor
    }

    public static LauncherFragment getInstance() {
        if (sInstance == null) {
            sInstance = new LauncherFragment();
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
        View view = inflater.inflate(R.layout.fragment_launcher, container, false);
        mOnDemand = (Button)view.findViewById(R.id.onDemand);
        mMedicines = (Button)view.findViewById(R.id.mediciens_button);

        mOnDemand.setOnClickListener(this);
        mMedicines.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLauncherFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLauncherFragmentInteractionListener) {
            mListener = (OnLauncherFragmentInteractionListener) context;
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
            case R.id.onDemand:
                Intent intent = new Intent(getActivity(), OnDemandFragment.class);
                startActivity(intent);

                break;
            case R.id.mediciens_button:
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
    public interface OnLauncherFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLauncherFragmentInteraction(Uri uri);
    }
}
