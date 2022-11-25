package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client{
	public static void main(String[] args) throws Exception {
		Socket client = new Socket( "localhost", 5487 );
		String message = "";
		while( !message.equalsIgnoreCase("Exit") ){
			System.out.print("BDD >> ");
			Scanner scan = new Scanner(System.in);
			message = scan.nextLine();
			OutputStream out = client.getOutputStream();
			out.write( message.getBytes() );
			DataInputStream input = new DataInputStream( client.getInputStream() );
			String valiny = input.readUTF();
			System.out.println(valiny);
			
		}
	}
}