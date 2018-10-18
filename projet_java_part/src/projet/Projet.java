package Projet;
import java.io.*;
import static java.lang.Integer.max;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Session;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.mail.NoSuchProviderException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;

public class Projet extends Application {
   @FXML CheckBox checkEmail,checkSMS;
   @FXML TextField inp1,inp2,inp3,inp4,c1,c3,objet,msg,host,serverPort,tmp;
   @FXML PasswordField c2;
   @FXML Label labelValue,MEmail,MNumber,etat2,etat1,jj,etatClim,C,s1,s2,n,sec;  
   @FXML ComboBox comboBoxPorts;
   @FXML AnchorPane anch;
   @FXML ComboBox comboBoxSMS;
   @FXML ToggleButton toggle;
   @FXML ListView em;
    tim tim=new tim();
   static boolean sent=true,beeped=false,SMSsent=false,not=false,t=false;
   ObservableList <String> portList = FXCollections.observableArrayList();
   String MyEmail="",MyPassword="",MyNumber="",UserEmail="",UserNumber="",FMyEmail="",FMyPassword="",
           FObjet="",mssg="",FMsg="",Seuil1="27",Seuil2="30",FileName ="User.txt", SMSport,Shost="",Sport="",Tmp;
    ArrayList<String> emails =new ArrayList<>();
   int seuil1=0,seuil2=0,i=0,aa,bb,scnd;
   SerialPort arduinoPort = null; 
    public void setChamps() throws IOException   {
         F f=new F();
    /*  if (!inp4.getText().isEmpty()) { UserNumber=inp4.getText(); etat1.setText(" ");} 
      else {UserNumber="0656257769";etat1.setText("entrer votre numero SVP");}
      */
     /** if (!inp3.getText().isEmpty()){
         // UserEmail=inp3.getText();
          String ss[]=inp3.getText().split(" ");
          emails= new ArrayList<>(Arrays.asList(ss));
      }  
      else  //UserEmail="a.announ@esi-sba.dz"
          emails.add("a.announ@esi-sba.dz");
         // etat1.setText("entrer votre email SVP");
      **/
      if (!objet.getText().isEmpty()) FObjet=objet.getText();    else FObjet="temperature alerte";
      if (!msg.getText().isEmpty())   mssg=msg.getText();        else mssg="";
     // if (!inp1.getText().isEmpty()){ Seuil1=inp1.getText(); seuil1=Integer.parseInt(Seuil1);}
      //if (!inp2.getText().isEmpty()) { Seuil2=inp2.getText();seuil2=Integer.parseInt(Seuil2);}
      String[] s=f.read(this.FileName).split(" ");
      FMyEmail=s[0];
      c1.setText(FMyEmail);
      MEmail.setText("votre Email : " +FMyEmail);
      SMSsent=false;
      sent=false;
      FMyPassword=s[1];
      c2.setText(FMyPassword);
      
      Shost=s[2];
      host.setText(Shost);
        //MyEmail+" "+MyPassword+" "+Shost+" "+Seuil1+" "+Seuil2+" "+UserNumber+" "+" "+Sport+" "+UserEmail); 
      seuil1=Integer.parseInt(s[3]);
      s1.setText("Seuil 1 : " +seuil1); 
      inp1.setText(s[3]);
      
      seuil2=Integer.parseInt(s[4]);
      inp2.setText(s[4]);  
      s2.setText("Seuil 2 : " +seuil2);
      
     
      
      
      UserNumber=s[5];
      n.setText("Votre numéro : " +UserNumber);
       inp4.setText(UserNumber);
       
        Sport=s[6];
        
        Tmp=s[7];
        tmp.setText(Tmp);
       FObjet=s[8];
       objet.setText(FObjet);
       mssg=s[9];
       msg.setText(mssg);
        
      
      //*****************************************
        emails= new ArrayList<>();
     ObservableList <String> l = FXCollections.observableArrayList("votre email : "+FMyEmail,"serveur : " +Shost,"Port : " +Sport,
                                                                 "temps  entre les email : "+Tmp,  "Seuil 1 : " +seuil1,"Seuil 2 : " +seuil2,"Votre numéro : " +UserNumber,"titre : "+FObjet,"message : "+mssg);
        String ss="";
       for(int i=10;i<s.length;i++){
           ss+=s[i]+" ";
           emails.add(s[i]);
           l.add("Email "+ (i-9)+" : " +s[i]);
       }
        inp3.setText(ss);
       em.setOpacity(1);
       em.setMaxHeight(33*l.size());
       em.setItems(l);
         // emails= new ArrayList<>(Arrays.asList(ss));
      //***********************************************
    }
      @FXML
    public void save() throws IOException {
       F f=new F();
      if (!c1.getText().isEmpty())   MyEmail=c1.getText();
      if(MyEmail.contains("gmail")) host.setText("smtp.gmail.com");
         else if(MyEmail.contains("yahoo")) host.setText("smtp.mail.yahoo.com");
         else if(MyEmail.contains("life")||MyEmail.contains("hotmail")) host.setText("smtp.live.com");
      if (!c2.getText().isEmpty())   MyPassword=c2.getText();
       if (!tmp.getText().isEmpty())   Tmp=tmp.getText();
      if (!inp1.getText().isEmpty()) Seuil1=inp1.getText();
      if (!inp2.getText().isEmpty()) Seuil2=inp2.getText();
      if (!host.getText().isEmpty()) Shost=host.getText();
      
      if (!inp4.getText().isEmpty()) UserNumber=inp4.getText();
      if (!inp3.getText().isEmpty()) UserEmail=inp3.getText();  
      if (!serverPort.getText().isEmpty()) Sport=serverPort.getText(); 
         if (!objet.getText().isEmpty()) FObjet=objet.getText().replace(' ', '_');    
         if (!msg.getText().isEmpty())   mssg=msg.getText().replace(' ', '_');  
     // if (!c3.getText().isEmpty()) MyNumber=c3.getText();  
        String d="^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"+ "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
      if((!c1.getText().isEmpty())&&(!c2.getText().isEmpty())&&(!host.getText().isEmpty())&&(!inp1.getText().isEmpty()&&(!msg.getText().isEmpty())&&  (!objet.getText().isEmpty()) )
              &&(!inp2.getText().isEmpty())&&(!inp4.getText().isEmpty())&&(!inp3.getText().isEmpty())&&(!serverPort.getText().isEmpty())) 
      {
          if(!MyEmail.matches(d)) etat2.setText("vérifier votre email");
          else 
          {              
          f.FileWrite(this.FileName,
                  MyEmail+" "+MyPassword+" "+Shost+" "+Seuil1+" "+Seuil2+" "+UserNumber+" "+Sport+" "+Tmp+" "+FObjet+" "+mssg+" "+UserEmail); 
          etat2.setText(" Done !!");
          }
         setChamps();
      }
      else  etat2.setText("il faut remplir tout les champs");
               
    }  
    private void detectPort(){ 
        // try {beep("beep.wav");} catch (Exception ex) {}
      
        
        String[] serialPortNames = SerialPortList.getPortNames();
        if(portList.size()>0) portList.remove(0, portList.size());
        portList.addAll(Arrays.asList(serialPortNames));
        portList.add("po");
    }  
    public void beep(String song) throws Exception{
         
    AudioInputStream audioInputStream =
    AudioSystem.getAudioInputStream(this.getClass().getResource("beep.wav"));
    Clip clip = AudioSystem.getClip();
    clip.open(audioInputStream);
    clip.start();


      
    }
     @FXML
    public void btn() throws IOException /* quand on clicker sur le button start*/    { 
                if(toggle.isSelected()){
                   
                    
        detectPort(); // detecter le port
        
         comboBoxSMS.setItems(portList);
         comboBoxPorts.setItems(portList);//ajouter les port a combo box        
         setChamps();       //intialisé tout les variable  
         
            //        System.out.println(seuil1+" "+ Seuil2 +" "+Shost);
        comboBoxSMS.valueProperty().addListener(new ChangeListener<String>() // fi l'utilusateur choiser un port 
       {
            @Override
            public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
                SMSport=newValue;
                System.out.println(newValue);
                SMSport=newValue;
            }
        });
       comboBoxPorts.valueProperty().addListener(new ChangeListener<String>() // fi l'utilusateur choiser un port 
       {
            @Override
            public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {

                disconnectArduino();
                
               connectArduino(newValue);
            }

        });
        }
    }
   @SuppressWarnings("empty-statement")
    public boolean connectArduino(String port){
        boolean success = false;
        SerialPort serialPort = new SerialPort(port);
        try {
          serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new SerialPortEventListener() {
              @Override
              public void serialEvent(SerialPortEvent serialPortEvent) {
                  
                  if(toggle.isSelected()){
                      anch.setDisable(false);
                      if (serialPortEvent.isRXCHAR()){
                         
                          Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  byte[] b = null ;
                                  try {
                                      b = serialPort.readBytes();
                                  } catch (SerialPortException ex) {}
                                  try{
                                      if(i%2==0) aa= b[0] & 255;
                                      else       bb=   b[0] & 255;
                                      i++;
                                  }
                                  catch(Exception e){};
                                  // bb= b[0] & 255;
                                  if((aa==bb)&&(aa<20)) aa=aa*2;
                                  int value =max(aa,bb);
                             //     System.out.println("aa="+aa+"  bb="+bb);
                                  String st = String.valueOf(value);
                                  try {
                                      //Update label in ui thread
                                      serialPort.writeInt(seuil1);
                                  } catch (SerialPortException ex) { }
                                  
                                  etatClim.setFont(Font.font(40));
                                  
                                  labelValue.setText("température = "+st);
                                  C.setOpacity(1);
                                  
                                  if(value<seuil1)
                                  { 
                                      sec.setText(" ");
                                      not=false;
                                  beeped=false;
                                  etatClim.setTextFill(Paint.valueOf("#04b8ff"));
                                  etatClim.setText("inactive");
                                  }
                                  else
                                  {
                                      
                                      if (value>seuil2) //si la temperature depassé le seuil 2 ,, ca vaut dire que la clim activé mais la température rest augmenter alors il y a un probleme
                                      {
                                          
                                          Calendar cal = Calendar.getInstance();
                                          SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                          FMsg= mssg+ " \n la temperature est : "+ (value)+" degré "+"\n en "+sdf.format(cal.getTime());
                                      //    System.out.println(FMsg);
                                          try {
                                              if(!beeped){
                                                  beep("beep.wav");
                                                  
                                              }
                                          } catch (Exception ex) {}
                                          if(!not){
                                              // JOptionPane.showMessageDialog(null,"temperature a passé le deuxieme seuil");
                                              not=true;}
                                          
                                          
                                          etatClim.setTextFill(Paint.valueOf("#ff0505"));
                                          etatClim.setText("ACTIVE");
                                          beeped=true;
                                          
                                          if(checkEmail.isSelected())
                                          {  
                                              if(!t){
                                               tim.start();
                                               t=true;
                                          }
                                               sec.setText("temp avant le prochein envoi d'email: "+String.valueOf(scnd)+" (s)");
                                              if(!sent) // pour envoi l'email une seul fois
                                              {
                                                  
                                                  emails.stream().map((e) -> {
                                                      UserEmail=e;
                                                      return e;
                                                  }).map((_item) -> Calendar.getInstance()).map((now) -> {
                                                      return now;
                                                  }).map((_item) -> new SendMail()).map((sm) -> {
                                                      sm.start();
                                                      return sm;
                                                  }).forEach((_item) -> {
                                                      
                                                  });
                                                  sent=true;
                                              }
                                          }
                                          if(checkSMS.isSelected()&&(!SMSsent)){
                                              
                                              SMSSender sms=new SMSSender(SMSport,UserNumber);
                                              sms.sendNotification();
                                              SMSsent=true;
                                              
                                          }
                                          //  SMSSender sms =new SMSSender(port, port)
                                          
                                      }
                                      else // si la temperature compris entre seuil1 et seuil 2
                                      {
                                          sec.setText(" ");
                                          not=false;
                                          beeped=false;
                                          etatClim.setTextFill(Paint.valueOf("#04b8ff"));
                                          etatClim.setText("ACTIVE");
                                      }
                                      
                                  }
                              }
                          });
                      }}
                  else anch.setDisable(true);
              }
          });
            arduinoPort = serialPort;
            success = true;
        } catch (SerialPortException ex) {
        }
        return success;
    }
    public void disconnectArduino() {
        
        System.out.println("disconnectArduino()");
        if(arduinoPort != null){
            try {
                arduinoPort.removeEventListener();
                
                if(arduinoPort.isOpened()){
                    arduinoPort.closePort();
                }
                
            } catch (SerialPortException ex) {
            }
        }
    }      
    @Override
    public void start(Stage stage) throws Exception {   
       Parent root = root= FXMLLoader.load(getClass().getResource("Interface.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/ico/img.png"));
        stage.setTitle(" Régulateur de température");
        stage.show();    
    }       
    public static void main(String[] args) {
        launch(args);
    } 
    public class F    {
        
        public  String read(String fileName) throws FileNotFoundException, IOException 
        {
           BufferedReader br =new BufferedReader(new FileReader(fileName));
            return br.readLine();   
        }
        
       public  void FileWrite(String fileName,String Username) throws FileNotFoundException, IOException
       {
           PrintWriter pw;
           pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName,false)));
           pw.println(Username);
           pw.flush();
           pw.close();
        }
    } 
    public void mail(String user,String pass,String sub,String to ,String msg,String host,String sport){
        Properties props = new Properties();  
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", sport);	
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getDefaultInstance(props,new Authenticator() 
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() 
            { 
                JOptionPane.showMessageDialog(null,"email en cour d'envoi...."); 
                return new PasswordAuthentication(user, pass);    
            }
           
        });
        try 
        {
            Message message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(sub);
            message.setText(msg);
       
            Transport.send(message);
            
            JOptionPane.showMessageDialog(null,"L'email été bien envoyer!!");  
           // Projet.sent=false;!
          
            
        } catch (MessagingException e) 
        {
            JOptionPane.showMessageDialog(null,"Error -_-");
           //  Projet.sent=false;
            throw new RuntimeException(e);
        }
       }
    public void yahoo(String user,String pas,String sub,String Too ,String msg){
         // Sender's email ID needs to be mentioned
     String from = user;
     String pass =pas;
    // Recipient's email ID needs to be mentioned.
   String to = Too;
   String hostt = "smtp.mail.yahoo.com";

   // Get system properties
   Properties properties = System.getProperties();
   // Setup mail server
   properties.put("mail.smtp.starttls.enable", "true");
   properties.put("mail.smtp.host", hostt);
   properties.put("mail.smtp.user", from);
   properties.put("mail.smtp.password", pass);
   properties.put("mail.smtp.port", "587");
   properties.put("mail.smtp.auth", "true");

   // Get the default Session object.
   Session session = Session.getDefaultInstance(properties);

   try{
           JOptionPane.showMessageDialog(null,"connecting...."); 
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO,
                               new InternetAddress(to));

      // Set Subject: header field
      message.setSubject(sub);

      // Now set the actual message
      message.setText(msg);
       System.out.println("1");
      // Send message
      Transport transport = session.getTransport("smtp");
      transport.connect(hostt, from, pass);
       System.out.println("2");
      transport.sendMessage(message, message.getAllRecipients());
       System.out.println("3");
      transport.close();
      System.out.println("Sent message successfully....");
       JOptionPane.showMessageDialog(null,"Email sended!");
   }catch (MessagingException mex) 
   {
        JOptionPane.showMessageDialog(null,"Something happened!");
   }
    }
    public void yahooo(String user,String pass,String sub,String to ,String msg){
       Properties props = new Properties();
            try {
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.host", 587);
                props.put("mail.smtp.auth", "true");
                // props.put("mail.smtps.quitwait", "false");
                
                Session mailSession = Session.getDefaultInstance(props);
                mailSession.setDebug(true);
                Transport transport = mailSession.getTransport();
                
                MimeMessage message = new MimeMessage(mailSession);
                message.setSubject("Testing SMTP-SSL");
                message.setContent("This is a test", "text/plain");
                
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress("rlss@abc.com"));
                
                transport.connect
                  ("smtp.mail.yahoo.com", 587, FMyEmail, FMyPassword);
                
                transport.sendMessage(message,
                        message.getRecipients(Message.RecipientType.TO));
                transport.close();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Projet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(Projet.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    public class SendMail extends Thread  {
        
          String user=FMyEmail,pass=FMyPassword,sub=FObjet,to=UserEmail,msg=FMsg;
         
          @Override
       public void run(){ 
          
      // if(FMyEmail.contains("mail")) 
           mail(user, pass, sub, to, msg,Shost,Sport);
      // else if(FMyEmail.contains("yahoo")) yahoo(user, pass, sub, to, msg);
       }
    } 
    public class SMS_Sender extends Thread        {
     @Override
     public void run(){
      SerialPort port = new SerialPort(SMSport);
    char enter = 13;
    char control_z=26;
    char comilla = 34;
    try {
        
        System.out.println("Port open: " + port.openPort());
        System.out.println("Params setted: " + port.setParams(9600, 8, 1, 0));     

        System.out.println("Set to SMS: " + port.writeBytes(("AT+CMGF=1"+enter).getBytes()));
        System.out.println("Set the destinatar: " + port.writeBytes(("AT+CMGS"+comilla+"+phone_number"+comilla+enter).getBytes()));
        System.out.println("Set the text: " + port.writeBytes(("text"+control_z).getBytes()));

        System.out.println("Port closed: " + port.closePort());
    }
    catch (SerialPortException ex){
        System.out.println(ex);
    }
}}
    public class SMSSender extends SerialPort implements SerialPortEventListener{
    
    private String numeroCorrespondant;
    private static final String
            COMMAND_REMISE_A_ZERO = "ATZ",
            COMMAND_SMS_MODE_TEXT = "AT+CMGF=1",
            COMMAND_ENVOIE_SMS = "AT+CMGS=";
    
    public SMSSender(String portList,String correspondant){
        super(portList);
        this.numeroCorrespondant = correspondant;
        try{
            this.openPort();
            this.setParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            this.addEventListener(this);
            System.out.println("J'envoie: " + SMSSender.COMMAND_REMISE_A_ZERO +"\r\n");
            this.writeString(SMSSender.COMMAND_REMISE_A_ZERO + "\r\n");
            this.writeString(SMSSender.COMMAND_SMS_MODE_TEXT + "\r\n");
        }
        catch(SerialPortException exp){
        }
    }
    
    public void sendNotification(){
        try{
            JOptionPane.showMessageDialog(null,"SMS en cour d'envoi ...");
            if(this.isOpened()){ 
               
                this.writeString(SMSSender.COMMAND_ENVOIE_SMS + "\"" + this.numeroCorrespondant + "\"\r\n");
                this.writeString(FMsg + '\032');
                JOptionPane.showMessageDialog(null,"SMS été bien envoyer!!");
            }
        }
        catch(SerialPortException exp){
            Alert t = new Alert(AlertType.ERROR);
            t.setTitle("SMS error");
            t.setHeaderText("SMS connexion opening error");
            t.setContentText("Impossible to open a communication to the specified server");
            t.showAndWait();
        }
    }
    
    public void fermerConnexion(){
        try{
            this.closePort();
        }
        catch(SerialPortException exp){
            
        }
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        try{
            System.out.println("reponse = " + readString());
        }
        catch(SerialPortException exp){
        }
    }
}
    public class tim extends Thread{
    @Override
    public void run(){
        while(true){
            sent=false;
            
            for(int j=Integer.parseInt(Tmp)-1;j>=0;j--){
            
            for(int i=60;i>0;i--){
                
           
       try{
           scnd=j*60+i;
            
         
            Thread.sleep(1000);
            
                 
         //  Thread.sleep((int)(Integer.parseInt(Tmp)*30000));
         //  Thread.sleep((int)(Integer.parseInt(Tmp)*30000));
          }
       catch (InterruptedException ex) {}  }    
            }
            
        }
    }
    }
    @FXML
private void initialize() throws IOException { 

}
   
}
 