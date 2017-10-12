package advance.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @autho baifugui
 * @create 2017 09 25 10:14
 */
public class SqlUtil {
    private static InputStreamReader read = null;
    private static SqlSessionFactoryBuilder builder = null;
    private static SqlSessionFactory factory = null;
    private static SqlUtil sqlUtil = null;

    public static SqlSession getSqlSession() {
        getInstance();

        if(sqlSession == null){
            synchronized (SqlUtil.class){
                if(sqlSession == null){
                    sqlSession = factory.openSession();
                }
            }
        }
        return sqlSession;
    }

    public static void setSqlSession(SqlSession sqlSession) {
        SqlUtil.sqlSession = sqlSession;
    }

    private static SqlSession sqlSession = null;

    public static void getInstance() {
        if(factory == null){
            synchronized (SqlUtil.class){
                if(factory == null){
                    try {

                        read = new InputStreamReader(
                                SqlUtil.class.getClassLoader().getResourceAsStream("mybatisConfigs.xml"),"utf-8");
                    }catch (UnsupportedEncodingException e) { }
                    builder = new SqlSessionFactoryBuilder();
                    factory = builder.build(read);
                    factory.getConfiguration().setCacheEnabled(Boolean.FALSE);
                    System.out.println("===============================mybatis初始化完成============================");
                }
            }
        }
    }

    public static void commit(){
        if(sqlSession != null){
            synchronized (SqlUtil.class){
                if(sqlSession != null){
                    sqlSession.commit();
                    sqlSession.close();
                    sqlSession = null;
                    System.out.println("=========================================数据提交完成=================================================");
                }
            }
        }

    }


}
