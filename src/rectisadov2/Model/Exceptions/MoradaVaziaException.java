/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model.Exceptions;

/**
 *
 * @author Costeira
 */
public class MoradaVaziaException extends RuntimeException{

    public MoradaVaziaException() {
        super("O campo morada não pode estar vazio");
    }
    
}
