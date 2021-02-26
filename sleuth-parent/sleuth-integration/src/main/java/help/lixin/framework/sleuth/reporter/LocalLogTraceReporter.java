package help.lixin.framework.sleuth.reporter;

import help.lixin.framework.sleuth.sender.LocalLogCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

public class LocalLogTraceReporter implements Reporter<Span> {
    private Logger logger = LoggerFactory.getLogger(LocalLogTraceReporter.class);

    @Override
    public void report(Span span) {
        StringBuilder spans = new StringBuilder();
        spans.append("[").append(span.toString()).append("]");
        logger.info(spans.toString());
    }
}
