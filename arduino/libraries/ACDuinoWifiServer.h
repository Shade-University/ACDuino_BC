#ifndef ACDUINO_WIFI_SERVER_H
#define ACDUINO_WIFI_SERVER_H

#include "WiFi.h"
#include "ACDuinoHardwareController.h"
#include "ArduinoJson.h"


class AcDuinoWifiServer {
  public:
    AcDuinoWifiServer(int port, AcDuinoHardwareController* hwController)
    {
      server = new WiFiServer(port);
      this->hwController = hwController;
      this->port = port;
      this->registered = false;
    }
    void start();
    void listen();

  private:
    int port;
    bool registered;
    const char* secret_key;
    WiFiServer* server;
    AcDuinoHardwareController* hwController;
    String readJson(WiFiClient* client);
    void sendHttpHeader(WiFiClient* client);
};
#endif