package com.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.constant.CommonConstants;
import com.exception.BusinessInterfaceException;
import com.utils.R;

/**
 * 增强的controller，
 * 全局异常处理
 * 全局数据绑定
 * 全局数据预处理
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(value = BusinessInterfaceException.class)
	public ResponseEntity<R<String>> handlerBusinessException(BusinessInterfaceException e) {
		return ResponseEntity.status(HttpStatus.OK).body(R.failed(null, e.getMessage()));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<R<String>> handlerException(Exception e) {
		log.error("操作失败", e);
		return ResponseEntity.status(HttpStatus.OK).body(R.failed(null, CommonConstants.OPERATE_FAILED));
	}
}
