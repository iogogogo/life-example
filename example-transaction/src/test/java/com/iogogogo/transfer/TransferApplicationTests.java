package com.iogogogo.transfer;

import com.iogogogo.transfer.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tao.zeng on 2020/2/16.
 */
@Slf4j
@Ignore
@org.junit.runner.RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = TransferApplication.class)
public class TransferApplicationTests {

    @Autowired
    private TransferService transferService;

    @Test
    public void test1() {
        transferService.transfer("jack.zhang", "kevin.yu", 200);
    }
}
