package br.app.adv.main.spring;


public class BdAcessConfig {

	/*Configuração dos Padrões de conexão 
	@Primary
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        // Desenvolvimento
        dataSource.setUrl("jdbc:postgresql://localhost:5432/app_advogado?currentSchema=app_adv&useSSL=false");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");

        return dataSource;
    }

    // Define o hibernate como implementação do JPA
    @Bean
    public Properties additionalProperties(){
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        return props;
    }*/
    
}
