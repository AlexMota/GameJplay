/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogoDaMemoria;


import jplay.Sound;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Time;
import jplay.Window;



/**
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */

public class JogoMemoria
{
    Window window;
    Mouse mouse;
    Keyboard keyboard;
    MatrizPecas pecas;
    Peca pecaUmEscolhida;
    Peca pecaDoisEscolhida;
    Time tempo;
    GameImage fundo;
    int numPecasCombinadas;
    int pontuacao;
    int NUMERO_TOTAL_COMBINACOES;//É o número de peças diferentes
    int tempoDeEspera;

    public JogoMemoria()
    {
            carregarObjetos();
            DisplayMode[] d = window.getCompatibleDisplayMode();
            loop();
            descarregarObjetos();
    }

    private void carregarObjetos()
    {
            //A windows SEMPRE deve ser a primeira a ser CARREGADA
            window = new Window(800,600);
            window.setCursor( window.createCustomCursor("mouse.png") );

            mouse = window.getMouse();
            keyboard = window.getKeyboard();

            pecas = new MatrizPecas();
            pecas.setDeslocamento(150, 130);
            pecas.gerarPosicoesAleatorias();

            numPecasCombinadas = 0;
            fundo = new GameImage("fundo.png");

            tempo = new Time(660, 140, true);
            tempo.setColor(Color.GRAY);
            tempo.setFont(new Font("sansserif", Font.TRUETYPE_FONT, 12));

            NUMERO_TOTAL_COMBINACOES = 10;
    }

    private void loop()
    {
            boolean executanto = true;
            long tempoPassado = 0;

            numPecasCombinadas = NUMERO_TOTAL_COMBINACOES;
            while(executanto)
            {
                    if (numPecasCombinadas != NUMERO_TOTAL_COMBINACOES)
                    {
                            desenhar();
                            escolherPecas();
                            tempoPassado = verificarPecas(tempoPassado);
                    }
                    else
                    {
                            TelaMensagemFimJogo telaMensagemFimJogo = new TelaMensagemFimJogo(window, tempo);

                            //Reseta o tempo para zero
                            //tempo.setTime(int hour, int minute, int seconds)
                            //hora = 0; minuto = 0; segundos = 0
                            tempo.setTime(0, 0, 0);

                            pecas.esconderTodasPecas();
                            pecas.gerarPosicoesAleatorias();
                            numPecasCombinadas = 0;
                            pontuacao = 0;
                    }

                    if (keyboard.keyDown(Keyboard.ESCAPE_KEY))
                            executanto = false;
            }
    }


    //Verifica quais peças o jogador escolheu
    private void escolherPecas()
    {
            //O valor null significa que o jogador está com o ponteiro do mouse 
            //fora da área onde existem peças, ou seja, não está selecionando
            //peça alguma.
            Peca peca = pecas.getPecaSobMouse( mouse.getPosition() );
            if ( (peca != null) && (peca.foiEscolhida() == false ) && mouse.isLeftButtonPressed() )
            {
                    //As variáveis 'pecaUmEscolhida' e 'pecaDoisEscolhida' 
                    //apontam para peças existentes na matriz de peças, isso se
                    //o jogador escolher alguma peça.
                    if (pecaUmEscolhida == null)
                    {
                        pecaUmEscolhida = peca;
                        pecaUmEscolhida.mostrar();
                        new Sound("som1.wav").play();
                    }
                    else
                    {   //A segunda condição do if previne que a
                        //primeiraPecaEscolhida e a SegundaPecaEscolhida
                        //sejam a mesma peça
                        if (pecaDoisEscolhida == null && peca != pecaUmEscolhida)
                        {
                                pecaDoisEscolhida = peca;
                                pecaDoisEscolhida.mostrar();
                        }
                    }
            }
    }

    //Verifica se as pecas escolhidas são iguais
    private long verificarPecas(long tempoPassado)
    {
            //Se as duas pecas forem diferentes de null, significa que o jogador
            //escolheu duas peças
            if (pecaDoisEscolhida != null && pecaUmEscolhida != null)
            {
                    //Antes de fazer a verificação se a duas peças escolhidas 
                    //são iguais ou não, esperamos 0.3 segundos, esse tempo é
                    //necessário para que o usuário veja as peças que ele
                    //escolheu. O tempo só pode comecar a ser contado a partir
                    //do momento em que duas peças são escolhidas.
                    boolean anularPecasEscolhidas = true;
                    if( pecaUmEscolhida.getID().compareTo(pecaDoisEscolhida.getID()) == 0 )
                    {
                            numPecasCombinadas++;
                            pontuacao += 5;
                            new Sound("som3.wav").play();
                    }
                    else
                    {
                        if (tempoPassado > 500)
                        {
                                pontuacao -= 5;
                                pecaUmEscolhida.esconder();
                                pecaDoisEscolhida.esconder();
                                new Sound("som2.wav").play();
                                tempoPassado = 0;
                        }
                        else
                        {
                            tempoPassado += window.deltaTime();
                            anularPecasEscolhidas = false;
                        }
                    }

                    if (anularPecasEscolhidas)
                    {
                            pecaUmEscolhida = null;
                            pecaDoisEscolhida = null;
                    }
            }
            
            return tempoPassado;
    }
    
    private void desenhar()
    {
            fundo.draw();
            pecas.desenharPecas();
            window.drawText("Score: " + pontuacao, 90, 140, Color.GRAY);
            window.drawText("Não beba e dirija!", 100, 580, Color.gray);
            tempo.draw("Time: ");

            //esse método SEMPRE deve ser chamado por último
            window.update();
    }

    private void descarregarObjetos()
    {
            pecas = null;
            mouse = null;
            keyboard = null;
            pecaUmEscolhida = null;
            pecaDoisEscolhida = null;

            //Fecha a janela de jogo
            window.exit();
    }
}
