package easy.gc_coffee_api.usecase.menu;

import easy.gc_coffee_api.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ThumnailFatoryTest {

    @Mock
    private FileRepository fileRepository;

    @Test
    void null입력시_기본이미지_생성(){
        //TODO 테스트 작성
    }
}