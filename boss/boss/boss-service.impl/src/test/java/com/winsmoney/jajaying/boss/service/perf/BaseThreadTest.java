package com.winsmoney.jajaying.boss.service.perf;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import com.clarkware.junitperf.ConstantTimer;
import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TimedTest;
import com.clarkware.junitperf.Timer;

/**
 * 压力测试父类
 * 
 * @author 李悦
 *
 */
public class BaseThreadTest extends TestCase {

	public static ApplicationContext ctx;

	static {
		try {
			Log4jConfigurer.initLogging("classpath:log4j.xml");
		} catch (FileNotFoundException ex) {
		}
	}

	public BaseThreadTest(String name) {

		super(name);

	}

	@Override
	protected void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext(new String[] { "/spring/boss-bean.xml" });
		System.out.println(ctx.getApplicationName());
	}

	@Override
	protected void tearDown() throws Exception {

	}

	public void testOneSecondResponse() throws Exception {

		Thread.sleep(1000);

	}

	public void testOne() throws InterruptedException {

		Thread.sleep(2000);

		System.out.println("test running");

	}

	// public static Test suite() {
	//
	// Test testCase = new ExampleTestCase("testOne");
	//
	// // 创建一个TestSuite
	//
	// TestSuite suite = new TestSuite();
	//
	// // 增加一个TimedTest
	//
	// // 默认有第三个参数：true，可以完整运行
	//
	// suite.addTest(new TimedTest(testCase, 2000));
	//
	// // 第三个参数表示超过限定时间 不再继续执行，无法得到程序运行总时间
	//
	// // suite.addTest( new TimedTest( testCase, 2000,false));
	//
	// return suite;
	//
	// }

	public static Test loadSuite(BaseThreadTest baseThreadTest) {

		// Test testCase = new BaseThreadTest(method);

		TestSuite suite = new TestSuite();

		int users = 5; // 执行用户数，模拟多线程并发

		int iterations = 2;// 每用户数执行多少次

		Timer timer = new ConstantTimer(10);// 每次执行相差时间，即1秒执行一次

		long maxElapsedTimeInMillis = 5000;// 总执行时间

		// Test loadTest = new LoadTest(testCase, users, iterations,timer);

		// Test timedTest = new TimedTest(loadTest,maxElapsedTimeInMillis);

		suite.addTest(new TimedTest(new LoadTest(baseThreadTest, users, iterations, timer), maxElapsedTimeInMillis));

		return suite;

	}

	public static void main(String[] args) {
		BaseThreadTest baseThreadTest = new BaseThreadTest("testOne");
		TestRunner.run(loadSuite(baseThreadTest));

	}

}
