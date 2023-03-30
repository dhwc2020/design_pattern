/**
 *  适配器模式
 *      优点: 1.将一个类或接口包装成客户所期望的类或接口
 *           2.将包装与被包装类或接口解耦, 增加类或接口的灵活度
 *
 *      缺点: 1.如果包装与被包装类或接口差异过大, 则包装逻辑会繁多且复杂
 */
public class Adapter {
    public static void main(String[] args) {
        AbstractDCPower dcPower = new DCPowerAdapter();
        dcPower.outputDC();
    }
}

//直流电源
abstract class AbstractDCPower{
    public abstract void outputDC();
}

//生活用电(交流电源220V)
interface IACPowerAdaptee{
    void outputAC();
    void description();
}

//适配器
class DCPowerAdapter extends AbstractDCPower implements IACPowerAdaptee{
    //也可以使用关联ACPowerAdaptee而非实现的方式完成适配

    @Override
    public void outputAC() {
        System.out.println("输出220v交流电");
    }

    @Override
    public void description() {}

    private void convert(){
        System.out.println("220v交流转换为12v直流电");
    }

    @Override
    public void outputDC() {
        this.outputAC();
        this.convert();
        System.out.println("输出12V直流电");
    }
}