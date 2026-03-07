package xenosoft.imldintelligence;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动入口，负责引导 IMLD-Intelligence 主程序。
 */
@SpringBootApplication
@MapperScan(basePackages = "xenosoft.imldintelligence.module", annotationClass = Mapper.class)
public class ImldIntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImldIntelligenceApplication.class, args);
    }

}
