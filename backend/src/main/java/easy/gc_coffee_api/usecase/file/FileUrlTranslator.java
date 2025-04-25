package easy.gc_coffee_api.usecase.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUrlTranslator {

    private String prefix;

    public FileUrlTranslator(@Value("${file.storage_path}")String prefix) {
        this.prefix = prefix;
    }

    public String execute(String newFileName) {
        return prefix + newFileName;
    }
}
