package com.example.scanit;

public class HistoryElement {
    private String data;
    private String time_stamp;

    public HistoryElement()
    {
        /*
         *  Default Constructor.
         */
    }

    public HistoryElement(String data, String time_stamp)
    {
        /*
         *  Constructor
         */
        this.data = data;
        this.time_stamp = time_stamp;
    }

    public String getData()
    {
        /*
         *  Getter Method for data
         */
        return data;
    }

    public String getTime_stamp()
    {
        /*
         *  Getter Method for time stamp.
         */
        return time_stamp;
    }

    public void setData(String data)
    {
        /*
         *  Setter method for Data.
         */
        this.data = data;
    }

    public void setTime_stamp(String time_stamp)
    {
        /*
         *  Setter method for timestamp.
         */
        this.time_stamp = time_stamp;
    }
}
