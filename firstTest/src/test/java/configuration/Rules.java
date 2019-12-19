package configuration;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class Rules extends TestWatcher {

    @Override
    protected void starting(Description description) {
        System.out.println("---before()---");
    }

    @Override
    protected void finished(Description description) {
        System.out.println("---after()---");
    }

    @Override
    protected void succeeded(Description description) {
        System.out.println("---succeeded()---");
    }

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println("---failed()---");
    }
}
