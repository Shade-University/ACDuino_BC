#include "libraries/ACDuinoWifi.h"
#include "libraries/ACDuinoWifiServer.h"
#include "libraries/ACDuinoHardwareController.h"


char* ssid = "Vondra";
char* password = "lopata97";
AcDuinoWifiServer* server;
AcDuinoHardwareController* hwController;
void setup() {
    Serial.begin(9600);
    AcDuinoWifi wifi(ssid, password);
    wifi.connect();
    hwController = new AcDuinoHardwareController();
    server = new AcDuinoWifiServer(8080, hwController);   
    server->start();
}

void loop() {
    server->listen();
    hwController->lightLed();
    delay(1000);
}