package org.example;

import java.util.Objects;
import java.util.Scanner;

public class Console {
    private static final String KEY = "1234password4321";

    public static void SetInputFileSettings(Scanner input, Settings InputFileSettings) throws Exception {
        String check;

        System.out.println("Is the input file archived? (Yes/no) ");
        check = input.nextLine();
        InputFileSettings.check_file_zip = check.equals("Yes");

        System.out.println("Is the input file encrypted? (Yes/no) ");
        check = input.nextLine();
        InputFileSettings.check_file_encrypted = check.equals("Yes");


        System.out.println("Choose input file type (.txt/.xml/.json) ");
        InputFileSettings.file_type = input.nextLine();

        if (!InputFileSettings.file_type.equals(".txt") && !InputFileSettings.file_type.equals(".json") && !InputFileSettings.file_type.equals(".xml")) {
            throw new Exception("Error file type");
        }

        if (InputFileSettings.check_file_zip) {
            System.out.println("Enter input archive name :");
            InputFileSettings.archive_name = input.nextLine();
        }

        System.out.println("Enter input file name :");
        InputFileSettings.file_name = input.nextLine() + InputFileSettings.file_type;
    }

    public static void SetOutputFileSettings(Scanner input, Settings OutputFileSettings) throws Exception {
        System.out.println("\nIs the output file archived? (Yes/no) ");
        String check = input.nextLine();
        OutputFileSettings.check_file_zip = check.equals("Yes");

        System.out.println("Is the output file encoded? (Yes/no) ");
        check = input.nextLine();
        OutputFileSettings.check_file_encrypted = check.equals("Yes");

        System.out.println("Choose output file type (.txt/.xml/.json) ");
        OutputFileSettings.file_type = input.nextLine();

        if (!OutputFileSettings.file_type.equals(".txt") && !OutputFileSettings.file_type.equals(".json") && !OutputFileSettings.file_type.equals(".xml")) {
            throw new Exception("Error file type");
        }

        if (OutputFileSettings.check_file_zip) {
            System.out.println("Enter output archive name :");
            OutputFileSettings.archive_name = input.nextLine();
        }

        System.out.println("Enter output file name : ");
        OutputFileSettings.file_name = input.nextLine() + OutputFileSettings.file_type;
    }

    public static void ReadInputFile(Settings InputFileSettings, Scanner input) throws Exception {
        boolean json_choice = true;
        boolean xml_choice = true;

        if (InputFileSettings.file_type.equals(".json")) {
            System.out.println("Choose parser for the json file: by json parser(0), or by reading line by line(1) ");
            String choice = input.nextLine();

            switch (choice) {
                case "0" -> json_choice = true;
                case "1" -> json_choice = false;
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
        }

        if (InputFileSettings.file_type.equals(".xml")) {
            System.out.println("Chose parser for the xml file: by DOM(0), or by reading line by line(1) ");
            String choice = input.nextLine();

            switch (choice) {
                case "0" -> xml_choice = true;
                case "1" -> xml_choice = false;
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
        }

        if (InputFileSettings.check_file_zip && InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = Archive.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = Operations.BytesToString(InputFileSettings.byte_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.byte_info = Archive.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = Operations.BytesToString(InputFileSettings.byte_info);

                JsonParser parser = new JsonParser();
                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }

                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.byte_info = Archive.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptBytes(KEY, InputFileSettings.byte_info);
                InputFileSettings.txt_info = Operations.BytesToString(InputFileSettings.byte_info);

                Xml parser = new Xml();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }

                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_encrypted) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = Encryption.DecryptFileToString(KEY, InputFileSettings.file_name);
                InputFileSettings.byte_info = Encryption.DecryptFileToBytes(KEY, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = Encryption.DecryptFileToString(KEY, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = Encryption.DecryptFileToString(KEY, InputFileSettings.file_name);

                Xml parser = new Xml();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        } else if (InputFileSettings.check_file_zip) {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.txt_info = Archive.readStringFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
                InputFileSettings.byte_info = Archive.readBytesFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                InputFileSettings.txt_info = Archive.readStringFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                JsonParser parser = new JsonParser();
                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByParser(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                InputFileSettings.txt_info = Archive.readStringFromFileInArchive(InputFileSettings.archive_name, InputFileSettings.file_name);

                Xml parser = new Xml();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseStringByDOM(InputFileSettings.txt_info);
                } else {
                    InputFileSettings.txt_info = parser.ParseStringByReadingLineByLine(InputFileSettings.txt_info);
                }
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        } else {
            if (Objects.equals(InputFileSettings.file_type, ".txt")) {
                InputFileSettings.byte_info = Operations.readBytesFromFile(InputFileSettings.file_name);
                InputFileSettings.txt_info = Operations.readStringFromFile(InputFileSettings.file_name);
            } else if (Objects.equals(InputFileSettings.file_type, ".json")) {
                JsonParser parser = new JsonParser();

                if (json_choice) {
                    InputFileSettings.txt_info = parser.ParseFileByParser(InputFileSettings.file_name);
                } else {
                    InputFileSettings.txt_info = parser.ParseFileByReadingLineByLine(InputFileSettings.file_name);
                }
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            } else if (Objects.equals(InputFileSettings.file_type, ".xml")) {
                Xml parser = new Xml();
                if (xml_choice) {
                    InputFileSettings.txt_info = parser.ParseFileByDOM(InputFileSettings.file_name);
                } else {
                    InputFileSettings.txt_info = parser.ParseFileByReadingLineByLine(InputFileSettings.file_name);
                }
                InputFileSettings.byte_info = Operations.StringToBytes(InputFileSettings.txt_info);
            }
        }
    }

    public static void WriteOutputFile(Settings OutputFileSettings, Scanner input) throws Exception {
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

    public static void ParseMathematicalExpressions(Settings InputFileSettings, Settings OutputFileSettings, Scanner input) throws Exception {
        ParserDirector matchParserDirector = new ParserDirector();

        System.out.println("Select a match parser : ParserByStack(0), ParserByLib(1),  ParserByRegular(2) ");
        String choice = input.nextLine();

        switch (choice) {
            case "0" -> matchParserDirector.SetBuilder(new ParserByStack());
            case "1" -> matchParserDirector.SetBuilder(new ParserByLib());
            case "2" -> matchParserDirector.SetBuilder(new Regular());
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);
    }

}