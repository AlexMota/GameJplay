/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogoDaMemoria;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


/**
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */


public class MatrizPecas
{
    Peca matriz[][];
    int deslocaX;//descolamento em relação a borda no eixo X
    int deslocaY;//descolamento em relação a borda no eixo Y
    int numeroLinhas = 5;
    int numeroColunas = 4;

    public MatrizPecas()
    {
            matriz = new Peca[numeroLinhas][numeroColunas];

            //peca 01
            matriz[0][0] = new Peca("peca01.png", "peca01");
            matriz[4][2] = new Peca("peca01.png", "peca01");

            //peca 02
            matriz[3][1] = new Peca("peca02.png", "peca02");
            matriz[1][3] = new Peca("peca02.png", "peca02");

            //peca 03
            matriz[0][2] = new Peca("peca03.png", "peca03");
            matriz[2][1] = new Peca("peca03.png", "peca03");

            //peca 04
            matriz[1][2] = new Peca("peca04.png", "peca04");
            matriz[2][2] = new Peca("peca04.png", "peca04");

            //peca 05
            matriz[1][0] = new Peca("peca05.png", "peca05");
            matriz[2][3] = new Peca("peca05.png", "peca05");

            //peca 06
            matriz[0][3] = new Peca("peca06.png", "peca06");
            matriz[3][3] = new Peca("peca06.png", "peca06");

            //peca 07
            matriz[2][0] = new Peca("peca07.png", "peca07");
            matriz[1][1] = new Peca("peca07.png", "peca07");

            //peca 08
            matriz[3][0] = new Peca("peca08.png", "peca08");
            matriz[0][1] = new Peca("peca08.png", "peca08");

            //peca 09
            matriz[4][0] = new Peca("peca09.png", "peca09");
            matriz[4][3] = new Peca("peca09.png", "peca09");

            //peca 10
            matriz[3][2] = new Peca("peca10.png", "peca10");
            matriz[4][1] = new Peca("peca10.png", "peca10");
    }

    public void desenharPecas()
    {
            for (int i =0; i < numeroLinhas; i++)
                for (int j =0; j < numeroColunas; j++)
                    matriz[i][j].draw();
    }

    //Dividimos a tela em blocos. Tome o seguinte caso: linhas = 5, coluna = 4.
    //Posicao do mouse = (x,y) = (200,300).
    //Logo, temos linhas * coluna peças = 20 peças.
    //Dividimos a tela em 20 blocos. Cada bloco tendo a altura e a largura igual a de uma peça.
    //O incremento pode ser o valor da altura ou da largura.
    //Como dividimos a tela em blocos iremos percorrê-los em linhas.
    //Se chamarmos a função com incremento = largura, o número de numlinhas será igual a 5
    //e estaremos percorrendo os blocos em relação a largura.
    //Se chamarmos a função com incremento = altura, o número de numColunas será igual a 4
    //e estaremos percorrendo os blocos em relação a altura.
    //Então, o numLinhas, uma hora será o número de colunas e outra o número de linhas da matriz.

    //Como o mouse tem x = 200
    //Iremos procurar a que linha corresponde a peça sob o mouse.
    //valorDimensao = 20 + 100; // 20 = largura, deslocamento = 100, 100 + 20 = 120
    //i = 0, 200 <= 120? Não, duzentos é maior. valorDimensao = 120 + 20 = 140
    //i = 1, 200 <= 140? Não, duzentos é maior. valorDimensao = 140 + 20 = 160
    //i = 2, 200 <= 160? Não, duzentos é maior. valorDimensao = 160 + 20 = 180
    //i = 3, 200 <= 180? Não, duzentos é maior. valorDimensao = 180 + 20 = 200
    //i = 4, 200 <= 200? Sim, retorna linha = 4

    //Já descobrimos a que linha a peça pertence.
    //Agora, falta saber qual a coluna da peça.

    //Como o mouse tem y = 300.
    //Iremos procurar a que coluna corresponde a peça sob o mouse.
    //valorDimensao = 60 + 100; // 60 = altura, deslocamento = 100, 60 + 100 = 160.
    //i = 0, 200 <= 160? Não, duzentos é maior. valorDimensao = 160 + 60 = 220.
    //i = 1, 200 <= 220? Sim, retorna coluna = 1.

    //Agora, sabemos que o mouse está sobre a peça (4,1) na matriz de peças


    private int procurarPosicao(int posicaoMouse, int incremento, int deslocamento, int numLinhas)
    {
            //valorDimensao - pode ser o valor da altura ou da largura,
            //esse valor irá depender do qual foi passado por parâmetro
            int valorDimensao = incremento + deslocamento;
            for (int i=0; i < numLinhas; i++)
            {
                    if ( posicaoMouse <= valorDimensao )
                    {
                        return i;
                    }
                    valorDimensao += incremento;
            }
            return -1;
    }

    //Se o mouse está fora da área das peças retorna null
    //Todas as pecas possuem a mesma largura e altura
    public Peca getPecaSobMouse(Point posicaoMouse)
    {
            //Somando a largura de todas as peças temos a larguraTotal
            int larguraTotal = deslocaX + matriz[0][0].width * numeroLinhas;
            if ( posicaoMouse.x < deslocaX || posicaoMouse.x >  larguraTotal)
                return null;

            //Somando a altura de todas as peças temos a alturaTotal
            int alturaTotal = deslocaY + matriz[0][0].height * numeroColunas;
            if ( posicaoMouse.y < deslocaY || posicaoMouse.y > alturaTotal )
                return null;

            int coluna = procurarPosicao(posicaoMouse.y, matriz[0][0].height, deslocaY, numeroColunas);
            int linha  = procurarPosicao(posicaoMouse.x, matriz[0][0].width, deslocaX, numeroLinhas);

            return matriz[linha][coluna];
    }


    //Suponha uma janela com dimensão de 800 x 600 pixels.
    //Um conjunto de peça que só ocupe 300 pixels na largura e 300 pixels na altura.
    //Sendo que todo sprite, imagem ou animação no JPlay, tem suas coordenads iniciadas
    //em (0,0) todas as imagens seriam desenhadas a partir desse ponto.
    //
    //Setando um deslocamento para x a imagem seria desenhada a partir desse ponto.
    //Exemplo:
    // Boneco(x,y) = Boneco(0,0) na hora que o mesmo é criado.
    // para desenhar o boneco sempre a partir da posição (100, 100) poderíamos fazer:
    // Boneco(x,y) = Boneco(100,100).
    // Mas, como em um jogo da memória não temos só uma peça e sim várias,
    // é mais prático guardar esse valor de deslocamento em uma variável.
    // A medida que forem sendo adicionadas peças ou quisermos mudar a posição inicial de onde
    // as peças serão desenhadas, basta mudar o valor do deslocamento para cada coordenada.
    public void setDeslocamento(int deslocamentoX, int deslocamentoY)
    {
            this.deslocaX = deslocamentoX;
            this.deslocaY = deslocamentoY;
    }

    public void esconderTodasPecas()
    {
          for (int i =0; i < numeroLinhas; i++)
                for (int j =0; j < numeroColunas; j++)
                    matriz[i][j].esconder();
    }

    public void gerarPosicoesAleatorias()
    {

            //Passo 1: Anular todas as referências das peças na matriz e
            //         adicionar todas as peças a uma lista ligada de peças.
            LinkedList pecas = new LinkedList();
            anularReferenciasDePecasNaMatriz(pecas);

            //Passo 2: Gerar posições aleatórias que serão usadas para posicionar
            //          as peças na matriz. As posições geradas serão posições
            //          que existem dentro da matriz, no caso serão alguma (i,j).

            //Armazenará todas as posições que foram utilizadas para posicionar as peças.
            ArrayList posicoes = new ArrayList();

            //Pega a primeira peça, para retirar o valor da altura e largura
            Peca peca = (Peca) pecas.element();
            int largura = peca.width;
            int altura = peca.height;

            //Enquanto houver peças...
            while(pecas.size()> 0)
            {
                    //Gera uma nova posicão aleatória que não existe na
                    //lista de posições já usadas.
                    Posicao pos = gerarValorRandomicoPosicao(posicoes);

                    //Remove a primeira peça da lista
                    peca = (Peca)pecas.remove();

                    //largura * pos.linha + deslocaX = posição inicial da peça em relação a X.
                    //altura * pos.coluna + deslocaY = posição inicial da peça em relação a Y.
                    peca.x = largura * pos.linha + deslocaX;
                    peca.y = altura * pos.coluna + deslocaY;
                    posicoes.add(pos);

                    matriz[pos.linha][pos.coluna] = peca;
            }
    }

    private void anularReferenciasDePecasNaMatriz(LinkedList pecas)
    {
            for (int i =0; i < numeroLinhas; i++)
            {
                    for (int j =0; j < numeroColunas; j++)
                    {
                        pecas.add(matriz[i][j]);
                        matriz[i][j] = null;
                    }
            }
    }


    private Posicao gerarValorRandomicoPosicao(ArrayList posicoes)
    {
            Random x = new Random();

            Posicao pos = new Posicao(x.nextInt(numeroLinhas),
                                      x.nextInt(numeroColunas));

            //Só podemos retorna a posição gerada se ela não existe na lista de posições.
            while( posicaoExiste(posicoes, pos) )
            {
                    //random.nextInt(numeroLinhas) - retorna um número aleatório
                    //menor do que o número de linhas e maior que zero.
                    pos.linha  = x.nextInt(numeroLinhas);

                    //random.nextInt(numeroColunas) - retorna um número aleatório
                    //menor do que o número de colunas e maior que zero.
                    pos.coluna = x.nextInt(numeroColunas);
            }

            //Foi gerada uma posição que não existe na lista então retorna essa
            //posição.
            return pos;
    }

    private boolean posicaoExiste(ArrayList posicoes, Posicao pos)
    {
        for (int i =0; i < posicoes.size(); i++ )
        {
                Posicao temp = (Posicao) posicoes.get(i);
                if ( pos.coluna == temp.coluna && pos.linha == temp.linha )
                    return true;
        }

        return false;
    }



    private class Posicao
    {
        int linha;
        int coluna;

        public Posicao(int linha, int coluna)
        {
            this.linha = linha;
            this.coluna = coluna;
        }
    }

    public void mostrarPeca(int i, int j)
    {
        this.matriz[i][j].mostrar();
    }

}
