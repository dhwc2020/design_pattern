/**
 * 单例模式
 *      优点: 1. 内存中仅存在一个实例, 节省内存空间
 *           2. 一个对象的创建过程需要加载一些配置资源, 可以使用单例模式且常驻内存
 *           3. 同一个对象频繁创建或销毁, 且性能开销无法优化时, 采用单例模式
 *           4. 可以作为全局访问点, 缓存
 *
 *      缺点: 1. 违反单一职责原则, 类的职能与是否保证单个实例是两个职责
 *           2. 扩展困难, 没有抽象, 只有具体, 因此只能修改这个类
 */
////饿汉式
//public final class Singleton {
//    //采用类加载机制, 保证只生成一个实例对象
//    @SuppressWarnings("InstantiationOfUtilityClass")
//    private static final Singleton SINGLETON = new Singleton();
//
//    //构造私有化, 防止直接创建实例对象
//    private Singleton(){
//        //防止反射再次创建一个实例对象
//        if(SINGLETON != null){
//            throw new RuntimeException("not supported reflection");
//        }
//    }
//
//    //提供外部访问出口
//    public static Singleton getInstance(){
//        return SINGLETON;
//    }
//}

//懒汉式
public final class Singleton{
    //采用volatile修饰instance, 避免高并发下获取到不完整的对象
    private static volatile Singleton instance;

    //构造私有化, 防止直接创建实例对象
    private Singleton(){
        //防止反射再次创建一个实例对象
        if(instance != null){
            throw new RuntimeException("not supported reflection");
        }
    }

    //提供外部访问出口
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static Singleton getInstance() {
        //采用双重检查锁完成单个实例的生成
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

}