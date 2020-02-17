package com.iogogogo.transfer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iogogogo.transfer.entity.TransferAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by tao.zeng on 2020/2/16.
 */
@Repository
public interface TransferMapper extends BaseMapper<TransferAccount> {

    /**
     * @param in    转入人
     * @param money 金额
     */
    @Update("update transfer_account set money = money + #{money} where name = #{name}")
    void inMoney(@Param("name") String in, @Param("money") float money);

    /**
     * @param out   转出人
     * @param money 金额
     */
    @Update("update transfer_account set money = money - #{money} where name = #{name}")
    void outMoney(@Param("name") String out, @Param("money") float money);
}
