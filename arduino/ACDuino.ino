#include "libraries/ACDuinoWifi.h"
#include "libraries/ACDuinoWifiServer.h"
#include "libraries/ACDuinoHardwareController.h"


char* ssid = "<ssid>>";
char* password = "<password>"; //Wifi config
AcDuinoWifiServer* server;
AcDuinoHardwareController* hwController;
void setup() {
    Serial.begin(9600);
    AcDuinoWifi wifi(ssid, password);
    wifi.connect(); //Will block until connected
    hwController = new AcDuinoHardwareController();
    server = new AcDuinoWifiServer(8080, hwController); //Inicializatio
    server->start(); //Webserver start
}

void loop() {
    server->listen(); //Listen for any requests
    hwController->handleRfid(); //handle rfid cards
    delay(1000);
}