package com.sharplook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by tao.zeng on 2019-03-12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("vi_relation_between_ci_and_lognum")
public class CiRelationLogNum extends Model<CiRelationLogNum> implements Serializable {


    /**
     * 主键ID
     */
    private Long id;

    /**
     * 一级分类名字
     */
    private String firstcatename;

    /**
     * 二级分类名称
     */
    private String secondcatename;

    /**
     * 三级分类名称
     */
    private String thirdcatename;

    /**
     * 唯一的日志编号
     */
    private String lognum;

    /**
     * 主键ID
     */
    private Long lognumid;

    /**
     * 日志名（一个日志编号对应一个日志名）
     */
    private String logname;

    /**
     *
     */
    private String cinum;

    /**
     *
     */
    private String ciname;

    /**
     *
     */
    private String hostname;

    /**
     *
     */
    private String manageip;

    /**
     *
     */
    @TableField(exist = false)
    private String ip;

}
