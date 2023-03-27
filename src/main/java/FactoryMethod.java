/**
 * 工厂方法模式
 *      优点: 1.使用与构建分离, 更加灵活简单, 无需关注对象构建过程
 *           2.符合开闭原则, 扩展容易, 比如新增人种仅需添加一个Human的实现类
 *           3.使一个类的实例化延迟到其子类
 *           4.可以延迟初始化, 通过Map缓存共用造出来的产品
 *           5.可以限制构造实例的个数, 比如最大连接数等应用
 *
 *      缺点: 1.需要维护具体产品类与该产品工厂的关系
 */
public class FactoryMethod {
    public static void main(String[] args) {
        AbstractHumanFactory humanFactory = new HumanFactory();

        Human yellowHuman = humanFactory.createHuman(YellowHuman.class);
        Human blackHuman = humanFactory.createHuman(BlackHuman.class);

        yellowHuman.getColor();
        yellowHuman.talk();

        blackHuman.getColor();
        blackHuman.talk();
    }
}

//抽象人类
abstract class Human {
    public abstract void getColor();
    public abstract void talk();
}

//具体人类, 黄种人
class YellowHuman extends Human{

    @Override
    public void talk() {
        System.out.println("talk use the two character");
    }

    @Override
    public void getColor() {
        System.out.println("yellow color");
    }
}

//具体人类, 黑人
class BlackHuman extends Human{

    @Override
    public void talk() {
        System.out.println("we generally don't understand what they say");
    }

    @Override
    public void getColor() {
        System.out.println("black color");
    }
}

//创造人类的抽象工厂
abstract class AbstractHumanFactory {
    public abstract <T extends Human> T createHuman(Class<T> clazz);
}

//通过反射替换多工厂, 更加灵活(如果构建过程需要个性化赋予初值, 则采用多工厂)
class HumanFactory extends AbstractHumanFactory{
    @Override
    public <T extends Human> T createHuman(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
