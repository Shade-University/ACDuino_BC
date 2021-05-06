#ifndef ACDUINO_WIFI_H
#define ACDUINO_WIFI_H

class AcDuinoWifi
{
public:
  AcDuinoWifi(char *ssid, char *password)
      : ssid(ssid), password(password) {}
  void connect();

private:
  char *ssid;
  char *password;
};
#endif