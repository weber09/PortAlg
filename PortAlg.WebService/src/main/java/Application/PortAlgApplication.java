package Application;

import Service.PortAlgService;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gabriel on 21/05/2016.
 */
@ApplicationPath("PortAlgWS")
public class PortAlgApplication extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(PortAlgService.class);
        return s;
    }
}
