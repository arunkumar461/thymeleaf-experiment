package com.moj.thymeleafexperiment;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class DetailsElementTagProcessor extends AbstractElementTagProcessor {


    private static final String TAG_NAME = "details";
    private static final int PRECEDENCE = 1000;

    public DetailsElementTagProcessor() {
        super(TemplateMode.HTML, "govUk", TAG_NAME, true, null, false, PRECEDENCE);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {

        String summaryText = tag.getAttributeValue("summaryText");
        String text = tag.getAttributeValue("text");

        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model =
                modelFactory.parse(
                        context.getTemplateData(),
                        "<details class=\"govuk-details\">\n" +
                                "  <summary class=\"govuk-details__summary\">\n" +
                                "    <span class=\"govuk-details__summary-text\">" +
                                summaryText +
                                "    </span>\n" +
                                "  </summary>\n" +
                                "  <div class=\"govuk-details__text\">\n" +
                                text +
                                "  </div>\n" +
                                "</details>");

        structureHandler.replaceWith(model, false);
    }
}
