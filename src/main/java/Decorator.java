/**
 *  装饰模式
 *      优点: 1.将装饰对象与被装饰对象解耦
 *           2.适用于对基本功能增强, 且增强种类多, 组合不固定的情况
 *           3.对于装饰对象来说是容易扩展的
 *
 *      缺点: 1.装饰层数过多, 会导致阅读和排错困难
 */
public class Decorator {
    public static void main(String[] args) {
        //一层装饰
        AbstractGameRole role = new SunglassesDecorator(new GameRole());
        role.show();

        //二层装饰
        AbstractGameRole bodyRole = new ShirtDecorator(role);
        bodyRole.show();

        //三层装饰
        AbstractGameRole allBodyRole = new SkirtDecorator(bodyRole);
        allBodyRole.show();
    }
}

abstract class AbstractGameRole{
    public abstract void show();
}

class GameRole extends AbstractGameRole{

    @Override
    public void show() {
        System.out.println("默认装扮");
    }
}

//游戏角色装饰器
abstract class AbstractGameRoleDecorator extends AbstractGameRole{
    protected AbstractGameRole gameRole;

    public AbstractGameRoleDecorator(AbstractGameRole gameRole) {
        this.gameRole = gameRole;
    }


}

//头部装饰
abstract class AbstractGameRoleHeadDecorator extends AbstractGameRoleDecorator{
    public AbstractGameRoleHeadDecorator(AbstractGameRole gameRole) {
        super(gameRole);
    }
}

class SunglassesDecorator extends AbstractGameRoleHeadDecorator{
    public SunglassesDecorator(AbstractGameRole gameRole) {
        super(gameRole);
    }

    @Override
    public void show() {
        super.gameRole.show();
        System.out.println("头部装饰: 墨镜装扮");
    }
}

//上身装饰
abstract class AbstractGameRoleUpperBodyDecorator extends AbstractGameRoleDecorator{
    public AbstractGameRoleUpperBodyDecorator(AbstractGameRole gameRole) {
        super(gameRole);
    }
}

class ShirtDecorator extends AbstractGameRoleUpperBodyDecorator{
    //private String imageUrl;

    public ShirtDecorator(AbstractGameRole gameRole) {
        super(gameRole);
    }

    @Override
    public void show() {
        super.gameRole.show();
        System.out.println("上身装饰: 衬衫装扮");
    }
}

//下身装扮
abstract class AbstractGameRoleLowerBodyDecorator extends AbstractGameRoleDecorator{
    public AbstractGameRoleLowerBodyDecorator(AbstractGameRole gameRole) {
        super(gameRole);
    }
}

class SkirtDecorator extends AbstractGameRoleLowerBodyDecorator{
    //private String imageUrl;

    public SkirtDecorator(AbstractGameRole gameRole) {
        super(gameRole);
    }

    @Override
    public void show() {
        super.gameRole.show();
        System.out.println("下身装扮: 裙子装扮");
    }
}