package ef.parser.models;

import javax.persistence.*;

@Entity
@Table(name = "blocked_ips")
public class BlockedIps {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ip;

    private String comment;

    public BlockedIps() {
    }

    public BlockedIps(String ip, String comment) {
        this.ip = ip;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
