package com.nowcoder.community;

import com.nowcoder.community.util.SensitiverFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiverTests {

    @Autowired
    private SensitiverFilter sensitiverFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里可以赌$博，可以嫖娼$，可以吸，可以票，哈哈哈！";
        String result = sensitiverFilter.filter(text);
        System.out.println(result);

    }

}
