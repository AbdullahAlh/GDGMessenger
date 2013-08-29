package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.gdgmessenger.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by xlethal on 8/18/13.
 */
public class MessageFragment extends Fragment implements OnClickListener{

    Handler handler = new Handler();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	view.findViewById(R.id.button_send).setOnClickListener(this);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_message, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_send){
			final String message = ((EditText)getView().findViewById(R.id.editText_message)).getText().toString();
			AsyncHttpClient myClient = new AsyncHttpClient();
            RequestParams requestParams = new RequestParams();
            requestParams.put("tweet", message);
            myClient.post(getResources().getString(R.string.api_link)+"tweets/?format=json", requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    handler.post(new Runnable() {
        	            @Override
        	            public void run() {
        	                 Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
        	                 ((EditText)getView().findViewById(R.id.editText_message)).setText("");
        	            }
        	        });
                }
                
                @Override
                public void onFailure(Throwable arg0, String arg1) {
                	// TODO Auto-generated method stub
                	super.onFailure(arg0, arg1);
                	 Toast.makeText(getActivity(), "Message not Sent", Toast.LENGTH_SHORT).show();
 	                
                }
            });
			
		}
		
	}
}
