package com.mjc.translate.korTrans;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mjc.translate.R;
import com.mjc.translate.config.Papago;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class KorToJpnFragment extends Fragment implements View.OnClickListener {

    private EditText inputText;
    private TextView outputText;
    private BootstrapButton submitButton;
    private String inputString;
    private String result;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kortojpn, container, false);

        layoutInit();


        return view;
    }


    private void layoutInit() {
        inputText = view.findViewById(R.id.korToJpn_input);
        outputText = view.findViewById(R.id.korToJpn_output);
        submitButton = view.findViewById(R.id.korToJpn_submit);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        inputString = inputText.getText().toString();
        new BackgroundTask().execute();
    }

    // 백그라운드에서 파파고 API 사용!
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(Integer... arg0) {
            StringBuilder output = new StringBuilder();
            String clientId = Papago.getClientId(); // 애플리케이션 클라이언트 아이디 값";
            String clientSecret = Papago.getClientSecret(); // 애플리케이션 클라이언트 시크릿 값";
            try {
                // 번역문을 UTF-8으로 인코딩합니다.
                String text = URLEncoder.encode(inputString, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

                // 파파고 API와 연결을 수행합니다.
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                // 번역할 문장을 파라미터로 전송합니다.
                String postParams = "source=ko&target=ja&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                // 번역 결과를 받아옵니다.
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    output.append(inputLine);
                }
                br.close();
            } catch(Exception ex) {
                Log.e("SampleHTTP", "Exception in processing response.", ex);
                ex.printStackTrace();
            }
            result = output.toString();
            return null;
        }

        protected void onPostExecute(Integer a) {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            if(element.getAsJsonObject().get("errorMessage") != null) {
                Log.e("번역 오류", "번역 오류가 발생했습니다. " +
                        "[오류 코드: " + element.getAsJsonObject().get("errorCode").getAsString() + "]");
            } else if(element.getAsJsonObject().get("message") != null) {
                // 번역 결과 출력
                outputText.setText(element.getAsJsonObject().get("message").getAsJsonObject().get("result")
                        .getAsJsonObject().get("translatedText").getAsString());
            }

        }

    }

}
