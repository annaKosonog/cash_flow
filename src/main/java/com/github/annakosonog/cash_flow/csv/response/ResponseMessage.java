package com.github.annakosonog.cash_flow.csv.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseMessage {

    private String message;
    private String fileDownloadUri;

}
