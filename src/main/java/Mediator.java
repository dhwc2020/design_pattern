/**
 *  中介者
 *      优点: 1.将对象复杂的关系网转化为星型结构, 降低对象间的耦合度
 *           2.将交互交由中介者来统一处理, 便于修改
 *
 *      缺点: 1.将对象的复杂交互转移到中介者中, 如果对象过多, 则会造成中介者臃肿
 */
public class Mediator {
    public static void main(String[] args) {
        AbstractComputerBusMediator mediator = new ComputerBusMediator();
        CPUColleague cpu = new CPUColleague(mediator);
        mediator.setCpu(cpu);

        InternalStorageColleague internalStorage = new InternalStorageColleague(mediator);
        mediator.setStorage(internalStorage);

        HardDiskColleague hardDisk = new HardDiskColleague(mediator);
        mediator.setHardDisk(hardDisk);

        cpu.readCommand();
        internalStorage.readHardDisk();
    }
}

interface IMessageCode {
    String CPU_READ_COMMAND = "cpu.readCommand";
    String CPU_WRITE_RESULT = "cpu.writeResult";
    String INTERNAL_STORAGE_SEND_COMMAND = "internalStorage.sendCommand";
    String INTERNAL_STORAGE_RECEIVE_RESULT = "internalStorage.receiveResult";
    String INTERNAL_STORAGE_READ_HARD_DISK = "internalStorage.readHardDisk";
    String INTERNAL_STORAGE_WRITE_HARD_DISK = "internalStorage.writeHardDisk";
    String HARD_DISK_FIND_DATA = "hardDisk.findData";
    String HARD_DISK_SAVE_DATA = "hardDisk.saveData";
}

abstract class AbstractComputerBusMediator{

    protected InternalStorageColleague storage;
    protected HardDiskColleague hardDisk;
    protected CPUColleague cpu;

    public void setStorage(InternalStorageColleague storage) {
        this.storage = storage;
    }

    public void setHardDisk(HardDiskColleague hardDisk) {
        this.hardDisk = hardDisk;
    }

    public void setCpu(CPUColleague cpu) {
        this.cpu = cpu;
    }

    public abstract Object execute(String messageCode, Object... args);
}

class ComputerBusMediator extends AbstractComputerBusMediator{
    @Override
    public Object execute(String messageCode, Object... args) {
        //去除switch, 采用map, 将messageCode作为key, Handler作为value
        //Handler封装具体的分支处理方式, 对外统一暴露handle方法
        Object result = null;
        switch (messageCode){
            case IMessageCode.CPU_READ_COMMAND:
                super.cpu.readCommand();
                break;
            case IMessageCode.CPU_WRITE_RESULT:
                super.cpu.writeResult(String.valueOf(args[0]));
                break;
            case IMessageCode.INTERNAL_STORAGE_SEND_COMMAND:
                result = super.storage.sendCommand();
                break;
            case IMessageCode.INTERNAL_STORAGE_RECEIVE_RESULT:
                super.storage.receiveResult(String.valueOf(args[0]));
                break;
            case IMessageCode.INTERNAL_STORAGE_READ_HARD_DISK:
                super.storage.readHardDisk();
                break;
            case IMessageCode.INTERNAL_STORAGE_WRITE_HARD_DISK:
                super.storage.writeHardDisk(String.valueOf(args[0]));
                break;
            case IMessageCode.HARD_DISK_FIND_DATA:
                result = super.hardDisk.findData();
                break;
            case IMessageCode.HARD_DISK_SAVE_DATA:
                super.hardDisk.saveData(String.valueOf(args[0]));
        }
        return result;
    }
}

abstract class AbstractColleague{
    protected AbstractComputerBusMediator mediator;

    public AbstractColleague(AbstractComputerBusMediator mediator) {
        this.mediator = mediator;
    }
}

//Colleague后缀可以不要, 这里主要区分其他模式中的同名类
class CPUColleague extends AbstractColleague{
    public CPUColleague(AbstractComputerBusMediator mediator) {
        super(mediator);
    }

    public void readCommand(){
        Object result = mediator.execute(IMessageCode.INTERNAL_STORAGE_SEND_COMMAND);
        System.out.println("cpu read data: " + result);
    }

    public void writeResult(String result){
        mediator.execute(IMessageCode.INTERNAL_STORAGE_RECEIVE_RESULT, result);
        System.out.println("cpu write data success");
    }
}

class InternalStorageColleague extends AbstractColleague{
    public InternalStorageColleague(AbstractComputerBusMediator mediator) {
        super(mediator);
    }

    public void receiveResult(String result){
        System.out.println("memory receive result success: " + result);
    }

    public String sendCommand(){
        System.out.println("内存向cpu发送指令");
        return "send success";
    }

    public void readHardDisk(){
        Object result = mediator.execute(IMessageCode.HARD_DISK_FIND_DATA);
        System.out.println("memory successfully read data for hard disk: " + result);;
    }

    public void writeHardDisk(String data){
        mediator.execute(IMessageCode.HARD_DISK_SAVE_DATA, data);
        System.out.println("memory successfully wrote data to the hard disk");
    }
}

class HardDiskColleague extends AbstractColleague{
    public HardDiskColleague(AbstractComputerBusMediator mediator) {
        super(mediator);
    }

    public String findData(){
        System.out.println("找到指定数据");
        return "hard disk data";
    }

    public void saveData(String data){
        System.out.println("hard disk save data: " + data);
    }
}