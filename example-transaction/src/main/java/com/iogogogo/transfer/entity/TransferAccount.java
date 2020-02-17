package com.iogogogo.transfer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by tao.zeng on 2020/2/16.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer_account")
@TableName("transfer_account")
public class TransferAccount implements Serializable {

    @Id
    private long id;

    private String name;

    private float money;
}
