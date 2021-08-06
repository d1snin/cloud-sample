package xyz.d1snin.commons.server_responses.model.files;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserFilesData implements Serializable  {
  private final List<String> files;
}
