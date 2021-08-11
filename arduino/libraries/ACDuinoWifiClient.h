#ifndef ACDUINO_WIFI_CLIENT_H
#define ACDUINO_WIFI_CLIENT_H

#include "WiFi.h"
#include "ArduinoJson.h"

class AcDuinoWifiClient
{
public:
    AcDuinoWifiClient(String host, int port)
        : host(host), port(port) {}

    bool authorizeRfid(int rfidTag);

private:
    WiFiClient client;
    String host;
    int port;

    String readJson(WiFiClient* client);
};
#endif