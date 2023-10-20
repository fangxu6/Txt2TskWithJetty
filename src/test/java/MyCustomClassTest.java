import customer.handler.CustomExceptionHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * className: MyCustomClassTest
 * package: PACKAGE_NAME
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/10/15 21:28
 */
@SpringBootTest
public class MyCustomClassTest {

    @Autowired
    private CustomExceptionHandler myCustomClass; // 替换为你的自定义类的类型

    @Test
    public void testMyCustomClass() {
        // 如果能够成功注入，说明自定义类被正确扫描到
        // 可以在这个测试方法中添加其他测试逻辑
    }
}
