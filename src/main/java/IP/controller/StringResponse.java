package IP.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StringResponse {

    private String message;

    public StringResponse(String s) {
        this.message = s;
    }

    public StringResponse() {

    }

}