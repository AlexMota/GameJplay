/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogoDaMemoria;

import jplay.Animation;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Time;
import jplay.Window;
import java.awt.Color;
import java.awt.Font;


/**
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */


public class TelaMensagemFimJogo
{
    Window window;
    String tempo;
    Keyboard keyboard;
    Animation botao;
    GameImage backGround;
    Mouse mouse;
    Font fonteDaMensagemFinal;

    public TelaMensagemFimJogo(Window window, Time tempo)
    {
            this.window = window;
            this.tempo = tempo.toString();
            this.keyboard = window.getKeyboard();
            this.mouse  = window.getMouse();
            this.botao = new Animation("botao.png", 5);
            this.backGround = new GameImage("fundoBotao.png");
            this.fonteDaMensagemFinal = new Font("Comic Sans MS", Font.TRUETYPE_FONT, 20);

            setarConfiguracoes();
            loop();
            descarregarObjetos();
    }

    private void setarConfiguracoes()
    {
            botao.setTotalDuration(900);
            botao.x = 350;
            botao.y = 250;
            botao.setSequence(0, 4);
    }

    private void loop()
    {
            boolean executando = true;
            botao.pause();
            while(executando)
            {                   
                    if (mouse.isOverObject(botao) && mouse.isLeftButtonPressed() )
                        botao.play();
                    System.out.println("X: " + mouse.getPosition().x + " y: "+ mouse.getPosition().y);

                    botao.update();

                    if (keyboard.keyDown(Keyboard.ESCAPE_KEY) || botao.getCurrFrame()+1 == botao.getFinalFrame())
                        executando = false;

                    desenhar();
            }
    }

    private void desenhar()
    {
            window.clear(Color.black);
            backGround.draw();
            botao.draw();
            window.drawText("Você conseguiu em: " + tempo + "!",250, 250, Color.YELLOW, fonteDaMensagemFinal);

            //Esse método SEMPRE deve ser chamado por último.
            window.update();
    }

    private void descarregarObjetos()
    {
            backGround = null;
            botao = null;
            tempo = null;
            keyboard = null;
            mouse = null;                                          
    }
}
