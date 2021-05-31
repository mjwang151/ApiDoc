package com.amarsoft.exportdoc.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 通用模板
 */
@Component("IconTemplate")
public class IconTemplate extends TemplateFactory{
    @Override
    public Template getTemplate() throws IOException {
        Configuration configuration = super.initConfiguration();
        return configuration.getTemplate("edsservice-icon.ftl");
    }
}
