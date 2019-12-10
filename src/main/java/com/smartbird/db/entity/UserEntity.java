package com.smartbird.db.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user", schema = "public", catalog = "quarkus_hope")
public class UserEntity extends PanacheEntityBase {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "userIdSequence",
            sequenceName = "user_id_seq",
            allocationSize = 1,
            initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdSequence")
    public Long id;

    @Basic
    @Column(name = "login_name")
    public String loginName;

    @Basic
    @Column(name = "nick_name")
    public String nickName;

    @Basic
    @Column(name = "login_pass")
    public String loginPass;

    @Basic
    @Column(name = "user_code")
    public String userCode;

    @Basic
    @Column(name = "create_time")
    public Timestamp createTime;

    @Basic
    @Column(name = "update_time")
    public Timestamp updateTime;

    @Basic
    @Column(name = "last_login_time")
    public Timestamp lastLoginTime;
}
