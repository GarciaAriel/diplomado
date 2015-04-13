package com.example.ariel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bmapp.contacts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ResultsFragment extends Fragment {

    private static final String LOG_TAG = ResultsFragment.class.getSimpleName();
    private ArrayAdapter<String> arrayAdapter;

    public ResultsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateResults();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }




    private void updateResults() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        GetResultTask task = new GetResultTask();

        task.execute();//teamId, days
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.result_view,
                R.id.result_text_view,
                new ArrayList<String>());

        ListView listView = (ListView)rootView.findViewById(R.id.result_list_view);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);

                intent.putExtra(Intent.EXTRA_TEXT, arrayAdapter.getItem(position));
                startActivity(intent);
            }
        });

        return rootView;
    }

    class GetResultTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray result = new JSONArray();
            if (params.length != 0) {
//                result[0]= {'hola'};
                return result;
            }

            String resultString = Utility.getJsonStringFromNetwork();

            try {
                return Utility.parseFixtureJson(resultString);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                e.printStackTrace();
                return result;
            }
        }

        @Override
        protected void onPostExecute(JSONArray array) {
            arrayAdapter.clear();
            final String USER = "user";
            final String NAME = "name";
            final String USERNAME = "username";
            final String PICTURE =  "picture";
            String name="";
            for (int i = 0; i < array.length(); i++) {
                JSONObject resultObject;
                String srcfoto="http://api.randomuser.me/portraits/men/74.jpg";
                try {
                    JSONObject matchObject = array.getJSONObject(i);
                    resultObject = matchObject.getJSONObject(USER);
                    JSONObject photo = resultObject.getJSONObject(PICTURE);
                    JSONObject resultObjectName = resultObject.getJSONObject(NAME);
                    name = resultObjectName.getString("title")+" "+resultObjectName.getString("first")+" "+resultObjectName.getString("last");

                     srcfoto = photo.getString("thumbnail");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                arrayAdapter.add(name);
            }

        }
    }
}
