package citycloud.listener;

import org.omg.CORBA.Environment;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Administrator on 2017-1-20.
 */
@WebListener
public class Log4jConfigListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("/");
        System.setProperty("webapp",path);
    }
}
