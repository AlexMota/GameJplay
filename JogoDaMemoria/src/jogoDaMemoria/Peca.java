/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogoDaMemoria;

import jplay.Animation;


/**
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */


public class Peca extends Animation
{
    String id;
    boolean pecaEscolhida;

    public Peca(String fileName, String id)
    {
            //2 = número de frames de cada peça
            super(fileName, 2);
            setCurrFrame(1);
            this.id = id;
            pecaEscolhida = false;
    }

    public String getID()
    {
            return id;
    }

    public void setID(String id)
    {
            this.id = id;
    }

    public boolean pecaEstaVirada()
    {
            return pecaEscolhida;
    }

    public void mostrar()
    {
            setCurrFrame(0);
            pecaEscolhida = true;
    }

    public void esconder()
    {
            setCurrFrame(1);
            pecaEscolhida = false;
    }

    public boolean foiEscolhida()
    {
            return pecaEscolhida;
    }

}
