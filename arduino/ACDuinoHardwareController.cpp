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

void AcDuinoHardwareController::handleRfid()
{
    // head [1 byte] (always 0x02)
    // data [10 byte]
    // checksum [2 byte]
    // tail [1 byte] (always (0x03)

    if (registered && RFID.available() > 0)
    {
        Serial.println("Card detected");
        Serial.println("Loaded: ");

        for (int i = 0; i < RDM6300_PACKET_SIZE; i++)
        {
            rfidId[i] = RFID.read();
            Serial.print(rfidId[i]);
        } //Load RFID

        if (!validateLoadedRfid())
        {
            Serial.println("Validation failed.");
            return;
        } //Validate

        for (int i = 0; i < RDM6300_PACKET_SIZE; i++)
        {
            latestRfidId[i] = rfidId[i];
        }
        lastRfidTime = millis(); 
        //Set last loaded rfid and set time of set

        Serial.print("\n");
        Serial.println("Card tag: ");
        for (int i = 1; i < RDM6300_PACKET_SIZE - 3; i++)
        {
            rfidTag[i - 1] = rfidId[i];
            Serial.print(rfidTag[i - 1]);
        } //Save rfidTag
        Serial.print("\n");

        if(server->authorizeRfid(rfidTag))
            blinkOpenSuccess();
        else
            blinkOpenDenied();

        RFID.flush();
    }
}

bool AcDuinoHardwareController::validateLoadedRfid()
{
    if (millis() - lastRfidTime > RDM6300_LATEST_TIMEOUT)
    {
        for (int i = 0; i < RDM6300_PACKET_SIZE; i++)
            latestRfidId[i] = 0;
    } //Reset last rfid

    if (rfidId[0] != RDM6300_PACKET_BEGIN || rfidId[RDM6300_PACKET_SIZE - 1] != RDM6300_PACKET_END)
    {
        Serial.println("Invalid rfid bytes");
        return false;
    } //validate first and last bit

    bool nullable_tag = true;
    for (int i = 1; i < RDM6300_PACKET_SIZE - 3; i++)
    {
        if (rfidId[i] != 0)
            nullable_tag = false;
    } 
    if (nullable_tag)
        return false;
    //Validate if tag is not null (better to check checksum, but whatever)

    bool rfidChanged = false;
    ;    for (int i = 0; i < RDM6300_PACKET_SIZE; i++)
    {
        if (latestRfidId[i] != rfidId[i])
            rfidChanged = true;
    } 
    if (!rfidChanged)
        return false;
    //Check if last rfid is same as new one

    return true;
}