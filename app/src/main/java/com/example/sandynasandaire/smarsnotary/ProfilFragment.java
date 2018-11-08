package com.example.sandynasandaire.smarsnotary;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandynasandaire.smarsnotary.model.Notaire;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.sandynasandaire.smarsnotary.utils.settings.API_LINK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressDialog progressDialog;
    Notaire selNotaire;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param notaire Parameter 1.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(Notaire notaire) {
        ProfilFragment fragment = new ProfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        // Inflate the layout for this fragment
        selNotaire = (Notaire) getArguments().get(ARG_PARAM1);

        //tvFullName tvEmail  tvPhone tvReviews btnFollowNotaire tvAddress
        TextView tvFullName = (TextView)  view.findViewById(R.id.tvFullName);
        TextView tvEmail = (TextView)  view.findViewById(R.id.tvEmail);
        TextView tvPhone = (TextView)  view.findViewById(R.id.tvPhone);
        TextView tvReviews = (TextView)  view.findViewById(R.id.tvReviews);
        TextView tvAddress = (TextView)  view.findViewById(R.id.tvAddress);
        TextView tvCommision = (TextView)  view.findViewById(R.id.tvCommision);
        TextView tvStarCount = (TextView)  view.findViewById(R.id.tvStarCount);

        RatingBar rbRating = (RatingBar)  view.findViewById(R.id.rbRating);
        rbRating.setRating(0);
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getActivity(), "Vote:"+v, Toast.LENGTH_SHORT).show();
                String rateValue = String.valueOf(ratingBar.getRating());
                System.out.println("Rate for Module is"+rateValue);
            }
        });

        tvFullName.setText(selNotaire.getPrenom_notaire()+" "+selNotaire.getNom_notaie());
        tvEmail.setText(selNotaire.getEmail());
        tvPhone.setText(selNotaire.getTelephone());
        tvAddress.setText(selNotaire.getAdresse_cabinet());
        tvCommision.setText(selNotaire.getDate_commision());
        tvStarCount.setText("0.0");
        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog =new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        //Toast.makeText(getActivity(), ""+selNotaire.getAdresse_cabinet(), Toast.LENGTH_SHORT).show();
        
        return view;
    }

    private void add_rating(String email, String password) {
        progressDialog.setMessage("Vote en cours...");
        String apiLink = API_LINK+"rating/rating.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("clientID", email);
        params.put("notaireID", selNotaire.getId_notaire());
        params.put("rateValue", "1");

        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    if(array.getJSONObject(0).getString("key").equals("SUCCESS")){
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
            }
        });
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
//            throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
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

    //tvFullName tvEmail  tvPhone tvReviews btnFollowNotaire tvAddress
}
