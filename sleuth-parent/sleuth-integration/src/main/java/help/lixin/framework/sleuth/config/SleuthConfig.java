package help.lixin.framework.sleuth.config;

import brave.sampler.CountingSampler;
import brave.sampler.Sampler;
import help.lixin.framework.sleuth.predicate.DefaultSpanPredicate;
import help.lixin.framework.sleuth.predicate.SpanPredicate;
import help.lixin.framework.sleuth.properties.CustomSamplerProperties;
import help.lixin.framework.sleuth.properties.SleuthLogProperties;
import help.lixin.framework.sleuth.reporter.LocalLogTraceReporter;
import help.lixin.framework.sleuth.sender.LocalLogFileSender;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SleuthConfig {

    @Autowired
    private SleuthLogProperties sleuthLogProperties;

    @Autowired
    private CustomSamplerProperties customSamplerProperties;

    @Autowired(required = false)
    private List<SpanPredicate<Span>> spanPredicates = new ArrayList<>();

    @Bean
    @ConditionalOnMissingBean
    public SpanPredicate<Span> defaultSpanPredicate(){
        return new DefaultSpanPredicate();
    }

    @Bean
    public Sampler sleuthTraceSampler() {
        return CountingSampler.create(customSamplerProperties.getRate());
    }

    @Bean
    public Reporter<zipkin2.Span> reporter() {
        LocalLogTraceReporter localLogTraceReporter = new LocalLogTraceReporter(spanPredicates);
        return localLogTraceReporter;
    }
}
