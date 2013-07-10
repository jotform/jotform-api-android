Installation
------------

- Download the zipped project from downloads page or clone the git repository to your computer.
- Once you have the project folder in your system, launch Eclipse, click File -> Import.
- Select "Existing Android Code Into Workspace", browse to the project folder and click finish.
- Right click on you own android project (the one that you want to use the library with) and click properties.
- Click "Android" tab and add "jotform-api-android" project using the Add button in the Library section.

Sample Usage
------------

Once you successfully added the library as a dependency to your project, you can use it in your Application as follows;

    package com.example;
    
    import android.app.Activity;
    
    // import Jotform client library
    // and its reponse handler
    import com.jotform.api.JotformAPIClient;
    import com.loopj.android.http.JsonHttpResponseHandler;
    
    public class MyActivity extends Avtivity{
        
        private JotformAPIClient apiClient;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
  	    super.onCreate(savedInstanceState);
            
            // this is for demonstration purposes only
            // there is no use case we can site that includes hardcoding an api key ;)
            apiClient = new JotformAPIClient("API_KEY_GOES_HERE");
            
            apiClient.getSubmissions(null, null, new JsonHttpHandler(){
            
                @Override
    		    public void onSuccess(JSONObject submissionsResponse){
    			    try {
    				    submissions = submissionsResponse.getJSONArray("content");
                        
                        // do something with the submissions jsonArray;
                        
                    } catch (JSONException e){
    				    // fail
    			    }
                    
                }
            });
        }
    }
    
