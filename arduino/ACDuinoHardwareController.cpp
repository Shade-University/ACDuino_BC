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

void AcDuinoHardwareController::setWifiClient(String host, int port)
{
    this->client = new AcDuinoWifiClient(host, port);
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

    if (registered && client != NULL && RFID.available() > 0)
    {
        char buff[RDM6300_PACKET_SIZE];
        uint8_t checksum;

        Serial.println("Card detected");
        Serial.println("Loaded: ");

        for (int i = 0; i < RDM6300_PACKET_SIZE; i++)
        {
            buff[i] = RFID.read();
            Serial.print(buff[i]);
        } //Load RFID
        Serial.println("");

        if (buff[0] != RDM6300_PACKET_BEGIN || buff[RDM6300_PACKET_SIZE - 1] != RDM6300_PACKET_END)
        {
            Serial.println("Invalid rfid bytes");
            return;
        }                                  //validate first and last bit
        buff[RDM6300_PACKET_SIZE - 1] = 0; //Remove last byte
        buff[0] = 0;                       //Remove first byte

        checksum = strtol(buff + 11, NULL, 16); //From position 11 to 0 [position 13] - checksum
        buff[11] = 0;                           //Set 0 to perform strtol
        rfidTag = strtol(buff + 3, NULL, 16);    //From position 3 to 0 [position 11]
        buff[3] = 0;                            //Set 0 to perform strtol
        checksum ^= strtol(buff + 1, NULL, 16); //append version to checksum. From position 1 to 0 [position 3]

        /* xore the rfidTag and validate checksum */
        for (uint8_t i = 0; i < 32; i += 8)
            checksum ^= ((rfidTag >> i) & 0xFF);
        if (checksum)
        {
            Serial.println("Checksum validation failed.");
            return;
        }

        if(rfidTag == 0)
        {
            Serial.println("Invalid 0 tag");
            return;
        }

        if(millis() - lastRfidTime > RDM6300_LATEST_TIMEOUT) latestRfidTag = 0;

        Serial.print("Card tag: ");
        Serial.println(rfidTag, HEX);

        if (rfidTag == latestRfidTag)
        {
            Serial.println("Duplicite rfid reading.");
            return;
        }

        lastRfidTime = millis();
        latestRfidTag = rfidTag;

        if (client->authorizeRfid(rfidTag))
            blinkOpenSuccess();
        else
            blinkOpenDenied();

        while (RFID.available() > 0) RFID.read(); //clear
    }
}