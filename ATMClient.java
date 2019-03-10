package io.github.northmachine;
import java.net.*;
import java.util.Scanner;
import org.omg.Messaging.SyncScopeHelper;
import java.io.*;
public class ATMClient {
	static Socket socket;
	static BufferedReader in;
	static PrintWriter out;
	static String msg;
	static String msg2;
	static String c;
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter IP and port:");
		System.out.print("	IP:");
		String ip=sc.nextLine();
		socket=new Socket(ip,4321);
		in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		Thread t1=new Thread(new Runnable(){
			public void run() {
				while(true) {
					try {
						msg2=in.readLine();
						if(msg2!=null)
							System.out.println("reply:"+msg2);
						else
							break;
					} catch (IOException e) {

					}
				}
			}
		});
		Thread t2=new Thread(new Runnable() {
			public void run() {
				try {
					while(true) {
						msg=sc.nextLine();
						out.println(msg);
					}
				}catch(Exception e) {

				}
			}
		});
		t2.setDaemon(true);
		t1.start();
		t2.start();
	}
}