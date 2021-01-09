package com.huanxi.music;

import com.huanxi.music.music.kuwo.KuwoService;
import com.huanxi.music.music.kuwo.vo.SearchKeyVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author huanxi
 * @version 1.0
 * @date 2020/12/31 1:31 下午
 */
@SpringBootTest
public class KuwoTest {
    @Resource
    KuwoService kuwoService;
    @Test
    public void test() {
        SearchKeyVo z = kuwoService.getSearchKey("z");
        System.out.println(z);

    }
}
