/**
 *  建造者模式
 *      优点: 1.良好的封装性, 客户端不必知道产品的构建和组装
 *           2.对于具体产品建造者来说符合开闭原则
 *           3.对于构建细节可以做精细把控, 单个组件的构建和组装
 *
 *      缺点: 1.对于构建组件的添加是困难的
 */
public class Builder {

    public static void main(String[] args) {
        PhoneDirector director = new PhoneDirector();

        HuaweiBuilder huaweiBuilder = new HuaweiBuilder();
        //指导具体部件的构建顺序和参与个数
        director.construct(huaweiBuilder);
        Phone huaweiPhone = huaweiBuilder.getResult();

        System.out.println("--------------");
        VIVOBuilder vivoBuilder = new VIVOBuilder();
        director.construct(vivoBuilder);
        Phone vivoPhone = vivoBuilder.getResult();
    }
}

//抽象产品
abstract class Phone{
    private Camera camera;
    private Screen screen;
    private CPU cpu;
    private GPU gpu;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public GPU getGpu() {
        return gpu;
    }

    public void setGpu(GPU gpu) {
        this.gpu = gpu;
    }

}

//具体产品
class VIVOPhone extends Phone{}
class HuaweiPhone extends Phone{}

//抽象组件
abstract class Camera{}
abstract class Screen{}
abstract class CPU{}
abstract class GPU{}

//具体组件
class LEICACamera extends Camera{}
class SONYCamera extends Camera{}

class FullScreen extends Screen{}
class WaterDropletScreen extends Screen{}

class SnapdragonCPU extends CPU{}
class KirinCPU extends CPU{}

class AMDGPU extends GPU{}
class NVIDIAGPU extends GPU{}

//抽象构建者
abstract class PhoneBuilder{
    public abstract void buildPhone();
    public abstract void buildCamera();
    public abstract void buildScreen();
    public abstract void buildCPU();
    public abstract void buildGPU();
    public abstract Phone getResult();
}

//具体产品构建者
class VIVOBuilder extends PhoneBuilder{
    //为了方便, 可以直接在此处实例化
    private Phone vivoPhone;

    //懒加载, VIVOBuilder对象实例化完成, 不调用该方法则不实例化Phone
    @Override
    public void buildPhone() {
        this.vivoPhone = new VIVOPhone();
    }

    @Override
    public void buildCamera() {
        SONYCamera sonyCamera = new SONYCamera();
        vivoPhone.setCamera(sonyCamera);
    }

    @Override
    public void buildScreen() {
        FullScreen fullScreen = new FullScreen();
        vivoPhone.setScreen(fullScreen);
    }

    @Override
    public void buildCPU() {
        SnapdragonCPU snapdragonCPU = new SnapdragonCPU();
        vivoPhone.setCpu(snapdragonCPU);
    }

    @Override
    public void buildGPU() {
        AMDGPU amdGPU = new AMDGPU();
        vivoPhone.setGpu(amdGPU);
    }

    @Override
    public Phone getResult(){
        return vivoPhone;
    }
}

//具体产品构建者
class HuaweiBuilder extends PhoneBuilder{
    private Phone huawei;

    @Override
    public void buildPhone() {
        this.huawei = new HuaweiPhone();
    }

    @Override
    public void buildCamera() {
        LEICACamera leicaCamera = new LEICACamera();
        huawei.setCamera(leicaCamera);
    }

    @Override
    public void buildScreen() {
        WaterDropletScreen waterDropletScreen = new WaterDropletScreen();
        huawei.setScreen(waterDropletScreen);
    }

    @Override
    public void buildCPU() {
        KirinCPU kirinCPU = new KirinCPU();
        huawei.setCpu(kirinCPU);
    }

    @Override
    public void buildGPU() {
        NVIDIAGPU nvidiaGPU = new NVIDIAGPU();
        huawei.setGpu(nvidiaGPU);
    }

    @Override
    public Phone getResult() {
        return huawei;
    }
}

//指挥者
class PhoneDirector{

    //可以个性化定制(多写几个指挥方法)
    public void construct(PhoneBuilder phoneBuilder){
        phoneBuilder.buildPhone();
        phoneBuilder.buildCamera();
        phoneBuilder.buildScreen();
        phoneBuilder.buildCPU();
        phoneBuilder.buildGPU();
    }

}
