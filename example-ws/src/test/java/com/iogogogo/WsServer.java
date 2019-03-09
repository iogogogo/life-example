package com.iogogogo;

import lombok.extern.slf4j.Slf4j;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by tao.zeng on 2019-03-09.
 */
@Slf4j
@WebService
public class WsServer {

    @WebMethod
    public String sayHi(String name) {
        return "hello " + name;
    }

    public static void main(String[] args) {
        String address = "http://localhost:9000/sayHi";
        WsServer wsServer = new WsServer();
        Endpoint endpoint = Endpoint.publish(address, wsServer);
        log.info("publish endpoint: {}", endpoint);
    }
}
