package fr.redstom.utilsbot.commands;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.CommandResult;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import scala.Function2;

import java.awt.*;
import java.util.concurrent.ExecutionException;

public class CommandPing implements Function2<MessageCreateEvent, CommandContext<MessageCreateEvent>, CommandResult> {

    @Override
    public CommandResult apply(MessageCreateEvent event, CommandContext<MessageCreateEvent> context) {

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Pong !")
                .setColor(new Color(0, 206, 201))
                .setFooter("By RedsTom");


        event.getChannel().sendMessage(embedBuilder).thenApply((fn) -> {

            try {
                long millis = event.getMessage().getCreationTimestamp().toEpochMilli();
                long messageSent = event.getApi().getMessageById(fn.getId(), event.getChannel()).get().getCreationTimestamp().toEpochMilli();
                long ping = messageSent - millis;
                embedBuilder.setDescription("Delay : " + ping + "ms");

                event.getApi().getMessageById(fn.getId(), event.getChannel()).get().edit(embedBuilder);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return null;
        });

        return new CommandResult.ImplicitResult(CommandResult.SUCCESS()).whilst("Executing ping command");

    }

}
