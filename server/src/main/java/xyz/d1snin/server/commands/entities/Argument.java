package xyz.d1snin.server.commands.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Argument {
  private final String usage;
  private final String type;
  private final boolean valueRequired;
  private final boolean variableLength;
  @Setter private String value;
}
