package org.example.gui;

public class MatchParserDirector {
    Builder matchParserBuilder;

    public Builder GetBuilder() {
        return this.matchParserBuilder;
    }

    public void SetBuilder(Builder matchParserBuilder) {
        this.matchParserBuilder = matchParserBuilder;
    }

    void GetAnswer(Settings InputFileSettings, Settings OutputFileSettings) throws Exception {
        matchParserBuilder.ParseTxt(InputFileSettings, OutputFileSettings);
    }
}