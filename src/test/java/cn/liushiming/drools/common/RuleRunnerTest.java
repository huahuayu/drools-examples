package cn.liushiming.drools.common;

import cn.liushiming.drools.service.RuleRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author shiming
 * @Date 2020/3/26 5:26 PM
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest()
public class RuleRunnerTest {
    @Autowired
    private RuleRunner ruleRunner;

    @Test
    public void customDateCompare() throws ParseException, URISyntaxException {
        Map<String, Object> facts = new HashMap<>();
        Date dateVar = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-26");
        facts.put("dateVar", dateVar);
        ruleRunner.run("rules/date_compare.drl", facts);
    }

    @Test
    public void customNumberCompare() throws URISyntaxException {
        Map<String, Object> facts = new HashMap<>();
        Double numA = new Double("10.00011D");
        facts.put("numA", numA);
        ruleRunner.run("rules/number_compare.drl", facts);
    }
}