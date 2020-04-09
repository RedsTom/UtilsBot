package fr.redstom.utilsbot;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.CommandSpec;
import fr.il_totore.ucp.registration.CommandRegistry;
import fr.il_totore.ucp.registration.PrefixedCommandRegistry;
import fr.redstom.utilsbot.commands.CommandPing;
import fr.redstom.utilsbot.runnables.ActivityRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import scala.Option;
import scala.collection.mutable.ListBuffer;

public class Bot {

    private static final String TOKEN = System.getenv("token");
    private static final CommandRegistry<MessageCreateEvent> registry = new PrefixedCommandRegistry(new ListBuffer(), "/");
    public static DiscordApi api;

    public static void main(String[] args) {

        api = new DiscordApiBuilder().setToken(TOKEN).login().join();

        new ActivityRunnable(5000).start();

        System.out.println(api.createBotInvite());

        final CommandSpec.ImplicitSpec<MessageCreateEvent> pingCommand = new CommandSpec.ImplicitSpec<MessageCreateEvent>("ping").describedAs("Ping the bot").executing(new CommandPing());
        registry.register(pingCommand);

        api.addMessageCreateListener(event -> {
            Option<CommandContext<MessageCreateEvent>> context = registry.parse(event, event.getMessage().getContent()).getContext();
            if (!(context.nonEmpty())) return;
            if (!(context.get() == null)) {
                context.get().execute(event);
            }
        });


    }

}
