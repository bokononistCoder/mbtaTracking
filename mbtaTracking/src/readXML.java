import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;



public class readXML extends DefaultHandler {
	
	private boolean bTrip=false;
	private boolean bPlatform=false;
	private boolean bInfoType=false;
	private boolean bTime=false;
	private boolean bTimeRem=false;
	
	//Hashmap of trainData's
	private static HashMap<String, trainData> trainSchedule;
	
	//train data
	private trainData train;
	
	//Lookup table for PlatoformKey and Platform, direction
	private HashMap<String, platformData> platformLookup;
	
	
	readXML()
	{
		super();
	}
	
	readXML(InputStream input)
	{
		
		try
		{
			XMLReader xr = XMLReaderFactory.createXMLReader();
			readXML handler = new readXML();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			xr.parse(new InputSource(input));
		}
		catch(Exception e)
		{	
			System.out.println("Error parsing through XML");
			System.out.println(e);
		}
	}	
	
	
	public void startDocument()
	{
		initializePlatformLookup();
		trainSchedule=new HashMap<String, trainData>();
		
		System.out.println("XML Parsing Started");
	}
	
	public void endDocument()
	{
		System.out.println("XML Parsing Ended");
	}
	
    public void startElement (String uri, String name,
		      String qName, Attributes atts)
    {	
//    	System.out.println("Start element: " + qName);
    	if(qName.equalsIgnoreCase("Trip"))
    		bTrip=true;
    	else if(qName.equalsIgnoreCase("PlatformKey"))
    		bPlatform=true;
    	else if(qName.equalsIgnoreCase("InformationType"))
    		bInfoType=true;
    	else if(qName.equalsIgnoreCase("Time"))
    		bTime=true;
    	else if(qName.equalsIgnoreCase("TimeRemaining"))
    		bTimeRem=true;

    	
    }

    public void endElement (String uri, String name, String qName)
    {
//    	System.out.println("End element: " + qName );
    }

	public void characters(char ch[], int start, int length)
	{
		 
		if (bTrip)
		{
			String tripNumber = new String(ch, start, length);
			train = new trainData();
			train.setTripNumber(tripNumber);
			System.out.println("Trip Number : " + train.getTripNumber());
			bTrip = false;
		}
		else if (bPlatform)
		{
			String PlatformKey=new String(ch, start, length);
//			System.out.println('\"'+PlatformKey+'\"');
			
			
			platformData pd=(platformData)platformLookup.get(PlatformKey);
			if(pd==null)
				System.out.println("Internal error. PlatformKey cannot be found");
			
			train.setPlatformName(pd.getPlatformName());
			train.setDirection(pd.getDirection());
			
			System.out.println("Platform Key : " + train.getPlatformName() +" - " + train.getDirection());
			bPlatform = false;
		}
		else if (bInfoType)
		{
			String InfoType=new String(ch, start, length);
			train.setInfomationType(InfoType);
			
			System.out.println("Info Type : " + train.getInfomationType());
			bInfoType = false;
		}
		else if (bTime)
		{

/*			Calendar cal=Calendar.getInstance();
			
			cal.set(Integer.parseInt(new String(ch, start, 4)),
					Integer.parseInt(new String(ch, start+5, 2)),
					Integer.parseInt(new String(ch, start+8, 2)),
					Integer.parseInt(new String(ch, start+11, 2)),
					Integer.parseInt(new String(ch, start+14, 2)),
					Integer.parseInt(new String(ch, start+17, 2)));
*/
			
			String DateTime = new String(ch, start, length);
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss", Locale.ENGLISH).parse(DateTime);
				train.setDateTime(date);
				//System.out.println(date); // Sat Jan 02 00:00:00 BOT 2010
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Error parsing through date");
				e.printStackTrace();
			}
			
			
			System.out.println("Time : " + train.getDateTime());
			bTime = false;
		}
		else if (bTimeRem)
		{
			
			String TimeRem = new String(ch, start, length);
			Date date;
			try {
				date = new SimpleDateFormat("kk:mm:ss", Locale.ENGLISH).parse(TimeRem);
				train.setTimeRemaining(date);
				//System.out.println(date); // Sat Jan 02 00:00:00 BOT 2010
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("Error parsing through remaining time");
				e.printStackTrace();
			}
			
			
			
			
			System.out.println("Time Remaining : " + train.getTimeRemaining());
			bTimeRem = false;
			
			trainSchedule.put(train.getPlatformName() + "-" + train.getDirection(),train);
			
			
			//Debug
			trainData t=trainSchedule.get(train.getPlatformName() + "-" + train.getDirection());
			System.out.print("Debug data for- ");
			System.out.println(train.getPlatformName() + "-" + train.getDirection());
			t.displayTrainData();
		}
	}
    
    private void initializePlatformLookup()
    {
    	
    	platformLookup=new HashMap<String, platformData>();
    	//Red Line
    	platformLookup.put("RALEN", new platformData("Alewife Station","NB"));	
    	platformLookup.put("RDAVN",new platformData("Davis Station","NB"));
    	platformLookup.put("RDAVS",new platformData("Davis Station","SB"));
    	platformLookup.put("RPORN",new platformData("Porter Square Station","NB"));
    	platformLookup.put("RPORS",new platformData("Porter Square Station","SB"));
    	platformLookup.put("RHARN",new platformData("Harvard Square Station","NB"));
    	platformLookup.put("RHARS",new platformData("Harvard Square Station","SB"));
    	platformLookup.put("RCENN",new platformData("Central Square Station","NB"));
    	platformLookup.put("RCENS",new platformData("Central Square Station","SB"));
    	platformLookup.put("RKENN",new platformData("Kendall/MIT Station","NB"));
    	platformLookup.put("RKENS",new platformData("Kendall/MIT Station","SB"));
    	platformLookup.put("RMGHN",new platformData("Charles/MGH Station","NB"));
    	platformLookup.put("RMGHS",new platformData("Charles/MGH Station","SB"));
    	platformLookup.put("RPRKN",new platformData("Park St. Station","NB"));
    	platformLookup.put("RPRKS",new platformData("Park St. Station","SB"));
    	platformLookup.put("RDTCN",new platformData("Downtown Crossing Station","NB"));
    	platformLookup.put("RDTCS",new platformData("Downtown Crossing Station","SB"));
    	platformLookup.put("RSOUN",new platformData("South Station","NB"));
    	platformLookup.put("RSOUS",new platformData("South Station","SB"));
    	platformLookup.put("RBRON",new platformData("Broadway Station","NB"));
    	platformLookup.put("RBROS",new platformData("Broadway Station","SB"));
    	platformLookup.put("RANDN",new platformData("Andrew Station","NB"));
    	platformLookup.put("RANDS",new platformData("Andrew Station","SB"));
    	platformLookup.put("RJFKN",new platformData("JFK/UMass Station","NB"));
    	platformLookup.put("RJFKS",new platformData("JFK/UMass Station","SB"));
    	platformLookup.put("RSAVN",new platformData("Savin Hill Station","NB"));
    	platformLookup.put("RSAVS",new platformData("Savin Hill Station","SB"));
    	platformLookup.put("RFIEN",new platformData("Fields Corner Station","NB"));
    	platformLookup.put("RFIES",new platformData("Fields Corner Station","SB"));
    	platformLookup.put("RSHAN",new platformData("Shawmut Station","NB"));
    	platformLookup.put("RSHAS",new platformData("Shawmut Station","SB"));
    	platformLookup.put("RASHS",new platformData("Ashmont Station","SB"));
    	platformLookup.put("RNQUN",new platformData("North Quincy Station","NB"));
    	platformLookup.put("RNQUS",new platformData("North Quincy Station","SB"));
    	platformLookup.put("RWOLN",new platformData("Wollaston Station","NB"));
    	platformLookup.put("RWOLS",new platformData("Wollaston Station","SB"));
    	platformLookup.put("RQUCN",new platformData("Quincy Center Station","NB"));
    	platformLookup.put("RQUCS",new platformData("Quincy Center Station","SB"));
    	platformLookup.put("RQUAN",new platformData("Quincy Adams Station","NB"));
    	platformLookup.put("RQUAS",new platformData("Quincy Adams Station","SB"));
    	platformLookup.put("RBRAS",new platformData("Braintree Station","SB"));
    	
    	//Orange Line
    	platformLookup.put("OOAKN",new platformData("Oak Grove Station","NB"));
    	platformLookup.put("OMALN",new platformData("Malden Center Station","NB"));
    	platformLookup.put("OMALS",new platformData("Malden Center Station","SB"));
    	platformLookup.put("OWELN",new platformData("Wellington Station","NB"));
    	platformLookup.put("OWELS",new platformData("Wellington Station","SB"));
    	platformLookup.put("OSULN",new platformData("Sullivan Station","NB"));
    	platformLookup.put("OSULS",new platformData("Sullivan Station","SB"));
    	platformLookup.put("OCOMN",new platformData("Community College Station","NB"));
    	platformLookup.put("OCOMS",new platformData("Community College Station","SB"));
    	platformLookup.put("ONSTN",new platformData("North Station","NB"));
    	platformLookup.put("ONSTS",new platformData("North Station","SB"));
    	platformLookup.put("OHAYN",new platformData("Haymarket Station","NB"));
    	platformLookup.put("OHAYS",new platformData("Haymarket Station","SB"));
    	platformLookup.put("OSTNN",new platformData("State St. Station","NB"));
    	platformLookup.put("OSTSS",new platformData("State St. Station","SB"));
    	platformLookup.put("ODTNN",new platformData("Downtown Crossing Station","NB"));
    	platformLookup.put("ODTSS",new platformData("Downtown Crossing Station","SB"));
    	platformLookup.put("OCHNN",new platformData("Chinatown Station","NB"));
    	platformLookup.put("OCHSS",new platformData("Chinatown Station","SB"));
    	platformLookup.put("ONEMN",new platformData("Tufts Medical Center Station","NB"));
    	platformLookup.put("ONEMS",new platformData("Tufts Medical Center Station","SB"));
    	platformLookup.put("OBACN",new platformData("Back Bay Station","NB"));
    	platformLookup.put("OBACS",new platformData("Back Bay Station","SB"));
    	platformLookup.put("OMASN",new platformData("Massachusetts Ave. Station","NB"));
    	platformLookup.put("OMASS",new platformData("Massachusetts Ave. Station","SB"));
    	platformLookup.put("ORUGN",new platformData("Ruggles Station","NB"));
    	platformLookup.put("ORUGS",new platformData("Ruggles Station","SB"));
    	platformLookup.put("OROXN",new platformData("Roxbury Crossing Station","NB"));
    	platformLookup.put("OROXS",new platformData("Roxbury Crossing Station","SB"));
    	platformLookup.put("OJACN",new platformData("Jackson Square Station","NB"));
    	platformLookup.put("OJACS",new platformData("Jackson Square Station","SB"));
    	platformLookup.put("OSTON",new platformData("Stony Brook Station","NB"));
    	platformLookup.put("OSTOS",new platformData("Stony Brook Station","SB"));
    	platformLookup.put("OGREN",new platformData("Green St. Station","NB"));
    	platformLookup.put("OGRES",new platformData("Green St. Station","SB"));
    	platformLookup.put("OFORS",new platformData("Forest Hills Station","SB"));
    	
    	//Blue line
    	platformLookup.put("BWONE",new platformData("Wonderland Station","EB"));
    	platformLookup.put("BREVE",new platformData("Revere Beach Station","EB"));
    	platformLookup.put("BREVW",new platformData("Revere Beach Station","WB"));
    	platformLookup.put("BBEAE",new platformData("Beachmont Station","EB"));
    	platformLookup.put("BBEAW",new platformData("Beachmont Station","WB"));
    	platformLookup.put("BSUFE",new platformData("Suffolk Downs Station","EB"));
    	platformLookup.put("BSUFW",new platformData("Suffolk Downs Station","WB"));
    	platformLookup.put("BORHE",new platformData("Orient Heights Station","EB"));
    	platformLookup.put("BORHW",new platformData("Orient Heights Station","WB"));
    	platformLookup.put("BWOOE",new platformData("Wood Island Station","EB"));
    	platformLookup.put("BWOOW",new platformData("Wood Island Station","WB"));
    	platformLookup.put("BAIRE",new platformData("Airport Station","EB"));
    	platformLookup.put("BAIRW",new platformData("Airport Station","WB"));
    	platformLookup.put("BMAVE",new platformData("Maverick Station","EB"));
    	platformLookup.put("BMAVW",new platformData("Maverick Station","WB"));
    	platformLookup.put("BAQUE",new platformData("Aquarium Station","EB"));
    	platformLookup.put("BAQUW",new platformData("Aquarium Station","WB"));
    	platformLookup.put("BSTAE",new platformData("State St. Station","EB"));
    	platformLookup.put("BSTAW",new platformData("State St. Station","WB"));
    	platformLookup.put("BGOVE",new platformData("Government Center Station","EB"));
    	platformLookup.put("BGOVW",new platformData("Government Center Station","WB"));
    	platformLookup.put("BBOWE",new platformData("Bowdoin Station","EB"));
    	platformLookup.put("BBOWW",new platformData("Bowdoin Station","WB"));

    	
    	if(platformLookup==null)
			System.out.println("Error Generating Look Up Table");
		else
			System.out.println("Lookup Table initialized");
    }
	
    
    public void findTrain(String key)
    {
    	if(trainSchedule==null)
    		System.out.println("train sched is null");
    	
    	
    	trainData t=trainSchedule.get(key);
    	if(t!=null)
    	{
    		System.out.println("");
    		System.out.println("Train Service Found");
    		t.displayTrainData();
    	}
    	else
    		System.out.println("Train Service Not Found");
    		
    }
}
