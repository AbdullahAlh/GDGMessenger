package fragment;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gdgmessenger.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by xlethal on 8/18/13.
 */
public class MessagesListFragment extends Fragment {

    Handler handler = new Handler();
    String[] messages;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages_list, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_messages_list, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	AsyncHttpClient myClient = new AsyncHttpClient();
        myClient.get(getResources().getString(R.string.api_link)+"tweets/?format=json", new AsyncHttpResponseHandler() {
        
        	@Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                
                try {
					JSONArray jsonArray = new JSONArray(s);
					messages = new String[jsonArray.length()];
					for (int i = 0; i < jsonArray.length(); i++) {
						messages[i] = (String) jsonArray.getJSONObject(i).get("tweet");
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
                
                handler.post(new Runnable() {
					
					@Override
					public void run() {
						ListView lv = (ListView)getView().findViewById(R.id.listView_tweetsList);
				        lv.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.message_textview, messages));
					}
				});
            }
        });
        return super.onOptionsItemSelected(item);
    }
    
}
