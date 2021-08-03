#include "libraries/ACDuinoHardwareController.h"

void AcDuinoHardwareController::lightLed()
{
    if(registered == true)
    {
        Serial.println("blink green");
        digitalWrite(LED_GREEN, HIGH);
        digitalWrite(LED_RED, LOW);
    }
    else
    {
        Serial.println("blink red");
        digitalWrite(LED_GREEN, LOW);
        digitalWrite(LED_RED, HIGH); 
    }
}

void AcDuinoHardwareController::setLedRegistered()
{
    registered = true;
    Serial.println("changed registered status to true");
}

void AcDuinoHardwareController::setLedUnregistered()
{
    Serial.println("changed registered status to false");
    registered = false;
}