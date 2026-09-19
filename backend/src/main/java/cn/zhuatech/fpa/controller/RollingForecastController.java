/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fpa.controller;
import cn.zhuatech.fpa.service.RollingForecastService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/fpa/insights/rolling-forecast") public class RollingForecastController {private final RollingForecastService service;/**
                                                                                                                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                            */
public RollingForecastController(RollingForecastService service){this.service=service;}/**
                                                                                                                                                                                                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                   */
@PostMapping RollingForecastService.Result forecast(@Valid @RequestBody RollingForecastService.Request request){return service.forecast(request);}}
