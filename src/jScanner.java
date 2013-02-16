import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;


public class jScanner {
	static int openPorts = 0;
	static InetAddress address;
	public static void main(String[] args) {
		
		String host = null;
		
		StringBuffer sb = new StringBuffer();
		for (String arg : args) {
			sb.append(arg);
		}
		Scanner argsIn = new Scanner(sb.toString());
		
		while (argsIn.hasNext()) {
			String n = argsIn.next();
			if (n.equalsIgnoreCase("-h") || n.equalsIgnoreCase("--host")) {
				if(argsIn.hasNext()) {
					host = argsIn.next();
				}
				else {
					System.out.println("Invalid arguments. Use -? or --help to see help message.");
					System.exit(0);
				}
			}
			if (n.equalsIgnoreCase("-?") || n.equalsIgnoreCase("--help")) {
				System.out.println("jScanner - simple port scanner in java");
				System.out.println("Written by geohhot in 2012/02");
				System.out.println();
				System.out.println("Arguments.");
				System.out.println("-? or --help: show help message(actuall this message)");
				System.out.println("-h or --host [host] - select host to scan");
			}
		}
		
		if (host == null) {
			System.out.println("Invalid arguments, use -? -h or --help to see help message.");
			System.exit(0);
		}
		
		try {
			Date startDate = new Date();
			address = InetAddress.getByName(host);
			
			System.out.println("Scanning "+address.getHostName()+" ["+address.getHostAddress()+"].");
			
			int portsToScan = 7000;
			
			for (int i=1;i<portsToScan;i++) {
				final int port = i;
				Thread scan = new Thread(new Runnable() {
					public void run() {
						try {
							Socket s = new Socket(address, port);
							System.out.println("* Port "+port+" is up.");
							openPorts ++;
							s.close();
						}
						catch (Exception e) {}
					}
				});
				scan.start();
			}
			Date endDate = new Date();
			long t = endDate.getTime() - startDate.getTime();
			System.out.println("Stats: "+openPorts+"/"+portsToScan+" ports are up. Scanned in "+t+" miliseconds.");
		} catch (UnknownHostException e) {
			System.out.println("Unknown host. Could not connect to that computer.");
		}
		
	}
}
