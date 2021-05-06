#ifndef ACDUINO_WIFI_SERVER_H
#define ACDUINO_WIFI_SERVER_H

#include "WiFi.h"

class AcDuinoWifiServer {
  public:
    AcDuinoWifiServer(int port)
    {
      server = new WiFiServer(port);
      this->port = port;
    }
    void start();
    void receive();

  private:
    int port;
    WiFiServer* server;
};
#endif