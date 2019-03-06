package com.example;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class WsClient {

    public static void main(String[] args) {

        String builder = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<soap:Envelope "
                + "xmlns:api='http://demo.ls.com/' "
                + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
                + "xmlns:xsd='http://www.w3.org/2001/XMLSchema' "
                + "xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
                "<soap:Body>" +
                "<api:sayHi>" +
                "<arg0>小花脸</arg0>" +
                "</api:sayHi>" +
                "</soap:Body>" +
                "</soap:Envelope>";


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(org.springframework.http.MediaType.TEXT_XML_VALUE), builder);
        Request request = new Request.Builder()
                .url("http://localhost:9000/sayHi?wsdl")
                .post(body)
                .addHeader("SoapAction", "SoapAction")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                log.info("{}", response);
            }
        });
    }
}
