package top.cusie.web.hook.filter;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import top.cusie.api.model.context.ReqInfoContext;
import top.cusie.core.utill.CrossUtil;
import top.cusie.core.utill.IpUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author Cusie
 * @date 2024/10/31
 */

@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "selfProcessBeforeFilter")
/**
 * ReqRecordFilter是一个实现Filter接口的类，用于在请求处理前和处理后执行特定的操作。
 * 它主要负责记录请求信息和构建跨域响应头。
 */
public class ReqRecordFilter implements Filter{
    // 定义一个专门用于记录请求日志的Logger
    private static Logger REQ_LOG = LoggerFactory.getLogger("req");

    /**
     * 初始化过滤器配置。
     * @param filterConfig 过滤器配置对象，提供过滤器初始化参数的访问
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * 执行过滤操作，记录请求开始时间，初始化请求信息，构建跨域响应头，并记录请求日志。
     * @param servletRequest Servlet请求对象，用于访问请求内容
     * @param servletResponse Servlet响应对象，用于访问响应内容
     * @param filterChain 过滤链，用于将请求传递给下一个过滤器或目标资源
     * @throws IOException 如果在执行过程中发生I/O错误
     * @throws ServletException 如果在执行过程中发生Servlet异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = null;
        try {
            request = this.initReqInfo((HttpServletRequest) servletRequest);
            CrossUtil.buildCors(request, (HttpServletResponse) servletResponse);
            filterChain.doFilter(request, servletResponse);
        } finally {
            buildRequestLog(ReqInfoContext.getReqInfo(), request, System.currentTimeMillis() - start);
            ReqInfoContext.clear();
        }
    }

    /**
     * 销毁过滤器，释放资源。
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * 初始化请求信息对象，包括主机、路径、引用页、客户端IP、UUID和用户代理等信息。
     * @param request HTTP请求对象，包含请求的详细信息
     * @return 返回经过包装的HTTP请求对象
     */
    private HttpServletRequest initReqInfo(HttpServletRequest request) {
        try {
            ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
            reqInfo.setHost(request.getHeader("host"));
            reqInfo.setPath(request.getPathInfo());
            reqInfo.setReferer(request.getHeader("referer"));
            reqInfo.setClientIp(IpUtil.getClientIp(request));
            reqInfo.setUuid(request.getHeader("x-uuid"));
            reqInfo.setUserAgent(request.getHeader("User-Agent"));

            request = this.wrapperRequest(request, reqInfo);
            ReqInfoContext.addReqInfo(reqInfo);

            // fixme 根据x-uuid获取对应的用户信息
            reqInfo.setUserId(0L);
        } catch (Exception e) {
            log.error("init reqInfo error!", e);
        }

        return request;
    }

    /**
     * 构建并记录请求日志，包括请求方法、引用页、客户端IP、用户代理、UUID、用户ID、请求URI、请求参数、请求负载和耗时等信息。
     * @param req 请求信息对象，包含请求的详细信息
     * @param request HTTP请求对象，用于获取请求URI和查询字符串
     * @param costTime 请求处理耗时，单位为毫秒
     */
    private void buildRequestLog(ReqInfoContext.ReqInfo req, HttpServletRequest request, long costTime) {
        // fixme 过滤不需要记录请求日志的场景
        if (request == null
                || request.getRequestURI().endsWith("css")
                || request.getRequestURI().endsWith("js")
                || request.getRequestURI().endsWith("png")
                || request.getRequestURI().endsWith("ico")) {
            return;
        }

        StringBuilder msg = new StringBuilder();
        msg.append("method=").append(request.getMethod()).append("; ");
        if (StringUtils.isNotBlank(req.getReferer())) {
            msg.append("referer=").append(URLDecoder.decode(req.getReferer())).append("; ");
        }
        msg.append("remoteIp=").append(req.getClientIp());
        msg.append("; agent=").append(req.getUserAgent());

        if (StringUtils.isNotBlank(req.getUuid())) {
            // 打印设备信息
            msg.append("; uuid=").append(req.getUuid());
        }

        if (req.getUserId() != null) {
            // 打印用户信息
            msg.append("; user=").append(req.getUserId());
        }

        msg.append("; uri=").append(request.getRequestURI());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            msg.append('?').append(URLDecoder.decode(request.getQueryString()));
        }

        msg.append("; payload=").append(req.getPayload());
        msg.append("; cost=").append(costTime);
        REQ_LOG.info("{}", msg);
    }

    /**
     * 包装HTTP请求对象，仅对POST请求进行处理，读取并保存请求体内容。
     * @param request HTTP请求对象，包含请求的详细信息
     * @param reqInfo 请求信息对象，用于保存请求体内容
     * @return 返回经过包装的HTTP请求对象
     */
    private HttpServletRequest wrapperRequest(HttpServletRequest request, ReqInfoContext.ReqInfo reqInfo) {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            return request;
        }

        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        reqInfo.setPayload(requestWrapper.getBodyString());
        return requestWrapper;
    }
}

