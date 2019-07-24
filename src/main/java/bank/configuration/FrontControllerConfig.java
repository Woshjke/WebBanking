package bank.configuration;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.annotation.Nonnull;

public class FrontControllerConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                WebMvcConfig.class,
                HibernateConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                WebMvcConfig.class
        };
    }

    @Override
    @Nonnull
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    @Nonnull
    protected FrameworkServlet createDispatcherServlet(@Nonnull WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcher = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcher.setThrowExceptionIfNoHandlerFound(true);
        return dispatcher;
    }
}
