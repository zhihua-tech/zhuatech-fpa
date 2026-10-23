/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fpa.workbench;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.zhuatech.fpa.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@SpringBootTest
@AutoConfigureMockMvc
class AiWorkbenchSecurityIntegrationTest {
  @Autowired MockMvc mvc;
  @Autowired JwtService jwt;

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void securityFilterRejectsAnonymous() throws Exception {
    mvc.perform(
            post("/api/workbench/rolling-forecast-bridge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"annualBudget\":1000,\"periods\":[{\"id\":\"2026-01\",\"closed\":true,\"actual\":400,\"committed\":0,\"forecastUncommitted\":0},{\"id\":\"2026-02\",\"closed\":false,\"actual\":0,\"committed\":200,\"forecastUncommitted\":500}]}"))
        .andExpect(status().isUnauthorized());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void securityFilterRejectsInvalidBearer() throws Exception {
    mvc.perform(
            post("/api/workbench/rolling-forecast-bridge")
                .header("Authorization", "Bearer invalid-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"annualBudget\":1000,\"periods\":[{\"id\":\"2026-01\",\"closed\":true,\"actual\":400,\"committed\":0,\"forecastUncommitted\":0},{\"id\":\"2026-02\",\"closed\":false,\"actual\":0,\"committed\":200,\"forecastUncommitted\":500}]}"))
        .andExpect(status().isUnauthorized());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedCallerCanCompute() throws Exception {
    mvc.perform(
            post("/api/workbench/rolling-forecast-bridge")
                .header("Authorization", "Bearer " + jwt.generate("operator"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"annualBudget\":1000,\"periods\":[{\"id\":\"2026-01\",\"closed\":true,\"actual\":400,\"committed\":0,\"forecastUncommitted\":0},{\"id\":\"2026-02\",\"closed\":false,\"actual\":0,\"committed\":200,\"forecastUncommitted\":500}]}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void authenticatedCallerGetsValidationError() throws Exception {
    mvc.perform(
            post("/api/workbench/rolling-forecast-bridge")
                .header("Authorization", "Bearer " + jwt.generate("operator"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }
}
