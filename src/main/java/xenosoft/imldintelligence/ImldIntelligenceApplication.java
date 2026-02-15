package xenosoft.imldintelligence;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "xenosoft.imldintelligence.module", annotationClass = Mapper.class)
public class ImldIntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImldIntelligenceApplication.class, args);
    }

}
