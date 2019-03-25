package com.iogogogo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iogogogo.security.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {


    @Select("select * from role where id in (SELECT id from user_roles where user_id = #{userId});")
    List<Role> findRoleByUserId(@Param("userId") long userId);
}
