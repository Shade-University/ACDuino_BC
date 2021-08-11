#ifndef ACDUINO_HARDWARE_CONTROLLER_H
#define ACDUINO_HARDWARE_CONTROLLER_H

#define RFID Serial2
#define RDM6300_PACKET_SIZE		14
#define RDM6300_PACKET_BEGIN	0x02
#define RDM6300_PACKET_END		0x03
#define RDM6300_LATEST_TIMEOUT		10000

#include "ACDuinoWifiClient.h"
#import <Arduino.h>

class AcDuinoHardwareController{
  public:
    AcDuinoHardwareController() 
    {
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
    void setWifiClient(String host, int port);
    void blinkOpenSuccess();
    void blinkOpenDenied();
    void handleRfid();

  private:
    AcDuinoWifiClient* client;
    bool registered;
    int rfidTag;
    int latestRfidTag;
    unsigned long lastRfidTime;   

};
#endif