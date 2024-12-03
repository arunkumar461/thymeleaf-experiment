package com.moj.thymeleafexperiment;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class ButtonElementTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "button";
    private static final int PRECEDENCE = 1000;

    public ButtonElementTagProcessor() {
        super(TemplateMode.HTML, "govUk", TAG_NAME, true, null, false, PRECEDENCE);
    }


    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {

        String name = tag.getAttributeValue("name");

        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();

        model.add(modelFactory.createOpenElementTag("button", "class", "govuk-button"));
        model.add(modelFactory.createText(HtmlEscape.escapeHtml5(name)));
        model.add(modelFactory.createCloseElementTag("button"));

        structureHandler.replaceWith(model, false);
    }
}
