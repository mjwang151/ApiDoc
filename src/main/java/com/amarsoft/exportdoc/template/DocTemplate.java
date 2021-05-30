package com.amarsoft.exportdoc.template;

import freemarker.template.Template;

import java.io.IOException;

public interface DocTemplate {
    /**
     * 获取模板
     * @return
     */
    Template getTemplate() throws IOException;
}
