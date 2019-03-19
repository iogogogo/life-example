package com.iogogogo.vertica.persistent;

import com.iogogogo.util.IoUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tao.zeng on 2019-03-12.
 */
public class SqlSessionFactoryHelper {

    private SqlSessionFactoryHelper() {
    }

    /**
     * 主要用来创建 SqlSession 相当于一次会话，类似于jdbc中的Connection对象
     * 当程序访问数据库时，就需要使用该对象构建 SqlSession，所以他应该位于整个生命周期
     * 并且不希望有多个实例对象，所以做成单例模式
     */
    private volatile static SqlSessionFactory mSqlSessionFactory;


    /**
     * 返回 SqlSession 对象 该对象主要用来执行 sql 语句
     * 它应该位于一个应用的请求和操作中，注意使用时需要及时关闭回收资源
     *
     * @return
     */
    public static SqlSession openSqlSession() {
        initSqlSessionFactory();
        return mSqlSessionFactory.openSession();
    }

    private static void initSqlSessionFactory() {
        InputStream in = null;
        try {
            // 加载 resources 目录下面的 mybatis.xml 文件
            in = Resources.getResourceAsStream("mybatis.xml");
            if (in == null) {
                String dir = System.getProperty("user.dir");
                File file = new File(dir + File.separator + "mybatis.xml");
                in = new FileInputStream(file);
            }

            if (mSqlSessionFactory == null) {
                synchronized (SqlSessionFactoryHelper.class) {
                    if (mSqlSessionFactory == null) {

                        // SqlSessionFactoryBuilder 主要用来生成 SqlSessionFactory 对象
                        // 一旦 SqlSessionFactory 构建完成，就应该将它回收，所以他在方法内部维护就好
                        mSqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(in);
        }
    }
}
