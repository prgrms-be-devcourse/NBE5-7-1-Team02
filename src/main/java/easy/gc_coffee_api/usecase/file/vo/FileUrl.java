package easy.gc_coffee_api.usecase.file.vo;

public record FileUrl(String value) {
    @Override
    public String toString() {
        return value;
    }
}
