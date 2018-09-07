/**
 * 〈〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/7
 */
public class BClass {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BClass(Integer id) {
        this.id = id;
    }

    public BClass(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
