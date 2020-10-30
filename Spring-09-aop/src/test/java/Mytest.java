import com.chen.service.UserService;
import com.chen.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mytest {
    @Test
    public void Test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //动态代理，代理的是接口！
        UserService userService = (UserService) context.getBean("UserService");

        userService.add();
    }
}
