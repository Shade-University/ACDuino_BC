#ifndef ACDUINO_HARDWARE_CONTROLLER_H
#define ACDUINO_HARDWARE_CONTROLLER_H

#import <Arduino.h>

class AcDuinoHardwareController{
  public:
    AcDuinoHardwareController() 
    {
        pinMode(LED_GREEN, OUTPUT);
        pinMode(LED_RED, OUTPUT);
        pinMode(LED_BLUE, OUTPUT);
        setLedUnregistered();
    }

    static const int LED_RED = 25;
    static const int LED_GREEN = 26;
    static const int LED_BLUE = 27;

    void setLedUnregistered();
    void setLedRegistered();
    void blinkOpenSuccess();
    void blinkOpenDenied();

  private:
    bool registered;
};
#endif