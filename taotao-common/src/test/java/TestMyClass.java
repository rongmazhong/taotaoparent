import com.taotao.common.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/7
 */
public class TestMyClass {

    // 去除list中的子list的所有元素，并生成新的list。
    public static void main(String[] args) {
        List<BClass> newList = new ArrayList();
        BClass bClass;
        for (int i = 0; i < 10; i++) {
            bClass = new BClass(i, "name:" + i);
            newList.add(bClass);
        }
        List<MyClass> list = new ArrayList();
        list.add(new MyClass(newList));
        list.add(new MyClass(newList));
        List<BClass> myBclass = new ArrayList<>();
        List<BClass> myBCCclass = new ArrayList<>();
        list.stream().map(MyClass -> MyClass.getbList()).forEach(a -> {
            Iterator<BClass> iterator = a.iterator();
            while (iterator.hasNext()) {
                myBclass.add(iterator.next());
            }
        });
        list.stream().map(MyClass::getbList).forEach(ab -> myBCCclass.addAll(ab));
        String s = JsonUtils.objectToJson(myBclass);
        String sb = JsonUtils.objectToJson(myBCCclass);
        System.out.println(s);
        System.out.println(sb);
        System.out.println(s.equals(sb));
    }
}
