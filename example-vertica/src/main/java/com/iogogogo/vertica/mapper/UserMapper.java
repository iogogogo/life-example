package com.iogogogo.vertica.mapper;

import com.iogogogo.vertica.entity.UserEntity;
import com.iogogogo.vertica.persistent.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;

/**
 * Created by tao.zeng on 2019-03-13.
 */
@Mapper
public interface UserMapper extends BaseMapper<Long, UserEntity> {

    @Override
    @Insert("insert into public.user(id,name,description) values(#{model.id},#{model.name},#{model.description})")
    boolean save(@Param("model") UserEntity userEntity);

    @Override
    @Select("select * from public.user")
    Collection<UserEntity> findAll();
}
