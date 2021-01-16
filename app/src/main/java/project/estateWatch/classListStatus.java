package project.estateWatch;

public class classListStatus {

        public String ReportName;
        public String ReportDescription;
        public String Status;
        public String Date;

        public classListStatus(String ReportName, String ReportDescription, String Status, String Date)
        {
            this.ReportName = ReportName;
            this.ReportDescription = ReportDescription;
            this.Status = Status;
            this.Date = Date;
        }

        public String getstatusReportName() {
            return ReportName;
        }

        public String getstatusReportDescription() {
            return ReportDescription;
        }


        public String getstatusStatus() {
            return Status;
        }

        public String getstatusDate() {
            return Date;
        }
    }


