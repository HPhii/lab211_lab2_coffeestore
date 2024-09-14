package dto;

import java.io.Serializable;
import java.util.List;

public class Drink implements Serializable {

    private String code;
    private String name;
    private List<Ingredient> ingredients;

    public Drink(String code, String name, List<Ingredient> ingredients) {
        this.code = code;
        this.name = name;
        this.ingredients = ingredients;
    }

    public boolean isAvailable() {
        for (Ingredient ingredient : ingredients) {
            if (!ingredient.isAvailable()) {
                return false;
            }
        }
        return true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Drink{" + "code=" + code + ", name=" + name + ", ingredients=" + ingredients + '}';
    }

}
