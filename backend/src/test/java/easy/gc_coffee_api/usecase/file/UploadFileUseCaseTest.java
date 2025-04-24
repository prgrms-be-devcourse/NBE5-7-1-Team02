package easy.gc_coffee_api.usecase.file;

import easy.gc_coffee_api.dto.file.UploadFileResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.repository.FileRepository;
import easy.gc_coffee_api.usecase.file.strategy.LocalFileStorageStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadFileUseCaseTest {
    @Mock
    private FileRepository fileRepository;

    private UploadFileUseCase uploadFileUseCase;

    @BeforeEach
    void setUp() throws IOException {
        uploadFileUseCase = new UploadFileUseCase(fileRepository, new LocalFileStorageStrategy());
    }

    @Test
    void 파일없이_이미지_파일_업로드_요청시_예외() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "", "image/jpeg", "".getBytes()
        );

        assertThatThrownBy(() -> uploadFileUseCase.execute(multipartFile))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미지가아닌_파일_업로드_요청시_예외() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "",
                "application/json",
                "".getBytes()
        );

        assertThatThrownBy(
                () -> {
                    uploadFileUseCase.execute(multipartFile);
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 확장자가없는_파일로_업로드요청시_예외() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "cat",
                "image/jpeg",
                "".getBytes()
        );

        assertThatThrownBy(
                () -> {
                    uploadFileUseCase.execute(multipartFile);
                }
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 파일_업로드_성공() throws Exception {
        String mimeType = "image/jpg";
        String url = "/storage/78b6f135-afce-411f-8dec-01ed9969dd79.jpg";

        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("face.jpg");
        when(multipartFile.getContentType()).thenReturn(mimeType);

        doNothing().when(multipartFile).transferTo(any(java.io.File.class));

        File file = new File(mimeType, url);

        try {
            Field idField = File.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(file, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(fileRepository.save(any())).thenReturn(file);

        UploadFileResponseDto responseDto = uploadFileUseCase.execute(multipartFile);

        verify(multipartFile, times(1)).transferTo(any(java.io.File.class));

        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getKey()).isEqualTo(url);
    }
}
