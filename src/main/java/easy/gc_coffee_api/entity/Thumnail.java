package easy.gc_coffee_api.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thumnail {
    @ManyToOne
    @JoinColumn(name = "file_id")
    private File thumbnail;
}