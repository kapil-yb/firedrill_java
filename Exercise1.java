// Blacklist leader
import java.io.*;
import java.util.*;  

public class Exercise1{
	public static void main ( String args[]) {
	try {
		File file = new File("/home/yugabyte/firedrill/config.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));

		ProcessBuilder pb = new ProcessBuilder();

		Map<String, String> map=new HashMap<String, String>();  


		String st;

		while ((st = br.readLine()) != null)
		{
			String[] arg = st.split("=",2);
			map.put(arg[0],arg[1]);
		}


		Set set=map.entrySet();
		Iterator itr=set.iterator(); 
		while(itr.hasNext()){ 
			Map.Entry entry=(Map.Entry)itr.next();
			String value=(String)entry.getKey();
			if (value.contains("server"))
				{
//					System.out.println(entry.getKey()+" "+entry.getValue()); 
					String execCode = "sudo ssh -i " + map.get("key_location") + " -ostricthostkeychecking=no -p " + map.get("ssh_port") + " yugabyte@" + entry.getValue() + " " + map.get("install_location") + "yb-admin --master_addresses " + map.get("server1_ip") + "," + map.get("server2_ip") + "," + map.get("server3_ip") + " -certs_dir_name " + map.get("tls_location") + " change_leader_blacklist ADD " + map.get("server1_ip");
//					System.out.println(execCode);
					pb.command("sh", "-c",execCode);
					Process process = pb.start();
					int status = process.waitFor();
					if (status != 0){ 
						System.out.println("Unable to connect to \n " + execCode + "\n\nPlease fix to continue");
						System.exit(1) ; } 
//					System.out.println("Status: " + status);
//					BufferedReader stdInput= new BufferedReader(new InputStreamReader(process.getInputStream()));
//       					String s = null;
//  				        while ((s = stdInput.readLine()) != null) {
//            					System.out.println(s); }		
					System.out.println("\nThere are no tablet leaders on one node. Please investigate\n");
					break;	
				}
		}
		}
	       catch (IOException e) {
            e.printStackTrace();
        }
	              catch (InterruptedException e) {
            e.printStackTrace();}
	}
}
