package com.hackerbetter.artist.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;


/**
 * 清缓存用
 * @author hacker
 *
 */
public class CacheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerFactory.getLogger(CacheServlet.class);
	private MemcachedClient memcachedClient;

	@Override
	public void init() throws ServletException {
		try {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader().getResourceAsStream("META-INF/spring/client.properties"));
			MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(pros.getProperty("memcache.client.ip")));
			memcachedClient=builder.build();
		} catch (IOException e) {
			logger.error("memcache 初始化异常",e);
		}
	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		try {
			memcachedClient.flushAll();
			out.print("flush_all OK!");
		} catch (TimeoutException e) {
			out.print("fail!");
			logger.error("memcache flushAll操作异常",e);
		} catch (InterruptedException e) {
			out.print("fail!");
			logger.error("memcache flushAll操作异常",e);
		} catch (MemcachedException e) {
			out.print("fail!");
			logger.error("memcache flushAll操作异常",e);
		}
	}
	

}
