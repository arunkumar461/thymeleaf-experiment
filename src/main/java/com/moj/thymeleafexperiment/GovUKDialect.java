package com.moj.thymeleafexperiment;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.Set;

public class GovUKDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "GovUKDialect";

    protected GovUKDialect() {
        super(DIALECT_NAME, "govuk", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        return Set.of(new ButtonElementTagProcessor(), new DetailsElementTagProcessor());
    }
}