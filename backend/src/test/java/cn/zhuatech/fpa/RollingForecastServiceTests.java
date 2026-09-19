/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fpa;
import cn.zhuatech.fpa.service.RollingForecastService;import org.junit.jupiter.api.Test;import java.math.*;import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class RollingForecastServiceTests {private final RollingForecastService service=new RollingForecastService();
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void requestsReforecastForLargeOverrun(){var r=service.forecast(new RollingForecastService.Request(b("600"),b("500"),b("200"),b("350"),b("1000")));assertEquals("REFORECAST",r.status());assertEquals(b("150.00"),r.budgetVariance());}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @Test void keepsSmallVarianceOnPlan(){var r=service.forecast(new RollingForecastService.Request(b("500"),b("500"),b("200"),b("280"),b("1000")));assertEquals("ON_PLAN",r.status());}/**
                                                                                                                                                                                      * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                      */
private BigDecimal b(String v){return new BigDecimal(v);}}
