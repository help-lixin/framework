package help.lixin.framework.sleuth;

import help.lixin.framework.sleuth.predicate.SpanPredicate;
import org.junit.Assert;
import org.junit.Test;
import zipkin2.Endpoint;
import zipkin2.Span;

import java.util.ArrayList;
import java.util.List;

public class SpanPredicateTest {
    @Test
    public void testSpanPredicate(){
        SpanPredicate spanPredicate1 = new SpanPredicate<Span>(){
            @Override
            public boolean test(Span span) {
                return false;
            }
        };

        SpanPredicate spanPredicate2 = new SpanPredicate<Span>(){
            @Override
            public boolean test(Span span) {
                return false;
            }
        };

        List<SpanPredicate> spanPredicates  = new ArrayList<>();
        spanPredicates.add(spanPredicate1);
        spanPredicates.add(spanPredicate2);

        Span span = null;
        long count = spanPredicates.stream().filter(spanPredicate->spanPredicate.test(span)).count();
        Assert.assertEquals(2,count);
    }
}
