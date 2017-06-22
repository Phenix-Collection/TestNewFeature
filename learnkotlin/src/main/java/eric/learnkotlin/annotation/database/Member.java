package eric.learnkotlin.annotation.database;

/**
 * Created by wenjun.zhong on 2017/6/13.
 */

@DBTable(name = "MEMBER")
public class Member {
    @SQLString(30)
    String firstName;

    @SQLString(50)
    String lastName;

    @SQLInteger
    Integer age;

    @SQLString(value = 30, constraints = @Constraints(primaryKey = true))
    String handler;

    static int memberCount;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getHandler() {
        return handler;
    }

    @Override
    public String toString() {
        return handler;
    }
}
