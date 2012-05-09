import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class mbtaTrack {

	/**
	 * @param args
	 */
	public static void main(String[] args)  throws IOException{
		// TODO Auto-generated method stub
		URL url = new URL("http://developer.mbta.com/Data/Red.xml");
        try
        {
        	URLConnection conn = url.openConnection();
        	readXML rx=new readXML(conn.getInputStream());
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 

            System.out.print("Enter a platform name: ");
            String pname = in.readLine(); // Get what the user types.
            System.out.print("Enter a platform direction: ");
            
            String pdirec=in.readLine(); // Get what the user types.
            in.close();
            System.out.println("Searching: "+pname+"-"+pdirec);
            rx.findTrain(pname+"-"+pdirec);
            
        }
        catch(Exception e)
        {
        	System.out.println("Unable to connect to MBTA");
        	System.out.println(e);
        }
        
	}

}
