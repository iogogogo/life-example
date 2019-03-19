package com.iogogogo.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iogogogo.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-19.
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Insert("insert into sys_user values(#{x.id},#{x.name},#{x.birthday})")
    boolean save(@Param("x") SysUser user);

    @Select("select * from sys_user")
    List<SysUser> list();
}
