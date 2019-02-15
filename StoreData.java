package bouzid.spyme.mosaic.bouzid;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoreData  {

    private static Context ctx;

    public StoreData(Context context){
        ctx=context;
    }


    public void sendGetRequest(String url){
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(ctx);







        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AAAAAAAAAA",response);
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("AA", "11"); //Add the data you'd like to send to the server.
                MyData.put("BB", "22"); //Add the data you'd like to send to the server.
                return MyData;
            }
        };

        ExampleRequestQueue.add(ExampleStringRequest);


    }

    public void SendPostRequest( String url,String msg,String DataString ){
        final String msgg=msg;
        final String DataStringg=DataString;
        url = "https://radiant-cliffs-98969.herokuapp.com/storeData";
        url = "http://195.148.125.96/script.php";

        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("WWWWWWWWWWWWWWWWW", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(DataStringg, msgg);
                params.put("AA", "data");

                return params;
            }
        };
        queue.add(postRequest);
    }

    public int SendPostRequestNew( String url,String AppEvents1,String SelectedApp1,String MobilityChange1, String LastMobility1 ){
        if(url==null || AppEvents1==null || SelectedApp1==null || MobilityChange1==null || LastMobility1==null ){
            return -1;
        }
        final String aa=AppEvents1;
        final String bb=SelectedApp1;
        final String cc=MobilityChange1;
        final String dd=LastMobility1;
        //final String DataStringg=DataString;
        url = "http://195.148.125.96/script.php";

        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("AALTO", response);

                        MainActivity.SuccessPost=1;
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.AALTO", String.valueOf(error));
                       // error.printStackTrace();
                        MainActivity.SuccessPost=0;
                        error.printStackTrace();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("AA", aa);
                params.put("BB", bb);
                params.put("CC", cc);
                params.put("DD", dd);


                Log.d("CHECKK.AALTO", String.valueOf(aa));
                Log.d("CHECKK.AALTO", String.valueOf(bb));
                Log.d("CHECKK.AALTO", String.valueOf(cc));
                Log.d("CHECKK.AALTO", String.valueOf(dd));

                return params;

            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);
        return 1;
    }




    public void SaveDataDatabase( String url,String AppEvents1,String SelectedApp1,String MobilityChange1, String LastMobility1 ){

        /*
        final String AppEvents=AppEvents1;
        final String SelectedApp= SelectedApp1;
        final String MobilityChange=MobilityChange1;
        final String LastMobility=LastMobility1;
        url = "http://195.148.125.94/katia.php";


        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("WWWWWWWWWWWWWWWWWWWWWWWW", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("AA",AppEvents);
                params.put("BB", SelectedApp);
                params.put("CC",MobilityChange);
                params.put("DD", LastMobility);

                return params;
            }
        };
        queue.add(postRequest);


        Map<String, String> params = new HashMap<String, String>();
        params.put("userID", "userid");
        params.put("email","email");
        params.put("passwd", "password");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, "url", new JSONObject(params),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("response -->> " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("change Pass response -->> " + error.toString());
                    }
                });

        request.setRetryPolicy(new

                DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(ctx).add(request);
  */


    }




    }

















