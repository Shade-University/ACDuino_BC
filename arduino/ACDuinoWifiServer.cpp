#include "libraries/ACDuinoWifiServer.h"

void AcDuinoWifiServer::start()
{
    Serial.println("Attempting to start web server on port: " + this->port);
    server->begin();
    Serial.println("Web server started on port: " + this->port);
}

void AcDuinoWifiServer::listen()
{

    Serial.println("Server is listening for request..");
    WiFiClient client = server->available();

    if (client)
    {
        Serial.println("New client connected.");

        while (client.connected())
        {
            if (client.available())
            {
                char c = client.read();
                Serial.print(c);
                if (c == '{')
                {
                    String json = readJson(&client);

                    DynamicJsonDocument doc(1024);
                    DeserializationError error = deserializeJson(doc, json);

                    if (error)
                    {
                        Serial.println(error.f_str());
                        sendHttpHeader(&client);
                        client.println("{\"status\":\"ERROR\"}");
                        client.stop();
                        return;
                    }

                    if (doc["request"] == "REGISTRATION")
                    {
                        if (registered == false)
                        {
                            secret_key = doc["secret_key"];
                            Serial.println(secret_key);
                            registered = true;
                            Serial.println("Changing blink...");
                            hwController->setLedRegistered();
                            Serial.println("Sending http header");
                            sendHttpHeader(&client);
                            Serial.println("Sending status");
                            client.println("{\"status\":\"OK\"}");
                        }
                    }
                    else if(doc["request"] == "UNREGISTRATION")
                    {
                        secret_key = NULL;
                        registered = false;
                        hwController->setLedUnregistered();
                        sendHttpHeader(&client);
                        client.println("{\"status\":\"OK\"}");

                    }
                    Serial.println("Server stop");
                    client.print("\n");
                    client.stop();
                }
            }
            delay(1);
        }
    }
}

String AcDuinoWifiServer::readJson(WiFiClient* client)
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
}

void AcDuinoWifiServer::sendHttpHeader(WiFiClient* client)
{

    client->println("HTTP/1.1 200 OK");
    client->println("Content-Type: application/json");
    client->println("Server: ACDuino-client");
    client->println("Connection: close");
    client->print("\n");
}