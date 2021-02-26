package help.lixin.framework.sleuth.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin2.Call;
import zipkin2.Callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 最终记录日志
 */
public class LocalLogCall extends Call<Void> {
    private Logger logger = LoggerFactory.getLogger(LocalLogCall.class);

    private List<byte[]> encodedSpans;

    public LocalLogCall(List<byte[]> encodedSpans) {
        super();
        this.encodedSpans = encodedSpans;
    }

    @Override
    public Void execute() throws IOException {
        if (encodedSpans.size() > 0) {
            // 把内容转换成数组形式.
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            for (int i = 0, length = encodedSpans.size(); i < length; ) {
                byte[] span = encodedSpans.get(i++);
                try {
                    String spanString = new String(span, "UTF-8");
                    if (i < length) {
                        builder.append(spanString);
                        builder.append(",");
                    }
                } catch (UnsupportedEncodingException ignore) {
                    logger.warn("byte conver to string error:[{}]", ignore);
                }
            }
            builder.append("]");
            if (builder.length() > 0) {
                logger.info(builder.toString());
            }
            // 销毁对象
            builder = null;
        }
        return null;
    }

    @Override
    public void enqueue(Callback<Void> callback) {
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<Void> clone() {
        return null;
    }
}
