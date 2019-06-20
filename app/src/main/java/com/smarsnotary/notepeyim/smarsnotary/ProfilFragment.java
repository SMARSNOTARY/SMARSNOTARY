package com.smarsnotary.notepeyim.smarsnotary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smarsnotary.notepeyim.smarsnotary.model.Notaire;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.API_LINK;


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
    static TextView tvDate;
    SharedPreferences sharedPreferences ;

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
        View view = inflater.inflate(com.smarsnotary.notepeyim.smarsnotary.R.layout.fragment_profil, container, false);
        // Inflate the layout for this fragment
        selNotaire = (Notaire) getArguments().get(ARG_PARAM1);

        sharedPreferences = getActivity().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);

        progressDialog =new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        //tvFullName tvEmail  tvPhone tvReviews btnFollowNotaire tvAddress
        TextView tvFullName = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvFullName);
        TextView tvEmail = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvEmail);
        TextView tvPhone = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvPhone);
        TextView tvReviews = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvReviews);
        TextView tvAddress = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvAddress);
        TextView tvCommision = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvCommision);
        TextView tvStarCount = (TextView)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvStarCount);

        Button btnFollowNotaire = (Button)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnFollowNotaire);
        btnFollowNotaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               alert_add_meeting("Demander un rendez-vous ", "Tous les rendez-vous inscrit sur ce système sont automatiquements enregistré à l'adresse du cabinet du Notaire");
            }
        });

        RatingBar rbRating = (RatingBar)  view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.rbRating);
        rbRating.setRating(0);
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //Toast.makeText(getActivity(), "Vote:"+ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                String rateValue = String.valueOf(ratingBar.getRating());
               // System.out.println("Rate for Module is"+rateValue);
                progressDialog.setMessage("Vote en cours...");
                progressDialog.show();
                add_rating(rateValue);
            }
        });

        if(sharedPreferences.getString("type_User", null).equals("CLIENT")){
            btnFollowNotaire.setVisibility(View.VISIBLE);
            rbRating.setVisibility(View.VISIBLE);

        }else{
            btnFollowNotaire.setVisibility(View.GONE);
            rbRating.setVisibility(View.GONE);
        }

        tvFullName.setText(selNotaire.getPrenom_notaire()+" "+selNotaire.getNom_notaie());
        tvEmail.setText(selNotaire.getEmail());
        tvPhone.setText(selNotaire.getTelephone());
        tvAddress.setText(selNotaire.getAdresse_cabinet());
        tvCommision.setText(selNotaire.getDate_commision());
        if(selNotaire.getRating() != null){
            tvStarCount.setText(selNotaire.getRating());
        }else{
            tvStarCount.setText("0/0");
        }

        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
            }
        });
        
        return view;
    }


    private void alert_add_meeting(String title, String message) {
      final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View layout=inflater.inflate(com.smarsnotary.notepeyim.smarsnotary.R.layout.alert_dialog_meeting, null);
        dialog.setView(layout);

        final EditText edtSubject=(EditText)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.edtSubject);
        final EditText edtDescMotif=(EditText)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.edtDescMotif);
        tvDate=(TextView)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvDate);
        final Button btnDate=(Button)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnDate);

        final Button btnCancel=(Button)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnCancel);
        final Button btnSave=(Button)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnSave);

        final AppCompatSpinner spTime=(AppCompatSpinner)layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.spTime);

        dialog.setTitle( title )
                .setIcon(android.R.drawable.ic_input_add)
                .setMessage(message);
//                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        if (!edtSubject.getText().toString().isEmpty() && !edtDescMotif.getText().toString().isEmpty() && !tvDate.getText().toString().equals("...")){
//                            progressDialog.setMessage("Enregistrement en cours...");
//                            progressDialog.show();
//                            //add_meeting(dialog, edtSubject.getText().toString(), edtDescMotif.getText().toString(), tvDate.getText().toString(), spTime.getSelectedItem().toString());
//                        }else{
//                            Toast.makeText(getActivity(), "Sélectionner date, entrer un sujet et description pour demande de rendez-vous avec un notaire....", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog show = dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtSubject.getText().toString().isEmpty() && !edtDescMotif.getText().toString().isEmpty() && !tvDate.getText().toString().equals("...")){
                    progressDialog.setMessage("Enregistrement en cours...");
                    progressDialog.show();
                    add_meeting(show, edtSubject.getText().toString(), edtDescMotif.getText().toString(), tvDate.getText().toString(), spTime.getSelectedItem().toString());
                }else{
                    Toast.makeText(getActivity(), "Sélectionner date, entrer un sujet et description pour demande de rendez-vous avec un notaire....", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "datePicker");
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

       /*@Override
        public void onDismiss(DialogInterface dialog) {
            tvDate.setText("...");
            super.onDismiss(dialog);
        }*/

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tvDate.setText(simpleDateFormat.format(c.getTime()));

        }
    }

    private void add_meeting(final AlertDialog dialog, String subject, String description, String date, String time) {

        String apiLink = API_LINK+"meet/meet.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();

        params.put("sujet_motif", subject);
        params.put("motif_description", description);
        params.put("clientUserID", sharedPreferences.getString("iduser", null));
        params.put("notaireUserID", selNotaire.getUserNoID());
        params.put("date_rendez_vous", date+" "+time);

        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
//ACCEPTER //DECLINER //NON-DISPONIBLE //ATTENTE


                    //JSONArray array = json.getJSONArray("response");

                    //notaireadapter.addAll(Notaire.fromJSONArray(json.getJSONArray("response")));
                    if(array.getJSONObject(0).getBoolean("status")){
                        dialog.dismiss();
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Rendez-vous en attente de confirmation de "+selNotaire.getPrenom_notaire()+" "+selNotaire.getNom_notaie()+".", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Erreur lors du sauvegarde, vérifier et éssayer a nouveau...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Erreur lors du sauvegarde, vérifier et éssayer a nouveau [0.1]...", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Erreur lors du sauvegarde, vérifier et éssayer a nouveau [0.2]...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void add_rating(String rateValue) {
        String apiLink = API_LINK+"rating/rating.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("clientID", sharedPreferences.getString("iduser", null));
        params.put("notaireID", selNotaire.getUserNoID());
        params.put("rateValue", 1);

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

        progressDialog.dismiss();
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
