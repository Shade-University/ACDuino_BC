#include "libraries/ACDuinoWifi.h"
#include "libraries/ACDuinoWifiServer.h"


char* ssid = "Vondra";
char* password = "lopata97";
AcDuinoWifiServer* server;
void setup() {
    Serial.begin(9600);
    AcDuinoWifi wifi(ssid, password);
    wifi.connect();
    server = new AcDuinoWifiServer(8080);
    server->start();
}

void loop() {
    server->receive();
    delay(1000);
}