package cz.upce.ACDuino.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.ACDuino.AcDuinoApplication;
import cz.upce.ACDuino.enums.RequestType;
import cz.upce.ACDuino.enums.ResponseStatus;
import cz.upce.ACDuino.model.RegistrationResponse;
import cz.upce.ACDuino.model.Response;
import cz.upce.ACDuino.model.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(AcDuinoApplication.class);
    private static final int TIMEOUT = 20000;

    private final RequestFactory requestFactory;

    @Value("${server.port}")
    private int serverPort;

    public RegistrationService(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public Response sendRegistrationRequest(String ip) throws IOException {
        return sendRequest(ip, RequestType.REGISTRATION);
    }

    public Response sendUnregistrationRequest(String ip) throws IOException {
        return sendRequest(ip, RequestType.UNREGISTRATION);
    }

    private Response sendRequest(String ip, RequestType type) throws IOException {
        if (!InetAddress.getByName(ip).isReachable(TIMEOUT)) {
            return new Response(ResponseStatus.HOST_UNREACHABLE);
        }

        URL url = new URL("http://" + ip + ":" + serverPort + type.getUrl());

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        ObjectMapper objectMapper = new ObjectMapper();
        try (OutputStream os = con.getOutputStream()) {

            byte[] input = objectMapper.writeValueAsBytes(requestFactory.getRequest(type));
            os.write(input, 0, input.length);
            logger.info(new String(input));
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            logger.info(response.toString());
            ResponseStatus status = objectMapper.readValue(response.toString(), ResponseStatus.class);
            return new RegistrationResponse(status);
        }
    }
}
