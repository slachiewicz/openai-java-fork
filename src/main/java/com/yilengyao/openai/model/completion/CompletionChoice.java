package com.yilengyao.openai.model.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompletionChoice {
  String text;
  Integer index;
  LogProbResult logprobs;
  @JsonProperty("finish_reason")
  String finishReason;

  public com.yilengyao.openai.graphql.generated.types.CompletionChoice toGraphQl() {
    var output = com.yilengyao.openai.graphql.generated.types.CompletionChoice.newBuilder();
    if (this.getText() != null) {
      output.text(this.getText());
    }
    if (this.getIndex() != null) {
      output.index(this.getIndex());
    }
    if (this.getLogprobs() != null) {
      output.logprobs(this.getLogprobs().toGraphQl());
    }
    if (this.getFinishReason() != null) {
      output.finish_reason(this.getFinishReason());
    }
    return output.build();
  }

}