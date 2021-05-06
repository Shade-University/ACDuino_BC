#include "libraries/ACDuinoWifiServer.h"

void AcDuinoWifiServer::start()
{
    Serial.println("Attempting to start web server on port: " + this->port);
    server->begin();
    Serial.println("Web server started on port: " + this->port);
}

void AcDuinoWifiServer::receive()
{
    Serial.println("Server started receiving..");
    WiFiClient client = server->available();
    Serial.println(client);

    if (client)
        Serial.println("New client connected.");

    while (client.connected())
    {
        if (client.available())
        {
            char c = client.read();
            Serial.print(c);
            if(c == '}') break;
        }
        delay(1);
    }
    client.write("OK");
    client.stop();
    Serial.println("end receive");
}