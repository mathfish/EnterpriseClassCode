package thompson.library.system.utilities;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import thompson.library.system.daos.BranchItemCheckoutDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.remote.Application;
import thompson.library.system.services.BranchServices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {BranchItemCheckoutDao.class, BranchServices.class, LoggingInterceptor.class,
        PatronDto.class})
@EnableAspectJAutoProxy
public class LibraryConfig {

    @Bean
    public BasicDataSource dataSource(){
        //File propfile = new File("/Users/jonathanthompson/EnterpriseDesign/Code/2016FA-jthomp97/application/database.properties");
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("database.properties");
        //File propfile = new File("database.properties");
        Properties properties = new Properties();
        try {
            //properties.load(new FileReader(propfile));
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(properties.getProperty("databaseType").equals("hsql")){
            File file = new File(properties.getProperty("hsqlDbUrl"));
            String dbPath = file.getAbsoluteFile().getAbsolutePath();
            String url = "jdbc:hsqldb:file:" + dbPath;

            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("org.hsqldb.jdbcDriver");
            ds.setUsername("sa");
            ds.setPassword("");
            ds.setUrl(url);
            ds.setInitialSize(5);
            ds.setMaxTotal(10);
            return  ds;
        } else if(properties.getProperty("databaseType").equals("derby")){
            File file = new File(properties.getProperty("javaDbUrl"));
            String dbPath = file.getAbsoluteFile().getAbsolutePath();
            String url = "jdbc:derby:" + dbPath;
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
            ds.setUsername("");
            ds.setPassword("");
            ds.setUrl(url);
            ds.setInitialSize(5);
            ds.setMaxTotal(10);
            return ds;
        } else {
            throw new IllegalStateException("Unsupported database type in properties file");
        }
    }

    @Bean
    public JdbcTemplate jdbcTemplate(BasicDataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource());
        return txManager;
    }
}
