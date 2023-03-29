/**
 *  策略模式
 *      优点: 1.拥有良好的扩展性, 策略算法扩展相当容易
 *           2.通过上下文对象来封装策略的访问, 让客户端与策略类进行解耦
 *           3.策略算法切换非常容易
 *
 *      缺点: 1.客户端与策略类在使用上具有一定的耦合, 客户端需要了解不同的策略
 *             并创建相应的策略类, 通过使用工厂方法来进一步解耦
 *
 */
public class Strategy {
    public static void main(String[] args) {
//        IDeliveryStrategy electricCarDelivery = new ElectricCarDeliveryStrategy();
//        DeliveryContext context1 = new DeliveryContext(electricCarDelivery);
//        context1.delivery();
//
//        IDeliveryStrategy motorcycleDelivery = new MotorcycleDeliveryStrategy();
//        DeliveryContext context2 = new DeliveryContext(motorcycleDelivery);
//        context2.delivery();
        //采用工厂方法 + 反射 + 枚举去除条件判断, 配合策略模式使用
        DeliveryContext context = new DeliveryContext(DeliveryStrategyClassNameEnum.ELECTRIC_CAR_DELIVERY_STRATEGY);
        context.delivery();
    }
}

//控制外部输入, 对外暴露策略种类和功能
enum DeliveryStrategyClassNameEnum{
    //电动车配送策略类全限定名
    ELECTRIC_CAR_DELIVERY_STRATEGY("ElectricCarDeliveryStrategy"),
    //摩托车配送策略类全限定名
    MOTORCYCLE_DELIVERY_STRATEGY("MotorcycleDeliveryStrategy");

    private String className;

    DeliveryStrategyClassNameEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}

//工厂方法中的抽象工厂
abstract class AbstractDeliveryStrategyFactory{
    public abstract IDeliveryStrategy createDeliveryStrategy(String className);
}

//通过反射构造具体的策略对象(可以直接使用简单工厂, 不过为了扩展性, 采用工厂方法)
class DeliveryStrategyFactory extends AbstractDeliveryStrategyFactory{
    @Override
    public IDeliveryStrategy createDeliveryStrategy(String className) {
        try {
            return (IDeliveryStrategy) Class.forName(className).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//策略模式的上下文对象, 封装策略的访问
class DeliveryContext{
    private AbstractDeliveryStrategyFactory factory = new DeliveryStrategyFactory();
    private IDeliveryStrategy strategy;

    public DeliveryContext(DeliveryStrategyClassNameEnum deliveryStrategyClassNameEnum) {
        this.strategy = this.factory.createDeliveryStrategy(deliveryStrategyClassNameEnum.getClassName());
    }

    public void delivery(){
        this.strategy.deliveryWay();
    }
}

//策略接口
interface IDeliveryStrategy{
    void deliveryWay();
}

//具体策略实现类
class ElectricCarDeliveryStrategy implements IDeliveryStrategy{
    @Override
    public void deliveryWay() {
        System.out.println("采用电动车进行配送");
    }
}

class MotorcycleDeliveryStrategy implements IDeliveryStrategy{
    @Override
    public void deliveryWay() {
        System.out.println("采用摩托车进行配送");
    }
}