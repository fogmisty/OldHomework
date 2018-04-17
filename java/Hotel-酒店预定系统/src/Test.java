//测试类：
public class Test {
    public static void main(String[] args){
        Hotel ht = new Hotel();
        ht.print();
        //订房
        ht.order("301");
        ht.print();
        //退房
        ht.checkout("301");
        ht.print();
    }
}