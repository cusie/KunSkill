package top.cusie.web.hook.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * post 流数据封装，避免因为打印日志导致请求参数被提前消费
 *
 * @author Cusie
 * @date 2024/10/30
 */


/**
 * 自定义的 HttpServletRequestWrapper，用于捕获并提供对请求体的访问。
 * 该包装类主要用于多次读取请求体或以非标准方式访问请求体。
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    // HTTP 方法列表，这些方法可能包含请求体
    private static final List<String> POST_METHOD = Arrays.asList("POST", "PUT");
    // 日志记录器，用于记录日志信息
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 存储的请求体，字节数组格式
    private final byte[] body;
    // 存储的请求体，字符串格式
    private final String bodyString;

    /**
     * 构造 BodyReaderHttpServletRequestWrapper 实例。
     * 如果请求满足某些条件，则读取并存储请求体。
     *
     * @param request 原始的 HttpServletRequest 对象
     */
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);

        if (POST_METHOD.contains(request.getMethod()) && !isMultipart(request) && !isBinaryContent(request)) {
            // 读取请求体并转换为字符串
            bodyString = getBodyString(request);
            body = bodyString.getBytes(StandardCharsets.UTF_8);
        } else {
            bodyString = null;
            body = null;
        }
    }

    /**
     * 获取请求体的 BufferedReader。
     *
     * @return BufferedReader 对象
     * @throws IOException 如果发生 I/O 错误
     */
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 获取请求体的 InputStream。
     *
     * @return ServletInputStream 对象
     * @throws IOException 如果发生 I/O 错误
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (body == null) {
            return super.getInputStream();
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    /**
     * 检查请求是否包含有效负载。
     *
     * @return 如果请求体不为空则返回 true，否则返回 false
     */
    public boolean hasPayload() {
        return bodyString != null;
    }

    /**
     * 获取请求体的字符串表示。
     *
     * @return 请求体的字符串表示
     */
    public String getBodyString() {
        return bodyString;
    }

    /**
     * 从 HttpServletRequest 中读取请求体并返回字符串表示。
     *
     * @param request 原始的 HttpServletRequest 对象
     * @return 请求体的字符串表示
     */
    private String getBodyString(HttpServletRequest request) {
        BufferedReader br;
        try {
            br = request.getReader();
        } catch (IOException e) {
            logger.warn("获取 Reader 失败", e);
            return "";
        }

        String str;
        StringBuilder body = new StringBuilder();
        try {
            while ((str = br.readLine()) != null) {
                body.append(str);
            }
        } catch (IOException e) {
            logger.warn("读取行失败", e);
        }

        try {
            br.close();
        } catch (IOException e) {
            logger.warn("关闭 Reader 失败", e);
        }

        return body.toString();
    }

    /**
     * 检查请求内容是否为二进制内容。
     *
     * @param request 原始的 HttpServletRequest 对象
     * @return 如果请求内容类型为 image、video 或 audio，则返回 true，否则返回 false
     */
    private boolean isBinaryContent(final HttpServletRequest request) {
        return request.getContentType() != null &&
                (request.getContentType().startsWith("image") || request.getContentType().startsWith("video") ||
                        request.getContentType().startsWith("audio"));
    }

    /**
     * 检查请求内容是否为多部分表单数据。
     *
     * @param request 原始的 HttpServletRequest 对象
     * @return 如果请求内容类型为 multipart/form-data，则返回 true，否则返回 false
     */
    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }
}

