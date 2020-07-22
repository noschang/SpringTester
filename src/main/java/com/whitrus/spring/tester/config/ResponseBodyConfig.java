package com.whitrus.spring.tester.config;

import java.util.Collection;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.Getter;

@ControllerAdvice
public class ResponseBodyConfig implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body instanceof Page) {
			return new PageModel((Page<?>) body);
		}

		return body;
	}

	@Getter
	private static final class PageModel {
		private final PageMetadata page;
		private final Collection<?> content;

		public PageModel(Page<?> page) {
			this.page = new PageMetadata(page);
			this.content = page.getContent();
		}
	}

	@Getter
	public static final class PageMetadata {
		private final int size;
		private final long totalElements;
		private final int totalPages;
		private final int number;

		public PageMetadata(Page<?> page) {
			this.size = page.getSize();
			this.totalElements = page.getTotalElements();
			this.totalPages = page.getTotalPages();
			this.number = page.getNumber();
		}
	}
}