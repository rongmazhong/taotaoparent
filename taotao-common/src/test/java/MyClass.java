import java.util.List;

/**
 * 〈测试类〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/7
 */
public class MyClass {

    public List<BClass> getbList() {
        return bList;
    }

    public void setbList(List<BClass> bList) {
        this.bList = bList;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "bList=" + bList +
                '}';
    }

    public MyClass(List<BClass> bList) {
        this.bList = bList;
    }

    private List<BClass> bList;
}
