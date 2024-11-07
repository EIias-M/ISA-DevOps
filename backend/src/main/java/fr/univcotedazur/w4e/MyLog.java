package fr.univcotedazur.w4e;
import java.util.logging.Logger;


public class MyLog {
    private static final Logger logger = Logger.getLogger(MyLog.class.getName());

    public void myMethod() {
        logger.info("This is an info message");
        logger.severe("This is an error message");
    }
}
