package com.smarsnotary.notepeyim.smarsnotary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smarsnotary.notepeyim.smarsnotary.adapter.MessageAdapter;
import com.smarsnotary.notepeyim.smarsnotary.model.Message;
import com.smarsnotary.notepeyim.smarsnotary.model.Notaire;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.API_LINK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressDialog progressDialog;
    Notaire selNotaire;
    SharedPreferences sharedPreferences ;
    EditText etMessage;
    ImageButton btSend;

    private ArrayList<Message> Liste_message;
    private com.smarsnotary.notepeyim.smarsnotary.adapter.MessageAdapter messageAdapter;
    ListView lvMessage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(Notaire notaire) {
        MessageFragment fragment = new MessageFragment();
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
        View view = inflater.inflate(com.smarsnotary.notepeyim.smarsnotary.R.layout.fragment_message, container, false);

        selNotaire = (Notaire) getArguments().get(ARG_PARAM1);
        sharedPreferences = getActivity().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);

        progressDialog =new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Téléchargement historique de vos messages...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        lvMessage = view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.listViewMessage);

        etMessage = view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.etMessage);
        btSend = view.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etMessage.getText().toString().isEmpty() || !etMessage.getText().toString().equals("")){
                    send_message(etMessage.getText().toString());
                }

            }
        });
        get_list_message();
        return view;
    }

    private void send_message(String message) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("message", message);

        String fromMe = sharedPreferences.getString("iduser", null);
        String toUSer = selNotaire.getUserNoID();

        client.post(API_LINK+"message/sent.php?from="+fromMe+"&to="+toUSer, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    if(array.getJSONObject(0).getBoolean("status")){
                        etMessage.setText("");
                        get_list_message();
                    }else{
                        Toast.makeText(getActivity(), "Échec envoie, Éssayer à nouveau...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void get_list_message() {
        Liste_message = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(), Liste_message );
        lvMessage.setAdapter(messageAdapter);

        String fromMe = sharedPreferences.getString("iduser", null);
        String toUSer = selNotaire.getUserNoID();

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(API_LINK+"message/read.php?from="+fromMe+"&to="+toUSer, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    progressDialog.dismiss();
                    JSONArray array = json.getJSONArray("response");
                    messageAdapter.addAll(Message.fromJSONArray(json.getJSONArray("response")));
                    Log.d("DEBUG APP: ", Liste_message.toString());
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
                   // + " must implement OnFragmentInteractionListener");
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
}
