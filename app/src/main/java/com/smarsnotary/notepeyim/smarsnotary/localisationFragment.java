package com.smarsnotary.notepeyim.smarsnotary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.smarsnotary.notepeyim.smarsnotary.model.Notaire;
import com.smarsnotary.notepeyim.smarsnotary.utils.GPSTracker;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link localisationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link localisationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class localisationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressDialog progressDialog;
    Notaire selNotaire;
    SharedPreferences sharedPreferences ;

    // GPSTracker class
    GPSTracker gps;
    Double latitude, longitude;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public localisationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param notaire Parameter 1.
     * @return A new instance of fragment localisationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static localisationFragment newInstance(Notaire notaire) {
        localisationFragment fragment = new localisationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, notaire);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Notaire mp = (Notaire) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.smarsnotary.notepeyim.smarsnotary.R.layout.fragment_localisation, container, false);

        selNotaire = (Notaire) getArguments().get(ARG_PARAM1);
        sharedPreferences = getActivity().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);

        progressDialog =new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        latitude = 0.0;
        longitude = 0.0;

        WebView myWebView = view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.webview);
        // Configure related browser settings
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Enable responsive layout
        myWebView.getSettings().setUseWideViewPort(true);
        // Zoom out if the content width is greater than the width of the viewport
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        myWebView.getSettings().setDisplayZoomControls(false); // disable the default zoom controls on the page
        // Configure the client to use when opening URLs
        myWebView.setWebViewClient(new WebViewClient());
        // Load the initial URL
        //myWebView.loadUrl("https://www.google.com/maps/?q=-15.623037,18.388672");
        //myWebView.loadUrl("https://www.google.com/maps/@-15.394108,17.9629518,9z");
        find_phone_position();
        if(latitude!=0.0 && longitude != 0.0){
            myWebView.loadUrl("https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude+"");
        }else{
            myWebView.loadUrl("https://www.google.com/maps/search/?api=1&query=18.575980,-72.294660");
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
         //   throw new RuntimeException(context.toString()
             //       + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void find_phone_position() {
        // create class object
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getActivity(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            latitude = 0.0;
            longitude = 0.0;
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
}
