/**
 * 我的栈 实现IStack接口
 */
public class MyStack<E> implements IStack<E> {
	private E[] elements;// 元素数组
	private int size;// 长度
	private static final int INITIAL_CAPACITY = 30;// 初始化栈大小

	/**
	 * 默认大小的构造方法
	 */
	@SuppressWarnings("unchecked")
	public MyStack() {
		elements = (E[]) new Object[INITIAL_CAPACITY];// java不支持泛型数组
	}

	/**
	 * 指定大小的构造方法
	 * 
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public MyStack(int capacity) {
		elements = (E[]) new Object[capacity];
	}

	/**
	 * 判断是否为空
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * 访问栈顶元素
	 */
	public E peek() {
		if (size == 0) {
			throw new java.util.EmptyStackException();
		}
		return elements[size - 1];
	}

	/**
	 * 删除当前栈顶元素
	 */
	public E pop() {
		if (size == 0) {
			throw new java.util.EmptyStackException();
		}
		E element = elements[--size];
		elements[size] = null;
		return element;
	}

	/**
	 * 新的元素入栈
	 */
	@SuppressWarnings("unchecked")
	public void push(Object element) {
		if (size == elements.length) {
			resize();
		}
		elements[size++] = (E) element;
	}

	/**
	 * 返回栈中元素的个数
	 */
	public int size() {
		return size;
	}

	/**
	 * 重新分配大小，长度翻倍
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		assert size == elements.length;
		Object[] a = new Object[2 * size];
		System.arraycopy(elements, 0, a, 0, size);
		elements = (E[]) a;
	}
}
