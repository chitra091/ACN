	import java.io.*;
import java.net.*;
	
	import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
	 
	public class Javafilereceive 
	{
	 
	    // private static ServerSocket servsock;
	    private static Socket clientSocket;
	    private static FileInputStream fis;
	    private static InputStream inputStream;
	    private static BufferedInputStream bis;
	    private static FileOutputStream fileOutputStream;
	    private static BufferedOutputStream bufferedOutputStream;
	    private static int filesize = 100000000; // filesize temporary hardcoded 
	    private static int bytesRead;
	    private static int current = 0;
	 
	    public static void main(String[] args) throws IOException 
	    {
	    	int bytesRead;
	    	int current = 0;
	    	FileOutputStream fos = null;
	    	BufferedOutputStream bos = null;
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        OutputStream os = null;
	        ServerSocket servsock = null;
	        Socket sock = null;
	        DataInputStream dataInputStream = null;
	        int i=1;
	        
	    	try
	    	{
	    		  servsock = new ServerSocket(4444);
	    		  System.out.println("Server : Waiting...");
	    		      
    		      while (i==1) {
		          clientSocket = servsock.accept();
		          dataInputStream = new DataInputStream(clientSocket.getInputStream());
		          int DataStream = dataInputStream.readInt();
		          switch (DataStream)
		          {
		          	case 1 : 
		          	{
		          		try
		          		{
						      				 	
					        System.out.println("Server started. Listening to port 4444");
					        //create byte array to buffer the file
 					        byte[] mybytearray = new byte[filesize];    
	 
					        inputStream = clientSocket.getInputStream();
					        fileOutputStream = new FileOutputStream("C:/Users/Chitra/OneDrive/samplejavarec.txt");
					      
					        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
	 
					        System.out.println("Receiving file from android");
					       
					 
					        //following lines read the input slide file byte by byte
					        bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
					        current = bytesRead;
	        
					        do {
						            bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
						            if (bytesRead >= 0) 
						            {
						            	current += bytesRead;
						            }
					           } while (bytesRead > -1);
	 
					        bufferedOutputStream.write(mybytearray, 0, current);
					        bufferedOutputStream.flush();
					    }
				        finally 
				        {
					        bufferedOutputStream.close();
					        inputStream.close();
					        Date d = new Date();
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					        System.out.println("Sever recieved the file from android at:" + dateFormat.format(d));
					    } 
		          		break;
		          	}
	    		    case 2:
	    		    {
	    		    	try 
	    		    	{
		
	    		    		System.out.println("Accepted connection to send the file");
	    		    		// send file
	    		    		File myFile = new File ("C:/Users/Chitra/OneDrive/samplejavasend.txt");
	    		    		byte [] mybytearray  = new byte [(int)myFile.length()];
         
	    		    		fis = new FileInputStream(myFile);
	    		    		bis = new BufferedInputStream(fis);
	    		    		bis.read(mybytearray,0,mybytearray.length);
	    		    		os = clientSocket.getOutputStream();
	    		    		System.out.println("Send file from server to android");
	    		    		os.write(mybytearray,0,mybytearray.length);
	    		    		os.flush();
	    		    		Date d = new Date();
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    		    		System.out.println("Server sent file to android at:" + dateFormat.format(d));
	    		    	}
	    		    	finally 
	    		    	{
	    		    		if (bis != null) bis.close();
	    		    		if (os != null) os.close();
	    		    	} 
	    		    	break;
	    		    }

	    		    case 3:   
	    		    {
	    		    	if (servsock != null) 
	    		    	{
	    		    		System.out.println("Terminate connection:");
	    		    		servsock.close();
	    		    		i=2;
	    		    		Date d = new Date();
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    		    		System.out.println("Connection terminated successfully at:" + dateFormat.format(d));
	    		    		break;
	    		    	}
	    		    }
       
		          }
	    	}
    	}	
	    finally 
	    {
	        //if (servsock != null) servsock.close();
	    }
	  }
	}