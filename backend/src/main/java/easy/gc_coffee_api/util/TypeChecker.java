package easy.gc_coffee_api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeChecker {

    // mimetype 유효성 검사
    static public boolean isImage(String mimeType) {
        return mimeType != null && mimeType.startsWith("image");
    }
}