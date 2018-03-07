package ef.parser.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rows_log")
public class RowsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    private String Ip;

    private String request;

    private int status;

    private String userAgent;

    public RowsLog() {
    }

    public RowsLog(Date dateCreated, String ip, String request, int status, String userAgent) {
        this.dateCreated = dateCreated;
        Ip = ip;
        this.request = request;
        this.status = status;
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


}
