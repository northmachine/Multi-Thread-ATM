package io.github.northmachine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.net.*;
import java.io.*;
public class ATMServer {
	public static void main(String[] args) throws Exception {
		Server s=new Server();
		s.Increasement();
		s.begin();
	}
}
class Account{
	Account(int id,String u,String p,double balance,
			double mode1balance,double mode2balance,double mode3balance,
			double LeftTimeOfSC1,double LeftTimeOfSC2,double LeftTimeOfSC3,double loan,double lefttimeofloan
			,boolean f){
		this.id=id;
		username=u;
		password=p;
		this.balance=balance;
		this.LeftTimeOfSC1=LeftTimeOfSC1;
		this.LeftTimeOfSC2=LeftTimeOfSC2;
		this.LeftTimeOfSC3=LeftTimeOfSC3;
		this.mode1balance=mode1balance;
		this.mode2balance=mode2balance;
		this.mode3balance=mode3balance;
		this.loan=loan;
		this.lefttimeofloan=lefttimeofloan;
		this.freezen=f;
	}
	private int id;
	private String username;
	private double loan;
	private double lefttimeofloan;
	private String password;
	private double balance;
	private double LeftTimeOfSC1;
	private double LeftTimeOfSC2;
	private double LeftTimeOfSC3;
	private double mode1balance=0;
	private double mode2balance=0;
	private double mode3balance=0;
	public boolean freezen;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getLeftTimeOfSC1() {
		return LeftTimeOfSC1;
	}
	public void setLeftTimeOfSC1(double leftTimeOfSC1) {
		if(leftTimeOfSC1>=0)
			LeftTimeOfSC1 = leftTimeOfSC1;
		else
			LeftTimeOfSC1=0;
	}
	public void deposit(double m) {
		setBalance(getBalance()+m);
	}
	public void withdrawl(double m) {
		setBalance(getBalance()-m);
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		if(balance>=0)
			this.balance = balance;
	}
	public double getMode1balance() {
		return mode1balance;
	}
	public void setMode1balance(double mode1balance) {
		this.mode1balance = mode1balance;
	}
	public double getMode2balance() {
		return mode2balance;
	}
	public void setMode2balance(double mode2balance) {
		this.mode2balance = mode2balance;
	}
	public double getMode3balance() {
		return mode3balance;
	}
	public void setMode3balance(double mode3balance) {
		this.mode3balance = mode3balance;
	}
	public double getLeftTimeOfSC2() {
		return LeftTimeOfSC2;
	}
	public void setLeftTimeOfSC2(double leftTimeOfSC2) {
		if(leftTimeOfSC2>=0)
			LeftTimeOfSC2 = leftTimeOfSC2;
		else
			LeftTimeOfSC2=0;	}
	public double getLeftTimeOfSC3() {
		return LeftTimeOfSC3;
	}
	public void setLeftTimeOfSC3(double leftTimeOfSC3) {
		if(leftTimeOfSC3>=0)
			LeftTimeOfSC3 = leftTimeOfSC3;
		else
			LeftTimeOfSC3=0;
	}
	public double getLoan() {
		return loan;
	}
	public void setLoan(double loan) {
		if(loan>0)
			this.loan = loan;
		else this.loan=0;
	}
	public double getLefttimeofloan() {
		return lefttimeofloan;
	}
	public void setLefttimeofloan(double lefttimeofloan) {
		if(lefttimeofloan>=0)
			this.lefttimeofloan = lefttimeofloan;
		else
			this.lefttimeofloan=0.0;
	}
}
class Lib{
	Lib(){
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://127.0.0.1:3306/my_db?useSSL=true&rewriteBatchedStatements=true";
		String user="root";
		String password="root";
		try {
			amap=new HashMap<Integer,Account>();
			Class.forName(driver);
			conn=DriverManager.getConnection(url,user,password);
			conn.setAutoCommit(false);
			statement=conn.createStatement();
			try {
				statement.executeUpdate("CREATE TABLE atmdb3 (id int Not NULL,"
						+ "username varchar(255),password varchar(255),"
						+ "balance double,mode1balance double,mode2balance double, mode3balance double,"
						+ "LeftTimeOfSC1 double,LeftTimeOfSC2 double,"
						+ "LeftTimeOfSC3 double,loan double,lefttimeofloan double,freezen bool,"
						+ "PRIMARY KEY(id));");
			}catch(Exception e) {

			}
			String sql="SELECT * FROM atmdb3;";
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Account tmp=new Account(rs.getInt(1),rs.getString(2),rs.getString(3),
						rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getDouble(7),
						rs.getDouble(8),rs.getDouble(9),rs.getDouble(10),rs.getDouble(11),
						rs.getDouble(12),rs.getBoolean(13));
				amap.put(rs.getInt(1),tmp);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		current=amap.size();
	}
	Connection conn;
	Statement statement;
	private int current;
	private Map<Integer,Account> amap;
	public void InsertIntoDB(Account a) throws Exception {
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO atmdb3 VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setInt(1,a.getId());
		pstmt.setString(2, a.getUsername());
		pstmt.setString(3, a.getPassword());
		pstmt.setDouble(4, a.getBalance());
		pstmt.setDouble(5, a.getMode1balance());
		pstmt.setDouble(6, a.getMode2balance());
		pstmt.setDouble(7, a.getMode3balance());
		pstmt.setDouble(8, a.getLeftTimeOfSC1());
		pstmt.setDouble(9, a.getLeftTimeOfSC2());
		pstmt.setDouble(10, a.getLeftTimeOfSC3());
		pstmt.setDouble(11, a.getLoan());
		pstmt.setDouble(12, a.getLefttimeofloan());
		pstmt.setBoolean(13, a.freezen);

		pstmt.executeUpdate();
	}
	public synchronized int getMid() {
		return amap.size();
	}
	public void addAccount(Account a) throws Exception {
		amap.put(a.getId(), a);
		InsertIntoDB(a);
	}
	public boolean checkAccount(int id,String passwd) {
		return amap.get(id).getPassword().equals(passwd);
	}
	public Account getAccount(int id) {
		return amap.get(id);
	}
	public void save() throws Exception {
		PreparedStatement pstmt=conn.prepareStatement("UPDATE atmdb3 SET username=? ,"
				+ "password=? ,balance=?, mode1balance=?,mode2balance=?,mode3balance=?,"
				+ " LeftTimeOfSC1=? ,LeftTimeOfSC2=? ,LeftTimeOfSC3=? ,loan=?, "
				+ "lefttimeofloan=? ,freezen=? WHERE id=?");
		for(int i=amap.size();i>0;i--) {
			Account a=amap.get(i-1);
			pstmt.setString(1, a.getUsername());
			pstmt.setString(2, a.getPassword());
			pstmt.setDouble(3, a.getBalance());
			pstmt.setDouble(4, a.getMode1balance());
			pstmt.setDouble(5, a.getMode2balance());
			pstmt.setDouble(6, a.getMode3balance());
			pstmt.setDouble(7, a.getLeftTimeOfSC1());
			pstmt.setDouble(8, a.getLeftTimeOfSC2());
			pstmt.setDouble(9, a.getLeftTimeOfSC3());
			pstmt.setDouble(10, a.getLoan());
			pstmt.setDouble(11, a.getLefttimeofloan());
			pstmt.setBoolean(12, a.freezen);
			pstmt.setInt(13,a.getId());
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		conn.commit();
	}
	public Iterator getIter() {
		return amap.entrySet().iterator();
	}
}
class Server{
	private final int RECEIVE_PORT=4321;
	private final int RECEIVE_PORT2=4322;
	private Lib lib;
	ServerSocket ss;
	Server() throws Exception{
		lib=new Lib();
		ss=null;
	}
	public void begin() {
		Socket s=null;
		ssThread receiveThread=null;
		try {
			ss=new ServerSocket(RECEIVE_PORT);
			while(true) {
				s=ss.accept();
				receiveThread=new ssThread(s,lib);
				receiveThread.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void Increasement() {
		Thread t1=new Thread(new Runnable() {
			public void run() {
				while(true) {
					Iterator iter=lib.getIter();
					while(iter.hasNext()) {
						Map.Entry entry=(Map.Entry)iter.next();
						Account a=(Account) entry.getValue();
						a.setBalance(a.getBalance()*(0.0015+1.0));
						a.setMode1balance(a.getMode1balance()*(1.0+0.02));
						a.setMode2balance(a.getMode2balance()*(1.0+0.03));
						a.setMode3balance(a.getMode3balance()*(1.0+0.045));
						a.setLeftTimeOfSC1(a.getLeftTimeOfSC1()-5000);
						a.setLeftTimeOfSC2(a.getLeftTimeOfSC2()-5000);
						a.setLeftTimeOfSC3(a.getLeftTimeOfSC3()-5000);
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
		Thread t2=new Thread(new Runnable() {
			public void run() {
				while(true) {
					Iterator iter=lib.getIter();
					while(iter.hasNext()) {
						Map.Entry entry=(Map.Entry)iter.next();
						Account a=(Account) entry.getValue();
						if(a.getLoan()!=0) {
							a.setLoan(a.getLoan()*(1.0+0.05));
							a.setLefttimeofloan(a.getLefttimeofloan()-5000);
							if(a.getLefttimeofloan()>0) {
								if(a.getBalance()>=a.getLoan()/10) {
									a.setBalance(a.getBalance()-a.getLoan()/10);
									a.setLoan(a.getLoan()*0.9);
									a.freezen=false;
								}
								else if(a.getLeftTimeOfSC1()>=a.getLoan()/10) {
									a.setMode1balance(a.getMode1balance()-a.getLoan()/10);
									a.setLoan(a.getLoan()*0.9);
									a.freezen=false;
								}
								else if(a.getLeftTimeOfSC2()>=a.getLoan()/10) {
									a.setMode2balance(a.getMode2balance()-a.getLoan()/10);
									a.setLoan(a.getLoan()*0.9);
									a.freezen=false;
								}
								else if(a.getLeftTimeOfSC3()>=a.getLoan()/10) {
									a.setMode3balance(a.getMode3balance()-a.getLoan()/10);
									a.setLoan(a.getLoan()*0.9);
									a.freezen=false;
								}
								else
									a.freezen=true;
							}
							else {
								if(a.getBalance()>=a.getLoan()) {
									a.setBalance(a.getBalance()-a.getLoan());
									a.setLoan(0.0);
									a.freezen=false;
								}
								else if(a.getLeftTimeOfSC1()>=a.getLoan()) {
									a.setMode1balance(a.getMode1balance()-a.getLoan());
									a.setLoan(0.0);
									a.freezen=false;
								}
								else if(a.getLeftTimeOfSC2()>=a.getLoan()) {
									a.setMode2balance(a.getMode2balance()-a.getLoan());
									a.setLoan(0.0);
									a.freezen=false;
								}
								else if(a.getLeftTimeOfSC3()>=a.getLoan()) {
									a.setMode3balance(a.getMode3balance()-a.getLoan());
									a.setLoan(0.0);
									a.freezen=false;
								}
								else
									a.freezen=true;
							}
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}) ;
		t1.start();
		t2.start();
	}
}
class ssThread extends Thread{
	private Socket clientRequest;
	private BufferedReader in;
	private PrintWriter out;
	private PrintWriter out2;
	private Lib lib;
	private Account a=null;
	ssThread(Socket s,Lib l) {
		clientRequest=s;
		lib=l;
		InputStreamReader reader;  
		OutputStreamWriter writer;  
		try { 
			reader = new InputStreamReader(clientRequest.getInputStream());  
			writer = new OutputStreamWriter(clientRequest.getOutputStream());  
			in = new BufferedReader(reader);  
			out = new PrintWriter(writer, true);  
		} catch (IOException e) {  

		}
	}
	public void run() {
		out.println("Welcome to HONGGE'S Bank!");
		out.println("We should serve you as our family.");
		String i="";
		String j="";
		String k="";
		do {
			out.println("Please enter 1 to register and 2 to login (3 to leave) :");
			try {
				i=in.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch(i) {
			case "1":
				try {
					Register();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "2":
				while(a==null) {
					try {
						Login();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				while(a!=null)
				{
					if(!a.freezen) {
						out.println("Please Enter Your Choice: ");
						out.println("	1.Draw Money;");
						out.println("	2.Save Money;");
						out.println("	3.Apply for Loan;");
						out.println("	4.Check Account;");
						out.println("	5.exit.");
						try {
							j=in.readLine();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						switch(j) {
						case "1":
							try {
								DrawMoney();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							break;
						case "2":

							try {
								SaveMoney();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							break;
						case "3":
							try {
								ApplyForLoan();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							break;
						case "4":
							CheckAccount();
							break;
						case "5":
							String name=a.getUsername();
							a=null;
							out.println(name +","+" thank you for your visiting! ");
							try {
								lib.save();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						default:
							break;
						}
					}
					else {
						out.println("Sorry, your account has been freezen.");
						out.println("You can only deposit money to make it active.");
						out.println("Enter 1 to deposit or 2 to exit:");
						try {
							k=in.readLine();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						switch(k) {
						case "1":
							try {
								SaveMoney();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case "2":
							String name=a.getUsername();
							a=null;
							out.println(name +","+" thank you for your visiting! ");
							try {
								lib.save();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						default:
							break;
						}
					}
				}
				break;
			case "3":
				try {
					lib.save();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}while(!i.equals("3"));
		out.close();
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			clientRequest.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void Register() throws Exception {
		out.println("Create Your Username: ");
		String username=in.readLine();
		out.println("Create Your Password: ");
		String password=in.readLine();
		Account tmpAccount=new Account(lib.getMid(),username,password,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,false);
		lib.addAccount(tmpAccount);
		out.println("Your ID is : "+tmpAccount.getId());;
		out.println("Please remnber your ID, it is useful.");
	}
	public void Login() throws Exception {
		Integer tmpint=0;
		String tmpstr="";
		out.println("Enter Your ID: ");
		tmpint=Integer.valueOf(in.readLine());
		out.println("Enter Your Password: ");
		tmpstr=in.readLine();
		out.println(tmpstr);
		if(lib.checkAccount(tmpint, tmpstr))
		{
			out.println("Login Successfully!");
			a=lib.getAccount(tmpint);
		}
		else
			out.println("Login Failed...Please login again.");
	}
	public void DrawMoney() throws Exception {
		if(a==null)
			out.println("Please Login First.");
		else {
			out.println("Which kind of account do you want to draw from?");
			out.println("	1.Checking Account;");
			out.println("	2.Saving Account half-year (IR:2.0%)");
			out.println("	3.Saving Account one-year (IR:3.0%)");
			out.println("	4.Saving Account five-year (IR:4.5%)");			
			String c=in.readLine();
			switch(c) {
			case "1":
				double n=0;
				out.println("Enter the Money you want to draw: ");
				try {
					n=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(a.getBalance()>=n){
					a.withdrawl(n);
					out.println("Your have "+a.getBalance()+ " left.");
				}
				else
					out.println("Sorry,the money you withdraw is beyond your balance.");
				break;
			case "2":
				double nn=0;
				out.println("Enter the Money you want to draw: ");
				try {
					nn=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(a.getLeftTimeOfSC1()>0)
					out.println("The lefttime of Saving Account has not past!");
				else
					a.setMode1balance(a.getMode1balance()-nn);
				out.println("Your have "+a.getMode1balance()+ " mode1balance left.");
				break;
			case "3":
				double nnn=0;
				out.println("Enter the Money you want to draw: ");
				try {
					nnn=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(a.getLeftTimeOfSC2()>0)
					out.println("The lefttime of Saving Account has not past!");
				else
					a.setMode2balance(a.getMode2balance()-nnn);
				out.println("Your have "+a.getMode2balance()+ " mode2balance left.");
				break;
			case "4":
				double nnnn=0;
				out.println("Enter the Money you want to draw: ");
				try {
					nnnn=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(a.getLeftTimeOfSC3()>0)
					out.println("The lefttime of Saving Account has not past!");
				else
					a.setMode3balance(a.getMode3balance()-nnnn);
				out.println("Your have "+a.getMode3balance()+ " mode3balance left.");
				break;
			default:
				break;
			}
		}
	}
	public void SaveMoney() throws Exception {
		out.println("Which kind do you want to save?");
		out.println("	1.Checking Account;");
		out.println("	2.Saving Account.");
		String c="";
		c=in.readLine();
		switch(c) {
		case "1":
			double n=0;
			out.println("Enter the Money you want to save: ");
			try {
				n=Double.valueOf(in.readLine()).doubleValue();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			a.deposit(n);	
			out.println("Your balance is " + a.getBalance() + ". ");
			break;
		case "2":
			out.println("How long do you want to save? ");
			out.println("	1.half-year (IR:2.0%)");
			out.println("	2.one-year (IR:3.0%)");
			out.println("	3.five-year (IR:4.5%)");
			String cc="";
			cc=in.readLine();
			switch(cc) {
			case "1":
				double m=0;
				out.println("Enter the Money you want to save: ");
				try {
					m=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				a.setMode1balance(a.getMode1balance()+m);
				a.setLeftTimeOfSC1(2500);
				break;
			case "2":
				double mm=0;
				out.println("Enter the Money you want to save: ");
				try {
					mm=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				a.setMode2balance(a.getMode2balance()+mm);
				a.setLeftTimeOfSC2(5000);
				break;
			case "3":
				double mmm=0;
				out.println("Enter the Money you want to save: ");
				try {
					mmm=Double.valueOf(in.readLine()).doubleValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				a.setMode3balance(a.getMode3balance()+mmm);
				a.setLeftTimeOfSC3(25000);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
	public void ApplyForLoan() throws Exception {
		out.println("Which kind of service do you want? ");
		out.println("	1.Ask for loan;");
		out.println("	2.Pay loan.");
		String c=in.readLine();
		switch(c) {
		case "1":
			out.println("How much do you want to loan? ");
			double m=Double.valueOf(in.readLine()).doubleValue();
			if(a.getLoan()==0.0) { 
				a.setLoan(m+a.getLoan());
				a.setLefttimeofloan(50000);
			}
			else {
				a.setLoan(m+a.getLoan());
			}
			out.println("You have "+a.getLoan()+ " loan.");
			break;
		case "2":
			out.println("Your have "+ a.getLoan()+ " loan.");
			out.println("How much do you want to repay for loan?");
			double mm=Double.valueOf(in.readLine()).doubleValue();
			a.setLoan(a.getLoan()-mm);
			out.println("You have "+a.getLoan()+ " loan.");
			break;
		default:
			break;
		}
	}
	public void CheckAccount() {
		out.println("Hello, " + a.getUsername() + ".");
		out.println("Your account are as followwing:");
		out.println("	Checking Balance: " + a.getBalance() + ". ");
		out.println("	Saving Balance for Half-Year-Above(IR=2%): "+ a.getMode1balance()+ ". ");
		out.println("		(Left Time: "+a.getLeftTimeOfSC1()+ ".) ");
		out.println("	Saving Balance for One-Year-Above(IR=3%): "+ a.getMode2balance()+ ". ");
		out.println("		(Left Time: "+a.getLeftTimeOfSC2()+ ".) ");
		out.println("	Saving Balance for Five-Year-Above(IR=4.5%): "+ a.getMode3balance()+ ". ");
		out.println("		(Left Time: "+a.getLeftTimeOfSC3()+ ".) ");
		out.println("	Loan to Pay(with an IR of 5% per Year): "+ a.getLoan()+ ". ");
		out.println("		(Left Time to Pay: "+a.getLefttimeofloan()+".)");
		out.println("		Please Pay as Soon, or Your Account Will be Freezen. ");
	}
}