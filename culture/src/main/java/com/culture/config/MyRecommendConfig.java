package com.culture.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class MyRecommendConfig {

    @Value("culture")
    private String dbbasename;

    @Value("root")
    private String dbuser;

    @Value("123456")
    private String dbpwd;


    //步骤1：创建DataModel模型，可以基于文件File的DataModel，也可基于数据库的JDBCDataModel。
    //如果数据库中表数据比较多，推荐耗时非常非常的慢，一般来说数据量都比较大可以基于文件DataModel模型来推荐，也可以将文件上传到hadoop，使用hadoop进行mapreduce计算，提高运算性能。
    //步骤2：采用欧几里得、皮尔逊等算法计算相似度。
    //步骤3：构建推荐器，基于用户或基于内容进行推荐。
    //步骤4：将推荐出来的商品id补全其他数据返回给用户展示。


    //设置基于数据库的DataModel模型
    @Bean
    public DataModel getMySQLDataModel() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser(dbuser);
        dataSource.setPassword(dbpwd);
        dataSource.setDatabaseName(dbbasename);
        dataSource.setServerTimezone("UTC");
        //参数 1 ：mysql数据源信息，参数 2 ：表名，参数 3 ：用户列字段，参数 4 ：文化列字段，参数 5 ：偏好值字段，参数 6 ：时间戳
        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "cul_like", "uid", "bid", "val", "createTime");
        return dataModel;

        /**
        * DataModel可基于数据也可基于文件
        *文件汇总数据格式
        * 用户 id : : 商品 id : : 偏好分值 : : 时间戳
        */
        //File file = new File ( "E:\\initData.dat" ) ;
        // try {
        //  DataModel dataModel = new GroupLensDataModel ( file ) ;
        // } catch ( IOException e ) {
        //e.printStackTrace ( ) ;
        // }
    }
}
