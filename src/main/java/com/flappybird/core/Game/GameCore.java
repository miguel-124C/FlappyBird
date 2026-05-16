package com.flappybird.core.Game;

import java.util.List;

import com.flappybird.core.ConfigCore;
import com.flappybird.core.ICore;
import com.flappybird.interfaces.enums.GameState;
import com.flappybird.interfaces.enums.PlayerState;
import com.flappybird.models.Player;
import com.flappybird.models.World;
import com.flappybird.utils.*;

public abstract class GameCore implements ICore {
    
    public final World world;

    private float timeSpawnPipes = 0;
    private float pipeSpeed = Constants.PIPE_SPEED;
    private float timePerPipes = Constants.TIME_PER_PIPES;
    private final float DISTANCE_PER_PIPES = Constants.DISTANCE_PER_PIPES;
    private final float MAX_PIPE_SPEED = 500;
    private int prevScore = 0;

    public GameCore(World world){
        this.world = world;
    }

    @Override
    public void initialize() {
        world.spawnPipes();
        ConfigCore.getInstance().speedGame = timePerPipes;
    }

    @Override
    public void update(float deltaTime){
        var configCore = ConfigCore.getInstance();
        if (checkGameOver(configCore.getPlayers())) {
            configCore.gameState = GameState.GAME_OVER;
            resetGame();
            return;
        }

        timeSpawnPipes += deltaTime;
        playerFall(configCore.getPlayers(), deltaTime);

        if (timeSpawnPipes >= timePerPipes) {
            world.spawnPipes();
            timeSpawnPipes = 0;
        }

        for (int i = 0; i < world.getPipes().size(); i++) {
            var pipe = world.getPipes().get(i);

            var displacement = pipeSpeed * deltaTime;
            pipe.moveLeft(displacement);
            
            for (var player : configCore.getPlayers()) {
                if (player.state == PlayerState.DEAD)
                    arrastrarPlayer(player, deltaTime, displacement);

                world.checkPipeBehind(pipe, player);
            }

            world.checkPipeOutScreen(pipe, i);
        }
        
        var maxScore = ConfigCore.getInstance().getMaxScore();
        if (pipeSpeed < MAX_PIPE_SPEED && prevScore != maxScore && maxScore > 0) {
            if (maxScore % 20 == 0) {
                pipeSpeed += 10;
                timePerPipes = DISTANCE_PER_PIPES / pipeSpeed;
                ConfigCore.getInstance().speedGame = timePerPipes;
                prevScore = maxScore;
            }
        }
    }

    private void playerFall(List<Player> players, float deltaTime){
        for (var player : players) {
            if (player.state == PlayerState.DEAD || player.state == PlayerState.OUT_SCREEN) continue;
            player.BIRD.fall(deltaTime);

            player.timeEnlapsedFlying += deltaTime;
            if (player.timeEnlapsedFlying >= player.TIME_IN_FLYING) {
                player.BIRD.sprite.changeFrame(0); // Cambia al sprite de caida
                player.timeEnlapsedFlying = 0;
            }
            
            var bird = player.BIRD;
            var dimension = bird.getDimensions();
            var collider = new Rectangle(dimension.X, dimension.Y, dimension.WIDTH * bird.scale.x(), dimension.HEIGHT * bird.scale.y());
            if (world.hasCollision(collider)){
                player.state = PlayerState.DEAD;
                player.BIRD.sprite.changeFrame(2); // Sprite de muerte
            }
        }
    }

    private void arrastrarPlayer(Player player, float deltaTime, float displacement){
        tumbarPlayer(player, deltaTime);
        var dimension = player.BIRD.getDimensions();
        var x = dimension.X - displacement;
        player.BIRD.position = new Vector2(x, dimension.Y);

        var birdOutScreen = dimension.X + dimension.WIDTH < 0;
        if (birdOutScreen)
            player.state = PlayerState.OUT_SCREEN;
    }

    private void tumbarPlayer(Player player, float deltaTime){
        var dimension = player.BIRD.getDimensions();
        
        if (dimension.Y + dimension.HEIGHT >= Constants.screenHeight) return;
        player.BIRD.fall(deltaTime);
    }

    private boolean checkGameOver(List<Player> players){
        for (Player player : players)
            if (player.state == PlayerState.LIVE) return false;

        return true;
    }

    public float getPipeSpeed(){
        return pipeSpeed;
    }

    private void resetGame(){
        timeSpawnPipes = Constants.TIME_PER_PIPES; // Se lo setea con este valor, para que a la siguiente no espere para spawnear pipes
        pipeSpeed = Constants.PIPE_SPEED;
        timePerPipes = Constants.TIME_PER_PIPES;
        prevScore = 0;
    }

}