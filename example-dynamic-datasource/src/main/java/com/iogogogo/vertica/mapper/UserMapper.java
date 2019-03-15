package com.iogogogo.vertica.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iogogogo.vertica.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-15.
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


    @Select("select * from public.user")
    List<User> list();
}
