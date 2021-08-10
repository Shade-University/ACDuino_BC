#ifndef ACDUINO_HARDWARE_CONTROLLER_H
#define ACDUINO_HARDWARE_CONTROLLER_H

#define RFID Serial2
#define RDM6300_PACKET_SIZE		14
#define RDM6300_PACKET_BEGIN	0x02
#define RDM6300_PACKET_END		0x03
#define RDM6300_LATEST_TIMEOUT		10000

#include "libraries/ACDuinoWifiServer.h"
#import <Arduino.h>

class AcDuinoHardwareController{
  public:
    AcDuinoHardwareController(AcDuinoWifiServer* server) 
    {
        this->server = server;
        RFID.begin(9600);
        pinMode(LED_GREEN, OUTPUT);
        pinMode(LED_RED, OUTPUT);
        pinMode(LED_BLUE, OUTPUT);
        setLedUnregistered();
    }

    static const int LED_RED = 26;
    static const int LED_GREEN = 27;
    static const int LED_BLUE = 25;
    static const int RFID_RX = 32;

    void setLedUnregistered();
    void setLedRegistered();
    void blinkOpenSuccess();
    void blinkOpenDenied();
    void handleRfid();

  private:
    AcDuinoWifiServer* server;
    bool registered;
    char rfidId[RDM6300_PACKET_SIZE];
    char rfidTag[RDM6300_PACKET_SIZE - 4];
    char latestRfidId[RDM6300_PACKET_SIZE];
    unsigned long lastRfidTime;   

    bool validateLoadedRfid(); 
};
#endif