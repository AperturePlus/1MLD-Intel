package xenosoft.imldintelligence.module.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ModulePackageStructureTest {
    private static final Path MODULE_ROOT = Path.of("src", "main", "java", "xenosoft", "imldintelligence", "module");

    @Test
    void everyModuleShouldExposeOnlyApiAndInternalAtTopLevel() throws IOException {
        try (Stream<Path> modules = Files.list(MODULE_ROOT)) {
            List<Path> moduleDirs = modules.filter(Files::isDirectory).sorted().toList();
            assertThat(moduleDirs).isNotEmpty();

            for (Path moduleDir : moduleDirs) {
                List<String> childDirs;
                try (Stream<Path> children = Files.list(moduleDir)) {
                    childDirs = children.filter(Files::isDirectory)
                            .map(path -> path.getFileName().toString())
                            .sorted()
                            .toList();
                }

                assertThat(childDirs)
                        .describedAs("%s top-level package directories", moduleDir.getFileName())
                        .containsExactly("api", "internal");
                assertThat(moduleDir.resolve("internal").resolve("api"))
                        .describedAs("%s should not keep public API under internal/api", moduleDir.getFileName())
                        .doesNotExist();
            }
        }
    }
}