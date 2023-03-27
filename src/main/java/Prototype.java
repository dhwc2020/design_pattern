import java.io.*;
import java.util.Objects;
import java.util.Optional;

/**
 *  原型模式
 *      优点: 1.通过克隆快速复刻一个对象, 其效率比构造方法构建高
 *
 *      缺点: 1.由于其内部实现是采用内存复制的原理, 因此不需要调用构造函数, 缺乏一定的约束
 */
public class Prototype {
    public static void main(String[] args) {
        Mouse mouse = new Mouse();
        Mouse clone = mouse.clone();
        Mouse deepClone = mouse.deepClone();
        System.out.println(Objects.equals(mouse.getKey(), clone.getKey()));
        System.out.println(Objects.equals(mouse.getKey(), deepClone.getKey()));
    }
}

class Mouse implements Cloneable, Serializable{
    private Key key = new Key();

    public Key getKey() {
        return key;
    }

    //浅克隆
    @Override
    public Mouse clone(){
        try {
            return (Mouse) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //深克隆(通过流处理实现)
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Mouse deepClone(){
        File file = null;
        try {
            file = File.createTempFile("mouse_copy", ".tmp");
            ObjectOutput output = new ObjectOutputStream(new FileOutputStream(file));
            ObjectInput input = new ObjectInputStream(new FileInputStream(file));

            this.writeObject(output);
            Mouse mouse = this.readObject(input);

            output.close();
            input.close();
            return mouse;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            Optional.ofNullable(file).ifPresent(File::delete);
        }
        return null;
    }

    private void writeObject(ObjectOutput out) throws IOException {
        out.writeObject(this);
        out.flush();
    }

    private Mouse readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        return (Mouse) in.readObject();
    }
}

class Key implements Serializable{}