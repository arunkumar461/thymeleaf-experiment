package com.moj.thymeleafexperiment;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashMap;
import java.util.Map;

public class ButtonElementTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "button";
    private static final int PRECEDENCE = 900;

    public ButtonElementTagProcessor() {
        super(TemplateMode.HTML, "govuk", TAG_NAME, true, null, false, PRECEDENCE);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {

        Map<String, String> attributes = parseAttributes(context, tag);
        String classNames = buildClassNames(attributes);
        String commonAttributes = buildCommonAttributes(classNames, attributes);
        String buttonAttributes = buildButtonAttributes(attributes);

        String html = attributes.containsKey("href")
                ? buildAnchorHtml(attributes, commonAttributes)
                : buildButtonHtml(attributes, commonAttributes, buttonAttributes);

        replaceElementWithHtml(context, structureHandler, html);
    }

    private Map<String, String> parseAttributes(ITemplateContext context, IProcessableElementTag tag) {
        Map<String, String> attributes = tag.getAttributeMap();
        Map<String, String> resolvedAttributes = new HashMap<>();
        IStandardExpressionParser parser = StandardExpressions.getExpressionParser(context.getConfiguration());

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith("th:")) {
                IStandardExpression expression = parser.parseExpression(context, value);
                resolvedAttributes.put(key.replace("th:", ""), (String) expression.execute(context));
            } else {
                resolvedAttributes.put(key, value);
            }
        }

        return resolvedAttributes;
    }

    private String buildClassNames(Map<String, String> attributes) {
        String classNames = "govuk-button";
        if (attributes.containsKey("classes")) {
            classNames += " " + attributes.get("classes");
        }
        return classNames;
    }

    private String buildCommonAttributes(String classNames, Map<String, String> attributes) {
        StringBuilder commonAttributes = new StringBuilder();
        commonAttributes.append("class=\"").append(classNames).append("\" data-module=\"govuk-button\"");
        if (attributes.containsKey("id")) {
            commonAttributes.append(" id=\"").append(attributes.get("id")).append("\"");
        }
        return commonAttributes.toString();
    }

    private String buildButtonAttributes(Map<String, String> attributes) {
        StringBuilder buttonAttributes = new StringBuilder();
        if (attributes.containsKey("name")) {
            buttonAttributes.append(" name=\"").append(attributes.get("name")).append("\"");
        }
        if (attributes.containsKey("disabled")) {
            buttonAttributes.append(" disabled aria-disabled=\"true\"");
        }
        if (attributes.containsKey("preventDoubleClick")) {
            buttonAttributes.append(" data-prevent-double-click=\"").append(attributes.get("preventDoubleClick")).append("\"");
        }
        return buttonAttributes.toString();
    }

    private String buildAnchorHtml(Map<String, String> attributes, String commonAttributes) {
        return new StringBuilder()
                .append("<a href=\"").append(attributes.getOrDefault("href", "#"))
                .append("\" role=\"button\" draggable=\"false\" ").append(commonAttributes).append(">")
                .append(attributes.getOrDefault("text", ""))
                .append("</a>")
                .toString();
    }

    private String buildButtonHtml(Map<String, String> attributes, String commonAttributes, String buttonAttributes) {
        return new StringBuilder()
                .append("<button type=\"").append(attributes.getOrDefault("type", "submit"))
                .append("\" ").append(buttonAttributes).append(" ").append(commonAttributes).append(">")
                .append(attributes.getOrDefault("text", ""))
                .append("</button>")
                .toString();
    }

    private void replaceElementWithHtml(ITemplateContext context, IElementTagStructureHandler structureHandler, String html) {
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.parse(context.getTemplateData(), html);
        structureHandler.replaceWith(model, false);
    }
}

