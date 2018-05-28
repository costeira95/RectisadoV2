/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rectisadov2.model.Exceptions;

import rectisadov2.model.Cliente;

/**
 *
 * @author Costeira
 */
public class ClienteExistenteException extends RuntimeException {

    public ClienteExistenteException(Cliente cliente) {
        super("O cliente " + cliente.getNome() + " já existe!");
    }
    
}
