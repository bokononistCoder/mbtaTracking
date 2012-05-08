
public class platformData
{
	private String PlatformName;
	private String Direction;
	
	platformData()
	{
		super();
	}
	
	platformData(String pn,String d)
	{
		setPlatformName(pn);
		setDirection(d);
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public String getPlatformName() {
		return PlatformName;
	}

	public void setPlatformName(String platformName) {
		PlatformName = platformName;
	}
	
}
