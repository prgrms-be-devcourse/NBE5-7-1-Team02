package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.entity.common.BaseDateEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mimetype;

    @Column(name = "`key`")
    private String key;

    public File(Long id, String mimetype, String key) {
        this.id = id;
        this.mimetype = mimetype;
        this.key = key;
    }

    public File(String mimetype, String key) {
        this(null, mimetype, key);
    }
}
