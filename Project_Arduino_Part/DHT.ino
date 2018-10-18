#include <LiquidCrystal_I2C.h>


#include <dht.h>
#include "DHT.h"
#define dht_apin A0   // Analog Pin sensor is connected to A0
//*****************************************
 int a,b,aa,bb,c=0;
dht DHT;
LiquidCrystal_I2C lcd(0x3f,16,2);
 int seuil;
 //*********************************************
void setup(){
  lcd.init();
lcd.backlight();
 lcd.setCursor(0,0);

   pinMode(8,OUTPUT);
  Serial.begin(9600);
}
//****************************************************************
void loop(){
  
     DHT.read11(dht_apin);
      a=(int)DHT.temperature;
      delay(10);
      b=(int)DHT.temperature;
      
      aa=max(a,b);
      bb=min(a,b);
      
      if((aa-bb<2)&&(c-aa<2)) c=aa;
      
      
     
     Serial.write(c); 
     lcd.setCursor(0,0);
     //*****************************************
     if(c>10){
  
    lcd.print("temperture =  ");
    lcd.print(c); 
  }
  else
  {
    lcd.print("temperture =  ");
    lcd.print(c);
    lcd.print("  ");
  }
     
     
  //***********************************   
     
    // Serial.println(c);
     //write as byte, to USB
       lcd.setCursor(0,1);
     seuil=Serial.read();
     if(c>seuil) {
       digitalWrite(8,HIGH) ;lcd.print("ON                ");
}else 
  if(c<=seuil) { digitalWrite(8,LOW); lcd.print("OFF                 ");

    
}

}

