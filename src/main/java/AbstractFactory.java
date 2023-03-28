/**
 *  抽象工厂模式
 *      工厂方法的增强版, 工厂方法只生产某一类产品, 而抽象工厂生产一系列产品(产品簇)
 *      优点: 1.封装具体实现, 通过接口来对外提供服务
 *           2.生产线的扩展是容易的, 符合开闭原则(比如: 氢能汽车)
 *
 *      缺点: 1.对于产品簇的扩展是困难的, 原因是抽象工厂规定了这一部分, 如果增加产品就需要修改抽象类, 违背开闭原则
 */
public class AbstractFactory {
    //汽车品牌 (笛卡儿积) 汽车动力能源
    //两个抽象工厂生成不同能源的一系列产品
    public static void main(String[] args) {
        //生产线一: 电动汽车
        AbstractCarFactory electricCarFactory = new ElectricCarFactory();
        ICarDescription benzElectricCar = electricCarFactory.creatBenz();
        ICarDescription bmwElectricCar = electricCarFactory.createBMW();
        bmwElectricCar.description();
        benzElectricCar.description();
        System.out.println("-----------------------");

        //生产线二: 燃油汽车
        AbstractCarFactory fuelCarFactory = new FuelCarFactory();
        ICarDescription benzFuelCar = fuelCarFactory.creatBenz();
        ICarDescription bmwFuelCar = fuelCarFactory.createBMW();
        benzFuelCar.description();
        bmwFuelCar.description();
    }
}

//可以换成抽象汽车类, 这里只是方便使用汽车自描述功能
interface ICarDescription{
    void description();
}

abstract class AbstractBMWCar implements ICarDescription{
    private String name;

    public AbstractBMWCar() {
        this.name = "宝马";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class BMWElectricCar extends AbstractBMWCar {
    @Override
    public void description() {
        System.out.println(super.getName() + "电动汽车");
    }
}

class BMWFuelCar extends AbstractBMWCar {
    @Override
    public void description() {
        System.out.println(super.getName() + "燃油汽车");
    }
}

abstract class AbstractBenzCar implements ICarDescription{
    private String name;

    public AbstractBenzCar() {
        this.name = "奔驰";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class BenzElectricCar extends AbstractBenzCar{
    @Override
    public void description() {
        System.out.println(super.getName() + "电动汽车");
    }
}

class BenzFuelCar extends AbstractBenzCar{
    @Override
    public void description() {
        System.out.println(super.getName() + "燃油汽车");
    }
}

//抽象工厂规定只生产两种品牌的汽车
abstract class AbstractCarFactory{
    public abstract ICarDescription createBMW();
    public abstract ICarDescription creatBenz();
}

//电动汽车工厂
class ElectricCarFactory extends AbstractCarFactory{
    @Override
    public ICarDescription createBMW() {
        return new BMWElectricCar();
    }

    @Override
    public ICarDescription creatBenz() {
        return new BenzElectricCar();
    }
}

//燃油汽车工厂
class FuelCarFactory extends AbstractCarFactory{
    @Override
    public ICarDescription createBMW() {
        return new BMWFuelCar();
    }

    @Override
    public ICarDescription creatBenz() {
        return new BenzFuelCar();
    }
}