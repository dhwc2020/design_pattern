/**
 *  模板方法模式
 *      优点: 1.父类构建算法框架, 子类实现具体功能, 拥有良好的扩展性
 *           2.父类的模板方法完成了对子类方法的动态调用
 *           3.封装不变部分, 扩展可变部分
 *           4.提取公共部分, 方便维护
 *
 *      缺点: 1.子类的实现对父类造成了影响, 在复杂项目中不便阅读理解
 */
public class TemplateMethod {
    public static void main(String[] args) {
        AbstractCarTemplate template = new UniversalCar();
        template.run();
    }
}

//抽象汽车模板
abstract class AbstractCarTemplate{
    //汽车启动
    protected abstract void doStart();
    //汽车亮灯
    protected abstract void doLighting();
    //汽车鸣笛
    protected abstract void doAlarm();
    //汽车停止
    protected abstract void doStop();

    //模板方法
    public final void run(){
        System.out.println("------汽车开始运行-------");
        this.doStart();
        this.doLighting();
        this.doAlarm();
        this.doStop();
    }
}

//子类实现具体操作
class UniversalCar extends AbstractCarTemplate{
    @Override
    protected void doStart() {
        System.out.println("汽车开始发动");
    }

    @Override
    protected void doLighting() {
        System.out.println("汽车打开前照灯");
    }

    @Override
    protected void doAlarm() {
        System.out.println("汽车开始鸣笛");
    }

    @Override
    protected void doStop() {
        System.out.println("汽车停止运行");
    }
}