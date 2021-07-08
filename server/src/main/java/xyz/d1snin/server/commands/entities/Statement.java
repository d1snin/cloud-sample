package xyz.d1snin.server.commands.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.d1snin.server.api.events.channel.ChannelMessageReceivedEvent;

import java.util.List;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class Statement {
  private final List<Argument> arguments;
  private final Consumer<ChannelMessageReceivedEvent> consumer;
  @Setter private int length = 0;
}
