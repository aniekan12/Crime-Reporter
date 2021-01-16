package project.estateWatch;

public class classListItems {
    public String ReportName;
    public String ReportDescription;
    public byte[] img;
    public byte[] video;
    public String crimeLocation; //Image URL
    public String Date;
    public String crimeType;//Name

    public classListItems(String ReportName, String ReportDescription, String crimeLocation, String Date, String crimeType)
    {
        this.ReportName = ReportName;
        this.ReportDescription = ReportDescription;
        this.crimeLocation = crimeLocation;
        this.Date = Date;
        this.crimeType = crimeType;
    }

    public String getReportName() {
        return ReportName;
    }

    public String getReportDescription() {
        return ReportDescription;
    }

    public byte[] getImg() {
        return img;
    }

    public byte[] getVideo() {
        return video;
    }

    public String getCrimeLocation() {
        return crimeLocation;
    }

    public String getDate() {
        return Date;
    }

    public String getCrimeType() {
        return crimeType;
    }
}
