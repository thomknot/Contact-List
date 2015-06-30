package com.orion.contacts;

import com.orion.contacts.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;








public class MainActivity extends ListActivity {
	
	private ProgressBar progressBar1;

	
	private static String url = "http://jsonplaceholder.typicode.com/users/";

	
	private static final String TAG_CONTACTS = "user";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_STREET = "street";
	private static final String TAG_SUITE = "suite";
	private static final String TAG_CITY = "city";
	private static final String TAG_ZIPCODE = "zipcode";
	private static final String TAG_GEO = "geo";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_WEBSITE = "website";
	private static final String TAG_COMPANY = "company";
	private static final String TAG_COMPANY_NAME = "name";
	private static final String TAG_CATCHPHRASE = "catchPhrase";
	private static final String TAG_BS = "bs";
	
	private int sort=0;

	JSONArray contacts = null;

	ArrayList<TreeMap<String, String>> contactList;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        getActionBar().setIcon(
     		   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(200, 0, 0, 139)));
        
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setVisibility(View.GONE);
        
      
		
		contactList = new ArrayList<TreeMap<String, String>>();

		ListView lv = getListView();

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			
			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String email = ((TextView) view.findViewById(R.id.email))
						.getText().toString();
				String phone = ((TextView) view.findViewById(R.id.phone))
						.getText().toString();
				String username =((TextView) view.findViewById(R.id.username))
						.getText().toString();
				String street =((TextView) view.findViewById(R.id.address))
						.getText().toString();
				String suite =((TextView) view.findViewById(R.id.suite))
						.getText().toString();
				String city1 =((TextView) view.findViewById(R.id.city))
						.getText().toString();
				String zipcode =((TextView) view.findViewById(R.id.zipcode))
						.getText().toString();
				String website =((TextView) view.findViewById(R.id.website))
						.getText().toString();
				String company_name =((TextView) view.findViewById(R.id.Compname))
						.getText().toString();
				String catchphrase =((TextView) view.findViewById(R.id.catchp))
						.getText().toString();
				String bs =((TextView) view.findViewById(R.id.bs))
						.getText().toString();
				String lat =((TextView) view.findViewById(R.id.lat))
						.getText().toString();
				String lng =((TextView) view.findViewById(R.id.lng))
						.getText().toString();
				// Starting single contact activity    REQUIRED TO PASS TO DETAILED CONTACT
				Intent in = new Intent(getApplicationContext(),
						DetailedContact.class);
				in.putExtra(TAG_ID, id);
				in.putExtra(TAG_NAME, name);
				in.putExtra(TAG_USERNAME, username);
				in.putExtra(TAG_EMAIL, email);
				in.putExtra(TAG_PHONE, phone);
				in.putExtra(TAG_STREET, street);
				in.putExtra(TAG_SUITE, suite);
				in.putExtra(TAG_CITY, city1);
				in.putExtra(TAG_ZIPCODE, zipcode);
				in.putExtra(TAG_WEBSITE, website);
				//in.putExtra(TAG_COMPANY_NAME, company_name);
				in.putExtra(TAG_CATCHPHRASE, catchphrase);
				in.putExtra(TAG_BS, bs);
				in.putExtra(TAG_LAT, lat);
				in.putExtra(TAG_LNG, lng);
				System.out.print (TAG_COMPANY_NAME+ "keirah");
				
				startActivity(in);

			}
		});

		// Calling async task to get json	
		new GetContacts().execute();
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch (item.getItemId()) {
        case R.id.A2Z:
        {
        	//contactList.clear();
		    Toast savedToast = Toast.makeText(getApplicationContext(), 
		        "Sorting A - Z", Toast.LENGTH_SHORT);
		    savedToast.show();
		  
		    
		    sort = 1;
		    new GetContacts().execute();
		    
        }
            return true;
        case R.id.Z2A:
        	
        {
        	
        	 Toast savedToast = Toast.makeText(getApplicationContext(), 
     		        "Sorting Z -A", Toast.LENGTH_SHORT);
     		    savedToast.show();
     		    
     		    
     		    sort =2;
     		   new GetContacts().execute();
     		 
        	
        }
            //showHelp();
            return true;
        default:
		
		}
		return super.onOptionsItemSelected(item);
    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress animation
			
			
			progressBar1.setVisibility(View.VISIBLE);
			
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			
			
			contactList.clear();
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			//JSONArray jsonStr = sh.getJSONFromUrl(url);
			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONArray contacts = new JSONArray(jsonStr);
					
					// Getting JSON Array node
					//contacts = jsonObj.getJSONArray(TAG_CONTACTS);

					// looping through All Contacts
					for (int i = 0; i < contacts.length(); i++) {
						JSONObject c = contacts.getJSONObject(i);
						
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String username = c.getString(TAG_USERNAME);
						String email = c.getString(TAG_EMAIL);
						
						
						// Phone node is JSON Object
						JSONObject address = c.getJSONObject(TAG_ADDRESS);
						String street = address.getString(TAG_STREET);
						String suite = address.getString(TAG_SUITE);
						String city2 = address.getString(TAG_CITY);
						String zipcode = address.getString(TAG_ZIPCODE);
						
						
						JSONObject geo = address.getJSONObject(TAG_GEO);
						String lat = geo.getString(TAG_LAT);
						String lng = geo.getString(TAG_LNG);
						
						
						String phone = c.getString(TAG_PHONE);
						String website = c.getString(TAG_WEBSITE);
						
						JSONObject company = c.getJSONObject(TAG_COMPANY);
						String company_name = company.getString(TAG_COMPANY_NAME);
						String catchphrase = company.getString(TAG_CATCHPHRASE);
						String bs = company.getString(TAG_BS);
						
						

						// tmp treemap for detailed contact
						TreeMap<String, String> contact = new TreeMap<String, String>();

						
						
						
						// adding each child node to TreeMap key => value
						contact.put(TAG_ID, id);
						contact.put(TAG_NAME, name);
						contact.put(TAG_USERNAME, username);
					    contact.put(TAG_EMAIL, email);
						contact.put(TAG_PHONE, phone);
						contact.put(TAG_STREET, street);
						contact.put(TAG_SUITE, suite);
						contact.put(TAG_CITY, city2);
						contact.put(TAG_ZIPCODE, zipcode);
						contact.put(TAG_CATCHPHRASE, catchphrase);
						contact.put(TAG_BS, bs);
						//contact.put(TAG_COMPANY_NAME, company_name);
						contact.put(TAG_WEBSITE , website);
						contact.put(TAG_LAT, lat);
						contact.put(TAG_LNG , lng);
						
						
						// adding contact to contact list
						contactList.add(contact);
						
						switch (sort){
						case 2:
						{

						    Collections.sort(contactList, new Comparator<TreeMap<String, String>>()
									{
									    @Override
									    public int compare(TreeMap<String, String> a, TreeMap<String, String> b)
									    {
									        return b.get(TAG_NAME).compareTo(a.get(TAG_NAME));
									    }
									});
						    break;
						}
						case 1:
						{
							
							Collections.sort(contactList, new Comparator<TreeMap<String, String>>()
				   					{
				   					    @Override
				   					    public int compare(TreeMap<String, String> a, TreeMap<String, String> b)
				   					    {
				   					        return a.get(TAG_NAME).compareTo(b.get(TAG_NAME));
				   					    }
				   					});
							break;
							
						}
						case 0:
							break;
						}

						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
		
			 if(progressBar1.isShown())
				progressBar1.setVisibility(View.GONE);
			
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter1 = new SimpleAdapter(
					MainActivity.this, contactList,
					R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
							TAG_PHONE, TAG_USERNAME,TAG_STREET ,TAG_SUITE,TAG_CITY, TAG_ZIPCODE,TAG_CATCHPHRASE,TAG_BS,
							TAG_WEBSITE,TAG_LAT,TAG_LNG,TAG_COMPANY_NAME}, new int[] { R.id.name,R.id.email, R.id.phone, R.id.username,
							R.id.address,R.id.suite, R.id.city , R.id.zipcode, R.id.catchp, R.id.bs, R.id.website,
							R.id.lat, R.id.lng, R.id.Compname});
			

			setListAdapter(adapter1);
			
			
			
			
		}

	}


}
