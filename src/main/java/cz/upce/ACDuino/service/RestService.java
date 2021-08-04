package cz.upce.ACDuino.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.ACDuino.AcDuinoApplication;
import cz.upce.ACDuino.enums.RequestType;
import cz.upce.ACDuino.enums.ResponseStatus;
import cz.upce.ACDuino.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
public class RestService {

    private static final Logger logger = LoggerFactory.getLogger(AcDuinoApplication.class);

    private final RequestFactory requestFactory;
    private final RestTemplate restTemplate;

    @Value("${server.port}")
    private int serverPort;

    public RestService(RequestFactory requestFactory, RestTemplateBuilder restTemplateBuilder) {
        this.requestFactory = requestFactory;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Response sendRegistrationRequest(String ip) {
        logger.info("Sending registration request");
        return sendRequest(ip, RequestType.REGISTRATION);
    }

    public Response sendUnregistrationRequest(String ip) {
        logger.info("Sending unregistration request");
        return sendRequest(ip, RequestType.UNREGISTRATION);
    }

    public Response sendCommandOpenRequest(String ip) {
        logger.info("Sending command open request");
        return sendRequest(ip, RequestType.COMMAND_OPEN);
    }

    private Response sendRequest(String ip, RequestType type) {
        String url = "http://{ip}:{port}{url}";HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Request> entity = new HttpEntity<>(requestFactory.getRequest(type), headers);

        return restTemplate.postForObject(url, entity, Response.class, ip, serverPort, type.getUrl());
    }
}
