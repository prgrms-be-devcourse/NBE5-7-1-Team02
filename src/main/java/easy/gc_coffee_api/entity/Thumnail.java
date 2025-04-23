package easy.gc_coffee_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Thumnail {
    @ManyToOne
    @JoinColumn(name = "file_id")
    private File thumbnail;
//
//    @Column(name = "file_id")
//    private Long file_id;

}