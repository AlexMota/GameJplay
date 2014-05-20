/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cenario_ex2;

import java.awt.Point;
import java.util.Vector;
import jplay.Keyboard;
import jplay.Scene;
import jplay.TileInfo;
import jplay.Window;

/**
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */

public class Cenario
{
    private Window window;
     private Keyboard keyboard;
     private Scene scene;
     private Player player;

     public Cenario()
     {
             window = new Window(800,600);
             keyboard = window.getKeyboard();
             scene = new Scene();
             scene.loadFromFile("scene.scn");
             scene.setDrawStartPos(15, 30);

             player = new Player();
             player.x = 454;
             player.y =  140;
             scene.addOverlay(player);
     }

     public void run()
     {
             boolean loop = true;
             while(loop)
             {
                 draw();
                 //controlarCaminho();
                 player.move(keyboard);

                 if (keyboard.keyDown(Keyboard.ESCAPE_KEY))
                     loop = false;
                 window.delay(10);
             }
             window.exit();
     }

     public void draw()
     {
             scene.draw();
             window.update();
     }

     boolean controlarCaminho()
     {
             //Posicao min é a posição (x,y) do player
             //Posicao max é a posição (x + largura, y + altura) do player
             Point playerMin = new Point ((int)player.x, (int)player.y);
             Point playerMax = new Point((int)(player.x + player.width), (int)(player.y + player.height));

             //Retorna as imagens que estiverem na mesma área do player (x,y), (x + largura, y + altura)
             Vector tiles = scene.getTilesFromRect(playerMin, playerMax);

             //para todos os tiles
             for(int i = 0 ; i < tiles.size() ; i++)
             {
                 TileInfo tile = (TileInfo)tiles.elementAt(i);

                 //se o tile é parede e o player colidiu com ele
                 if((tile.id == Constante.TILE_WALL) && player.collided(tile))
                 {
                         //se o player está se movendo na diagonal
                         if (player.moveuNaHorizontal())
                         {
                                 //o player está atrás da parede?
                                 if(player.x  <= tile.x -1)
                                     player.x = tile.x - player.width;//Reposiciona o player
                                 else
                                     //o player está na frente da parede
                                     player.x = tile.x + tile.width;//Reposiciona o player
                         }
                         else
                         {       //o player está abaixo da parede?
                                 if(player.y >= tile.y  + tile.height -1)
                                     player.y = tile.y + tile.height;//Reposiciona o player
                                 else
                                     //o player está acima da parede
                                     player.y = tile.y - player.height ;//Reposiciona o player
                         }
                         return false;
                 }
                 else
                     //se o player econtrou a estrela
                     if(tile.id == Constante.TILE_STAR)
                         return true;
             }
             return  false;
     }
 }