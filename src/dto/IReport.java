/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dto;

import controllers.Order;
import controllers.DrinksManagement;
import controllers.IngreManagement;

/**
 *
 * @author Hieu Phi Trinh
 */
public interface IReport {

    public void showAvailableIngredients(IngreManagement ingredientManager);

    public void showUnavailableDrinks(DrinksManagement drinkManager);

    public void showDispensingDrinks(Order dispensingManager);
}
