package fr.redstom.utilsbot.runnables;

import fr.redstom.utilsbot.Bot;
import org.javacord.api.DiscordApi;

public class ActivityRunnable {

    private DiscordApi api = Bot.api;
    private Thread activityThread;


    public ActivityRunnable(long millis) {

        activityThread = new Thread(() -> {

            while (!activityThread.isInterrupted()) {
                api.updateActivity("/help | " + api.getServers().size() + " servers");
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void start() {
        activityThread.start();
    }

    public void stop() {
        activityThread.interrupt();
    }

    public DiscordApi getApi() {
        return api;
    }

}
