package source.Additionally;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import source.windows.Game;

import java.util.ArrayList;
import java.util.Random;

public class Spawner {
    private Map map;
    private MyPlayer player;
    private ArrayList<Position> spawnPositions = new ArrayList<>();
    private int delay = 10;

    private ArrayList<Mob> mobs = new ArrayList<>();
    private int threadCycle = 0;
    private int localGameID;

    /**
     * Constructor of the {@link Spawner}
     *
     * Fill {@link #spawnPositions} with {@link Position}s of the Spawners (5)
     * Start {@link Thread} if there are any Spawners
     *
     * @param map {@link Map}
     * @param player {@link MyPlayer}
     */
    public Spawner(Map map, MyPlayer player) {
        this.map = map;
        this.player = player;
        this.localGameID = Game.getGameId();
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getMap()[j][i] == 5) {
                    spawnPositions.add(new Position(j, i));
                }
            }
        }

        if (!spawnPositions.isEmpty()) startThread();
    }

    /**
     * Create and Start {@link Thread} that do the following:
     */
    private void startThread() {
        new Thread(() -> {
            while (player.getAlive() && localGameID == Game.getGameId()) {

                /*
                    Every 30 cycles Create {@link Mob} at Random {@link Spawner} {@link Position}`s
                 */
                threadCycle++;
                if (threadCycle >= delay*2) {
                    Platform.runLater(() -> {
                        Position pos = spawnPositions.get(new Random().nextInt(spawnPositions.size()));
                        Mob mob = new Mob(map, pos);
                        map.getChildren().add(mob);
                        mobs.add(mob);
                    });
                    threadCycle = 0;
                }

                /*
                    Moves all {@link Mob}s and checks if they touch {@link MyPlayer}
                 */
                for (Mob mob : mobs) {
                    ArrayList<Position> usedPositions = new ArrayList<>(AdditionalMethods.getDirections());
                    while (player.getAlive() && localGameID == Game.getGameId()) {
                        Position pos = usedPositions.get(new Random().nextInt(usedPositions.size()));
                        if ((mob.getPosition().getX() + pos.getX()) != -1 && (mob.getPosition().getY() + pos.getY()) != -1) {
                            if ((mob.getPosition().getX() + pos.getX()) != (map.getSize()) && (mob.getPosition().getY() + pos.getY()) != (map.getSize())) {
                                if (map.getMap()[mob.getPosition().getX() + pos.getX()][mob.getPosition().getY() + pos.getY()] != 1) {
                                    mob.move(pos);
                                    break;
                                }
                            }
                        }
                        usedPositions.remove(pos);
                    }

                    double xDiff = player.getPosition().getX() * map.getUnit() + map.getUnit() / 2 - mob.getCenterX();
                    double yDiff = player.getPosition().getY() * map.getUnit() + map.getUnit() / 2 - mob.getCenterY();
                    if (Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)) <= map.getUnit()) {
                        player.setDead();
                        Game.decreaseHearts();
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
