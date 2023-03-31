import java.util.NoSuchElementException;

/**
 *  迭代器模式
 *      优点: 1.隔离对容器的直接操作, 为容器提供一个顺序访问的方式
 *           2.为遍历提供一套统一的接口
 *
 *      缺点: 1.基本上用不到, 编程语言已经为我们提供好了实现, 除非自定义新容器, 且底层使用最基本的数据结构
 */
public class Iterator {
    
    @SuppressWarnings("all")
    public static void main(String[] args) {
        IContainer<Integer> containers = new Container<>(10);
        containers.add(1);
        containers.add(4);
        containers.add(3);
        containers.add(2);

        IContainerIterator<Integer> iterator = containers.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

//抽象容器接口
interface IContainer<T> extends Iterable<T>{
    T get(int index);
    boolean add(T t);
    boolean remove();
    int size();

    @Override
    IContainerIterator<T> iterator();
}

//抽象迭代器接口
interface IContainerIterator<T> extends java.util.Iterator<T> {}

//具体容器
class Container<T> implements IContainer<T> {
    private Object[] elements;
    private int capacity;
    private int size;

    public Container(int capacity) {
        if(capacity < 1) throw new IllegalArgumentException("容量不能小于1");
        
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException("下标越界: " + index);
        return (T) this.elements[index];
    }

    @Override
    public boolean add(T t) {
        if(this.size == this.capacity){
            this.grow();
        }
        
        this.elements[this.size++] = t;
        return true;
    }

    //简单处理, 不考虑越界
    private void grow(){
        this.capacity = this.capacity << 1;
        Object[] newElements = new Object[this.capacity];
        System.arraycopy(this.elements, 0, newElements, 0, this.size);
        this.elements = newElements;
    }
    
    @Override
    public boolean remove() {
        if (this.size == 0) throw new IndexOutOfBoundsException("下标越界: -1");
        
        this.size--;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public IContainerIterator<T> iterator() {
        return new ContainerIterator<>(this);
    }
}

//具体迭代器
class ContainerIterator<T> implements IContainerIterator<T>{
    private IContainer<T> container;
    private int cursor = 0;

    public ContainerIterator(IContainer<T> container) {
        this.container = container;
    }

    @Override
    public boolean hasNext() {
        return this.cursor < this.container.size();
    }

    @Override
    public T next() {
        int size = this.container.size();
        if(this.cursor >= size) throw new NoSuchElementException();
        T t = container.get(this.cursor);
        this.cursor++;
        return t;
    }
}