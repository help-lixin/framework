package help.lixin.framework.sleuth.reporter;

import help.lixin.framework.sleuth.predicate.SpanPredicate;
import help.lixin.framework.sleuth.sender.LocalLogCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

import java.util.List;

public class LocalLogTraceReporter implements Reporter<Span> {

    private List<SpanPredicate<Span>> spanPredicates;

    private Logger logger = LoggerFactory.getLogger(LocalLogTraceReporter.class);

    public LocalLogTraceReporter(List<SpanPredicate<Span>> spanPredicates) {
        this.spanPredicates = spanPredicates;
    }

    @Override
    public void report(Span span) {
        if (null != spanPredicates && null != span) {
            long count = spanPredicates.stream().filter(spanPredicate -> spanPredicate.test(span)).count();
            if (count == 0 ) { //0:false,代表没有要过滤的,应该要打印日志
                StringBuilder spans = new StringBuilder();
                spans.append("[").append(span.toString()).append("]");
                logger.info(spans.toString());
            }
        }
    }
}
