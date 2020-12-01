package com.customized.libs.core.handler;

import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.customized.libs.core.controller.BaseController;
import com.customized.libs.core.exception.CommonErrCode;
import com.customized.libs.core.exception.CommonException;
import com.customized.libs.core.model.CommResp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.NoPermissionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author yan
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 添加关键字以"|"分割("数据库|异常")
     */
    private static final Pattern ERROR_MSG_KEYWORD_REGEX = Pattern.compile("(网络超时或未知异常|服务器内部错误|数据库|Exception|服务调用出错|Service Not Exists|Service Unavailable|Invoke Service Fail)");

    private static final String DEFAULT_ERROR_MSG = "系统繁忙，请稍后重试";

    private static final String NOT_FOUND = "not_found";

    private static final String HANDLER_METHOD_NOT_FOUND = "handler_method_not_found";

    private static final String HTTP_METHOD_NOT_SUPPORTED = "http_method_not_supported";

    private static final String MEDIA_TYPE_NOT_SUPPORTED = "media_type_not_supported";

    private static final String MEDIA_TYPE_NOT_ACCEPTABLE = "media_type_not_acceptable";

    private static final String REQUEST_PARAM_REQUIRED = "request_param_required";

    private static final String REQUEST_PART_REQUIRED = "request_part_required";

    private static final String REQUEST_HEADER_REQUIRED = "request_header_required";

    private static final String HTTP_MESSAGE_NOT_READABLE = "http_message_not_readable";

    private static final String CONVERSION_NOT_SUPPORTED = "conversion_not_supported";

    private static final String METHOD_ARGUMENT_NOT_VALID = "method_argument_not_valid";

    private static final String INTERNAL_SERVER_ERROR = "internal_server_error";

    private static final String FORBIDDEN = "permission_invalid";

    /**
     * 无效的 URL 请求
     * 找不到处理该 URL 的 Controller
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        CommResp entity = new CommResp(NOT_FOUND,
                "没有找到 [" + ex.getHttpMethod().toUpperCase() + " " + ex.getRequestURL() + "] 相应的处理器.");
        return getJsonResp(entity, HttpStatus.NOT_FOUND);
    }

    /**
     * 不支持的 Http Method
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        StringBuilder supportedMethods = new StringBuilder();
        if (ex.getSupportedMethods() != null && ex.getSupportedMethods().length > 0) {
            for (int i = 0; i < ex.getSupportedMethods().length; i++) {
                if (i != 0) {
                    supportedMethods.append(" | ");
                }
                supportedMethods.append(ex.getSupportedMethods()[i]);
            }
        }
        CommResp entity = new CommResp(HTTP_METHOD_NOT_SUPPORTED,
                "不支持的Http方法 [" + ex.getMethod().toUpperCase() + "], 请尝试 [" + supportedMethods.toString() + "].");
        return getJsonResp(entity, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 不支持的 Http media type
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        CommResp entity = new CommResp(MEDIA_TYPE_NOT_SUPPORTED,
                "不支持的Http媒体类型 [" + ex.getContentType().toString() + "].");
        return getJsonResp(entity, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        StringBuilder supportsList = new StringBuilder();
        if (ex.getSupportedMediaTypes() != null && ex.getSupportedMediaTypes().size() > 0) {
            for (int i = 0; i < ex.getSupportedMediaTypes().size(); i++) {
                if (i != 0) {
                    supportsList.append(" | ");
                }
                supportsList.append(ex.getSupportedMediaTypes().get(i).toString());
            }
        }
        CommResp entity = new CommResp(MEDIA_TYPE_NOT_ACCEPTABLE,
                "不可接受的Http媒体类型, 仅支持 [" + supportsList.toString() + "].");
        return getJsonResp(entity, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * URL 缺少必要的请求参数
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        CommResp entity = new CommResp(REQUEST_PARAM_REQUIRED,
                "URL参数 [" + ex.getParameterName() + "] 不能为空.");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    /**
     * Header 缺少必要的参数
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleServletRequestBindingException(ServletRequestBindingException ex) {
        CommResp entity = new CommResp(REQUEST_HEADER_REQUIRED, "HEADER参数不能为空.");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    /**
     * 缺少 Request Part
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        CommResp entity = new CommResp(REQUEST_PART_REQUIRED,
                "Request part [" + ex.getRequestPartName() + "] 不能为空.");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    /**
     * 请求体(Request Body)不可读, 或转换出错
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        CommResp entity = new CommResp(HTTP_MESSAGE_NOT_READABLE, ex.getMessage());
        log.error(">>> handleHttpMessageNotReadableException", ex);
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<CommResp> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> results = new HashMap<String, Object>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors != null) {
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError fieldError = fieldErrors.get(i);
                String value = ((fieldError.getRejectedValue() == null) ? "null" : fieldError.getRejectedValue().toString());
                String reason = ((fieldError.getDefaultMessage() == null) ? "" : fieldError.getDefaultMessage());
                String errorFieldMessage =
                        "被拒绝的值 [" + value + "], 原因 [" + reason + "].";
                results.put(fieldError.getField(), errorFieldMessage);
            }
        }
        CommResp entity = new CommResp(METHOD_ARGUMENT_NOT_VALID, "请求数据格式有误.");
        return getJsonResp(entity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoPermissionException.class})
    @ResponseBody
    public ResponseEntity<CommResp> handleNoPermissionException(NoPermissionException ex) {
        CommResp entity = new CommResp(FORBIDDEN, ex.getMessage());
        return getJsonResp(entity, HttpStatus.FORBIDDEN);
    }

    /**
     * String直接返回错误，会导致中文乱码，那么就说明ResponseEntity涉及到中文编码的问题，会自动处理响应数据
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BlockException.class)
    @ResponseBody
    public ResponseEntity<CommResp> sentinelBlockHandler(BlockException e) {
        AbstractRule rule = e.getRule();
        logger.warn("Blocked by Sentinel: {}", rule.toString());

        CommResp entity = new CommResp(FORBIDDEN, DEFAULT_ERROR_MSG);
        return getJsonResp(entity, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {CommonException.class})
    @ResponseBody
    public ResponseEntity<CommResp> handleCommonException(CommonException ex) {
        HttpStatus httpStatus;
        if (CommonErrCode.ARGS_INVALID.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (CommonErrCode.AUTH_TOKEN_INVALID.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if (CommonErrCode.NO_PERMISSION.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (CommonErrCode.NO_DATA_FOUND.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (CommonErrCode.REPEAT_REQUEST.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (CommonErrCode.BUSINESS.getCode().equals(ex.getErrCode())) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String errorMsg = ERROR_MSG_KEYWORD_REGEX.matcher(ex.getErrMsg()).find() ? DEFAULT_ERROR_MSG : ex.getErrMsg();
        CommResp entity = new CommResp(ex.getErrCode(), errorMsg);
        log.error(ex.getErrCode(), ex);
        return getJsonResp(entity, httpStatus);
    }

    @ExceptionHandler(value = {RuntimeException.class, Exception.class, Throwable.class})
    @ResponseBody
    public ResponseEntity<CommResp> handleException(Throwable th) {
        log.error("<<< Internal Exception", th);
        CommResp entity = new CommResp(INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MSG);
        return getJsonResp(entity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}