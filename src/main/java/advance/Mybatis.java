package advance;

import advance.model.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mybatis {
    public static void connectTimeTest() throws IOException, ClassNotFoundException, SQLException {
        String sql = "SELECT * from ssycm_user where id> ?";
        PreparedStatement st = null;
        ResultSet rs = null;

        long beforeTimeOffset = -1L; //创建Connection对象前时间
        long afterTimeOffset = -1L; //创建Connection对象后时间
        long executeTimeOffset = -1L; //创建Connection对象后时间

        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver");

        beforeTimeOffset = System.currentTimeMillis();
        System.out.println("before:\t" + beforeTimeOffset);

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajl_ssycm?useUnicode=true&characterEncoding=utf8", "root", "root");

        afterTimeOffset = System.currentTimeMillis();
        System.out.println("after:\t\t" + afterTimeOffset);
        System.out.println("Create Costs:\t\t" + (afterTimeOffset - beforeTimeOffset) + " ms");

        st = con.prepareStatement(sql);
        //设置参数
        st.setInt(1, 10);
        //查询，得出结果集
        rs = st.executeQuery();
        executeTimeOffset = System.currentTimeMillis();
        System.out.println("Exec Costs:\t\t" + (executeTimeOffset - afterTimeOffset) + " ms");
    }

    static void mybatisQueryFunctest() throws IOException {
          /*
         * 1.加载mybatis的配置文件，初始化mybatis，创建出SqlSessionFactory，是创建SqlSession的工厂
         * 这里只是为了演示的需要，SqlSessionFactory临时创建出来，在实际的使用中，SqlSessionFactory只需要创建一次，当作单例来使用
         */
        InputStreamReader read = new InputStreamReader(
                Mybatis.class.getClassLoader().getResourceAsStream("mybatisConfig.xml"),"utf-8");//考虑到编码格式
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(read);

        //2. 从SqlSession工厂 SqlSessionFactory中创建一个SqlSession，进行数据库操作
        SqlSession sqlSession = factory.openSession();

        //3.使用SqlSession查询
        Map<String,Object> params = new HashMap<String,Object>();

        params.put("min_salary",10000);
        //a.查询工资低于10000的员工
        List<Employee> result = sqlSession.selectList("advance.mapper.EmployeesMapper.selectByMinSalary",params);
        //b.未传最低工资，查所有员工
        List<Employee> result1 = sqlSession.selectList("advance.mapper.EmployeesMapper.selectByMinSalary");
        System.out.println("薪资低于10000的员工数："+result.size());
        //~output :   查询到的数据总数：5
        System.out.println("所有员工数: "+result1.size());
        //~output :  所有员工数: 8
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        mybatisQueryFunctest();
    }
}
