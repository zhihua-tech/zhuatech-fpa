/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.fpa.config;

import cn.zhuatech.fpa.model.*;
import cn.zhuatech.fpa.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration
public class DataInitializer {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Bean
    CommandLineRunner seed(OperatingUnitRepository operatingUnits, WorkRecordRepository orders,
                           ResourceRegisterRepository resources, ReviewRecordRepository reviewRecords,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (operatingUnits.count() > 0) return;
            OperatingUnit primaryUnit = operatingUnits.save(new OperatingUnit("FPA-CHEM", "消费事业群", "经营计划中心", 180));
            OperatingUnit secondaryUnit = operatingUnits.save(new OperatingUnit("FPA-MICRO", "研发中心", "研发中心", 120));
            OperatingUnit tertiaryUnit = operatingUnits.save(new OperatingUnit("FPA-MAT", "国际事业群", "工程中心", 96));

            WorkRecord t1 = orders.save(new WorkRecord("FC-260801-018", "REV-EAST", "华东渠道收入滚动预测", tertiaryUnit, 24, 16, 1, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "GW-Q3"));
            WorkRecord t2 = orders.save(new WorkRecord("FC-260801-021", "GP-CLOUD", "云服务毛利改善计划", primaryUnit, 18, 8, 0, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "TERM-12"));
            WorkRecord t3 = orders.save(new WorkRecord("BUD-260802-006", "OPEX-RD", "研发费用年度预算", secondaryUnit, 12, 0, 0, LocalDate.now().plusDays(3), WorkRecord.Status.RELEASED, "SP-2026"));
            WorkRecord t4 = orders.save(new WorkRecord("FC-260728-015", "CF-OVERSEA", "海外现金流预测", primaryUnit, 20, 20, 1, LocalDate.now(), WorkRecord.Status.COMPLETED, "SEA-09"));

            resources.saveAll(List.of(
                new ResourceRegister("CAT-HPLC-03", "收入驱动模型", primaryUnit, ResourceRegister.Status.RUNNING, 88),
                new ResourceRegister("CAT-ICP-02", "利润预测模型", primaryUnit, ResourceRegister.Status.IDLE, 76),
                new ResourceRegister("CAT-UTM-05", "现金流预测模型", tertiaryUnit, ResourceRegister.Status.RUNNING, 91),
                new ResourceRegister("CAT-INC-08", "费用分摊模型", secondaryUnit, ResourceRegister.Status.ALARM, 62)
            ));
            reviewRecords.saveAll(List.of(
                new ReviewRecord("ISS-260801-032", t1, "经营假设复核", 6, 0, ReviewRecord.Result.PASSED, "沈宁"),
                new ReviewRecord("ISS-260801-011", t2, "预测模型校验", 3, 0, ReviewRecord.Result.PASSED, "林悦"),
                new ReviewRecord("ISS-260801-018", t4, "版本锁定复核", 5, 1, ReviewRecord.Result.FAILED, "沈宁"),
                new ReviewRecord("ISS-260802-003", t3, "预算基线确认", 4, 0, ReviewRecord.Result.PENDING, "林悦")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "林悦", UserAccount.Role.DOMAIN_USER, "FPA-CHEM"),
                new UserAccount("planner", demo, "沈宁", UserAccount.Role.DOMAIN_OPERATOR, null),
                new UserAccount("quality", demo, "顾清", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
