package cn.edu.hdu.lab505.tlts.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hhx on 2017/1/9.
 */
@Entity
@Table(name = "t_admin")
public class Admin implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column(unique = true)
    private Long adminId;
    @Column(unique = true)
    private String weChatId;

    public Admin(String name, Long adminId, String weChatId) {
        this.name = name;
        this.adminId = adminId;
        this.weChatId = weChatId;
    }

    public Admin() {
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }
}
