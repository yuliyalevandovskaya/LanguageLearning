package dao;

import domain.Words;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Database implements WordsDao {

    private static Connection connection;
    private static Statement statement;
    private static Database instance;

    private Database()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:domain.Words.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }

    public static Database getInstance()
    {
        if(instance == null)
        {
            instance = new Database();
        }
        return instance;
    }

    public void createTable()  {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS Words " +
                    "(idWord INTEGER PRIMARY KEY AUTOINCREMENT, category Varchar(50), english VARCHAR(50), german VARCHAR(50), germanCounter INTEGER, french VARCHAR(50), frenchCounter INTEGER);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertWords(Words words)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Words (idWord, category, english, german, germanCounter, french, frenchCounter) VALUES (NULL, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, words.getCategory());
            preparedStatement.setString(2,words.getEnglish());
            preparedStatement.setString(3, words.getGerman());
            preparedStatement.setInt(4, words.getGermanCounter());
            preparedStatement.setString(5,words.getFrench());
            preparedStatement.setInt(6, words.getFrenchCounter());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Words> selectWords()
    {
        List<Words> wordsList = new LinkedList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Words");
            while(resultSet.next()){
                int idWord = resultSet.getInt("idWord");
                String category = resultSet.getString("category");
                String english = resultSet.getString("english");
                String german = resultSet.getString("german");
                int germanCounter = resultSet.getInt("germanCounter");
                String french = resultSet.getString("french");
                int frenchCounter = resultSet.getInt("frenchCounter");
                wordsList.add(new Words(idWord, category, english, german, germanCounter, french, frenchCounter));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordsList;
    }

    public void updateWords(Words words)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Words SET category=?, english=?, german=?, germanCounter=?, french=?, frenchCounter=? WHERE idWord=?;");
            preparedStatement.setString(1, words.getCategory());
            preparedStatement.setString(2, words.getEnglish());
            preparedStatement.setString(3, words.getGerman());
            preparedStatement.setInt(4, words.getGermanCounter());
            preparedStatement.setString(5, words.getFrench());
            preparedStatement.setInt(6, words.getFrenchCounter());
            preparedStatement.setInt(7, words.getIdWord());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGermanCounter(Words words)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Words SET germanCounter=? WHERE english=?;");
            preparedStatement.setInt(1, words.getGermanCounter());
            preparedStatement.setString(2,words.getEnglish());;
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFrenchCounter(Words words)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Words SET frenchCounter=? WHERE english=?;");
            preparedStatement.setInt(1, words.getFrenchCounter());
            preparedStatement.setString(2,words.getEnglish());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWords(String english)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Words WHERE english = ?;");
            preparedStatement.setString(1, english);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}