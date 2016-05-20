package com.example.androidclientservertest;

import java.io.BufferedOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;

public class MainActivity extends Activity {

TextView textIn;
TextView textIn1;
Socket client;
FileInputStream fileInputStream;
BufferedInputStream bufferedInputStream;
BufferedInputStream bufferedInputStream1;
OutputStream outputStream;
private static int filesize = 1000000; // filesize temporary hardcoded 
private static InputStream inputStream;
private static FileOutputStream fileOutputStream;
private static BufferedOutputStream bufferedOutputStream;
private static int bytesRead;
private static int current = 0;

 /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState) 
 {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.activity_main);
	     if (android.os.Build.VERSION.SDK_INT > 9)
	     {
	    	 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    	 StrictMode.setThreadPolicy(policy);
	     }

 
	     Button buttonSend = (Button)findViewById(R.id.send);
	     textIn = (TextView)findViewById(R.id.textin);
	     buttonSend.setOnClickListener(buttonSendOnClickListener);
	     
	     Button button1 = (Button)findViewById(R.id.button1);
	     textIn1 = (TextView)findViewById(R.id.textin1);
	     button1.setOnClickListener(buttonSendOnClickListener1);
	     
	     Button button2 = (Button)findViewById(R.id.button2);
	     button2.setOnClickListener(buttonSendOnClickListener2);
 }

 Button.OnClickListener buttonSendOnClickListener = new Button.OnClickListener()
 {

	@Override
	public void onClick(View arg0)
	{
		 // TODO Auto-generated method stub
		 Socket socket;
		 DataOutputStream dataOutputStream = null;
		 DataInputStream dataInputStream;
		 int s=1;
 
		 //indicate the path to send file
		 File file = new File("/storage/sdcard/Sampleandroidsend.txt");
		 
		 System.out.println("file length: " + file.length());
		 try 
		 {
		  socket = new Socket("10.21.42.254", 4444); 
		  //write to socket to indicate send button was clicked
		  dataOutputStream = new DataOutputStream(socket.getOutputStream());
		  dataOutputStream.writeInt(s);
		  
	   	  //create a byte array to file
		  byte[] mybytearray = new byte[(int) file.length()]; 

		  fileInputStream = new FileInputStream(file);
		  bufferedInputStream = new BufferedInputStream(fileInputStream);  
		
		  bufferedInputStream.read(mybytearray, 0, mybytearray.length); //read the file
		  System.out.println("Read into buffer:");
		  outputStream = socket.getOutputStream();
		
		  outputStream.write(mybytearray, 0, mybytearray.length); //write file to the output stream byte by byte
		  System.out.println("Written to buffer:");
		  outputStream.flush();
		  bufferedInputStream.close();
		  outputStream.close();
		  socket.close();
		  Date d = new Date();
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		  textIn.setText("File Sent at: " + dateFormat.format(d));
		 } 
		 catch (UnknownHostException e) 
		 {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 } 
		 catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
	}
 };//end of sending file
 
 
 Button.OnClickListener buttonSendOnClickListener1 = new Button.OnClickListener()
 {

	@Override
	public void onClick(View arg0) 
	{
	 Socket socket;
	 DataOutputStream dataOutputStream = null;
	 DataInputStream dataInputStream;
	 int s=2;
 
	 try 
	 {
		 socket = new Socket("10.21.42.254", 4444);
		 //write to socket to indicate receive button was clicked
		 dataOutputStream = new DataOutputStream(socket.getOutputStream());
		 dataOutputStream.writeInt(s);
		 
		 //create byte array to buffer the file
		 byte[] mybytearray = new byte[filesize];    
		 //path to store the file received
	     inputStream = socket.getInputStream();
	     fileOutputStream = new FileOutputStream("/storage/sdcard/sampleandriodrec.txt");
	     bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
	
	     System.out.println("Receiving file:");
	
	     //following lines read the input slide file byte by byte
	     bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
	     current = bytesRead;
     
	     do 
	     {
	         bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
	         System.out.println("bytes read: " + bytesRead);
	         if (bytesRead >= 0) 
	         {
	        	 current += bytesRead;
	         }
	     } while (bytesRead > -1);


	     bufferedOutputStream.write(mybytearray, 0, current);
	     bufferedOutputStream.flush();
	     bufferedOutputStream.close();
	     inputStream.close();
	     socket.close();
	     Date d = new Date();
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     textIn1.setText("File recieved at :" + dateFormat.format(d));
	 }
     
     catch (UnknownHostException e) 
     {
    	  // TODO Auto-generated catch block
    	  e.printStackTrace();
     } 
	 catch (IOException e) 
	 {
    	  // TODO Auto-generated catch block
    	  e.printStackTrace();
     }
	}
};//end of receiving file

Button.OnClickListener buttonSendOnClickListener2 = new Button.OnClickListener()
{

	@Override
	public void onClick(View arg0) 
	{
		 Socket socket;
		 DataOutputStream dataOutputStream = null;
		 DataInputStream dataInputStream;
		 int s=3;
	 
		 try 
		 {
			 socket = new Socket("10.21.42.254", 4444);
			 //write to socket to indicate terminate button was clicked
			 dataOutputStream = new DataOutputStream(socket.getOutputStream());
			 dataOutputStream.writeInt(s);
		 }
		 catch (UnknownHostException e) 
		 {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 } 
		 catch (IOException e) 
		 {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
      
	}
};//end of terminate connection

}