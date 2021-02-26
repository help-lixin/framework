package help.lixin.framework.sleuth.sender;

import help.lixin.framework.sleuth.autoconfig.SleuthAutoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zipkin2.Call;
import zipkin2.codec.Encoding;
import zipkin2.reporter.Sender;

import java.util.List;

public class LocalLogFileSender extends Sender {
    private Logger logger = LoggerFactory.getLogger(LocalLogFileSender.class);
    private int messageMaxBytes = 100;
    private Encoding encoding = Encoding.JSON;

    public void setEncoding(Encoding encoding) {
        if (null != encoding) {
            this.encoding = encoding;
        }
    }

    public void setMessageMaxBytes(int messageMaxBytes) {
        if (messageMaxBytes >= 0) {
            this.messageMaxBytes = messageMaxBytes;
        } else {
            messageMaxBytes = 100;
        }
    }

    @Override
    public Encoding encoding() {
        return encoding;
    }

    @Override
    public int messageMaxBytes() {
        return messageMaxBytes;
    }

    @Override
    public int messageSizeInBytes(List<byte[]> encodedSpans) {
        return encoding.listSizeInBytes(encodedSpans);
    }

    @Override
    public Call<Void> sendSpans(List<byte[]> encodedSpans) {
        LocalLogCall localLogCall = new LocalLogCall(encodedSpans);
        return localLogCall;
    }
}
