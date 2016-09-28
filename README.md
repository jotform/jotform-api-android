Updates
------------

# 28.10.2016 -- EU Server API Support Intagrated
If your user is in eu protected mode, now you can use JotForm EU Server API

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

Examples

Print all forms of the user

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
            // you should not harcode API Keys, it is different for each user
            apiClient = new JotformAPIClient("API_KEY_GOES_HERE");
            
            apiClient.getForms(new JsonHttpHandler(){
            
                @Override
    		    public void onSuccess(JSONObject formsResponse){
    			    try {
    				    forms = formsResponse.getJSONArray("content");
                        
                        // do something with the forms jsonArray;
                        
                    } catch (JSONException e){
    				    // fail
    			    }
                    
                }
            });
        }
    }
    
Get latest 100 submissions ordered by creation date

		public class MyActivity extends Avtivity{
        
        private JotformAPIClient apiClient;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
  	    super.onCreate(savedInstanceState);
            
            // this is for demonstration purposes only
            // you should not harcode API Keys, it is different for each user
            apiClient = new JotformAPIClient("API_KEY_GOES_HERE");
            
            apiClient.getSubmissions(100, null, new JsonHttpHandler(){
            
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
    
Submission filter example

		public class MyActivity extends Avtivity{
        
        private JotformAPIClient apiClient;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
  	    super.onCreate(savedInstanceState);
            
            // this is for demonstration purposes only
            // you should not harcode API Keys, it is different for each user
            apiClient = new JotformAPIClient("API_KEY_GOES_HERE");
            
            HashMap<String, String> filter = new HashMap<String, String>();
            filter.put("status", "ACTIVE");
            filter.put("flag", "0");
            filter.put("updated_at", "2013-06-24 08:17:44");
            
            JSONObject filterObject = new JSONObject(filter);
            
            apiClient.getSubmissions(100, "created_at", filterObject, new JsonHttpHandler(){
            
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
    
Delete Submission example

		public class MyActivity extends Avtivity{
        
        private JotformAPIClient apiClient;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
  	    super.onCreate(savedInstanceState);
            
            // this is for demonstration purposes only
            // you should not harcode API Keys, it is different for each user
            apiClient = new JotformAPIClient("API_KEY_GOES_HERE");
            
            long submissionId = SUBMISSION_ID
            
            apiClient.deleteSubmission(submissionId, new JsonHttpHandler(){
            
                @Override
    		    public void onSuccess(JSONObject submissionsResponse){
    		    
    			    try {
                        
                        // do something with the submissions jsonArray;
                        
							} catch (JSONException e){
    				    // fail
    			    }
                    
                }
            });
        }
    }