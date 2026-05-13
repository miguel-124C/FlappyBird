package com.flappybird.core;

import java.util.List;

import com.flappybird.interfaces.enums.GameState;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.Player;
import com.flappybird.models.World;
import com.flappybird.utils.Constants;
import com.flappybird.utils.Vector2;

public class GameCore implements ICore {
    
    public final World world;    

    private float timeSpawnPipes = 0;
    private final float SPEED_PIPE = 100f;
    public final float TIME_PER_PIPES = 4;

    public GameCore(World world){
        this.world = world;
    }

    @Override
    public void initialize() {
        world.spawnPipes();
    }

    @Override
    public void update(float deltaTime){
        var configCore = ConfigCore.getInstance();
        if (checkGameOver(configCore.getPlayers())) {
            configCore.gameState = GameState.GAME_OVER;
            return;
        }

        timeSpawnPipes += deltaTime;

        playerFall(configCore.getPlayers(), deltaTime);

        if (timeSpawnPipes >= TIME_PER_PIPES) {
            world.spawnPipes();
            timeSpawnPipes = 0;
        }

        for (int i = 0; i < world.getPipes().size(); i++) {
            var pipe = world.getPipes().get(i);

            var distance = SPEED_PIPE * deltaTime;
            pipe.moveLeft(distance);
            
            for (var player : configCore.getPlayers()) {
                if (player.state == PlayerState.DEAD) {
                    arrastrarPlayer(player, deltaTime, distance);
                }
                world.checkPipeBehind(pipe, player);
            }
            
            world.checkPipeOutScreen(pipe, i);
        }
    }
    
    @Override
    public void reset() {

    }

    private void playerFall(List<Player> players, float deltaTime){
        for (var player : players) {
            if (player.state == PlayerState.DEAD) continue;
            player.BIRD.fall(deltaTime);
            
            if (world.hasCollision(player.BIRD.getDimensions()))
                player.state = PlayerState.DEAD;
        }
    }

    private void arrastrarPlayer(Player player, float deltaTime, float distance){
        System.out.println("Arrastrando player");
        
        tumbarPlayer(player, deltaTime);
        var dimension = player.BIRD.getDimensions();
        var x = dimension.X - distance;
        player.BIRD.position = new Vector2(x, dimension.Y);

        var birdOutScreen = dimension.X + dimension.WIDTH < 0;
        if (birdOutScreen)
            player.state = PlayerState.OUT_SCREEN;
    }

    private void tumbarPlayer(Player player, float deltaTime){
        var dimension = player.BIRD.getDimensions();
        
        if (dimension.Y + dimension.HEIGHT >= Constants.screenHeight) {

            return;
        }
        player.BIRD.fall(deltaTime);
    }

    private boolean lastAlive(){
        var players = ConfigCore.getInstance().getPlayers();

        var amountAlive = 0;
        for (Player player : players) {
            if (player.state == PlayerState.LIVE)
                amountAlive++;   
        }

        return amountAlive == 1;
    }

    private boolean checkGameOver(List<Player> players){
        for (Player player : players) {
            if (player.state == PlayerState.LIVE) return false;
        }

        return true;
    }

}