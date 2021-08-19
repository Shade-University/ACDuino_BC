#include "WiFi.h"
#include "libraries/ACDuinoWifi.h"

void AcDuinoWifi::connect()
{
    WiFi.begin(ssid, password);
    Serial.println("Connecting");
    while (WiFi.status() != WL_CONNECTED)
    {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.print("Connected to WiFi network with IP Address: ");
    Serial.println(WiFi.localIP()); 
    //Method to connect to wifi
}