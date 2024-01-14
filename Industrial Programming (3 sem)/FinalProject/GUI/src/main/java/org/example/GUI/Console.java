package org.example.gui;

import java.util.Objects;
import java.util.Scanner;

public class Console {
    private static final String KEY = "1234password4321";

    public static void ReadInputFile(Settings InputFileSettings) throws Exception {
        if (InputFileSettings.check_file_zip && InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = Archive.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = Operations.BytesToString(InputFileSettings.byte_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.byte_info = Archive.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = Operations.BytesToString(InputFileSettings.byte_info);

                JsonParser parser = new JsonParser();

                InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.byte_info = Archive.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = Operations.BytesToString(InputFileSettings.byte_info);

                Xml parser = new Xml();

                InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = Encryption.DecryptFileToString(KEY, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptFileToBytes(KEY, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = Encryption.DecryptFileToString(KEY, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = Encryption.DecryptFileToString(KEY, InputFileSettings.file_name);

                Xml parser = new Xml();
                InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_zip) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = Archive.readStringFromInFileFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Archive.readBytesFromFileInFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = Archive.readStringFromInFileFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = Archive.readStringFromInFileFromArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                Xml parser = new Xml();
                InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        } else {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = Operations.readBytesFromFile(InputFileSettings.file_name);
                InputFileSettings.txt_info = Operations.readStringFromFile(InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();
                InputFileSettings.txt_info = parser.ParseFileByParser(InputFileSettings.file_name);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                Xml parser = new Xml();
                InputFileSettings.txt_info = parser.ParseFileByDOM(InputFileSettings.file_name);
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        }
    }

    public static void WriteOutputFile(Settings OutputFileSettings) throws Exception {
        if (OutputFileSettings.check_file_zip && OutputFileSettings.check_file_encrypted) {
            Archive.writeBytesToArchive(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                Archive.writeBytesToArchive(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                OutputFileSettings.byte_info = Operations.StringToBytes(parser.makeJson(OutputFileSettings.txt_info));
                Archive.writeBytesToArchive(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                Xml parser = new Xml();

                OutputFileSettings.byte_info = Operations.StringToBytes(parser.makeXml(OutputFileSettings.txt_info));
                Archive.writeBytesToArchive(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            }
        } else if (OutputFileSettings.check_file_encrypted) {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                Operations.WriteBytesToFile(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                OutputFileSettings.byte_info = Operations.StringToBytes(parser.makeJson(OutputFileSettings.txt_info));
                Operations.WriteBytesToFile(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                Xml parser = new Xml();

                OutputFileSettings.byte_info = Operations.StringToBytes(parser.makeXml(OutputFileSettings.txt_info));
                Operations.WriteBytesToFile(Encryption.EncryptBytes(KEY, OutputFileSettings.byte_info), OutputFileSettings.file_name);
            }
        } else if (OutputFileSettings.check_file_zip) {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                Archive.writeStringToArchive(OutputFileSettings.txt_info, OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();
                Archive.writeStringToArchive(parser.makeJson(OutputFileSettings.txt_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                Xml parser = new Xml();
                Archive.writeStringToArchive(parser.makeXml(OutputFileSettings.txt_info), OutputFileSettings.archive_name, OutputFileSettings.file_name);
            }
        } else {
            if (Objects.equals(OutputFileSettings.file_type, ".txt")) {
                Operations.WriteBytesToFile(OutputFileSettings.byte_info, OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();
                Operations.WriteStringToFile(parser.makeJson(OutputFileSettings.txt_info), OutputFileSettings.file_name);
            } else if (Objects.equals(OutputFileSettings.file_type, ".xml")) {
                Xml parser = new Xml();
                Operations.WriteStringToFile(parser.makeXml(OutputFileSettings.txt_info), OutputFileSettings.file_name);
            }
        }
    }

    public static void ParseMathematicalExpressions(Settings InputFileSettings, Settings OutputFileSettings, String ParseType) throws Exception {
        MatchParserDirector matchParserDirector = new MatchParserDirector();

        switch (ParseType) {
            case "Parse by stack" -> matchParserDirector.SetBuilder(new MatchParserByStack());
            case "Parse by lib" -> matchParserDirector.SetBuilder(new MatchParserByLib());
            case "Parse by regular" -> matchParserDirector.SetBuilder(new Regular());
            default -> throw new IllegalStateException("Unexpected value: " + ParseType);
        }

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);
    }

}