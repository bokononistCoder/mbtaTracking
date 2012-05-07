import java.io.IOException;
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
        }
        catch(Exception e)
        {
        	System.out.println("Unable to connect to MBTA");
        }
        
	}

}
