package com.cococompany.android.aq.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Notification;
import com.cococompany.android.aq.models.QuestionNotification;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class NotificationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String lpURL = "";
    private final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    private List<QuestionNotification> questionNotifications;
    private List<Long> questionNotificationIds;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        LoginPreferences preferences = new LoginPreferences(getContext());
//        lpURL = "http://10.0.2.2:8080/lp/qnotifications/" + preferences.getUser().getId();

//        lpURL = projectBaseUrl + "/lp/qnotifications/" + preferences.getUser().getId();

//        new NotificationsTask().execute(lpURL);

//        lp(lpURL);
    }

    private void lp(final String query) {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.setTimeout(60000);
        httpClient.get(query, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getActivity(), "Notification via lp retrieved!", Toast.LENGTH_LONG).show();
                Log.i("LP", query);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("omg android", statusCode + " " + throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("omg android", statusCode + " " + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Toast.makeText(getActivity(), "Notification via lp retrieved!", Toast.LENGTH_LONG).show();
                Log.i("LP", query);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Toast.makeText(getActivity(), "Notification via lp retrieved!", Toast.LENGTH_LONG).show();
                Log.i("LP", query);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(getActivity(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("omg android", statusCode + " " + throwable.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    class NotificationsTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.DAYS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();

//                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(params[0])
                        .build();

                Response httpResponse = null;

                try {
                    System.out.println("lp before execute");
                    httpResponse = client.newCall(request).execute();
                    System.out.println("lp after execute");
                    int statusCode = httpResponse.code();

                    if (statusCode == 200) {
                        String response = httpResponse.body().string();
                        parseLpJson(response);
                        result = 1;
                    } else {
                        result = 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                System.out.println("Retrieved value from lp: " + questionNotifications);
                new DeleteMyNotificationsTask().execute(lpURL);
                new NotificationsTask().execute(lpURL);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class DeleteMyNotificationsTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;

            try {
                OkHttpClient client = new OkHttpClient();

                MediaType MEDIA_TYPE_JSON
                        = MediaType.parse("application/json;charset=UTF-8");

                String body = questionNotificationIds.toString();

                Request request = new Request.Builder()
                        .url(params[0])
                        .delete(RequestBody.create(MEDIA_TYPE_JSON, body))
                        .build();

                Response httpResponse = null;

                try {
                    httpResponse = client.newCall(request).execute();
                    int statusCode = httpResponse.code();

                    if (statusCode == 200) {
                        String response = httpResponse.body().string();
                        result = 1;
                    } else {
                        result = 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                System.out.println("Deleted my notifications from lp");
            } else {
                Toast.makeText(getActivity(), "Failed to delete notifications from lp!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Парсинг нагадувань з JSON
    private void parseLpJson(String result) {
        List<Notification> notifications = new ArrayList<>();
        List<User> users = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(result);
            QuestionNotification item;
            for (int i = 0; i < array.length(); i++) {
                item = new QuestionNotification();

                JSONObject jsonObject = array.getJSONObject(i);
                if (jsonObject.has("id"))
                    item.setId(jsonObject.getLong("id"));
                if (jsonObject.has("creationTime"))
                    item.setId(jsonObject.getLong("creationTime"));
                if (jsonObject.has("notification")) {
                    JSONObject jsonNotification = jsonObject.getJSONObject("notification");

                    Notification notification = null;
                    if (jsonNotification != null) {
                        notification = new Notification();
                        notification.setId(jsonNotification.getLong("id"));
                        if (jsonNotification.has("notificationType"))
                            notification.setNotificationType(jsonNotification.getString("notificationType"));
                        if (jsonNotification.has("subjectId"))
                            notification.setSubjectId(jsonNotification.getLong("subjectId"));
                        if (jsonNotification.has("content"))
                            notification.setContent(jsonNotification.getString("content"));
                        if (jsonNotification.has("publicationTime"))
                            notification.setCreationTime("publicationTime");

                        notifications.add(notification);
                    } else {
                        Long id = jsonObject.getLong("notification");
                        for (Notification sNotification: notifications) {
                            if (id.equals(sNotification.getId())) {
                                notification = sNotification;
                                break;
                            }
                        }
                    }

                    item.setNotification(notification);
                }

                if (jsonObject.has("user")) {
                    JSONObject jsonUser = jsonObject.getJSONObject("user");

                    User user = null;
                    if (jsonUser != null) {
                        user = new User();
                        user.setId(jsonUser.getLong("id"));
                        if (jsonUser.has("creationTime"))
                            user.setCreationTime(jsonUser.getString("creationTime"));
                        if (jsonUser.has("email"))
                            user.setEmail(jsonUser.getString("email"));
                        if (jsonUser.has("active"))
                            user.setActive(jsonUser.getBoolean("active"));

                        users.add(user);
                    } else {
                        Long id = jsonObject.getLong("user");
                        for (User sUser: users) {
                            if (id.equals(sUser.getId())) {
                                user = sUser;
                                break;
                            }
                        }
                    }

                    item.setUser(user);
                }

                questionNotifications.add(item);
                questionNotificationIds.add(item.getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
