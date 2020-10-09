package honchi.api.domain.post.domain.enums;

import lombok.SneakyThrows;

public enum Category {
    FOOD(FoodType.class), PRODUCT(ProductType.class);

    private final Class category;

    Category(Class category) {
        this.category = category;
    }

    @SneakyThrows
    public String getCategory(String item) {
        return String.valueOf(category.getField(item).get(item));
    }
}
