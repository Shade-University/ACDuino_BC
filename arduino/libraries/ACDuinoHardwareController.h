#ifndef ACDUINO_HARDWARE_CONTROLLER_H
#define ACDUINO_HARDWARE_CONTROLLER_H

#import <Arduino.h>

class AcDuinoHardwareController{
  public:
    AcDuinoHardwareController() 
    {
        registered = false;
        pinMode(LED_GREEN, OUTPUT);
        pinMode(LED_RED, OUTPUT);
    }

    static const int LED_RED = 25;
    static const int LED_GREEN = 26;

    void lightLed();
    void setLedUnregistered();
    void setLedRegistered();

  private:
    bool registered;
};
#endif