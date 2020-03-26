package cn.liushiming.drools.service;

import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Description
 * @Author shiming
 * @Date 2020/3/26 5:06 PM
 * @Version 1.0
 **/
@Component
public class RuleRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleRunner.class);

    public KieBase createKieBaseFromDrl(String drlStr) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drlStr, ResourceType.DRL);
        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.WARNING, Message.Level.
                ERROR)) {
            List<Message> messages = results.getMessages(Message.
                    Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                LOGGER.info("Error: {}", message.getText());
            }
            throw new IllegalArgumentException("invalid drl rule");
        }
        return kieHelper.build();
    }

    public KieSession createKieSessionFromDrl(String drlStr) {
        return createKieBaseFromDrl(drlStr).newKieSession();
    }

    public void run(String rulePath, Map<String, Object> facts) throws URISyntaxException {
        KieSession kieSession = createKieSessionFromDrl(readResourceFile(rulePath));
        kieSession.insert(facts);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    /**
     * read resource file to string
     *
     * @param path path for resource file
     * @return
     * @throws URISyntaxException
     */
    private String readResourceFile(String path) throws URISyntaxException {
        Assert.notNull(path, "path is empty");
        URL res = getClass().getClassLoader().getResource(path);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(absolutePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            LOGGER.error("read file: {} error: {}", path, e.getMessage());
        }
        return contentBuilder.toString();
    }

}
