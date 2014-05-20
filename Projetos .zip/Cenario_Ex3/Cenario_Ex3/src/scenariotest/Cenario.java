/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scenariotest;

/**
 *
 * @author David
 */

import java.awt.Point;
import java.util.Vector;
import jplay.GameObject;
import jplay.Keyboard;
import jplay.Scene;
import jplay.TileInfo;
import jplay.Window;

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
        scene.setDrawStartPos(0, 0);

        player = new Player();
        player.x = 0;
        player.y = 0;
        player.setFloor(window.getHeight());
        scene.addOverlay(player);
    }

    public void run()
    {
        boolean loop = true;
        while(loop)
        {
            player.move(keyboard, window);
            controlarCaminho();
            draw();

            if (keyboard.keyDown(Keyboard.ESCAPE_KEY))
                loop = false;
            window.delay(10);
        }
        window.exit();
    }

    public void draw()
    {
            scene.moveScene(player);
            player.x += scene.getXOffset();
            player.y += scene.getYOffset();
            //scene.draw();
            window.update();
    }

    void controlarCaminho()
    {
        //Posicao min é a posição (x,y) do player
        //Posicao max é a posição (x + largura, y + altura) do player
        Point playerMin = new Point ((int)player.x, (int)player.y);
        Point playerMax = new Point((int)(player.x + player.width), (int)(player.y + player.height));

        //Retorna as imagens que estiverem na mesma área do player (x,y), (x + largura, y + altura)
        Vector tiles = scene.getTilesFromPosition(playerMin, playerMax);
        player.setFloor(window.getHeight());
        //para todos os tiles
        for(int i = 0 ; i < tiles.size() ; i++)
        {
            TileInfo tile = (TileInfo)tiles.elementAt(i);

            //se o tile é parede e o player colidiu com ele
            if((tile.id == Constante.TILE_WALL) && player.collided(tile))
            {
                //o player está abaixo da parede?
                if (VerticalCollision (player, tile)){
                    if(tile.y + tile.height + player.getVelocityY() - player.getMovementSpeed() - 1 < player.y){
                        player.y = tile.y + tile.height;//Reposiciona o player
                        player.setVelocityY(0.0);
                    }
                    else if (tile.y > player.y + player.height - player.getVelocityY() - player.getMovementSpeed() - 1){
                        //o player está acima da parede
                        player.setVelocityY(0.0);
                        player.setFloor((int)tile.y);
                        player.y = tile.y - player.height ;//Reposiciona o player
                    }
                }
                //se o player está se movendo na horizontal
                if((player.moveuNaHorizontal()) && (HorizontalCollision(player, tile))){
                    //o player está atrás da parede?
                    if(tile.x  > player.x + player.width - player.getMovementSpeed() - 1)
                        player.x = tile.x - player.width;//Reposiciona o player
                    else
                        //o player está na frente da parede
                        player.x = tile.x + tile.width;//Reposiciona o player
                }
            }
        }
    }

    boolean VerticalCollision (GameObject object, TileInfo tile){
        if (tile.x + tile.width <= object.x)
            return false;
        if (object.x + object.width <= tile.x)
            return false;
        return true;
    }

    boolean HorizontalCollision (GameObject object, TileInfo tile){
        if (tile.y + tile.height <= object.y)
            return false;
        if (object.y + object.height <= tile.y)
            return false;
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cenario cen = new Cenario();
        cen.run();
    }
}