/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fpa.service;
import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.math.*;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class RollingForecastService {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public Result forecast(Request r){BigDecimal estimate=r.actualYtd().add(r.remainingCommitted()).add(r.forecastRemaining()).setScale(2,RoundingMode.HALF_UP);BigDecimal variance=estimate.subtract(r.annualBudget()).setScale(2,RoundingMode.HALF_UP);double rate=r.annualBudget().signum()==0?0:variance.abs().multiply(BigDecimal.valueOf(100)).divide(r.annualBudget(),1,RoundingMode.HALF_UP).doubleValue();String status=rate<=5?"ON_PLAN":rate<=10?"WATCH":"REFORECAST";List<String> actions=new ArrayList<>();if(variance.signum()>0)actions.add("识别超预算科目并落实纠偏责任人");if(r.actualYtd().compareTo(r.budgetYtd())>0)actions.add("复核年初至今预算偏差的持续性");if(actions.isEmpty())actions.add("维持当前滚动预测并在月结后刷新假设");return new Result(estimate,variance,rate,status,actions);}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Request(@NotNull @DecimalMin("0") BigDecimal actualYtd,@NotNull @DecimalMin("0") BigDecimal budgetYtd,@NotNull @DecimalMin("0") BigDecimal remainingCommitted,@NotNull @DecimalMin("0") BigDecimal forecastRemaining,@NotNull @DecimalMin("0.01") BigDecimal annualBudget){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Result(BigDecimal fullYearEstimate,BigDecimal budgetVariance,double absoluteVarianceRate,String status,List<String> actions){}
}
