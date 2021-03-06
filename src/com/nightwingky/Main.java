package com.nightwingky;

import com.nightwingky.bean.StandardDialogueBean;
import com.nightwingky.dao.DialogueDAO;
import com.nightwingky.jsonData.JsonData;

import java.sql.SQLException;

public class Main {

    private static void procedure() throws SQLException {
        for (StandardDialogueBean s: JsonData.getDialogueList()) {
            DialogueDAO.addDialogues(s);
        }

        DialogueDAO.connection.close();
    }

    public static void main(String[] args) throws SQLException {
	// write your code here
        procedure();
    }
}
