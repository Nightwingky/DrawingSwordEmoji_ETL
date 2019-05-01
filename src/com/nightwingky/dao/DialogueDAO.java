package com.nightwingky.dao;

import com.nightwingky.bean.StandardDialogueBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DialogueDAO {

    public static Connection connection;

    static {
        connection = DBUtil.getConnection();
    }

    public static void addDialogues(StandardDialogueBean s) throws SQLException {
        String sql = "INSERT INTO tb_dialogue(log_id, variance, average, min, words) " +
                "values (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, s.getLog_id());
        preparedStatement.setDouble(2, s.getVariance());
        preparedStatement.setDouble(3, s.getAverage());
        preparedStatement.setDouble(4, s.getMin());
        preparedStatement.setString(5, s.getWords());

        preparedStatement.execute();
    }
}
