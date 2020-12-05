package ch.basel.eap7.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ch.geneva.eap7.ejb.Greeter;
import org.jboss.logging.Logger;


/**
 * producer class
 */
public class GreeterServicesProducer {

  private static final Logger logger = Logger.getLogger(GreeterServicesProducer.class);

  @Produces
  protected Greeter createConfigService(final InjectionPoint injectionPoint) throws NamingException {
    return lookup("Greeter", Greeter.class);
  }

  @SuppressWarnings("unchecked")
  private <T> T lookup(String name, Class<T> clazz) throws NamingException {
    final InitialContext initialContext = new InitialContext();
    String serviceName = "java:global/" + getJndiModuleName() + "/ejb/" + name + "EJB";
    logger.info("trying to find resource " + serviceName);
    return (T) initialContext.lookup(serviceName);
  }

  protected String getJndiAppName() {
    return "ejb";
  }

  protected String getJndiModuleName() {
    return "greeter";
  }

}