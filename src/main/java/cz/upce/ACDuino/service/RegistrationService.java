package cz.upce.ACDuino.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.ACDuino.enums.RegistrationResponseStatus;
import cz.upce.ACDuino.model.RegistrationRequest;
import cz.upce.ACDuino.model.RegistrationResponse;
import cz.upce.ACDuino.security.encryption.ContentEncryptor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationService {

    private static final int TIMEOUT = 5000;
    private static final int PORT = 8080;

    private final ContentEncryptor encryptor;

    public RegistrationService(ContentEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public RegistrationResponse sendRegistrationRequest(String ip, boolean https) throws IOException {
        if(!InetAddress.getByName(ip).isReachable(TIMEOUT)) {
            return new RegistrationResponse(
                    UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    RegistrationResponseStatus.HOST_UNREACHABLE);
        }

        URL url = new URL(
                https ? "https://" : "http://"
                        + ip
                        + ":" + PORT
                        + "/registration");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        ObjectMapper objectMapper = new ObjectMapper();
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = objectMapper.writeValueAsBytes(new RegistrationRequest(encryptor.getKey()));
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return objectMapper.readValue(response.toString(), RegistrationResponse.class);
        }
    }
}
