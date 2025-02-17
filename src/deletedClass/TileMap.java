package deletedClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class TileMap {
    private static final String[] SUPPORTED_FORMATS = {"png", "jpg"};
    private final String basePath;
    private final int maxLod;
    private final Map<String, BufferedImage> tileCache;
    private final Map<String, Future<?>> pendingLoads;
    private final ExecutorService executor;
    private final int cacheSize;

    public TileMap(String basePath, int maxLod, int cacheSize) {
        this.basePath = basePath;
        this.maxLod = maxLod;
        this.cacheSize = cacheSize;
        this.tileCache = new LinkedHashMap<String, BufferedImage>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > cacheSize;
            }
        };
        this.pendingLoads = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public BufferedImage getTile(double scale, int x, int y) {
        int lod = calculateLod(scale);
        String key = generateKey(lod, x, y);

        synchronized (tileCache) {
            BufferedImage tile = tileCache.get(key);
            if (tile != null) return tile;
        }

        if (!pendingLoads.containsKey(key)) {
            scheduleTileLoad(lod, x, y, key);
        }

        return getFallbackTile(lod, x, y);
    }

    private int calculateLod(double scale) {
        int lod = (int) Math.floor(Math.log(1 / scale) / Math.log(2));
        return Math.min(Math.max(lod, 0), maxLod);
    }

    private void scheduleTileLoad(int lod, int x, int y, String key) {
        Future<?> future = executor.submit(() -> {
            BufferedImage tile = loadTileFromDisk(lod, x, y);
            if (tile != null) {
                synchronized (tileCache) {
                    tileCache.put(key, tile);
                }
            }
            pendingLoads.remove(key);
        });
        
        pendingLoads.put(key, future);
    }

    private BufferedImage loadTileFromDisk(int lod, int x, int y) {
        for (String format : SUPPORTED_FORMATS) {
            String path = String.format("%s/LOD%d/tile_%d_%d.%s", 
                basePath, lod, x, y, format);
            
            try {
                BufferedImage image = ImageIO.read(new File(path));
                if (image != null) return image;
            } catch (IOException e) {
                System.err.println("Error loading tile: " + path);
            }
        }
        return null;
    }

    private BufferedImage getFallbackTile(int currentLod, int x, int y) {
        for (int lod = currentLod + 1; lod <= maxLod; lod++) {
            String key = generateKey(lod, x, y);
            synchronized (tileCache) {
                BufferedImage tile = tileCache.get(key);
                if (tile != null) return tile;
            }
        }
        return null;
    }

    private String generateKey(int lod, int x, int y) {
        return String.format("LOD%d_%d_%d", lod, x, y);
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}