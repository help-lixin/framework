package help.lixin.framework.sleuth.predicate;

import zipkin2.Span;

public class DefaultSpanPredicate implements  SpanPredicate<Span>{
    @Override
    public boolean test(Span span) {
        boolean isEureakServerSpan = span.localEndpoint().serviceName().equalsIgnoreCase("eureka-server");
        return isEureakServerSpan;
    }
}
