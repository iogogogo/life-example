package com.iogogogo.vertica.persistent;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by tao.zeng on 2019-03-12.
 */
public interface BaseMapper<PK extends Serializable, M extends Serializable> {

    /**
     * 查询所有数据
     *
     * @return
     */
    Collection<M> findAll();

    /**
     * 根据 id 查询数据
     *
     * @param id
     * @return
     */
    M findById(PK id);

    /**
     * 保存数据
     *
     * @param m
     * @return
     */
    boolean save(M m);

    /**
     * 批量保存
     *
     * @param collection
     * @return
     */
    boolean batchSave(Collection<M> collection);

    /**
     * 修改数据
     *
     * @param m
     * @return
     */
    boolean update(M m);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delete(PK id);

    /**
     * 根据ID批量删除
     *
     * @param idList
     * @return
     */
    boolean batchDeleteByIdList(Collection<PK> idList);
}
