import java.util.Objects;

/**
 * 代理模式
 *      优点: 1. 控制对象访问, 通过代理完成相关功能
 *           2. 使用代理可以增强原始功能, 且不影响原始功能(无侵入)
 *           3. 良好的扩展性, 对于原始功能的修改并不会影响代理对象
 */
public class Proxy {
    public static void main(String[] args) {
        //静态代理
        staticProxy();
        //JDK动态代理
        dynamicProxy();
        //强制代理
        forceProxy();
    }

    public static void staticProxy(){
        ISpeech professor = new Professor();
        ISpeech professorProxy = new ProfessorProxy(professor);
        professorProxy.speech();
    }

    //JDK动态代理只能代理接口(原因代理类都需要继承Proxy)
    public static void dynamicProxy(){
        ISpeech professor = new Professor();
        ISpeech professorProxy = (ISpeech) java.lang.reflect.Proxy.newProxyInstance(professor.getClass().getClassLoader(),
                professor.getClass().getInterfaces(),
                (proxy, method, args) -> method.invoke(professor, args));//功能增强点
        professorProxy.speech();
    }

    public static void forceProxy(){
        /*
                              获取代理对象前   获取代理对象后
            真实对象访问              否            否
            非指定代理对象访问         否            否
            指定代理对象访问           \            是

         */
        //1.获取代理对象前, 真实对象访问
//        Speech professor = new Professor1();
//        professor.speech();
        //2.获取代理对象后, 真实对象访问
//        Speech professor = new Professor1();
//        Speech proxy = professor.getProxy();
//        professor.speech();
        //3.获取代理对象前, 非指定代理对象访问
//        Speech professor = new Professor1();
//        ISpeech proxy1 = new ProfessorProxy1(professor);
//        proxy1.speech();
        //4.获取代理对象后, 非指定代理对象访问
//        Speech professor = new Professor1();
//        Speech proxy = professor.getProxy();
//        ISpeech proxy1 = new ProfessorProxy1(professor);
//        proxy1.speech();
        //5.获取代理对象后, 指定代理对象访问
        Speech professor = new Professor1();
        Speech proxy = professor.getProxy();
        proxy.speech();
    }
}

//演讲能力
interface ISpeech{
    void speech();
}

//教授
class Professor implements ISpeech{
    @Override
    public void speech() {
        System.out.println("进行演讲");
    }
}

//助理
class ProfessorProxy implements ISpeech{
    private final ISpeech professor;

    public ProfessorProxy(ISpeech professor) {
        this.professor = professor;
    }

    //接洽
    public void approach(){
        System.out.println("有演讲需求, 符合条件");
    }

    //排期
    public void scheduling(){
        System.out.println("进行排期");
    }

    @Override
    public void speech() {
        this.approach();
        this.scheduling();
        this.professor.speech();
        this.tracking();
    }

    public void tracking(){
        System.out.println("返程");
    }

}

//演讲者
abstract class Speech implements ISpeech{
    //通过传入代理对象来确认执行结果(指定代理对象调用结果是正确的)
    protected abstract void speech(Speech proxy);
    public abstract Speech getProxy();
}

//教授
class Professor1 extends Speech {

    private Speech proxy;

    @Override
    public void speech() {
        //不通过代理直接调用
        this.speech(this);
    }

    @Override
    protected void speech(Speech proxy) {
        //判断是否是指定的代理对象进行调用
        if(this.isProxy(proxy)){
            System.out.println("进行演讲");
        }else{
            System.out.println("请使用指定的代理对象访问");
        }
    }

    @Override
    public Speech getProxy() {
        this.proxy = new ProfessorProxy1(this);
        return this.proxy;
    }

    private boolean isProxy(Speech proxy){
        //proxy为null返回false
        //proxy不为null, 则比较传入的proxy与成员proxy是否是一个, 不是一个返回false
        return Objects.nonNull(proxy) && Objects.equals(this.proxy, proxy);
    }

}

//助理
class ProfessorProxy1 extends Speech {
    private final Speech professor;

    public ProfessorProxy1(Speech professor) {
        this.professor = professor;
    }

    @Override
    public Speech getProxy() {
        return this;
    }

    //接洽
    public void approach(){
        System.out.println("有演讲需求, 符合条件");
    }

    //排期
    public void scheduling(){
        System.out.println("进行排期");
    }

    @Override
    public void speech() {
        //自己是代理对象
        this.speech(this);
    }

    @Override
    protected void speech(Speech proxy) {
        this.approach();
        this.scheduling();
        //将自己传递进行
        this.professor.speech(proxy);
        this.tracking();
    }

    public void tracking(){
        System.out.println("返程");
    }

}