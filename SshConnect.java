import java.io.*;
import java.util.*;  

public class SshConnect{
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
					String execCode = "sudo ssh -i " + map.get("key_location") + " -ostricthostkeychecking=no -p " + map.get("ssh_port") + " yugabyte@" + map.get("server2_ip") + " date";
//					System.out.println(execCode);
					pb.command("sh", "-c",execCode);
					Process process = pb.start();
					int status = process.waitFor();
//					System.out.println("Status: " + status);
//					BufferedReader stdInput= new BufferedReader(new InputStreamReader(process.getInputStream()));
//       					String s = null;
//  				        while ((s = stdInput.readLine()) != null) {
//            					System.out.println(s); }			
				}
		}
/*
		String execCode = "sudo ssh -i " + map.get("key_location") + " -ostricthostkeychecking=no -p " + map.get("ssh_port") + " yugabyte@" + map.get("server2_ip") + " date";
		System.out.println(execCode);

//		pb.command("/bin/bash", "-c","sudo ssh -i",map.get("key_location"),"-ostricthostkeychecking=no -p",map.get("ssh_port"),"yugabyte"+map.get("server2_ip"),"date");

		pb.command("sh", "-c",execCode);		

		System.out.println("command: " + pb.command());

		
		Process process = pb.start();
		int status = process.waitFor();
		System.out.println("Status: " + status);

        BufferedReader stdInput= new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s); }
*/	
		}
	       catch (IOException e) {
            e.printStackTrace();
        }
	              catch (InterruptedException e) {
            e.printStackTrace();}
	}
}
