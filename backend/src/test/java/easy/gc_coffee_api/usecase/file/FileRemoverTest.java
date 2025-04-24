package easy.gc_coffee_api.usecase.file;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.exception.file.FileNotFoundException;
import easy.gc_coffee_api.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileRemoverTest {

    @Mock
    private FileRepository fileRepository;
    private FileRemover fileRemover;

    @BeforeEach
    public void initFileRemover(){
        fileRemover = new FileRemover(fileRepository);
    }

    @Test
    void 파일삭제(){
        Long fileId = 1L;
        File file = new File(fileId,"image/jpeg","test/url");
        when(fileRepository.findByIdAndDeletedAtIsNull(eq(fileId))).thenReturn(Optional.of(file));

        fileRemover.remove(fileId);

        assertThat(file.getDeletedAt()).isNotNull();
    }

    @Test
    void 없는_파일_삭제(){
        Long fileId = 2L;
        assertThatThrownBy(()->fileRemover.remove(fileId)).isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void 값에_null입력(){
        assertThatThrownBy(()->fileRemover.remove(null)).isInstanceOf(NullPointerException.class);
    }
}