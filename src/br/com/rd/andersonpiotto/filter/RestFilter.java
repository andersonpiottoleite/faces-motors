package br.com.rd.andersonpiotto.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;

import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;

@WebFilter("/toto*")
public class RestFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		System.out.println(request.getRemotePort());
		System.out.println(request.getRemoteHost());
		System.out.println(request.getContentType());
		System.out.println(request.getContentLength());
		System.out.println(request.getLocalName());
		System.out.println(request.getLocalPort());
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getProtocol());
		System.out.println(request.getLocalAddr());
		System.out.println(request.getLocale());
		System.out.println(request.getServletContext());
		System.out.println(request.getScheme());
		System.out.println(request.getScheme());
		System.out.println(request.getServerName());
		System.out.println(request.getServerPort());
		System.out.println(request.getRequestURI());
		System.out.println(request.getQueryString());
		System.out.println(request.getPathInfo());
		System.out.println(request.getUserPrincipal());
		System.out.println(request.getRequestURL());
		System.out.println(request.getServletPath());
		
		Enumeration<String> parameterNames = request.getParameterNames();
		
		while(parameterNames.hasMoreElements()) {
			System.out.println(parameterNames.nextElement()); 
		}
		
		
		
		Enumeration<String> attributeNames = request.getAttributeNames();
		
		while(attributeNames.hasMoreElements()) {
			System.out.println(attributeNames.nextElement()); 
		}
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		for (Entry<String, String[]> amp : parameterMap.entrySet()) {
			System.out.println(amp.getKey() + " " + amp.getValue());
		}
		
		
		
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
