// Change binary permission and restart host
import java.io.*;
import java.util.*;  

public class Exercise2{
	public static void main ( String args[]) {
	try {
		File file = new File("config.txt");

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
					String execCode = "sudo ssh -i " + map.get("key_location") + " -ostricthostkeychecking=no -p " + map.get("ssh_port") + " centos@" + entry.getValue() +  " sudo chmod -x " + map.get("install_location")+ "yb-tserver" ;
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
					System.out.println("\nService is not running on one of the node. Please investigate\n");

                                        String execCode1 = "sudo ssh -i " + map.get("key_location") + " -ostricthostkeychecking=no -p " + map.get("ssh_port") + " centos@" + entry.getValue() +  " sudo shutdown -r now" ;
//                                        System.out.println(execCode1);
                                        pb.command("sh", "-c",execCode1);
                                        Process process1 = pb.start();
                                        int status1 = process1.waitFor();


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
