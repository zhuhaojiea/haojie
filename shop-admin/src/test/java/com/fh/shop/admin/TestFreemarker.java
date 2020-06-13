package com.fh.shop.admin;


import com.fh.shop.admin.po.brand.Brand;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFreemarker {

    @Test
    public void testBuild() {
        Configuration configuration = new Configuration();
        // 设置编码
        configuration.setDefaultEncoding("utf-8");
        // 指定模板文件所在的文件夹 classpath:/template
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
        try {
            // 获取模板
            Template template = configuration.getTemplate("t1.html");
            // 构建数据
            Map result = new HashMap();
            result.put("userName", "zhangsan");
            result.put("age", 30);
            List<Brand> brandList = new ArrayList<>();

            Brand b1 = new Brand();
            b1.setId(1L);
            b1.setBrandName("小米");

            Brand b2 = new Brand();
            b2.setId(2L);
            b2.setBrandName("华为");

            brandList.add(b1);
            brandList.add(b2);
            result.put("brands", brandList);
            // 将模板和数据融合
            StringWriter stringWriter = new StringWriter();
            template.process(result, stringWriter);
            // 输出结果
            System.out.println(stringWriter.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
