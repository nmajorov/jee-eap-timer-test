package ch.basel.eap7.ejb;

import org.jboss.logging.Logger;

import ch.geneva.eap7.ejb.Greeter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;

@Singleton
@LocalBean
@Startup
public class MyTimer {

  private static final int INIT_TIMER_TIMEOUT = 6000;

  private static final Logger logger = Logger.getLogger(MyTimer.class);

  @Resource
  TimerService timerService;

  @EJB(lookup = "ejb:greeter/ejb/GreeterEJB!ch.geneva.eap7.ejb.Greeter")
  Greeter bean;

  
  @PostConstruct
  private void init() {
    try {
      timerService.createIntervalTimer(INIT_TIMER_TIMEOUT, INIT_TIMER_TIMEOUT, new TimerConfig());
    } catch (Exception e) {
      logger.error(e);
    }
  }

  @Timeout
  public void execute() {
    logger.info("********* Injected bean running say something: " + bean.sayHello());
  }
}
