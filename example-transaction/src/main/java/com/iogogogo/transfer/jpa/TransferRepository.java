package com.iogogogo.transfer.jpa;

import com.iogogogo.transfer.entity.TransferAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by tao.zeng on 2020/2/16.
 */
@Repository
public interface TransferRepository extends JpaRepository<TransferAccount, Long> {

    /**
     * @param in    转入人
     * @param money 金额
     */
    @Modifying
    @Query(value = "update transfer_account set money = money + ?2 where name = ?1", nativeQuery = true)
    void inMoney(String in, float money);

    /**
     * @param out   转出人
     * @param money 金额
     */
    @Modifying
    @Query(value = "update transfer_account set money = money - :money where name = :name", nativeQuery = true)
    void outMoney(@Param("name") String out, @Param("money") float money);
}
