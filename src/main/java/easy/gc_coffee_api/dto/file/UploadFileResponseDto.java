package easy.gc_coffee_api.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadFileResponseDto {
    private Long id;
    private String url;
}
