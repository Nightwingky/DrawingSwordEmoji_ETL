package com.nightwingky.jsonData;

import com.nightwingky.bean.StandardDialogueVO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonData {

    static {
        dialogueList = new ArrayList<>();

        try {
            fileReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<StandardDialogueVO> dialogueList;

    public static void fileReader() throws IOException {
        FileReader fileReader = new FileReader("dialogue.txt");
        BufferedReader br = new BufferedReader(fileReader);

        String jsonString = br.readLine();

        while (jsonString != null) {

            List<StandardDialogueVO> list = JsonConverter.getStandardDialogue(jsonString);

            for (StandardDialogueVO s : list) {
                dialogueList.add(s);
            }
            jsonString = br.readLine();
        }

        fileReader.close();
    }

    public static List<StandardDialogueVO> getDialogueList() {
        return dialogueList;
    }
}
