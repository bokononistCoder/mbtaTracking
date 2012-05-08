import java.sql.Time;
import java.util.Date;


	public class trainData
	{
		private String TripNumber;
		private String PlatformName;
		private String Direction;
		private String InfomationType;
		private Date DateTime;
		private Time TimeRemaining;
		
		
		trainData()
		{
			super();
		}
		
		public String getTripNumber() {
			return TripNumber;
		}
		public void setTripNumber(String tripNumber) {
			TripNumber = tripNumber;
		}
		public String getInfomationType() {
			return InfomationType;
		}
		public void setInfomationType(String infomationType) {
			InfomationType = infomationType;
		}
		public Time getTimeRemaining() {
			return TimeRemaining;
		}
		public void setTimeRemaining(Time timeRemaining) {
			TimeRemaining = timeRemaining;
		}
		public String getPlatformName() {
			return PlatformName;
		}
		public void setPlatformName(String platformName) {
			PlatformName = platformName;
		}
		public String getDirection() {
			return Direction;
		}
		public void setDirection(String direction) {
			Direction = direction;
		}
		public Date getDateTime() {
			return DateTime;
		}
		public void setDateTime(Date dateTime) {
			DateTime = dateTime;
		}

		
		
		
	}
