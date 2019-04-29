package rem.hw14.web;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessor {
    private static final String TEMPLATE_DIR = "/template/";
    private final Configuration templateConfiguration;

    public TemplateProcessor() {
        templateConfiguration = new Configuration(Configuration.VERSION_2_3_28);
        templateConfiguration.setClassForTemplateLoading(getClass(), TEMPLATE_DIR);
        templateConfiguration.setDefaultEncoding("UTF-8");
    }

    public String getTemplatePage(String filename, Map<String, Object> model) throws IOException {
        try(Writer writer = new StringWriter()) {
            Template template = templateConfiguration.getTemplate(filename);
            template.process(model, writer);
            return writer.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
