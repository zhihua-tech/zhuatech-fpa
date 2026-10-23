/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fpa.workbench;

import static cn.zhuatech.fpa.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);

    keys(r, "annualBudget", "periods");
    var budget = money(r, "annualBudget");
    Set<String> ids = new HashSet<>();
    BigDecimal actual = BigDecimal.ZERO, committed = BigDecimal.ZERO, forecast = BigDecimal.ZERO;
    List<Map<String, Object>> out = new ArrayList<>();
    for (var p : rows(r, "periods", 1, 24)) {
      keys(p, "id", "closed", "actual", "committed", "forecastUncommitted");
      String id = id(p, "id");
      unique(ids, id);
      boolean closed = flag(p, "closed");
      var a = money(p, "actual");
      var c = money(p, "committed");
      var f = money(p, "forecastUncommitted");
      if (closed && (c.signum() != 0 || f.signum() != 0) || !closed && a.signum() != 0)
        throw new IllegalArgumentException("已关闭期间只能计实际，未关闭期间只能计承诺及未承诺预测");
      actual = actual.add(a);
      committed = committed.add(c);
      forecast = forecast.add(f);
      out.add(Map.of("id", id, "closed", closed, "included", a.add(c).add(f)));
    }
    var total = actual.add(committed).add(forecast);
    var variance = total.subtract(budget);
    return Map.of(
        "actual",
        actual,
        "remainingCommitted",
        committed,
        "remainingUncommittedForecast",
        forecast,
        "fullYearEstimate",
        total,
        "budgetVariance",
        variance,
        "varianceRateDefined",
        budget.signum() > 0,
        "variancePercent",
        budget.signum() == 0
            ? BigDecimal.ZERO
            : variance.multiply(new BigDecimal("100")).divide(budget, 6, RoundingMode.HALF_UP),
        "periods",
        out);
  }
}
