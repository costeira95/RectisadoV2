/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model;

/**
 *
 * @author Costeira
 */
public enum ECredito {
    CREDITO, DEBITO;

    @Override
    public String toString() {
        switch(this){
            case CREDITO : return "CREDITO";
            default: return "DEBITO";
        }
    }
    
}
