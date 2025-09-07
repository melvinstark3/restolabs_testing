package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class getComoOTP extends browserSetup{
    public String getOTP(){
        String otp = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(readProperty("comoOTPapiURL"));
            String requestBody = String.format("{\n" +
                    "    \"phone_number\": \""+readProperty("comoLoginNumber")+"\",\n" +
                    "    \"api_key\": \""+readProperty("comoAPIkey")+"\"\n" +
                    "}");
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);

                JsonNode responseNode = rootNode.get("response");
                if (responseNode.has("code")) {
                    otp = responseNode.get("code").asText();
                    System.out.println("Como login OTP for " + readProperty("comoLoginNumber") +" is " + otp);
                } else {
                    System.err.println("Error: 'Code' not found in the API response.");
                    System.err.println("Como API response: " + responseBody);
                }
            }
        } catch (Exception e) {
            System.out.println("Something Went Wrong with the Como API");
        }
        return otp;
    }
}
