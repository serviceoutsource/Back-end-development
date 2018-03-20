package com.thinkgem.jeesite.modules.aliGenie.service.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class SendDataToPY {

    private String getRecommend(String accessToken, Boolean isNewUser) {
        StringBuffer context = new StringBuffer();
        BufferedReader br = null;
        try {
            URL realUrl = new URL("http://127.0.0.1:8000/hyperflex/recipe_recommended?isNewUser=" + isNewUser.toString());
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.connect();
            if (connection.getResponseCode() != 404) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String tempLine;
                while ((tempLine = br.readLine()) != null) {
                    context.append(tempLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return context.toString();
    }


}
