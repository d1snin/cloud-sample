package xyz.d1snin.commons.server_responses.model.files;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileData implements Serializable {
  private final byte[] bytes;
  private final String fileName;
}
