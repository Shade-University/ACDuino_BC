#include "libraries/ACDuinoHardwareController.h"

void AcDuinoHardwareController::setLedRegistered()
{
    registered = true;
    digitalWrite(LED_BLUE, HIGH);
    Serial.println("changed registered status to true");
}

void AcDuinoHardwareController::setLedUnregistered()
{
    registered = false;
    digitalWrite(LED_BLUE, LOW);
    Serial.println("changed registered status to false");
}

void AcDuinoHardwareController::blinkOpenSuccess()
{
    digitalWrite(LED_BLUE, LOW);

    digitalWrite(LED_GREEN, HIGH);
    delay(2000);
    digitalWrite(LED_GREEN, LOW);

    digitalWrite(LED_BLUE, HIGH);
}

void AcDuinoHardwareController::blinkOpenDenied()
{
    digitalWrite(LED_BLUE, LOW);

    digitalWrite(LED_RED, HIGH);
    delay(2000);
    digitalWrite(LED_RED, LOW);

    digitalWrite(LED_BLUE, HIGH);
}
