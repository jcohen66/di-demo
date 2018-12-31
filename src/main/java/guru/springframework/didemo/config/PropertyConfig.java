package guru.springframework.didemo.config;

import guru.springframework.didemo.examplebeans.FakeDataSource;
import guru.springframework.didemo.examplebeans.FakeJmsBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Note:  Added Environment vars GURU_USERNAME and USERNAME in run configuration.
 */
@Configuration
// @PropertySource({"classpath:datasource.properties", "classpath:jms.properties"})
@PropertySources({
    @PropertySource("classpath:datasource.properties"),
    @PropertySource("classpath:jms.properties")
})
public class PropertyConfig {

    @Autowired
    Environment env;

    @Value("${guru.username}")
    String user;

    @Value("${guru.password}")
    String password;

    @Value("${guru.dburl}")
    String url;

    @Value("${guru.jms.username}")
    String jmsUser;

    @Value("${guru.jms.password}")
    String jmsPassword;

    @Value("${guru.jms.dburl}")
    String jmsUrl;


    @Bean
    public FakeDataSource fakeDataSource() {
        FakeDataSource fakeDataSource = new FakeDataSource();
        // fakeDataSource.setUser(user);

        // User env variable override instead of exteernalized property.
        fakeDataSource.setUser(env.getProperty("USERNAME"));
        fakeDataSource.setPassword(password);
        fakeDataSource.setUrl(url);
        return fakeDataSource;
    }

    @Bean
    public FakeJmsBroker fakeJmsBroker() {
        FakeJmsBroker fakeJmsBroker = new FakeJmsBroker();
        fakeJmsBroker.setUser(user);
        fakeJmsBroker.setPassword(password);
        fakeJmsBroker.setUrl(url);
        return fakeJmsBroker;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {

        // This scans for property files and allows us to use @Value annotation.
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;

    }
}
