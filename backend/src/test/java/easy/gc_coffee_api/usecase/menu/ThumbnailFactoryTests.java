package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.entity.Thumbnail;
import easy.gc_coffee_api.repository.FileRepository;
import easy.gc_coffee_api.usecase.menu.helper.ThumbnailFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ThumbnailFactoryTests {

    @Mock
    private FileRepository fileRepository;

    @Test
    void null입력시_기본이미지_생성(){
        //TODO 테스트 작성
        ThumbnailFactory thumbnailFactory = new ThumbnailFactory(fileRepository);
        Thumbnail thumbnail = thumbnailFactory.create(null);

        assertThat(thumbnail.getFileId()).isNull();
    }

    @Test
    void 아이디존재시_파일_찾아서_생성(){
        Long fileId = 1L;
        when(fileRepository.findById(eq(fileId))).thenReturn(Optional.of(new File(1L,"image/jpeg","/test/url")));
        ThumbnailFactory thumbnailFactory = new ThumbnailFactory(fileRepository);

        Thumbnail thumbnail = thumbnailFactory.create(fileId);

        assertThat(thumbnail.getFileId()).isEqualTo(fileId);
    }
}