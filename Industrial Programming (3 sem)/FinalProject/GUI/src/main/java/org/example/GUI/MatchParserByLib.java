package org.example.gui;


import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Arrays;
import java.util.Vector;

public class MatchParserByLib extends Builder {
    public double Parse(String s) throws Exception {

        return new ExpressionBuilder(s).build().evaluate();
    }

    @Override
    public void ParseTxt(Settings InputFileSettings, Settings OutputFileSettings) throws Exception {
        String[] lines = InputFileSettings.txt_info.split("\n");
        Vector<String> vec = new Vector<>(Arrays.asList(lines));

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < vec.size(); i++) {
            vec.set(i, vec.get(i).replace(" ", ""));
            vec.set(i, vec.get(i).replace("\n", ""));
            vec.set(i, vec.get(i).replace("\r", ""));
            MatchParserByLib p = new MatchParserByLib();
            s.append(String.valueOf(p.Parse(vec.get(i)))).append("\n");
        }

        OutputFileSettings.txt_info = String.valueOf(s);
        OutputFileSettings.byte_info = Operations.StringToBytes(OutputFileSettings.txt_info);
    }
}