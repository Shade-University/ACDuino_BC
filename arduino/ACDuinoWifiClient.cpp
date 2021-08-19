#include "libraries/ACDuinoWifiClient.h"

bool AcDuinoWifiClient::authorizeRfid(int rfidTag)
{
    if (host.isEmpty())
        return false; //Should never happen 

    int hostnameLength = host.length() + 1;
    char buffer[hostnameLength];
    host.toCharArray(buffer, hostnameLength); //Convert string to char array

    if (!client.connect(buffer, port))
    {
        Serial.println("connection failed");
        return false;
    }
    Serial.println(rfidTag, HEX);
    Serial.println(String(rfidTag, HEX));
    String postData = "{ \"rfidTag\":\"" + String(rfidTag, HEX) + "\" }";
    Serial.println("Requesting open request..");

    // Send request to the server:
    client.println("POST /api/v1/openRequest HTTP/1.1");
    client.println("Host: ACDuino-server");
    client.println("Accept: application/json");
    client.println("Content-Type: application/json");
    client.print("Content-Length: ");
    client.println(postData.length());
    client.println();
    client.print(postData);
    ///Craft HTTP request and send to server

    unsigned long timeout = millis();
    while (client.available() == 0)
    {
        if (millis() - timeout > 50000)
        {
            Serial.println(">>> Client Timeout !");
            client.stop();
            return false;
        }
    } //check for timeout from server

    if (client.available())
    {
        char c = client.read();
        while (c != '}')
        {
            c = client.read();
            if (c == '{')
            {
                String json = readJson(&client);
                DynamicJsonDocument doc(1024);
                Serial.println(json);
                DeserializationError error = deserializeJson(doc, json);
                const char *status = doc["status"];

                if (error)
                {
                    Serial.println(error.f_str());
                    return false;
                }

                if (doc["status"] == "OPEN_SUCCESS")
                    return true;
                else
                    return false;
            } //Read whole response
        }

        return false;
    }
}

String AcDuinoWifiClient::readJson(WiFiClient *client)
{
    String json;
    char c = '{';
    while (c != '}')
    {
        json += c;
        c = client->read();
    }
    json += c;
    Serial.println(json);
    return json;
} //Helper method to read json