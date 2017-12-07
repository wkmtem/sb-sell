package com.nsntc.sell.pojo.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Class Name: SellerInfo
 * Package: com.nsntc.sell.pojo.po
 * Description: 卖家信息表
 * @author wkm
 * Create DateTime: 2017/12/7 下午11:46
 * Version: 1.0
 */
@Data
@DynamicUpdate
@Entity(name = "seller_info")
public class SellerInfo {

    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;
    private String token;
    private Date createTime;
    private Date updateTime;
}
