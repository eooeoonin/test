package com.winsmoney.jajaying.boss.service.perf;

import junit.textui.TestRunner;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winsmoney.jajaying.basedata.service.IProtocolService;

/**
 * 压力测试basedata
 * 
 * @author Moon
 *
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RecordAcctTest extends BaseThreadTest {
//	@Autowired
//	private ICardBinService iCardBinService;
	public RecordAcctTest(String name) {
		super(name);
	}
	
	public void recordAcct() throws InterruptedException {
		System.out.println("----------------开始------------------");
		/**
		 * 卡bin             数据库测试读取
		 */
//		ICardBinService iCardBinService = (ICardBinService) ctx.getBean("cardBinService");
//		CardBinReqVo cardBinReqVo = new CardBinReqVo();
//		cardBinReqVo.setAvailable(1);
//		iCardBinService.listCardBin(cardBinReqVo, 1, 10);
		
		
		/**
		 * 银行信息 			数据库测试读取
		 */
//		IBankInfoService bankInfoService = (IBankInfoService) ctx.getBean("bankInfoService");
//		BankInfoReqVo bankInfoReqVo=new BankInfoReqVo();
//		bankInfoService.listBankInfo(bankInfoReqVo, 1, 10);
		
		
		
		/**
		 * 错误码关系管理            数据库测试读取
		 */
//		IErrorCodeMapService errorCodeMapService = (IErrorCodeMapService) ctx.getBean("errorCodeMapService");
//		ErrorCodeMapReqVo errorCodeMapReqVo = new ErrorCodeMapReqVo();
//		errorCodeMapService.listErrorCodeMap(errorCodeMapReqVo, 1, 10);
	
		
		
		/**
		 * 错误码管理            数据库测试读取
		 */
//		IErrorCodeService errorCodeService = (IErrorCodeService) ctx.getBean("errorCodeService");
//		ErrorCodeReqVo errorCodeReqVo=new ErrorCodeReqVo();
//		errorCodeService.listErrorCode(errorCodeReqVo, 1, 10);
	
		
		/**
		 * 意见信息           数据库测试读取
		 */
//		INoticeService noticeService = (INoticeService) ctx.getBean("noticeService");
//		NoticeReqVo noticeReqVo=new NoticeReqVo();
//		noticeService.list(noticeReqVo, 1, 10);
		
		/**
		 * APP维护           数据库测试读取
		 */
//		IBannerService bannerService=(IBannerService)ctx.getBean("bannerService");
//		BannerReqVo  bannerReqVo=new BannerReqVo();
//		bannerReqVo.setType("3");//app维护
//		bannerService.list(bannerReqVo, 1, 10); //不带缓存分页
		
		
		
		/**
		 * APP维护           缓存测试读取
		 */
//		IBannerService bannerService=(IBannerService)ctx.getBean("bannerService");
//		BannerReqVo  bannerReqVo=new BannerReqVo();
//		bannerReqVo.setType("3");//app维护
//		bannerService.listByCache(bannerReqVo, 1, 10); //带缓存分页
		
		
		/**
		 *热点新闻           数据库测试读取
		 */
//		IBannerService bannerService=(IBannerService)ctx.getBean("bannerService");
//		BannerReqVo  bannerReqVo=new BannerReqVo();
//		bannerReqVo.setType("6");//热点新闻 
//		bannerService.list(bannerReqVo, 1, 10); //不带缓存分页
		
		
		/**
		 * 热点新闻             缓存测试读取
		 */
//		IBannerService bannerService=(IBannerService)ctx.getBean("bannerService");
//		BannerReqVo  bannerReqVo=new BannerReqVo();
//		bannerReqVo.setType("6");//热点新闻 
//		bannerService.listByCache(bannerReqVo, 1, 10); //带缓存分页
		
		
		/**
		 * 开屏广告           数据库测试读取
		 */
//		IBannerService bannerService=(IBannerService)ctx.getBean("bannerService");
//		BannerReqVo  bannerReqVo=new BannerReqVo();
//		bannerReqVo.setType("1");//开屏广告
//		bannerService.list(bannerReqVo, 1, 10); //不带缓存分页
		
		
		
		/**
		 * 开屏广告             缓存测试读取
		 */
//		IBannerService bannerService=(IBannerService)ctx.getBean("bannerService");
//		BannerReqVo  bannerReqVo=new BannerReqVo();
//		bannerReqVo.setType("1");//开屏广告
//		bannerService.listByCache(bannerReqVo, 1, 10); //带缓存分页
		
		
		
		/**
		 * 协议列表          数据库测试读取
		 */
//		IProtocolService protocolService=(IProtocolService)ctx.getBean("protocolService");
//		ProtocolReqVo protocolReqVo=new ProtocolReqVo();
//		protocolService.list(protocolReqVo, 1, 10);
		
		
		/**
		 * 发放红包规则          数据库测试读取
		 */
		IProtocolService protocolService=(IProtocolService)ctx.getBean("protocolService");
		
	
	} 
	

		
	

	public static void main(String[] args) {
		RecordAcctTest recordAcctTest = new RecordAcctTest("recordAcct");
		TestRunner.run(loadSuite(recordAcctTest));

	}

}
