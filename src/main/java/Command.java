/**
 *  命令模式
 *      优点: 1.将服务请求方与最终提供方进行解耦
 *           2.具体命令具有拥有良好的扩展性
 *           3.可以做命令的回撤
 *
 *      缺点: 1.命令越多, 抽象命令的子类越膨胀
 */
public class Command {
    public static void main(String[] args) {
        //服务员
        WaiterInvoker waiter = new WaiterInvoker();
        //客户的点餐命令
        AbstractOrderCommand aCoke = new ACokeCommand();
        AbstractOrderCommand aFriedChicken = new AFriedChickenCommand();

        //服务员传递命令
        waiter.setCommand(aCoke);
        waiter.action();

        waiter.setCommand(aFriedChicken);
        waiter.action();
    }
}

abstract class AbstractOrderCommand{
    protected ChefReceiver receiver = new ChefReceiver();

    public abstract void execute();
}

class AFriedChickenCommand extends AbstractOrderCommand{
    @Override
    public void execute() {
        System.out.println("客户: 点一份炸鸡");
        super.receiver.makeAFriedChicken();
    }
}

class ACokeCommand extends AbstractOrderCommand{
    @Override
    public void execute() {
        System.out.println("客户: 来一杯可乐");
        super.receiver.packACoke();
    }
}

class WaiterInvoker{
    private AbstractOrderCommand command;

    public void setCommand(AbstractOrderCommand command) {
        this.command = command;
    }

    public void action(){
        this.command.execute();
    }
}

//可以提供一个抽象类, 具体的操作实现与具体的命令绑定
class ChefReceiver{
    public void makeAFriedChicken(){
        System.out.println("厨师: 做一份炸鸡");
    }

    public void packACoke(){
        System.out.println("厨师: 打一杯可乐");
    }
}