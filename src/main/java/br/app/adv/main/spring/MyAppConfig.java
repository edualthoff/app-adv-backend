package br.app.adv.main.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class MyAppConfig {
	
	/* Desabilida o redirecionamento do Spring MVC para a pagina de error 404 - Not Found. 
	 * Sendo possivel gerar mensagem de exceções padrões como @Valided  */
    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }
}
