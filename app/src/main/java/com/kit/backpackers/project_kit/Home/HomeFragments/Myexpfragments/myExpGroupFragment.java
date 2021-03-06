package com.kit.backpackers.project_kit.Home.HomeFragments.Myexpfragments;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kit.backpackers.project_kit.Home.HomeFragments.Adapters.MyExpeditionAdapter;
import com.kit.backpackers.project_kit.R;
import com.kit.backpackers.project_kit.Utils.HttpRequests;
import com.kit.backpackers.project_kit.Utils.UserLoginSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class myExpGroupFragment extends Fragment {


    String[] exp_id;
    String[] exp_location;
    String[] exp_name;

    String userid;
    UserLoginSession userLoginSession;


    ListView myallCols;

    //myExpListView

    int fragNum;
    static myExpGroupFragment init(int val) {
        myExpGroupFragment truitonList = new myExpGroupFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonList.setArguments(args);
        return truitonList;
    }

    public myExpGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_one,
                container, false);

        //initlizing user session in order to get logged in user id
        userLoginSession = new UserLoginSession(getActivity());
        HashMap<String , String> getUserDetaiils = userLoginSession.getUserDetails();
        userid = getUserDetaiils.get(UserLoginSession.userid);

        //setting up list view
        myallCols = (ListView) layoutView.findViewById(R.id.myExpListView);
        return layoutView;
    }


    //once activity is created we call the asyc task in this method
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new LoadMyExpedition().execute();
    }


    //server api call goes here...
    //get my expediton list
    public class LoadMyExpedition extends AsyncTask<Void, Void, String> {
        private MaterialDialog nDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog =  new MaterialDialog.Builder(getActivity())
                    .content("Getting Your Expeditions...")
                    .progress(true, 0)
                    .backgroundColor(Color.WHITE)
                    .contentColor(Color.BLACK)
                    .titleColor(Color.BLACK)
                    .dividerColor(Color.BLACK)
                    .widgetColor(Color.parseColor("#55B0CF"))
                    .show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String responseString = null;
            try {
                //defined in HttpRequest Class. calling that method
                responseString = HttpRequests.GetMyExpeditoin(userid);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(nDialog.isShowing())
                nDialog.dismiss();

            //parsing the json data to display on the list view
            try {
                JSONObject jsonObj = new JSONObject(s);
                JSONArray getData = jsonObj.getJSONArray("result");

                //setting array size to the total data length
                exp_id = new String[getData.length()];
                exp_location = new String[getData.length()];
                exp_name = new String[getData.length()];

                if (getData.length() > 0) {
                    for (int i = 0; i < getData.length(); i++) {
                        JSONObject c = getData.getJSONObject(i);
                        exp_id[i] = c.getString("exp_id");
                        exp_location[i] = c.getString("exp_location");
                        exp_name[i] = c.getString("exp_name");
                    }

                    //passing data to My Expedition Adpater to show the data on list view

                    //MyExpeditionAdapter adapter = new MyExpeditionAdapter(getActivity(), exp_id, exp_location, exp_name, exp_name);


                   MyExpeditionAdapter adapter = new MyExpeditionAdapter(getActivity() , exp_id, exp_location, exp_name);
                    myallCols.setAdapter(adapter);

                }
            } catch (Exception e) {

            }


        }
    }

}