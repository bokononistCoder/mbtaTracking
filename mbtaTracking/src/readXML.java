import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class readXML extends DefaultHandler {
	
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
			InputSource is= new InputSource(input);
			xr.parse(is);
		}
		catch(Exception e)
		{	
			System.out.println("Error creating XMLReader");
		}
	}	
	
	
	public void startDocument()
	{
		System.out.println("Document Started");
	}
	
	public void endDocument()
	{
		System.out.println("Document Ended");
	}
	
    public void startElement (String uri, String name,
		      String qName, Attributes atts)
    {	
    	System.out.println("Start element: " + qName);
    }

    public void endElement (String uri, String name, String qName)
    {
    	System.out.println("End element: " + qName );
    }

    public void characters (char ch[], int start, int length)
    {
    	System.out.print("Characters:    \"");
    	for (int i = start; i < start + length; i++)
    	{
    		switch (ch[i])
    		{
    			case ' ':
    			case '\n':
    			case '\r':
    			case '\t':
    				break;
    			default:
    				System.out.print(ch[i]);
    				break;
    		}
    	}
    	System.out.print("\"\n");
    }

/*		
	    public void startDocument() throws SAXException {
	        System.out.println("start document   : ");
	    }

	    public void endDocument() throws SAXException {
	        System.out.println("end document     : ");
	    }

	    public void startElement(String uri, String localName,
	        String qName, Attributes attributes)
	    throws SAXException {

	        System.out.println("start element    : " + qName);
	    }

	    public void endElement(String uri, String localName, String qName)
	    throws SAXException {
	        System.out.println("end element      : " + qName);
	    }

	    public void characters(char ch[], int start, int length)
	    throws SAXException {
	        System.out.println("start characters : " +
	            new String(ch, start, length));
	    }

	}
	*/
	
}
