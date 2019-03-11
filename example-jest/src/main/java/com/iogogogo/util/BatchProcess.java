package com.iogogogo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tao.zeng on 2019-03-09.
 */
@Slf4j
public class BatchProcess {

    public static void process(Collection<?> totalList, int batchSize, BatchProcessListener batchProcessListener) throws Exception {
        if (CollectionUtils.isEmpty(totalList)) return;

        if (batchProcessListener == null) {
            throw new RuntimeException("没有批处理监听器!");
        }

        Iterator<?> iterator = totalList.parallelStream().iterator();
        int i = 0;
        List<Object> list = new ArrayList<>(1024);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            list.add(next);
            if ((i + 1) % batchSize == 0 || i == (totalList.size() - 1)) {
                // process
                batchProcessListener.onProcess(list);
                log.debug("batchSize:{} processSize:{} ", batchSize, list.size());
                list.clear();
            }
            i++;
        }
    }

    public interface BatchProcessListener {
        void onProcess(List<?> list) throws Exception;
    }
}
