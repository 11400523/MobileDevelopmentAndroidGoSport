package facebook.example.com.gosport.Class;

public class EventInformation {
    private int id;
    private String eventName;
    private String location;
    private String extraInfo;
    private Integer year, month, day, hour, minute;
    private Long timeInMillis;


    public EventInformation(String eventName, String location, String extraInfo, int year, int month, int day, int hour, int minute){
        this.eventName = eventName;
        this.location = location;
        this.extraInfo = extraInfo;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public EventInformation(){}

    public String getEventName() {
        return eventName;
    }
    public String getLocation() {
        return location;
    }
    public String getExtraInfo() {
        return extraInfo;
    }
    public int getId(){ return id;}
    public Integer getYear(){return year;}
    public Integer getMonth(){return month;}
    public Integer getDay(){return day;}
    public Integer getHour(){return hour;}
    public Integer getMinute(){return minute;}

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setEventName(String name){
        this.eventName = name;
    }
    public void setLocation(String location){this.location = location; }
    public void setExtraInfo(String info){ this.extraInfo = info; }
    public void setYear(Integer year){this.year = year;}
    public void setMonth(Integer month){this.month = month;}
    public void setDay(Integer day){this.day = day;}
    public void setHour(Integer hour){this.hour = hour;}
    public void setMinute(Integer minute){this.minute = minute;}
    public void setTimeInMillis(Long timeInMillis){
        this.timeInMillis = timeInMillis;
    }

}
