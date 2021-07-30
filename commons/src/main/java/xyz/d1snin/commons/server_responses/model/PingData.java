package xyz.d1snin.commons.server_responses.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PingData implements Serializable {
    private final String value;
}
