package com.sharplook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sharplook.entity.CiRelationLogNum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tao.zeng on 2019-03-12.
 */
@Repository
public interface CiRelationLogNumMapper extends BaseMapper<CiRelationLogNum> {

    @Select("select * from vi_relation_between_ci_and_lognum limit #{pageNo},#{pageSize}")
    List<CiRelationLogNum> listPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @Select("select count(1) from vi_relation_between_ci_and_lognum")
    int count();
}
