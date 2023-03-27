import junit.framework.Assert;
import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonTest extends TestCase {

    public void testGetInstance() throws Exception {
        Field field = Singleton.class.getDeclaredField("SINGLETON");
        field.setAccessible(true);
        Singleton singleton = (Singleton) field.get("SINGLETON");
        Assert.assertNotNull(singleton);
        Singleton instance = Singleton.getInstance();
        Assert.assertEquals(singleton, instance);
    }

    public void testGetInstanceLazy() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    Singleton instance = Singleton.getInstance();
                    System.out.println(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}